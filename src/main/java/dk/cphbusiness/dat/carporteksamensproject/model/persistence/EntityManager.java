package dk.cphbusiness.dat.carporteksamensproject.model.persistence;

import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.FunctionWithThrows;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;
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
            return insertEntity(new EntityData<>(entityClass), entity);
        } else if (entityClass.isAnnotationPresent(JoinedEntity.class)) {
            return insertJoinedEntity(new JoinedEntityData<>(entityClass), entity);
        }
        throw new DatabaseException("Class was not annotated as an entity or joinedEntity!");
    }

    private <T> T insertJoinedEntity(JoinedEntityData<T> joinedEntityData, Object entity) throws DatabaseException {
        List<Object> constArgs = new LinkedList<>();
        try {
            for (Iterator<Field> it = joinedEntityData.getFields().descendingIterator(); it.hasNext(); ) {
                Field field = it.next();
                Object fieldEntity = joinedEntityData.getEntityClass().getDeclaredMethod(field.getName()).invoke(entity);
                if (field.getType().isAnnotationPresent(JoinedEntity.class)) {
                    constArgs.add(insertJoinedEntity(new JoinedEntityData<>(field.getType()), fieldEntity));
                } else if (fieldEntity instanceof List<?> batch) {
                    constArgs.add(insertBatch(batch));
                } else {
                    Object baseEntity = insertCheckedEntity(new EntityData<>(field.getType()), fieldEntity);
                    if (entity instanceof IForeignKey updater) {
                        updater.updateForeignKey(baseEntity);
                    }
                    constArgs.add(baseEntity);
                }
            }
            Collections.reverse(constArgs);
            return joinedEntityData.getEntityClass().getConstructor(joinedEntityData.getConstructorEmp()).newInstance(constArgs.toArray());
        }
        catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
               IllegalAccessException | DatabaseException ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    private <T> List<T> insertBatch(Class<T> entityClass, List<T> batch) throws DatabaseException {
        FunctionWithThrows<Connection, List<T>> handler = connection -> {
            try (PreparedStatement ps = connection.prepareStatement("", Statement.RETURN_GENERATED_KEYS)) {
                insertEntityBatch(ps, batch);
            }
            catch (SQLException ex) {
                throw new DatabaseException(ex.getMessage());
            }
            return null;
        };

        if (entityClass.isAnnotationPresent(JoinedEntity.class)) {
            List<Object> subList = new ArrayList<>();
            for (T o : batch) {
                try {
                    Field field = o.getClass().getFields()[0];
                    String methodName = "get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
                    subList.add(o.getClass().getMethod(methodName).invoke(o));
                }
                catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
                    throw new DatabaseException(ex.getMessage());
                }
            }
            return insertJoinedEntityBatch(new JoinedEntityData<>(batch.get(0).getClass()), subList);
        } else if (entityClass.isAnnotationPresent(Entity.class)) {
            return makeConnection(handler);
        } else {
            throw new DatabaseException("Batch was not a list of joined entities or entities!");
        }
    }

    private <T> List<T> insertJoinedEntityBatch(JoinedEntityData<T> joinedEntityData, List<Object> batch) throws DatabaseException {
        subList = insertBatch(subList);
        List<T> list = new ArrayList<>();

        if (batch.size() != subList.size()){
            throw new DatabaseException("Batch size does not match insert batch size!");
        }


        for (int i = 0; i < batch.size(); i++) {
            T t = batch.get(i);
            JoinedEntityData<T> entityData = new JoinedEntityData<>(t.getClass());
            Object info = subList.get(i);

        }


        return list;
    }

    private <T> void insertEntityBatch(PreparedStatement ps, List<T> batch) throws DatabaseException, SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (batch.isEmpty()) throw new DatabaseException("batch is empty!");

        EntityData<?> entityData = new EntityData<>(batch.get(0).getClass());
        List<T> list = new ArrayList<>(batch.size());
        Field[] fields;
        final int batchSize = 1000;
        int count = 0;
        for (T t : batch) {
            fields = t.getClass().getDeclaredFields();
            for (int i = 1; i <= fields.length; ++i) {
                String fieldName = fields[i - 1].getName();
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Object value = t.getClass().getDeclaredMethod(methodName).invoke(t);
                if (fields[i - 1].getType().isEnum()) {
                    value = ((Enum<?>) value).name();
                }
                ps.setObject(i, value);
            }
            ps.addBatch();
            if (++count % batchSize == 0) {
                int[] rowStatus = ps.executeBatch();
                int rowStatusCount = 0;
                ResultSet resultSet = ps.getGeneratedKeys();
                while (resultSet.next() && rowStatus[rowStatusCount] >= Statement.SUCCESS_NO_INFO) {
                    list.add(extractIdFromQueryUpdate(new ResultData<>(1, resultSet, entityData, batch.get(list.size() - 1))));
                }
            }
        }
        int[] rowStatus = ps.executeBatch();
        int rowStatusCount = 0;
        ResultSet resultSet = ps.getGeneratedKeys();
        while (resultSet.next() && rowStatus[rowStatusCount] >= Statement.SUCCESS_NO_INFO) {
            list.add(extractIdFromQueryUpdate(new ResultData<>(1, resultSet, entityData, batch.get(list.size() - 1))));
        }

    }


    private <T> T insertCheckedEntity(EntityData<T> entityData, Object entity) throws DatabaseException {
        Map<String, Object> properties = new LinkedHashMap<>();

        for (Field field : entityData.getFields()) {
            try {
                String name = field.getName();
                Object value = entityData.getEntityClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)).invoke(entity);
                if (value == null || (name.equals("id") && value.equals(0))) continue;
                properties.put(field.getAnnotation(Column.class).value(), value);
            }
            catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                throw new DatabaseException(ex.getMessage());
            }
        }

        Optional<List<T>> objects = findEntities(entityData.getEntityClass(), properties);
        if (objects.isPresent()) {
            return objects.get().get(0);
        }
        return insertEntity(entityData, entityData.getEntityClass().cast(entity));
    }

    private <T> T insertEntity(EntityData<?> entityData, T entity) throws DatabaseException {
        if (entityData.getFieldForId().isAnnotationPresent(GeneratedValue.class)) {
            entityData.getFields().remove(entityData.getFieldForId());
        }

        String sqlColumns = entityData.getFields().stream().map(f -> f.getAnnotation(Column.class).value())
                .collect(Collectors.joining(", "));
        String options = "?".repeat(entityData.getFields().size()).replaceAll(".(?=.)", "$0, ");

        String sql = "INSERT INTO " + entityData.getTableName() + " (" + sqlColumns + ") VALUES (" + options + ")";

        FunctionWithThrows<ResultData<T>, T> handler = this::extractIdFromQueryUpdate;
        return makeConnection(conn -> newQueryUpdate(conn, sql, entityData, entity, handler));
    }

    public <T> List<T> getAll(Class<T> entityClass) throws DatabaseException {
        if (entityClass.isAnnotationPresent(Entity.class)) {
            return getAllEntity(entityClass);
        } else if (entityClass.isAnnotationPresent(JoinedEntity.class)) {
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
            return list.isEmpty() ? null : list;
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
        return Optional.ofNullable(makeConnection(connection -> newQuery(connection, sql, handler, primaryKey)));
    }

    public <T> Optional<List<T>> findAll(Class<T> entityClass, Map<String, Object> properties) throws DatabaseException {
        if (entityClass.isAnnotationPresent(Entity.class)) {
            return findEntities(entityClass, properties);
        } else if (entityClass.isAnnotationPresent(JoinedEntity.class)) {
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

        FunctionWithThrows<ResultSet, List<T>> handler = resultSet -> {
            List<T> list = new ArrayList<>();
            while (resultSet.next())
                list.add(createJoinedEntity(resultSet, joinedEntityData));
            return list.isEmpty() ? null : list;
        };
        return Optional.ofNullable(makeConnection(connection -> newQuery(connection, sql, handler, properties.values().toArray())));
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
            return list.isEmpty() ? null : list;
        };
        return Optional.ofNullable(makeConnection(connection -> newQuery(connection, sql, handler, properties.values().toArray())));
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
                if (field.getType().isAnnotationPresent(JoinedEntity.class)) {
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
