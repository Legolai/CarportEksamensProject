package dk.cphbusiness.dat.carporteksamensproject.model.userstories;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.AccountDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Account;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Address;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Person;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.AccountFacade;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserStory1Test extends SetupDatabaseTest {

    @Test
    void createAccountITest() throws DatabaseException {
        Address newAddress = new Address(0, "10", "drømmegade", null, "4000", "andeby");
        Person newPerson = new Person(0, "anders", "and", "anders.and@gmail.com", null, 0, false);
        LocalDateTime time = LocalDateTime.now().withNano(0);
        Account newAccount = new Account(0, time, 0, "AndersineHjerte", Role.COSTUMER);

        PersonDTO newPersonDTO = new PersonDTO(newPerson, newAddress);
        AccountDTO newAccountDTO = new AccountDTO(newAccount, newPersonDTO);

        AccountDTO actual = AccountFacade.createAccount(newAccountDTO, getConnectionPool());

        Address address = new Address(2, "10", "drømmegade", null, "4000", "andeby");
        Person person = new Person(2, "anders", "and", "anders.and@gmail.com", null, 2, false);
        Account account = new Account(2, time, 2, "AndersineHjerte", Role.COSTUMER);

        PersonDTO personDTO = new PersonDTO(person, address);
        AccountDTO expected = new AccountDTO(account, personDTO);

        assertEquals(expected, actual);
    }

    @Test
    void loginITest() throws DatabaseException {
        Address address = new Address(1, "12", "drømmegade", null, "4000", "andeby");
        Person person = new Person(1, "andersine", "and", "andersine.and@email.com", null, 1, false);
        Account account = new Account(1, getTime(), 1, "tomcat1234", Role.EMPLOYEE);

        PersonDTO personDTO = new PersonDTO(person, address);
        AccountDTO expected = new AccountDTO(account, personDTO);


        Optional<AccountDTO> actual = AccountFacade.login("andersine.and@email.com", "tomcat1234", getConnectionPool());
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void insertAndLoginITest() throws DatabaseException {
        Address newAddress = new Address(0, "10", "drømmegade", null, "4000", "andeby");
        Person newPerson = new Person(0, "anders", "and", "anders.and@gmail.com", null, 0, false);
        LocalDateTime time = LocalDateTime.now().withNano(0);
        Account newAccount = new Account(0, time, 0, "AndersineHjerte", Role.COSTUMER);

        PersonDTO newPersonDTO = new PersonDTO(newPerson, newAddress);
        AccountDTO newAccountDTO = new AccountDTO(newAccount, newPersonDTO);

        AccountDTO actual = AccountFacade.createAccount(newAccountDTO, getConnectionPool());

        Address address = new Address(2, "10", "drømmegade", null, "4000", "andeby");
        Person person = new Person(2, "anders", "and", "anders.and@gmail.com", null, 2, false);
        Account account = new Account(2, time, 2, "AndersineHjerte", Role.COSTUMER);

        PersonDTO personDTO = new PersonDTO(person, address);
        AccountDTO expected = new AccountDTO(account, personDTO);

        assertEquals(expected, actual);

        Optional<AccountDTO> actual2 = AccountFacade.login("anders.and@gmail.com", "AndersineHjerte", getConnectionPool());

        assertTrue(actual2.isPresent());
        assertEquals(expected, actual2.get());
    }
}
