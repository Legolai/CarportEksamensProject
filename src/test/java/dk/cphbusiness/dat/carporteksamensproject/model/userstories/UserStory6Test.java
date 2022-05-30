package dk.cphbusiness.dat.carporteksamensproject.model.userstories;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.InquiryDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.InquiryFacade;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserStory6Test extends SetupDatabaseTest {

    @Test
    void GetAllInquiriesITest() throws DatabaseException {
        List<InquiryDTO> inquiries = InquiryFacade.getAll(getConnectionPool());

        assertFalse(inquiries.isEmpty());
        assertEquals(1, inquiries.size());
    }

}
