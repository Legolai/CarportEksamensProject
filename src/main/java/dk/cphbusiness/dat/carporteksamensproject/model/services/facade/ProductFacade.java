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

    public static List<ProductDTO> getAll(ConnectionPool connectionPool) throws DatabaseException{
        ProductMapper productMapper = new ProductMapper(new EntityManager(connectionPool));
        return productMapper.getAll();
    }

    public static Optional<ProductDTO> findById(int materialId, ConnectionPool connectionPool) throws DatabaseException {
        ProductMapper productMapper = new ProductMapper(new EntityManager(connectionPool));
        return productMapper.find(Map.of("product_ID", materialId));
    }

    public static boolean update(ProductDTO productDTO, ConnectionPool connectionPool) throws DatabaseException {
        ProductMapper productMapper = new ProductMapper(new EntityManager(connectionPool));
        return productMapper.update(productDTO);
    }
}
