package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.person;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.AccountDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Account;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Address;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Person;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.userstories.SetupDatabaseTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountMapperTest extends SetupDatabaseTest {

    @Test
    void insert() throws DatabaseException {
        AccountMapper mapper = new AccountMapper(new EntityManager(getConnectionPool()));

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
                new Account(2, time, 2, "1234", Role.COSTUMER),
                new PersonDTO(
                        new Person(2, "Nicolai", "Andersen", "nico@gmail.com", null, 3, false),
                        new Address(3, "12", "Valmuevej", null, "3650", "Ølstykke")
                )
        );

        assertEquals(expected.account(), actual.account());
        assertEquals(expected.personDTO().person(), actual.personDTO().person());
        assertEquals(expected.personDTO().address(), actual.personDTO().address());
        assertEquals(expected, actual);
    }

    @Test
    void login() throws DatabaseException {
        AccountMapper mapper = new AccountMapper(new EntityManager(getConnectionPool()));

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
                new Account(2, time, 2, "1234", Role.COSTUMER),
                new PersonDTO(
                        new Person(2, "Nicolai", "Andersen", "nico@gmail.com", null, 3, false),
                        new Address(3, "12", "Valmuevej", null, "3650", "Ølstykke")
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