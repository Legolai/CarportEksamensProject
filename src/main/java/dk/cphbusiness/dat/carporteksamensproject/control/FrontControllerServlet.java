package dk.cphbusiness.dat.carporteksamensproject.control;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.Command;
import dk.cphbusiness.dat.carporteksamensproject.control.commands.CommandController;
import dk.cphbusiness.dat.carporteksamensproject.control.commands.UnknownCommand;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.model.config.ApplicationStart;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "FrontController", urlPatterns = {"/fc/*"})
public class FrontControllerServlet extends HttpServlet {

    private static ConnectionPool connectionPool;

    public static ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    @Override
    public void init() {
        connectionPool = ApplicationStart.getConnectionPool();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            Command action = CommandController.getInstance().fromPath(request);

            if (action instanceof UnknownCommand) {
                response.sendError(404);
                return;
            }

            PageDirect view = action.execute(request, response, connectionPool);

            switch (view.redirectType()) {
                case REDIRECT -> {
                    String page = view.pageName();
                    response.sendRedirect(page);
                }
                case COMMAND -> request.getRequestDispatcher(view.pageName()).forward(request, response);
                case ERROR -> request.getRequestDispatcher("/" + view.pageName() + ".jsp").forward(request, response);
                case DEFAULT ->
                        request.getRequestDispatcher("/WEB-INF/" + view.pageName() + ".jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("errormessage", ex.getMessage());
            Logger.getLogger("web").log(Level.SEVERE, ex.getMessage(), ex);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "FrontController for application";
    }
}