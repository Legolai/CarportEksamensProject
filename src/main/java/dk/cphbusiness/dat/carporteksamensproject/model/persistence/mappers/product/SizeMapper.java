package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.product;

import dk.cphbusiness.dat.carporteksamensproject.model.entities.Size;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.DataMapper;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SizeMapper implements DataMapper<Size> {

    private EntityManager entityManager;

    public SizeMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Size> find(Map<String, Object> properties) throws DatabaseException {
        return entityManager.find(Size.class, properties);
    }

    @Override
    public List<Size> getAll() throws DatabaseException {
        return entityManager.getAll(Size.class);
    }

    @Override
    public Optional<List<Size>> findAll(Map<String, Object> properties) throws DatabaseException {
        return entityManager.findAll(Size.class, properties);
    }

    @Override
    public Size insert(Size size) throws DatabaseException {
        return entityManager.insert(Size.class, size);
    }

    @Override
    public boolean delete(Size size) throws DatabaseException {
        size.setDeleted(true);
        return update(size);
    }

    @Override
    public boolean update(Size size) throws DatabaseException {
        return entityManager.update(Size.class, size);
    }
}
