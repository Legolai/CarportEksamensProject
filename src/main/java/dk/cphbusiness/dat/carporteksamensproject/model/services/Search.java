package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Column;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Entity;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public interface Search {

    static <T> Object deepSearch(String column, T entity) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object value = null;
        for (Field f : entity.getClass().getDeclaredFields()) {
            Object fieldEntity = entity.getClass().getDeclaredMethod(f.getName()).invoke(entity);
            if (f.getType().isAnnotationPresent(Entity.class)) {
                for (Field entityField : fieldEntity.getClass().getDeclaredFields()) {
                    if (entityField.isAnnotationPresent(Column.class) && entityField.getAnnotation(Column.class)
                            .value()
                            .equals(column)) {
                        String fieldName = entityField.getName();
                        String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        value = fieldEntity.getClass().getDeclaredMethod(methodName).invoke(fieldEntity);
                        break;
                    }
                }
            } else {
                if (f.getType().isAnnotationPresent(JoinedEntity.class)) {
                    value = deepSearch(column, fieldEntity);
                } else if (f.getType().equals(Optional.class) && ((Optional<?>) fieldEntity).isPresent()) {
                    value = deepSearch(column, ((Optional<?>) fieldEntity).get());
                }
            }
            if (value != null) {
                break;
            }
        }

        return value;
    }
}
