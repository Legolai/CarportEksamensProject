package dk.cphbusiness.dat.carporteksamensproject.model.persistence;

import java.sql.ResultSet;

public class ResultData<T> {

    private final int rowsAffected;
    private final ResultSet resultSet;
    private final EntityData<T> entityData;
    private T entity;

    public ResultData(int rowsAffected, ResultSet resultSet, EntityData<T> entityData, T entity) {
        this.rowsAffected = rowsAffected;
        this.resultSet = resultSet;
        this.entityData = entityData;
        this.entity = entity;
    }

    public ResultData(int rowsAffected, ResultSet resultSet, EntityData<T> entityData) {
        this.rowsAffected = rowsAffected;
        this.resultSet = resultSet;
        this.entityData = entityData;
    }

    public int getRowsAffected() {
        return rowsAffected;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public EntityData<T> getEntityData() {
        return entityData;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
