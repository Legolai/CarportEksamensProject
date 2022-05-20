package dk.cphbusiness.dat.carporteksamensproject.control.commands.pages;

import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.InquiryDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.InquiryFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EmployeeDashboardPage extends ProtectedPage {
    public EmployeeDashboardPage(String pageName, Role role) {
        super(pageName, role);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) throws DatabaseException {

        List<InquiryDTO> inquiryDTOS = InquiryFacade.getAll(connectionPool);
        request.setAttribute("inquiries", inquiryDTOS);
        return super.execute(request, response, connectionPool);
    }
}
