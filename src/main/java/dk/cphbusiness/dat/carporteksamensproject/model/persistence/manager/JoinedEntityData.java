package dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;

import java.lang.reflect.Field;
import java.util.*;

public class JoinedEntityData<T> {
    private final Class<?> mainTable;
    private final Class<?>[] join;
    private final LinkedList<Field> fields;
    private final Class<?>[] constructorEmp;
    private final Class<T> entityClass;

    public JoinedEntityData(Class<T> entityClass) {
        this.fields = new LinkedList<>(Arrays.asList(entityClass.getDeclaredFields()));
        this.mainTable = entityClass.getAnnotation(Join.class).main();
        this.join = entityClass.getAnnotation(Join.class).join();
        this.constructorEmp = fields.stream().map(Field::getType).toArray(Class[]::new);
        this.entityClass = entityClass;
    }

    public List<Field> getFields() {
        return fields;
    }

    public Iterator<Field> descendingFieldsIterator() {
        return fields.descendingIterator();
    }

    public Class<?>[] getConstructorEmp() {
        return constructorEmp;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public Class<?>[] getJoin() {
        return join;
    }

    public Class<?> getMainTable() {
        return mainTable;
    }
}
