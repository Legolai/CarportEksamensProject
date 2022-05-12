package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.UnprotectedPageCommand;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.UserMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand extends UnprotectedPageCommand {
    public LogoutCommand(String pageName) {
        super(pageName);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {
        UserMapper userMapper = new UserMapper(connectionPool);

        String email = request.getParameter("email");
        String password = request.getParameter("password");

//        try {
//            //DBEntity<User> dbUser = userMapper.login(email, password);
//
//            HttpSession session = request.getSession();
//            //session.setAttribute("user", dbUser);
//
//            String pageToShow = "index";
//            return new PageDirect(RedirectType.REDIRECT, pageToShow);
//        }
//        catch (DatabaseException ex) {
//            request.setAttribute("error", "Wrong username or password!");
//            return new PageDirect(RedirectType.DEFAULT, "login");
//        }
        return new PageDirect(RedirectType.DEFAULT, "index");
    }
}
