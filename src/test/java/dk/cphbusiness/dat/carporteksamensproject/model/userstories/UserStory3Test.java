package dk.cphbusiness.dat.carporteksamensproject.model.userstories;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.AccountDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.InquiryDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.AccountFacade;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.InquiryFacade;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserStory3Test extends SetupDatabaseTest {

    @Test
    void GetMyInquiriesITest() throws DatabaseException {
        Optional<AccountDTO> accountDTO = AccountFacade.login("andersine.and@email.com", "tomcat1234", getConnectionPool());

        assertTrue(accountDTO.isPresent());

        String email = accountDTO.get().personDTO().person().getEmail();
        Optional<List<InquiryDTO>> myInquiries = InquiryFacade.findAll(Map.of("person_email", email), getConnectionPool());

        assertFalse(myInquiries.isEmpty());
        assertEquals(1, myInquiries.get().size());
    }

}
