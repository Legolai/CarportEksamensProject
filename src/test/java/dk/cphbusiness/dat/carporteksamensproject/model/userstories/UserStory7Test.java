package dk.cphbusiness.dat.carporteksamensproject.model.userstories;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialLineItemDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.InquiryDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Carport;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.RoofType;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.services.CarportAlgorithmFactory;
import dk.cphbusiness.dat.carporteksamensproject.model.services.ICarportAlgorithm;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.BillOfMaterialFacade;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.InquiryFacade;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserStory7Test extends SetupDatabaseTest {

    @Test
    void updateBOMTest() throws DatabaseException {
        Shack shack = new Shack(0, 600, 210, true);
        Carport carport = new Carport(0, 600, 700, 210, RoofType.FLAT, 8, getTime(), 0);

        CarportDTO carportDTO = new CarportDTO(carport, Optional.of(shack));

        CarportAlgorithmFactory factory = new CarportAlgorithmFactory();
        ICarportAlgorithm algorithm = factory.createCarportAlgorithm(carportDTO);
        List<BillOfMaterialLineItemDTO> lineItems = algorithm.calcCarport(carportDTO);

        BillOfMaterialDTO bom = new BillOfMaterialDTO(lineItems);
        bom.lineItems().forEach(lineItem -> lineItem.lineItem().setInquiryId(1));

        boolean hasUpdated = BillOfMaterialFacade.resetBillForInquiry(1, bom, getConnectionPool());
        assertTrue(hasUpdated);
    }

    @Test
    void editPrice() throws DatabaseException {
        Optional<InquiryDTO> current = InquiryFacade.find(Map.of("person_ID", 1), getConnectionPool());
        assertTrue(current.isPresent());
        assertEquals(30000, current.get().inquiry().getPrice());
        boolean hasUpdated = InquiryFacade.updatePrice(1, 20000, getConnectionPool());
        assertTrue(hasUpdated);
        Optional<InquiryDTO> updated = InquiryFacade.find(Map.of("person_ID", 1), getConnectionPool());
        assertTrue(updated.isPresent());
        assertEquals(20000, updated.get().inquiry().getPrice());
    }
}
