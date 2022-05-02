package dk.cphbusiness.dat.carporteksamensproject.control;

import dk.cphbusiness.dat.carporteksamensproject.model.config.ApplicationStart;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.User;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.services.UserFacade;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends Command
{
    private ConnectionPool connectionPool;

    public Login()
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException
    {
        HttpSession session = request.getSession();
        session.setAttribute("user", null); // adding empty user object to session scope
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = UserFacade.login(username, password, connectionPool);
        session = request.getSession();
        session.setAttribute("user", user); // adding user object to session scope
        return "index";
    }
}