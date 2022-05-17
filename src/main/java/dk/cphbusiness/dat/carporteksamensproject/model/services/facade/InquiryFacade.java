package dk.cphbusiness.dat.carporteksamensproject.model.services.facade;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.InquiryDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.InquiryMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InquiryFacade {

    public static List<InquiryDTO> getAll(ConnectionPool connectionPool) throws DatabaseException {
        InquiryMapper inquiryMapper = new InquiryMapper(new EntityManager(connectionPool));
        return inquiryMapper.getAll();
    }


    public static Optional<List<InquiryDTO>> findAll(Map<String, Object> properties, ConnectionPool connectionPool) throws DatabaseException {
        InquiryMapper inquiryMapper = new InquiryMapper(new EntityManager(connectionPool));
        return inquiryMapper.findAll(properties);
    }
}
