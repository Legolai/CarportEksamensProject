package dk.cphbusiness.dat.carporteksamensproject.model.persistence;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;

import java.util.List;

public class PersonMapper {
    private EntityManager entityManager;

    public PersonMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PersonDTO createPerson(PersonDTO person) throws DatabaseException {
        return entityManager.insert(PersonDTO.class, person);
    }

    public List<PersonDTO> getAll() throws DatabaseException {
        return entityManager.getAll(PersonDTO.class);
    }


}
