package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.ProtectedPage;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.InquiryStatus;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.InquiryFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateInquiryStatusAction extends ProtectedPage {
    public UpdateInquiryStatusAction(Role role) {
        super("", role);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {
        String newStatus = request.getParameter("inquiry-status");
        String inquiryIdString = request.getParameter("inquiry-ID");

        int inquiryId = Integer.parseInt(inquiryIdString);

        try {
            if(!InquiryFacade.updateStatus(inquiryId, InquiryStatus.valueOf(newStatus), connectionPool)){
                request.setAttribute("errormessage", "Failed to change status of inquiry");
                return new PageDirect(RedirectType.ERROR, "error");
            }
        } catch (DatabaseException ex) {
            request.setAttribute("errormessage", ex.getMessage());
            return new PageDirect(RedirectType.ERROR, "error");
        }

        return new PageDirect(RedirectType.COMMAND, "employee-dashboard-page");
    }
}
