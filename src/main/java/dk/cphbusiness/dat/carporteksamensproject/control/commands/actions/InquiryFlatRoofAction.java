package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.Command;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.*;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.*;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.CarportAlgorithmFactory;
import dk.cphbusiness.dat.carporteksamensproject.model.services.ICarportAlgorithm;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.InquiryFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InquiryFlatRoofAction implements Command {
    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {
        String carportWidthString = request.getParameter("carport-width");
        String carportLengthString = request.getParameter("carport-length");
        String roofMaterialString = request.getParameter("roof-material");

        String hasShackString = request.getParameter("has-shack");
        String shackWidthString = request.getParameter("shack-width");
        String shackLengthString = request.getParameter("shack-length");

        String firstName = request.getParameter("firstName").toLowerCase();
        String lastName = request.getParameter("lastName").toLowerCase();
        String email = request.getParameter("email").toLowerCase();

        String street = request.getParameter("street").toLowerCase();
        String streetNumber = request.getParameter("streetNumber").toLowerCase();
        String city = request.getParameter("city").toLowerCase();
        String zip = request.getParameter("zip").toLowerCase();

        String comment = request.getParameter("comment");

        try {
            int carportWidth = Integer.parseInt(carportWidthString);
            int carportLength = Integer.parseInt(carportLengthString);
            int roofMaterial = Integer.parseInt(roofMaterialString);
            boolean hasShack = Boolean.parseBoolean(hasShackString);

            Address address = new Address(0, streetNumber, street, null, zip, city);
            Person person = new Person(0, firstName, lastName, email, null, 0, false);
            PersonDTO personDTO = new PersonDTO(person, address);

            Optional<Shack> optionalShack = Optional.empty();
            if(hasShack) {
                int shackWidth = Integer.parseInt(shackWidthString);
                int shackLength = Integer.parseInt(shackLengthString);
                Shack shack = new Shack(0, shackWidth, shackLength, true);
                optionalShack = Optional.of(shack);
            }

            LocalDateTime time = LocalDateTime.now().withNano(0);
            Carport carport = new Carport(0, carportWidth, carportLength, 210, RoofType.FLAT, roofMaterial, time, 0);
            CarportDTO carportDTO = new CarportDTO(carport, optionalShack);

            Inquiry inquiry = new Inquiry(0, InquiryStatus.OPEN, comment, 0, 0, 0, time, time);

            CarportAlgorithmFactory factory = new CarportAlgorithmFactory();
            ICarportAlgorithm carportAlgorithm = factory.createCarportAlgorithm(carportDTO);
            List<BillOfMaterialLineItemDTO> lineItemDTOS = carportAlgorithm.calcCarport(carportDTO);
            BillOfMaterialDTO bill = new BillOfMaterialDTO(lineItemDTOS);
            InquiryDTO inquiryDTO = new InquiryDTO(inquiry, personDTO, Optional.of(bill), carportDTO);

            InquiryDTO dbCreated = InquiryFacade.createInquiry(inquiryDTO, connectionPool);

            request.setAttribute("inquiry", dbCreated);

            return new PageDirect(RedirectType.DEFAULT, "pages/general/inquiry");
        } catch (NumberFormatException ex) {
            request.setAttribute("errormessage", "Could not handel sizes");
            Logger.getLogger("web").log(Level.SEVERE, "Could not parse string values to integers", ex);
            return new PageDirect(RedirectType.ERROR, "error");
        }  catch (DatabaseException ex) {
            request.setAttribute("errormessage", "Could not create inquiry");
            Logger.getLogger("web").log(Level.SEVERE, "Could not create inquiry", ex);
            return new PageDirect(RedirectType.ERROR, "error");
        }
    }
}
