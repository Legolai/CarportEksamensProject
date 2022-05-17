package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.UnprotectedPageCommand;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.InquiryDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.*;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.InquiryFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InquiryFlatRoofActionCommand extends UnprotectedPageCommand {
    public InquiryFlatRoofActionCommand(String pageName) {
        super(pageName);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {
        String carportWidthString = request.getParameter("carport-width");
        String carportLengthString = request.getParameter("carport-length");
        String roofMaterialString = request.getParameter("roof-material");

        String shackWidthString = request.getParameter("shack-width");
        String shackLengthString = request.getParameter("shack-length");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        String street = request.getParameter("street");
        String streetNumber = request.getParameter("streetNumber");
        String city = request.getParameter("city");
        String zip = request.getParameter("zip");

        String comment = request.getParameter("comment");

        try {
            int carportWidth = Integer.parseInt(carportWidthString);
            int carportLength = Integer.parseInt(carportLengthString);
            int roofMaterial = Integer.parseInt(roofMaterialString);
            int shackWidth = Integer.parseInt(shackWidthString);
            int shackLength = Integer.parseInt(shackLengthString);

            Address address = new Address(0, streetNumber, street, null, zip, city);
            Person person = new Person(0, firstName, lastName, email, null, 0, false);
            PersonDTO personDTO = new PersonDTO(person, address);

            Optional<Shack> optionalShack = Optional.empty();
            if (shackWidth != -1 || shackLength != -1){
                Shack shack = new Shack(0, shackWidth, shackLength, true, 0);
                optionalShack = Optional.of(shack);
            }

            LocalDateTime time = LocalDateTime.now().withNano(0);
            Carport carport = new Carport(0, carportWidth, carportLength, 210, RoofType.FLAT, roofMaterial, time);
            CarportDTO carportDTO = new CarportDTO(optionalShack, carport);

            Inquiry inquiry = new Inquiry(0, InquiryStatus.OPEN, comment, 0 ,0, time, time);
            InquiryDTO inquiryDTO = new InquiryDTO(inquiry, personDTO, Optional.empty(), carportDTO);

            InquiryDTO dbCreated = InquiryFacade.createInquiry(inquiryDTO, connectionPool);

            request.setAttribute("inquiry", dbCreated);

            return new PageDirect(RedirectType.DEFAULT, "");
        } catch (NumberFormatException ex) {
            request.setAttribute("errormessage", ex.getMessage());
            Logger.getLogger("web").log(Level.SEVERE, "Could not pass string values to integers", ex);
            return new PageDirect(RedirectType.ERROR, "error");
        }  catch (DatabaseException ex) {
            request.setAttribute("errormessage", ex.getMessage());
            Logger.getLogger("web").log(Level.SEVERE, "Could not create inquiry", ex);
            return new PageDirect(RedirectType.ERROR, "error");
        }
    }
}
