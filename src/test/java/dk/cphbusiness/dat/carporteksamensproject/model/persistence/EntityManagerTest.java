package dk.cphbusiness.dat.carporteksamensproject.model.persistence;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Address;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Person;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.userstories.SetupDatabaseTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EntityManagerTest extends SetupDatabaseTest {

    @Test
    void testGetAllAddressDTOs() throws DatabaseException {
        EntityManager entityManager = new EntityManager(getConnectionPool());
        List<Address> list = entityManager.getAll(Address.class);
        System.out.println(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void testGetAllPersonDTOs() throws DatabaseException {
        EntityManager entityManager = new EntityManager(getConnectionPool());
        List<PersonDTO> list = entityManager.getAll(PersonDTO.class);
        System.out.println(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void testGetAllAddress() throws DatabaseException {
        EntityManager entityManager = new EntityManager(getConnectionPool());
        List<Address> list = entityManager.getAll(Address.class);
        System.out.println(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void testGetAllPersons() throws DatabaseException {
        EntityManager entityManager = new EntityManager(getConnectionPool());
        List<Person> list = entityManager.getAll(Person.class);
        System.out.println(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void testFindPersonDTOs() throws DatabaseException {
        EntityManager entityManager = new EntityManager(getConnectionPool());
        Optional<List<PersonDTO>> list = entityManager.findAll(PersonDTO.class, Map.of("person_forename", "Nicolai"));
        System.out.println(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void testInsertPersonDTOs() throws DatabaseException {
        EntityManager entityManager = new EntityManager(getConnectionPool());
        PersonDTO expected = new PersonDTO(new Person(2, "Peter", "Jensen", "pet@gmail.com", "12345679", 3, false), new Address(3, "40", "Ny Øvej", null, "3550", "Slangerup"));
        Address address = new Address(0, "40", "Ny Øvej", null, "3550", "Slangerup");
        PersonDTO person = new PersonDTO(new Person(0, "Peter", "Jensen", "pet@gmail.com", "12345679", 0, false), address);
        PersonDTO actual = entityManager.insert(PersonDTO.class, person);
        assertEquals(expected, actual);
    }


}