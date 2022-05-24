package dk.cphbusiness.dat.carporteksamensproject.model.services.facade;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.InquiryDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.InquiryStatus;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.InquiryMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InquiryFacade {

    private InquiryFacade() {}

    public static List<InquiryDTO> getAll(ConnectionPool connectionPool) throws DatabaseException {
        InquiryMapper inquiryMapper = new InquiryMapper(new EntityManager(connectionPool));
        return inquiryMapper.getAll();
    }

    public static Optional<List<InquiryDTO>> findAll(Map<String, Object> properties, ConnectionPool connectionPool) throws DatabaseException {
        InquiryMapper inquiryMapper = new InquiryMapper(new EntityManager(connectionPool));
        return inquiryMapper.findAll(properties);
    }

    public static Optional<InquiryDTO> find(Map<String, Object> properties, ConnectionPool connectionPool) throws DatabaseException {
        InquiryMapper inquiryMapper = new InquiryMapper(new EntityManager(connectionPool));
        return inquiryMapper.find(properties);
    }


    public static InquiryDTO createInquiry(InquiryDTO inquiryDTO, ConnectionPool connectionPool) throws DatabaseException {
        InquiryMapper inquiryMapper = new InquiryMapper(new EntityManager(connectionPool));
        return inquiryMapper.insert(inquiryDTO);
    }

    public static boolean updateStatus(int inquiryId, InquiryStatus newStatus, ConnectionPool connectionPool) throws DatabaseException {
        InquiryMapper inquiryMapper = new InquiryMapper(new EntityManager(connectionPool));
        return inquiryMapper.updateStatus(inquiryId, newStatus);
    }

    public static boolean updatePrice(int inquiryId, int newPrice, ConnectionPool connectionPool) throws DatabaseException {
        InquiryMapper inquiryMapper = new InquiryMapper(new EntityManager(connectionPool));
        return inquiryMapper.updatePrice(inquiryId, newPrice);
    }
}
