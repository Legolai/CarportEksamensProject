package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.UnprotectedPageCommand;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.AccountDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Account;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Address;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Person;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.UserMapper;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.person.AccountMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class RegisterCommand extends UnprotectedPageCommand {
    public RegisterCommand(String pageName) {
        super(pageName);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {
        AccountMapper accountMapper = new AccountMapper(new EntityManager(connectionPool));

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmedPassword = request.getParameter("confirmedPassword");
        String street = request.getParameter("street");
        String streetNumber = request.getParameter("streetNumber");
        String zip = request.getParameter("zip");
        String city = request.getParameter("city");

        if(!password.equals(confirmedPassword)){
            request.setAttribute("error", "Confirmed password does not match!");
            return new PageDirect(RedirectType.DEFAULT, "register");
        }

        try{
            Address address = new Address(0, streetNumber, street,null, zip, city);
            Person person = new Person(0, firstName, lastName,email, null, 0, false);
            Account acc = new Account(0, LocalDateTime.now().withNano(0), 0, password, Role.COSTUMER);
            PersonDTO personDTO = new PersonDTO(person, address);
            AccountDTO accDTO = new AccountDTO(acc, personDTO);

            accDTO = accountMapper.insert(accDTO);

            HttpSession session = request.getSession();
            session.setAttribute("user", accDTO);

            return new PageDirect(RedirectType.DEFAULT, "index");
        } catch (DatabaseException ex) {
            request.setAttribute("error", "Email is already in use!");
            return new PageDirect(RedirectType.DEFAULT, "register");
        }
    }
}
