package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.person;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.DataMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PersonMapper implements DataMapper<PersonDTO> {
    private final EntityManager entityManager;

    public PersonMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PersonDTO insert(PersonDTO person) throws DatabaseException {
        return entityManager.insert(PersonDTO.class, person);
    }

    @Override
    public Optional<PersonDTO> find(Map<String, Object> properties) throws DatabaseException {
        return entityManager.find(PersonDTO.class, properties);
    }

    public List<PersonDTO> getAll() throws DatabaseException {
        return entityManager.getAll(PersonDTO.class);
    }

    @Override
    public Optional<List<PersonDTO>> findAll(Map<String, Object> properties) throws DatabaseException {
        return entityManager.findAll(PersonDTO.class, properties);
    }

    @Override
    public boolean delete(PersonDTO personDTO) throws DatabaseException {
        personDTO.person().setDeleted(true);
        return update(personDTO);
    }

    @Override
    public boolean update(PersonDTO personDTO) throws DatabaseException {
        return entityManager.update(PersonDTO.class, personDTO);
    }


}
