package dk.cphbusiness.dat.carporteksamensproject.model.persistence;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Table;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JoinedEntityData<T> {
    private String mainTable;
    private Map<String, String> joinMap;

    private LinkedList<Field> fields;
    private Class<?>[] constructorEmp;
    private Class<T> entityClass;

    public JoinedEntityData(Class<T> entityClass) {
        this.fields = new LinkedList<>(Arrays.asList(entityClass.getDeclaredFields()));
        this.mainTable = entityClass.getAnnotation(Table.class).value();
        Join join = entityClass.getAnnotation(Join.class);
        String[] joins = join.joins();
        String[] tables = join.tables();
        this.joinMap = IntStream.range(0, tables.length).boxed()
                .collect(Collectors.toMap(i -> tables[i], i -> joins[i]));
        this.constructorEmp = fields.stream().map(Field::getType).toArray(Class[]::new);
        this.entityClass = entityClass;
    }

    private void findJoinedEntity(Field classField, List<Class<?>> list, List<Class<?>> listDTOs){
        if(!classField.getType().isAnnotationPresent(JoinedEntity.class)) {
            list.add(classField.getType());
            return;
        }
        listDTOs.add(classField.getType());
        for (Field field : classField.getType().getFields()) {
            findJoinedEntity(field, list, listDTOs);
        }
    }

    public LinkedList<Field> getFields() {
        return fields;
    }

    public Class<?>[] getConstructorEmp() {
        return constructorEmp;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public Map<String, String> getJoinMap() {
        return joinMap;
    }

    public String getMainTable() {
        return mainTable;
    }
}
