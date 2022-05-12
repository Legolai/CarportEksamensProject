package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.person;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.AccountDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Account;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Address;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Person;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {

    private final static String USER = "teacher";
    private final static String PASSWORD = "rootuser";
    private final static String URL = "jdbc:mysql://localhost:3306/carportprojecttestdb?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";

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
                stmt.execute("delete from `Account`");

                stmt.execute("ALTER TABLE `Account` DISABLE KEYS");
                stmt.execute("ALTER TABLE `Account` AUTO_INCREMENT = 1");
                stmt.execute("ALTER TABLE `Account` ENABLE KEYS");

                stmt.execute("ALTER TABLE `Address` DISABLE KEYS");
                stmt.execute("ALTER TABLE `Address` AUTO_INCREMENT = 1");
                stmt.execute("ALTER TABLE `Address` ENABLE KEYS");

                stmt.execute("ALTER TABLE `Person` DISABLE KEYS");
                stmt.execute("ALTER TABLE `Person` AUTO_INCREMENT = 1");
                stmt.execute("ALTER TABLE `Person` ENABLE KEYS");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void insert() throws DatabaseException {
        AccountMapper mapper = new AccountMapper(new EntityManager(connectionPool));

        LocalDateTime time = LocalDateTime.now();

        AccountDTO created = new AccountDTO(
                new Account(0, time, 0, "1234", Role.COSTUMER),
                new PersonDTO(
                        new Person(0, "Nicolai", "Andersen", "nico@gmail.com", null, 0, false),
                        new Address(0, "12", "Valmuevej", null, "3650", "Ølstykke")
                )
        );

        AccountDTO actual = mapper.insert(created);
        AccountDTO expected = new AccountDTO(
                new Account(1, time, 1, "1234", Role.COSTUMER),
                new PersonDTO(
                        new Person(1, "Nicolai", "Andersen", "nico@gmail.com", null, 1, false),
                        new Address(1, "12", "Valmuevej", null, "3650", "Ølstykke")
                )
        );

        assertEquals(expected.account(), actual.account());
        assertEquals(expected.personDTO().person(), actual.personDTO().person());
        assertEquals(expected.personDTO().address(), actual.personDTO().address());
        assertEquals(expected, actual);
    }

    @Test
    void login() throws DatabaseException {
        AccountMapper mapper = new AccountMapper(new EntityManager(connectionPool));

        LocalDateTime time = LocalDateTime.now().withNano(0);
        System.out.println(time.getSecond());
        AccountDTO created = new AccountDTO(
                new Account(0, time, 0, "1234", Role.COSTUMER),
                new PersonDTO(
                        new Person(0, "Nicolai", "Andersen", "nico@gmail.com", null, 0, false),
                        new Address(0, "12", "Valmuevej", null, "3650", "Ølstykke")
                )
        );

        AccountDTO actual = mapper.insert(created);
        AccountDTO expected = new AccountDTO(
                new Account(1, time, 1, "1234", Role.COSTUMER),
                new PersonDTO(
                        new Person(1, "Nicolai", "Andersen", "nico@gmail.com", null, 1, false),
                        new Address(1, "12", "Valmuevej", null, "3650", "Ølstykke")
                )
        );

        assertEquals(expected.account(), actual.account());
        assertEquals(expected.personDTO().person(), actual.personDTO().person());
        assertEquals(expected.personDTO().address(), actual.personDTO().address());
        assertEquals(expected, actual);

        Optional<AccountDTO> actual2 = mapper.login("nico@gmail.com", "1234");
        assertTrue(actual2.isPresent());
        assertEquals(expected, actual2.get());
    }
}