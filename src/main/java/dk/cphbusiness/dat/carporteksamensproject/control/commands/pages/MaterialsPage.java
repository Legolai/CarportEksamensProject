package dk.cphbusiness.dat.carporteksamensproject.control.commands.pages;

import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MaterialsPage extends ProtectedPage{

    public MaterialsPage(String pageName, Role role) {
        super(pageName, role);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) throws DatabaseException {
        return super.execute(request, response, connectionPool);
    }
}
