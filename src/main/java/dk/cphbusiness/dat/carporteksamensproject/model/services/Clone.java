package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.EntityData;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

public interface Clone {
     static <T> T apply(Class<T> entityClass, T t) throws DatabaseException {
         List<Object> constArgs = new LinkedList<>();
         try {
             EntityData<T> manager = new EntityData<>(entityClass);
             for (Field field : manager.getFields()) {
                 Object value;
                 if (Modifier.isPublic(field.getModifiers())) {
                     value = field.get(t);
                } else {
                     String methodName = "get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
                     value = manager.getEntityClass().getMethod(methodName).invoke(t);
                 }
                 constArgs.add(value);
             }

            return manager.getEntityClass().getConstructor(manager.getConstructorEmp()).newInstance(constArgs.toArray());
         }
         catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException ex){
             throw new DatabaseException("Object is not possible to clone");
         }
     }
}
