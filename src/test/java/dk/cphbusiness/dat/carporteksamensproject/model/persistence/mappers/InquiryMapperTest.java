package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.InquiryDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.*;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.InquiryFacade;
import dk.cphbusiness.dat.carporteksamensproject.model.userstories.SetupTestDatabase;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InquiryMapperTest extends SetupTestDatabase {

    @Test
    void insertWithoutBill() throws DatabaseException {
        String firstName = "Nicolai";
        String lastName = "Peterson";
        String email = "nicpet@email.com";

        String street = "Solsikkevej";
        String streetNumber = "14B";
        String city = "Drømmeland";
        String zip = "1234";

        String comment = "";

        int carportWidth = 200;
        int carportLength = 300;
        int roofMaterial = 8;

        Address address = new Address(0, streetNumber, street, null, zip, city);
        Person person = new Person(0, firstName, lastName, email, null, 0, false);
        PersonDTO personDTO = new PersonDTO(person, address);

        Optional<Shack> optionalShack = Optional.of(new Shack(0,0,0, false));

        LocalDateTime time = LocalDateTime.now().withNano(0);
        Carport carport = new Carport(0, carportWidth, carportLength, 210, RoofType.FLAT, roofMaterial, time, 0);
        CarportDTO carportDTO = new CarportDTO(carport, optionalShack);

        Inquiry inquiry = new Inquiry(0, InquiryStatus.OPEN, comment, 0, 0, 1000, time, time);
        InquiryDTO inquiryDTO = new InquiryDTO(inquiry, personDTO, Optional.empty(), carportDTO);

        Address address1 = new Address(3, streetNumber, street, null, zip, city);
        Person person1 = new Person(2, firstName, lastName, email, null, 3, false);
        PersonDTO personDTO1 = new PersonDTO(person1, address1);

        Optional<Shack> optionalShack1 = Optional.of(new Shack(2,0,0,false));

        Carport carport1 = new Carport(2, carportWidth, carportLength, 210, RoofType.FLAT, roofMaterial, time, 2);
        CarportDTO carportDTO1 = new CarportDTO(carport1, optionalShack1);

        Inquiry inquiry1 = new Inquiry(2, InquiryStatus.OPEN, comment, 2, 2, 1000, time, time);
        InquiryDTO expected = new InquiryDTO(inquiry1, personDTO1, Optional.empty(), carportDTO1);

        InquiryDTO actual = InquiryFacade.createInquiry(inquiryDTO, getConnectionPool());

        assertEquals(expected, actual);
    }

    @Test
    void findAllForEmail() throws DatabaseException {
        String firstName = "Nicolai";
        String lastName = "Peterson";
        String email = "nic1@gmail.com";

        String street = "Solsikkevej";
        String streetNumber = "14B";
        String city = "Drømmeland";
        String zip = "1234";

        String comment = "";

        int carportWidth = 200;
        int carportLength = 300;
        int roofMaterial = 8;

        Address address = new Address(0, streetNumber, street, null, zip, city);
        Person person = new Person(0, firstName, lastName, email, null, 0, false);
        PersonDTO personDTO = new PersonDTO(person, address);

        Optional<Shack> optionalShack = Optional.of(new Shack(0, 0, 0, false));

        LocalDateTime time = LocalDateTime.now().withNano(0);
        Carport carport = new Carport(0, carportWidth, carportLength, 210, RoofType.FLAT, roofMaterial, time, 0);
        CarportDTO carportDTO = new CarportDTO(carport, optionalShack);

        Inquiry inquiry = new Inquiry(0, InquiryStatus.OPEN, comment, 0, 0, 1000, time, time);
        InquiryDTO inquiryDTO = new InquiryDTO(inquiry, personDTO, Optional.empty(), carportDTO);

        Carport carport1 = new Carport(0, carportWidth + 100, carportLength + 100, 210, RoofType.FLAT, roofMaterial, time, 0);
        CarportDTO carportDTO1 = new CarportDTO(carport1, optionalShack);
        InquiryDTO inquiryDTO1 = new InquiryDTO(inquiry, personDTO, Optional.empty(), carportDTO1);

        InquiryFacade.createInquiry(inquiryDTO, getConnectionPool());
        InquiryFacade.createInquiry(inquiryDTO1, getConnectionPool());

        Optional<List<InquiryDTO>> listOptional = InquiryFacade.findAll(Map.of("person_email", "nic1@gmail.com"), getConnectionPool());
        assertTrue(listOptional.isPresent());
        assertEquals(2, listOptional.get().size());
    }

}