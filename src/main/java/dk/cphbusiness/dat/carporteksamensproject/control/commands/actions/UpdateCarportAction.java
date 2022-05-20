package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.ProtectedPage;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialLineItemDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.*;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.CarportAlgorithmFactory;
import dk.cphbusiness.dat.carporteksamensproject.model.services.ICarportAlgorithm;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.BillOfMaterialFacade;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.CarportFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateCarportAction extends ProtectedPage {
    public UpdateCarportAction(Role role) {
        super("", role);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) throws DatabaseException {

        String inquiryIdString = request.getParameter("inquiry-ID");

        String carportIdString = request.getParameter("carport-ID");
        String carportWidthString = request.getParameter("carport-width");
        String carportLengthString = request.getParameter("carport-length");
        String roofMaterialString = request.getParameter("roof-material");

        String hasShackString = request.getParameter("has-shack");
        String shackIdString = request.getParameter("shack-ID");
        String shackWidthString = request.getParameter("shack-width");
        String shackLengthString = request.getParameter("shack-length");


        try {
            int inquiryID = Integer.parseInt(inquiryIdString);
            int carportID = Integer.parseInt(carportIdString);
            int carportWidth = Integer.parseInt(carportWidthString);
            int carportLength = Integer.parseInt(carportLengthString);
            int roofMaterial = Integer.parseInt(roofMaterialString);
            boolean hasShack = Boolean.parseBoolean(hasShackString);
            int shackID = 0;
            if(shackIdString != null){
                shackID = Integer.parseInt(shackIdString);
            }

            Optional<Shack> optionalShack = Optional.empty();
            if (hasShack) {
                int shackWidth = Integer.parseInt(shackWidthString);
                int shackLength = Integer.parseInt(shackLengthString);
                Shack shack = new Shack(shackID, shackWidth, shackLength, true);
                if (shackID == 0){
                    Shack dbShack = CarportFacade.createShack(shack, connectionPool);
                    shackID = dbShack.getId();
                    optionalShack = Optional.of(dbShack);
                } else {
                    optionalShack = Optional.of(shack);
                }
            }

            Carport carport = new Carport(carportID, carportWidth, carportLength, 210, RoofType.FLAT, roofMaterial, null, shackID);

            CarportDTO carportDTO = new CarportDTO(carport, optionalShack);
            if(!CarportFacade.updateCarport(carportDTO, connectionPool)) {
                return new PageDirect(RedirectType.ERROR, "error");
            }

            CarportAlgorithmFactory factory = new CarportAlgorithmFactory();
            ICarportAlgorithm carportAlgorithm = factory.createCarportAlgorithm(carportDTO);
            List<BillOfMaterialLineItemDTO> lineItemDTOS = carportAlgorithm.calcCarport(carportDTO);

            lineItemDTOS.forEach(lineItem -> lineItem.lineItem().setInquiryId(inquiryID));
            BillOfMaterialDTO bill = new BillOfMaterialDTO(lineItemDTOS);

            BillOfMaterialFacade.resetBillForInquiry(inquiryID, bill, connectionPool);

            request.setAttribute("inquiry-ID", inquiryID);
            return new PageDirect(RedirectType.COMMAND, "edit-inquiry-page");

        } catch (NumberFormatException ex) {
                request.setAttribute("errormessage", "Could not handel sizes");
                Logger.getLogger("web").log(Level.SEVERE, "Could not parse string values to integers", ex);
                return new PageDirect(RedirectType.ERROR, "error");
        }  catch (DatabaseException ex) {
            request.setAttribute("errormessage", "Could not update carport information");
            Logger.getLogger("web").log(Level.SEVERE, "Could not update carport information", ex);
            return new PageDirect(RedirectType.ERROR, "error");
        }
    }
}
