package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.Command;
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
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.AccountFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class RegisterActionCommand implements Command {
    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {
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
            Account acc = new Account(0, LocalDateTime.now(), 0, password, Role.COSTUMER);
            PersonDTO personDTO = new PersonDTO(person, address);
            AccountDTO accDTO = new AccountDTO(acc, personDTO);

            accDTO = AccountFacade.createAccount(accDTO, connectionPool);

            HttpSession session = request.getSession();
            session.setAttribute("account", accDTO);

            return new PageDirect(RedirectType.DEFAULT, "index");
        } catch (DatabaseException ex) {
            request.setAttribute("error", "Email is already in use!");
            return new PageDirect(RedirectType.DEFAULT, "register");
        }
    }
}
