package dk.cphbusiness.dat.carporteksamensproject.model.services.facade;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.product.ProductMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductFacade {

    private ProductFacade(){}

    public static Optional<List<ProductDTO>> findAll(Map<String, Object> properties, ConnectionPool connectionPool) throws DatabaseException {
        ProductMapper productMapper = new ProductMapper(new EntityManager(connectionPool));
        return productMapper.findAll(properties);
    }
}
