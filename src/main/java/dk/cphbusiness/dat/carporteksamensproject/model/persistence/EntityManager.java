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

    private <T, R> R newQueryUpdate(Connection connection, String sql, EntityData<T> entityData, T entity, FunctionWithThrows<ResultData<T>, R> queryHandler) throws DatabaseException {
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
                Object baseEntity;
                if (field.getType().isAnnotationPresent(JoinedEntity.class)) {
                    baseEntity = insertJoinedEntity(new JoinedEntityData<>(field.getType()), fieldEntity);
                } else if (fieldEntity instanceof List batch) {
                    baseEntity = insertBatch(batch.get(0).getClass(), batch);
                } else {
                    baseEntity = insertCheckedEntity(new EntityData<>(field.getType()), fieldEntity);
                }
                if (entity instanceof IForeignKey updater) {
                    updater.updateForeignKey(baseEntity);
                }
                constArgs.add(baseEntity);

            }
            Collections.reverse(constArgs);
            return joinedEntityData.getEntityClass().getConstructor(joinedEntityData.getConstructorEmp()).newInstance(constArgs.toArray());
        }
        catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
               IllegalAccessException | DatabaseException ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    public <T> List<T> insertBatch(Class<T> entityClass, List<T> batch) throws DatabaseException {
        if (entityClass.isAnnotationPresent(JoinedEntity.class)) {
            return insertJoinedEntityBatch(new JoinedEntityData<>(entityClass), batch);
        } else if (entityClass.isAnnotationPresent(Entity.class)) {
            return insertEntityBatch(new EntityData<>(entityClass), batch);
        } else {
            throw new DatabaseException("Batch was not a list of joined entities or entities!");
        }
    }

    private <T> List<T> insertJoinedEntityBatch(JoinedEntityData<T> joinedEntityData, List<T> batch) throws DatabaseException {
        if (batch.isEmpty()) throw new DatabaseException("Batch is empty!");

        List subList = new ArrayList<>();

        for (T o : batch) {
            try {
                Field[] fields = o.getClass().getDeclaredFields();
                Field field = fields[0];
                Object baseEntity = o.getClass().getMethod(field.getName()).invoke(o);
                subList.add(baseEntity);
            }
            catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
                throw new DatabaseException(ex.getMessage());
            }
        }
        Class unSafe = subList.get(0).getClass();
        subList = insertEntityBatch(new EntityData<>(unSafe), subList);


        if (batch.size() != subList.size()) {
            throw new DatabaseException("Batch size does not match insert batch size!");
        }
        List<T> list = new ArrayList<>();
        List<Object> constArgs = new ArrayList<>();
        for (int i = 0; i < batch.size(); i++) {
            T t = batch.get(i);
            Object info = subList.get(i);
            constArgs.add(info);
            for (Field field : joinedEntityData.getFields().subList(1, joinedEntityData.getFields().size()-1)) {
                try {
                    constArgs.add(t.getClass().getMethod(field.getName()).invoke(t));
                }
                catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    throw new DatabaseException(e.getMessage());
                }
            }
            try {
                list.add(joinedEntityData.getEntityClass().getConstructor(joinedEntityData.getConstructorEmp()).newInstance(constArgs.toArray()));
                constArgs.clear();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                throw new DatabaseException(ex.getMessage());
            }
        }
        return list;
    }

    private <T> List<T> insertEntityBatch(EntityData<T> entityData, List<T> batch) throws DatabaseException {
        if (batch.isEmpty()) throw new DatabaseException("batch is empty!");

        FunctionWithThrows<Connection, List<T>> handler = connection -> {
            if (entityData.getFieldForId().isAnnotationPresent(GeneratedValue.class)) {
                entityData.getFields().remove(entityData.getFieldForId());
            }

            String sqlColumns = entityData.getFields().stream().map(f -> f.getAnnotation(Column.class).value())
                    .collect(Collectors.joining(", "));
            String options = "?".repeat(entityData.getFields().size()).replaceAll(".(?=.)", "$0, ");

            String sql = "INSERT INTO " + entityData.getTableName() + " (" + sqlColumns + ") VALUES (" + options + ")";

            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                List<T> list = new ArrayList<>(batch.size());
                final int batchSize = 1000;
                int count = 0;
                for (Object t : batch) {
                    List<Field> fields = entityData.getFields();
                    for (int i = 1; i <= fields.size(); ++i) {
                        String fieldName = fields.get(i - 1).getName();
                        String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Object value = t.getClass().getDeclaredMethod(methodName).invoke(t);
                        if (fields.get(i - 1).getType().isEnum()) {
                            value = ((Enum<?>) value).name();
                        }
                        ps.setObject(i, value);
                    }
                    ps.addBatch();
                    if (++count % batchSize == 0) {
                        int[] rowStatus = ps.executeBatch();
                        int rowStatusCount = 0;
                        ResultSet resultSet = ps.getGeneratedKeys();
                        ResultData<T> resultData = new ResultData<>(1, resultSet, entityData);
                        while (resultSet.next() && rowStatus[rowStatusCount] >= Statement.SUCCESS_NO_INFO) {
                            resultData.setEntity(batch.get(list.size()));
                            list.add(extractIdFromQueryUpdate(resultData));
                            ++rowStatusCount;
                        }
                    }
                }
                int[] rowStatus = ps.executeBatch();
                int rowStatusCount = 0;
                ResultSet resultSet = ps.getGeneratedKeys();
                ResultData<T> resultData = new ResultData<>(1, resultSet, entityData);
                while (rowStatus.length > rowStatusCount && rowStatus[rowStatusCount] >= Statement.SUCCESS_NO_INFO) {
                    resultData.setEntity(batch.get(list.size()));
                    list.add(extractIdFromQueryUpdate(resultData));
                    ++rowStatusCount;
                }
                return list;
            }
            catch (SQLException ex) {
                throw new DatabaseException(ex.getMessage());
            }
        };
        return makeConnection(handler);
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

    private <T> T insertEntity(EntityData<T> entityData, T entity) throws DatabaseException {
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

        String join = Arrays.stream(joinedEntityData.getJoin()).reduce("", (curr, clazz) -> curr + String.format(" INNER JOIN %s USING(%s)", clazz.getAnnotation(Table.class).value(),
                Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.isAnnotationPresent(Id.class)).findFirst().orElseThrow().getAnnotation(Column.class).value()), String::concat);


        String sql = "SELECT * FROM " + joinedEntityData.getMainTable().getAnnotation(Table.class).value() + join;

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

    public <T> Optional<T> find(Class<T> entityClass ,Map<String, Object> properties) throws DatabaseException {
        EntityData<T> entityData = new EntityData<>(entityClass);

        String sqlColumns = String.join(" AND ", properties.keySet());
        String options = sqlColumns.replaceAll("\\b(?!AND\\b)\\w+", "$0 = ?");

        String sql = "SELECT * FROM " + entityData.getTableName() + " WHERE " + options + " LIMIT 1";

        FunctionWithThrows<ResultSet, T> handler = resultSet -> {
            if (resultSet.next())
                return createEntity(resultSet, entityData);
            return null;
        };
        return Optional.ofNullable(makeConnection(connection -> newQuery(connection, sql, handler, properties.values().toArray())));
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
        String join = Arrays.stream(joinedEntityData.getJoin()).reduce("", (curr, clazz) -> curr + String.format(" INNER JOIN %s USING(%s)", clazz.getAnnotation(Table.class).value(),
                Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.isAnnotationPresent(Id.class)).findFirst().orElseThrow().getAnnotation(Column.class).value()), String::concat);

        String sql = "SELECT * FROM " + joinedEntityData.getMainTable().getAnnotation(Table.class).value() + join + " WHERE " + options;

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
