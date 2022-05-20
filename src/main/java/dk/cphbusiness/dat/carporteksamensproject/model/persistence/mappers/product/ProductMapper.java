package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.product;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.DataMapper;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductMapper implements DataMapper<ProductDTO> {

    private final EntityManager entityManager;

    public ProductMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<ProductDTO> find(Map<String, Object> properties) throws DatabaseException {
        return entityManager.find(ProductDTO.class, properties);
    }

    @Override
    public List<ProductDTO> getAll() throws DatabaseException {
        return entityManager.getAll(ProductDTO.class);
    }

    @Override
    public Optional<List<ProductDTO>> findAll(Map<String, Object> properties) throws DatabaseException {
        return entityManager.findAll(ProductDTO.class, properties);
    }

    @Override
    public ProductDTO insert(ProductDTO productDTO) throws DatabaseException {
        return entityManager.insert(ProductDTO.class, productDTO);
    }

    @Override
    public boolean delete(ProductDTO productDTO) throws DatabaseException {
        productDTO.product().setDeleted(true);
        return update(productDTO);
    }

    @Override
    public boolean update(ProductDTO productDTO) throws DatabaseException {
        return false;
    }
}
