package dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Entity;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Id;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class EntityData<T> {

    private final String tableName;
    private final List<Field> fields;
    private final Field fieldForId;
    private final Class<?>[] constructorEmp;
    private final Class<T> entityClass;


    public EntityData(Class<T> entityClass) throws IllegalArgumentException {
        if (!entityClass.isAnnotationPresent(Entity.class) || entityClass.isAnnotationPresent(JoinedEntity.class))
            throw new IllegalArgumentException(String.format("Object %s is not annotated as Entity!", entityClass));

        this.tableName = entityClass.getAnnotation(Table.class).value();
        this.fields = new LinkedList<>(Arrays.asList(entityClass.getDeclaredFields()));
        this.fieldForId = fields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No ID is available on entity!"));
        this.constructorEmp = fields.stream().map(Field::getType).toArray(Class[]::new);
        this.entityClass = entityClass;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public String getTableName() {
        return tableName;
    }

    public List<Field> getFields() {
        return fields;
    }

    public Field getFieldForId() {
        return fieldForId;
    }

    public Class<?>[] getConstructorEmp() {
        return constructorEmp;
    }
}
