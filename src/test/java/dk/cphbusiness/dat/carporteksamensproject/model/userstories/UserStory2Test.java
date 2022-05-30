package dk.cphbusiness.dat.carporteksamensproject.model.userstories;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.InquiryDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.*;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.InquiryFacade;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserStory2Test extends SetupDatabaseTest {

    @Test
    void makeInquiry() throws DatabaseException {
        Address address = new Address(0, "10", "testby", null, "4242", "testby");
        Person person = new Person(0, "test", "test", "test@email.com", null, 0, false);
        PersonDTO personDTO = new PersonDTO(person, address);
        Shack shack = new Shack(0, 210, 300, true);
        Carport carport = new Carport(0, 600, 600, 210, RoofType.FLAT, 8, getTime(), 0);
        CarportDTO carportDTO = new CarportDTO(carport, Optional.of(shack));
        Inquiry inquiry = new Inquiry(0, InquiryStatus.OPEN, "", 0, 0, 40000, getTime(), getTime());
        InquiryDTO inquiryDTO = new InquiryDTO(inquiry, personDTO, Optional.empty(), carportDTO);
        InquiryDTO actual = InquiryFacade.createInquiry(inquiryDTO, getConnectionPool());

        Address address1 = new Address(3, "10", "testby", null, "4242", "testby");
        Person person1 = new Person(2, "test", "test", "test@email.com", null, 3, false);
        PersonDTO personDTO1 = new PersonDTO(person1, address1);
        Shack shack1 = new Shack(2, 210, 300, true);
        Carport carport1 = new Carport(2, 600, 600, 210, RoofType.FLAT, 8, getTime(), 2);
        CarportDTO carportDTO1 = new CarportDTO(carport1, Optional.of(shack1));
        Inquiry inquiry1 = new Inquiry(2, InquiryStatus.OPEN, "", 2, 2, 40000, getTime(), getTime());
        InquiryDTO expected = new InquiryDTO(inquiry1, personDTO1, Optional.empty(), carportDTO1);

        assertEquals(expected, actual);
    }

}
