package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers;

import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DataMapper<T> {
    Optional<T> find(Map<String, Object> properties) throws DatabaseException;
    List<T> getAll() throws DatabaseException;
    Optional<List<T>> findAll(Map<String, Object> properties) throws DatabaseException;
    T insert(T t) throws DatabaseException;
    boolean delete(T t) throws DatabaseException;
    boolean update(T t) throws DatabaseException;
}
