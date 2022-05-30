package dk.cphbusiness.dat.carporteksamensproject.model.services.facade;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductVariantDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.ProductVariant;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Size;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.product.ProductVariantMapper;

import java.util.List;
import java.util.Optional;

public class ProductVariantFacade {

    private ProductVariantFacade() {
    }

    public static Optional<List<ProductVariantDTO>> findAllByProductId(int materialId, ConnectionPool connectionPool) throws DatabaseException {
        ProductVariantMapper productVariantMapper = new ProductVariantMapper(new EntityManager(connectionPool));
        return productVariantMapper.findAllByProductId(materialId);
    }

    public static boolean createVariant(ProductVariant productVariant, Size size, ConnectionPool connectionPool) throws DatabaseException {
        ProductVariantMapper productVariantMapper = new ProductVariantMapper(new EntityManager(connectionPool));
        return productVariantMapper.insertVariant(productVariant, size);
    }
}
