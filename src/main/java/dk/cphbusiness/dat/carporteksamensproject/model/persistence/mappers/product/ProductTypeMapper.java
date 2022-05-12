package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.product;

import dk.cphbusiness.dat.carporteksamensproject.model.entities.ProductType;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.DataMapper;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductTypeMapper implements DataMapper<ProductType> {

    private final EntityManager entityManager;

    public ProductTypeMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<ProductType> find(Map<String, Object> properties) throws DatabaseException {
        return entityManager.find(ProductType.class, properties);
    }

    @Override
    public List<ProductType> getAll() throws DatabaseException {
        return entityManager.getAll(ProductType.class);
    }

    @Override
    public Optional<List<ProductType>> findAll(Map<String, Object> properties) throws DatabaseException {
        return entityManager.findAll(ProductType.class, properties);
    }

    @Override
    public ProductType insert(ProductType productType) throws DatabaseException {
        return entityManager.insert(ProductType.class, productType);
    }

    @Override
    public boolean delete(ProductType productType) throws DatabaseException {
        productType.setDeleted(true);
        return update(productType);
    }

    @Override
    public boolean update(ProductType productType) throws DatabaseException {
        return entityManager.update(ProductType.class, productType);
    }
}
