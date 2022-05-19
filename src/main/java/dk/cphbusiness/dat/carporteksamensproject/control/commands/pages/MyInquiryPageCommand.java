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
import java.util.Map;
import java.util.Optional;

public class MyInquiryPageCommand extends ProtectedPageCommand {
    public MyInquiryPageCommand(String pageName, Role role) {
        super(pageName, role);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) throws DatabaseException {
        String inquiryIdString = request.getParameter("inquiry-ID");
        HttpSession session = request.getSession(false);

        AccountDTO account = (AccountDTO) session.getAttribute("account");

        if (account == null){
            return new PageDirect(RedirectType.REDIRECT, "index");
        }

        int inquiryId = Integer.parseInt(inquiryIdString);

        Optional<InquiryDTO> optional = InquiryFacade.find(Map.of("inquiry_ID", inquiryId), connectionPool);

        if (optional.isEmpty()) {
            return new PageDirect(RedirectType.ERROR, "");
        }

        InquiryDTO inquiryDTO = optional.get();

        if (!account.personDTO().person().getEmail().equals(inquiryDTO.person().person().getEmail())){
            return new PageDirect(RedirectType.ERROR, "");
        }

        request.setAttribute("inquiry", inquiryDTO);
        return super.execute(request, response, connectionPool);
    }
}