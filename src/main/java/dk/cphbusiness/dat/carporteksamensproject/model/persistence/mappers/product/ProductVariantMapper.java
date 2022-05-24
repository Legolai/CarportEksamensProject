package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.product;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductVariantDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.ProductVariant;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Size;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.DataMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductVariantMapper implements DataMapper<ProductVariantDTO> {

    private final EntityManager entityManager;

    public ProductVariantMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<ProductVariantDTO> find(Map<String, Object> properties) throws DatabaseException {
        return entityManager.find(ProductVariantDTO.class, properties);
    }

    @Override
    public List<ProductVariantDTO> getAll() throws DatabaseException {
        return entityManager.getAll(ProductVariantDTO.class);
    }

    @Override
    public Optional<List<ProductVariantDTO>> findAll(Map<String, Object> properties) throws DatabaseException {
        return entityManager.findAll(ProductVariantDTO.class, properties);
    }

    @Override
    public ProductVariantDTO insert(ProductVariantDTO productVariantDTO) throws DatabaseException {
        return entityManager.insert(ProductVariantDTO.class, productVariantDTO);
    }

    @Override
    public boolean delete(ProductVariantDTO productVariantDTO) throws DatabaseException {
        productVariantDTO.variant().setDeleted(true);
        return update(productVariantDTO);
    }

    @Override
    public boolean update(ProductVariantDTO productVariantDTO) throws DatabaseException {
        return false;
    }

    public Optional<List<ProductVariantDTO>> findAllByProductId(int materialId) throws DatabaseException {
        return findAll(Map.of("product_ID", materialId));
    }

    public boolean insertVariant(ProductVariant productVariant, Size size) throws DatabaseException {
        Size dbSize = entityManager.insert(Size.class, size);
        productVariant.setSizeId(dbSize.getId());
        ProductVariant dbVariant = entityManager.insert(ProductVariant.class, productVariant);
        return dbVariant.getId() != 0;
    }
}
