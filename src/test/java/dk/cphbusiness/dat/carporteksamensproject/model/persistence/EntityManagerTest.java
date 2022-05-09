package dk.cphbusiness.dat.carporteksamensproject.model.persistence;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Address;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Person;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EntityManagerTest {

    private final static String USER = "teacher";
    private final static String PASSWORD = "rootuser";
    private final static String URL = "jdbc:mysql://localhost:3306/CarportProjectTestDB?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";

    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);
    }

    @BeforeEach
    void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement() ) {
                // Remove all rows from all tables
                stmt.execute("delete from `Person`");
                stmt.execute("delete from `Address`");

                stmt.execute("ALTER TABLE `Address` DISABLE KEYS");
                stmt.execute("ALTER TABLE `Address` AUTO_INCREMENT = 1");
                stmt.execute("insert into `Address` (`address_number`, `address_floor`, `address_street`, address_zipcode, address_city_name) " +
                        "values ('10B', null, 'Peter Bangs Vej','3400', 'Hillerød'),('2', null, 'Ny Øvej','3550', 'Slangerup'), ('100', '2. tv', 'Goddagvej', '3650', 'Ølstykke')");
                stmt.execute("ALTER TABLE `Address` ENABLE KEYS");

                stmt.execute("ALTER TABLE `Person` DISABLE KEYS");
                stmt.execute("ALTER TABLE `Person` AUTO_INCREMENT = 1");
                stmt.execute("insert into `Person` (`person_forename`, `person_surname`, `person_email`, `person_phone_number`, `address_ID`) " +
                        "values ('Nicolai', 'Jensen', 'nico@gmail.com', null, 2),('Michael', 'Thomasen', 'Mic@gmail.com', '12345678', 1), ('Jon', 'Peterson', 'jon@gmail.com', null, 3)");
                stmt.execute("ALTER TABLE `Person` ENABLE KEYS");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testConnection() throws SQLException
    {
        Connection connection = connectionPool.getConnection();
        assertNotNull(connection);
        connection.close();
    }

    @Test
    void testGetAllAddressDTOs() throws DatabaseException {
        EntityManager entityManager = new EntityManager(connectionPool);
        List<Address> list = entityManager.getAll(Address.class);
        System.out.println(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void testGetAllPersonDTOs() throws DatabaseException {
        EntityManager entityManager = new EntityManager(connectionPool);
        List<PersonDTO> list = entityManager.getAll(PersonDTO.class);
        System.out.println(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void testGetAllAddress() throws DatabaseException {
        EntityManager entityManager = new EntityManager(connectionPool);
        List<Address> list = entityManager.getAll(Address.class);
        System.out.println(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void testGetAllPersons() throws DatabaseException {
        EntityManager entityManager = new EntityManager(connectionPool);
        List<Person> list = entityManager.getAll(Person.class);
        System.out.println(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void testFindPersonDTOs() throws DatabaseException {
        EntityManager entityManager = new EntityManager(connectionPool);
        Optional<List<PersonDTO>> list = entityManager.findAll(PersonDTO.class, Map.of("person_forename", "Nicolai"));
        System.out.println(list);
        assertFalse(list.isEmpty());
        assertEquals(1,list.get().size());
    }

    @Test
    void testInsertPersonDTOs() throws DatabaseException {
        EntityManager entityManager = new EntityManager(connectionPool);
        PersonDTO expected = new PersonDTO(new Person(4, "Peter", "Jensen", "pet@gmail.com", "12345679",4), new Address(4, "40", "Ny Øvej", null, "3550", "Slangerup"));

        Address address = new Address(0, "40", "Ny Øvej", null, "3550", "Slangerup");
        PersonDTO person = new PersonDTO(new Person(0, "Peter", "Jensen", "pet@gmail.com", "12345679", address.getId()), address);
        PersonDTO actual = entityManager.insert(PersonDTO.class, person);
        assertEquals(expected, actual);
    }


}