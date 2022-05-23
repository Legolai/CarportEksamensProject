package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.Command;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.AccountDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.AccountFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginAction implements Command {
    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Optional<AccountDTO> account = AccountFacade.login(email, password, connectionPool);
            if (account.isEmpty()) {
                request.setAttribute("error", "Wrong username or password!");
                return new PageDirect(RedirectType.DEFAULT, "pages/general/login");
            }

            HttpSession session = request.getSession();
            session.setAttribute("account", account.get());

            return new PageDirect(RedirectType.REDIRECT, "index");
        }
        catch (DatabaseException ex) {
            request.setAttribute("error", "An error has happened");
            return new PageDirect(RedirectType.DEFAULT, "pages/general/login");
        }
    }
}
