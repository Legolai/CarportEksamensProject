package dk.cphbusiness.dat.carporteksamensproject.model.persistence;

import dk.cphbusiness.dat.carporteksamensproject.interfaces.FunctionWithThrows;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Column;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Entity;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.GeneratedValue;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntities;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class EntityManager {

    private final ConnectionPool connectionPool;

    public EntityManager(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private <R> R makeConnection(FunctionWithThrows<Connection, R> query) throws DatabaseException {
        try (Connection connection = connectionPool.getConnection()) {
            return query.apply(connection);
        }
        catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private <T, R> R newQueryUpdate(Connection connection, String sql, EntityData<?> entityData, T entity, FunctionWithThrows<ResultData<T>, R> queryHandler) throws DatabaseException {
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            List<Field> fields = entityData.getFields();
            for (int i = 1; i <= fields.size(); ++i) {
                String fieldName = fields.get(i - 1).getName();
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Object value = entityData.getEntityClass().getDeclaredMethod(methodName).invoke(entity);
                if (fields.get(i - 1).getType().isEnum()) {
                    value = ((Enum<?>) value).name();
                }
                ps.setObject(i, value);
            }
            int rowsAffected = ps.executeUpdate();
            ResultData<T> managerResult = new ResultData<>(rowsAffected, ps.getGeneratedKeys(), entityData, entity);
            return queryHandler.apply(managerResult);
        }
        catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private <R> R newQuery(Connection connection, String sql, FunctionWithThrows<ResultSet, R> queryHandler, Object... params) throws DatabaseException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 1; i <= params.length; ++i) {
                ps.setObject(i, params[i - 1]);
            }
            return queryHandler.apply(ps.executeQuery());
        }
        catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public <T> boolean update(Class<T> entityClass, T entity) throws DatabaseException {
        EntityData<T> entityData = new EntityData<>(entityClass);

        entityData.getFields().remove(entityData.getFieldForId());
        String sqlColumns = entityData.getFields().stream().map(f -> f.getAnnotation(Column.class).value()).collect(Collectors.joining(", "));
        entityData.getFields().add(entityData.getFieldForId());

        String idColumn = entityData.getFieldForId().getAnnotation(Column.class).value();
        String options = sqlColumns.replaceAll("\\w+", "$0 = ?");

        String sql = "UPDATE " + entityData.getTableName() + " SET " + options + " WHERE " + idColumn + " = ?;";

        FunctionWithThrows<ResultData<T>, Boolean> handler = (managerResult -> managerResult.getRowsAffected() == 1);
        return makeConnection(conn -> newQueryUpdate(conn, sql, entityData, entity, handler));
    }

    public <T> T insert(Class<T> entityClass, T entity) throws DatabaseException {
        if (entityClass.isAnnotationPresent(Entity.class)) {
            EntityData<T> entityData = new EntityData<>(entityClass);
            return insertEntity(entityData, entity);
        } else if (entityClass.isAnnotationPresent(JoinedEntities.class)) {
            JoinedEntityData<T> joinedEntityData = new JoinedEntityData<>(entityClass);
            return insertJoinedEntity(joinedEntityData, entity);
        }
        throw new DatabaseException("Class was not annotated as an entity or joinedEntity!");
    }

    private <T> T insertJoinedEntity(JoinedEntityData<?> joinedEntityData, T entity) throws DatabaseException {
        try {
            for (Field field : joinedEntityData.getFields()) {
                Object fieldEntity = joinedEntityData.getEntityClass().getDeclaredMethod(field.getName()).invoke(entity);
                if (field.getType().isAnnotationPresent(JoinedEntities.class)) {
                    insertJoinedEntity(new JoinedEntityData<>(field.getType()), fieldEntity);
                } else {
                    insertEntity(new EntityData<>(field.getType()), fieldEntity);
                }
            }
            return entity;
        }
        catch (InvocationTargetException | DatabaseException | IllegalAccessException | NoSuchMethodException ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    private <T> T insertEntity(EntityData<?> entityData, T entity) throws DatabaseException {
        if (entityData.getFieldForId().isAnnotationPresent(GeneratedValue.class)) {
            entityData.getFields().remove(entityData.getFieldForId());
        }

        String sqlColumns = entityData.getFields().stream().map(f -> f.getAnnotation(Column.class).value()).collect(Collectors.joining(", "));
        String options = "?".repeat(entityData.getFields().size()).replaceAll(".(?=.)", "$0, ");

        String sql = "INSERT INTO " + entityData.getTableName() + " (" + sqlColumns + ") VALUES (" + options + ")";

        Map<String, Object> properties = entityData.getFields().stream().collect(Collectors.toMap(Field::getName, field -> {
            try {
                return entityData.getEntityClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)).invoke(entity);
            }
            catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }));
        String findSqlColumns = String.join(" AND ", properties.keySet());
        String findOptions = sqlColumns.replaceAll("\\b(?!AND\\b)\\w+", "$0 LIKE ?");
        String findSql = "SELECT EXITS(SELECT * FROM " + entityData.getTableName() + " WHERE " + options + ") LIMIT 1";

        FunctionWithThrows<ResultData<T>, T> handler = this::extractIdFromQueryUpdate;
        return makeConnection(conn ->

                newQueryUpdate(conn, sql, entityData, entity, handler));
    }

    public <T> List<T> getAll(Class<T> entityClass) throws DatabaseException {
        if (entityClass.isAnnotationPresent(Entity.class)) {
            return getAllEntity(entityClass);
        } else if (entityClass.isAnnotationPresent(JoinedEntities.class)) {
            return getAllJoinedEntities(entityClass);
        }
        throw new DatabaseException("Class was not annotated as an entity or joinedEntity!");
    }

    private <T> List<T> getAllJoinedEntities(Class<T> entityClass) throws DatabaseException {
        JoinedEntityData<T> joinedEntityData = new JoinedEntityData<>(entityClass);

        String join = joinedEntityData.getJoinMap().entrySet().stream().reduce("", (curr, entry) -> curr + String.format(" INNER JOIN %s USING(%s)", entry.getKey(), entry.getValue()), String::concat);

        String sql = "SELECT * FROM " + joinedEntityData.getMainTable() + join;

        FunctionWithThrows<ResultSet, List<T>> handler = (resultSet -> {
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(createJoinedEntity(resultSet, joinedEntityData));
            }
            return list;
        });

        return makeConnection(conn -> newQuery(conn, sql, handler));
    }

    private <T> List<T> getAllEntity(Class<T> entityClass) throws DatabaseException {
        EntityData<T> entityData = new EntityData<>(entityClass);

        String sql = "SELECT * FROM " + entityData.getTableName();

        FunctionWithThrows<ResultSet, List<T>> handler = (resultSet -> {
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(createEntity(resultSet, entityData));
            }
            return list;
        });
        return makeConnection(conn -> newQuery(conn, sql, handler));
    }

    public <T> Optional<T> find(Class<T> entityClass, Object primaryKey) throws DatabaseException {
        EntityData<T> entityData = new EntityData<>(entityClass);

        String idColumn = entityData.getFieldForId().getAnnotation(Column.class).value();

        String sql = "SELECT * FROM " + entityData.getTableName() + " WHERE " + idColumn + " = ?";

        FunctionWithThrows<ResultSet, T> handler = resultSet -> {
            if (!resultSet.next()) return null;
            return createEntity(resultSet, entityData);
        };
        return Optional.of(makeConnection(connection -> newQuery(connection, sql, handler, primaryKey)));
    }

    public <T> Optional<List<T>> findAll(Class<T> entityClass, Map<String, Object> properties) throws DatabaseException {
        if (entityClass.isAnnotationPresent(Entity.class)) {
            return findEntities(entityClass, properties);
        } else if (entityClass.isAnnotationPresent(JoinedEntities.class)) {
            return findJoinedEntities(entityClass, properties);
        }
        throw new DatabaseException("Class was not annotated as an entity or joinedEntity!");
    }

    private <T> Optional<List<T>> findJoinedEntities(Class<T> entityClass, Map<String, Object> properties) throws DatabaseException {
        JoinedEntityData<T> joinedEntityData = new JoinedEntityData<>(entityClass);

        String sqlColumns = String.join(" AND ", properties.keySet());
        String options = sqlColumns.replaceAll("\\b(?!AND\\b)\\w+", "$0 = ?");
        String join = joinedEntityData.getJoinMap().entrySet().stream().reduce("", (curr, entry) -> curr + String.format(" INNER JOIN %s USING(%s)", entry.getKey(), entry.getValue()), String::concat);

        String sql = "SELECT * FROM " + joinedEntityData.getMainTable() + join + " WHERE " + options;
        System.out.println(sql);
        FunctionWithThrows<ResultSet, List<T>> handler = resultSet -> {
            List<T> list = new ArrayList<>();
            while (resultSet.next())
                list.add(createJoinedEntity(resultSet, joinedEntityData));
            return list;
        };
        return Optional.of(makeConnection(connection -> newQuery(connection, sql, handler, properties.values().toArray())));
    }

    private <T> Optional<List<T>> findEntities(Class<T> entityClass, Map<String, Object> properties) throws DatabaseException {
        EntityData<T> entityData = new EntityData<>(entityClass);

        String sqlColumns = String.join(" AND ", properties.keySet());
        String options = sqlColumns.replaceAll("\\b(?!AND\\b)\\w+", "$0 = ?");

        String sql = "SELECT * FROM " + entityData.getTableName() + " WHERE " + options;

        FunctionWithThrows<ResultSet, List<T>> handler = resultSet -> {
            List<T> list = new ArrayList<>();
            while (resultSet.next())
                list.add(createEntity(resultSet, entityData));
            return list;
        };
        return Optional.of(makeConnection(connection -> newQuery(connection, sql, handler, properties.values().toArray())));
    }

    private <T> T extractIdFromQueryUpdate(ResultData<T> managerResult) throws DatabaseException {
        Field fieldForId = managerResult.getEntityData().getFieldForId();
        T newEntity = managerResult.getEntity();
        try {
            if (managerResult.getRowsAffected() == 1 && fieldForId.isAnnotationPresent(GeneratedValue.class)) {
                managerResult.getResultSet().next();
                String fieldName = managerResult.getEntityData().getFieldForId().getName();
                String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Object value = managerResult.getResultSet().getObject(1);
                if (fieldForId.getType().equals(Integer.TYPE)) {
                    managerResult.getEntityData().getEntityClass().getMethod(methodName, fieldForId.getType()).invoke(newEntity, Integer.parseInt(value.toString()));
                } else {
                    managerResult.getEntityData().getEntityClass().getMethod(methodName, fieldForId.getType()).invoke(newEntity, value);
                }
            }
        }
        catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            throw new DatabaseException(ex.getMessage());
        }

        return newEntity;
    }

    private <T> T createEntity(ResultSet rs, EntityData<T> entityData) throws DatabaseException {
        List<Object> constArgs = new LinkedList<>();
        try {
            for (Field field : entityData.getFields()) {
                Column col = field.getAnnotation(Column.class);
                if (col != null) {
                    String name = col.value();
                    Object value = rs.getObject(name);
                    if (field.getType().isEnum()) {
                        value = Enum.valueOf(field.getType().asSubclass(Enum.class), (String) value);
                    }
                    constArgs.add(value);
                }
            }
            return entityData.getEntityClass().getConstructor(entityData.getConstructorEmp()).newInstance(constArgs.toArray());
        }
        catch (SQLException | NoSuchMethodException | InvocationTargetException | InstantiationException |
               IllegalAccessException ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    private <T> T createJoinedEntity(ResultSet rs, JoinedEntityData<T> joinedEntityData) throws DatabaseException {
        List<Object> constArgs = new LinkedList<>();
        try {
            for (Field field : joinedEntityData.getFields()) {
                if (field.getType().isAnnotationPresent(JoinedEntities.class)) {
                    constArgs.add(createJoinedEntity(rs, new JoinedEntityData<>(field.getType())));
                } else {
                    constArgs.add(createEntity(rs, new EntityData<>(field.getType())));
                }
            }
            return joinedEntityData.getEntityClass().getConstructor(joinedEntityData.getConstructorEmp()).newInstance(constArgs.toArray());
        }
        catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
               IllegalAccessException | DatabaseException ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

}
