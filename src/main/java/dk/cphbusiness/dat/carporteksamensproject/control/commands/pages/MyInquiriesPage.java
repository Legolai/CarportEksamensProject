package dk.cphbusiness.dat.carporteksamensproject.control.commands.pages;

import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.AccountDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.InquiryDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.InquiryFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyInquiriesPage extends ProtectedPage {
    public MyInquiriesPage(String pageName, Role role) {
        super(pageName, role);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {

        HttpSession session = request.getSession();

        AccountDTO accountDTO = (AccountDTO) session.getAttribute("account");

        try {
            Optional<List<InquiryDTO>> list = InquiryFacade.findAll(Map.of("person_email", accountDTO.personDTO()
                    .person()
                    .getEmail()), connectionPool);
            list.ifPresent(inquiryDTOS -> request.setAttribute("inquiries", inquiryDTOS));
        } catch (DatabaseException e) {
            Logger.getLogger("web").log(Level.SEVERE, "Failed to find inquiries for account!", e);
        }

        return new PageDirect(RedirectType.DEFAULT, getPageName());
    }
}
