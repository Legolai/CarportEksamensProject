package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.UnprotectedPageCommand;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.AccountDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.person.AccountMapper;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.person.IAccountMapper;
import dk.cphbusiness.dat.carporteksamensproject.model.services.AccountFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginActionCommand extends UnprotectedPageCommand {
    public LoginActionCommand(String pageName) {
        super(pageName);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Optional<AccountDTO> user = AccountFacade.login(email, password, connectionPool);
            if (user.isEmpty()) {
                request.setAttribute("error", "Wrong username or password!");
                return new PageDirect(RedirectType.DEFAULT, "login");
            }

            HttpSession session = request.getSession();
            session.setAttribute("user", user.get());

            return new PageDirect(RedirectType.REDIRECT, "index");
        }
        catch (DatabaseException ex) {
            request.setAttribute("error", "An error has happened");
            return new PageDirect(RedirectType.DEFAULT, "login");
        }
    }
}
