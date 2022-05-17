package dk.cphbusiness.dat.carporteksamensproject.model.services.facade;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.person.PersonMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PersonFacade {

    private PersonFacade(){}

    public static List<PersonDTO> getAll(ConnectionPool connectionPool) throws DatabaseException {
        PersonMapper personMapper = new PersonMapper(new EntityManager(connectionPool));
        return personMapper.getAll();
    }

    public static Optional<List<PersonDTO>> findAll(Map<String,Object> properties, ConnectionPool connectionPool) throws DatabaseException {
        PersonMapper personMapper = new PersonMapper(new EntityManager(connectionPool));
        return personMapper.findAll(properties);
    }

}
