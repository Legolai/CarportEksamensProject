package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.InquiryDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.*;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.InquiryFacade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InquiryMapperTest {
    private final static String USER = "teacher";
    private final static String PASSWORD = "rootuser";
    private final static String URL = "jdbc:mysql://localhost:3306/carportprojecttestdb?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";

    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);
    }

    @BeforeEach
    void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                List<String> list = Arrays.asList("`Order`", "Inquiry", "Bill_of_material_line_item", "Account", "Person",
                        "Address", "Shack", "Carport", "Product_variant", "Product", "Size", "Product_type"
                );

                for (String s : list) {
                    stmt.execute("delete from " + s);
                }

                for (String s : list) {
                    stmt.execute("ALTER TABLE " + s + " DISABLE KEYS");
                    stmt.execute("ALTER TABLE " + s + " AUTO_INCREMENT = 1");
                    stmt.execute("ALTER TABLE " + s + " ENABLE KEYS");
                }
            }
        }
        catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testConnection() throws SQLException {
        Connection connection = connectionPool.getConnection();
        assertNotNull(connection);
        if (connection != null) {
            connection.close();
        }
    }

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
        int roofMaterial = 0;

        Address address = new Address(0, streetNumber, street, null, zip, city);
        Person person = new Person(0, firstName, lastName, email, null, 0, false);
        PersonDTO personDTO = new PersonDTO(person, address);

        Optional<Shack> optionalShack = Optional.empty();

        LocalDateTime time = LocalDateTime.now().withNano(0);
        Carport carport = new Carport(0, carportWidth, carportLength, 210, RoofType.FLAT, roofMaterial, time, 0);
        CarportDTO carportDTO = new CarportDTO(carport, optionalShack);

        Inquiry inquiry = new Inquiry(0, InquiryStatus.OPEN, comment, 0, 0, time, time);
        InquiryDTO inquiryDTO = new InquiryDTO(inquiry, personDTO, Optional.empty(), carportDTO);


        Address address1 = new Address(1, streetNumber, street, null, zip, city);
        Person person1 = new Person(1, firstName, lastName, email, null, 1, false);
        PersonDTO personDTO1 = new PersonDTO(person1, address1);

        Optional<Shack> optionalShack1 = Optional.empty();

        Carport carport1 = new Carport(1, carportWidth, carportLength, 210, RoofType.FLAT, roofMaterial, time, 0);
        CarportDTO carportDTO1 = new CarportDTO(carport1, optionalShack1);

        Inquiry inquiry1 = new Inquiry(1, InquiryStatus.OPEN, comment, 1, 1, time, time);
        InquiryDTO expected = new InquiryDTO(inquiry1, personDTO1, Optional.empty(), carportDTO1);

        InquiryDTO actual = InquiryFacade.createInquiry(inquiryDTO, connectionPool);

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
        int roofMaterial = 0;

        Address address = new Address(0, streetNumber, street, null, zip, city);
        Person person = new Person(0, firstName, lastName, email, null, 0, false);
        PersonDTO personDTO = new PersonDTO(person, address);

        Optional<Shack> optionalShack = Optional.empty();

        LocalDateTime time = LocalDateTime.now().withNano(0);
        Carport carport = new Carport(0, carportWidth, carportLength, 210, RoofType.FLAT, roofMaterial, time, 0);
        CarportDTO carportDTO = new CarportDTO(carport, optionalShack);

        Inquiry inquiry = new Inquiry(0, InquiryStatus.OPEN, comment, 0, 0, time, time);
        InquiryDTO inquiryDTO = new InquiryDTO(inquiry, personDTO, Optional.empty(), carportDTO);

        Carport carport1 = new Carport(0, carportWidth+100, carportLength+100, 210, RoofType.FLAT, roofMaterial, time, 0);
        CarportDTO carportDTO1 = new CarportDTO(carport1, optionalShack);
        InquiryDTO inquiryDTO1 = new InquiryDTO(inquiry, personDTO, Optional.empty(), carportDTO1);

        InquiryFacade.createInquiry(inquiryDTO, connectionPool);
        InquiryFacade.createInquiry(inquiryDTO1, connectionPool);

        Optional<List<InquiryDTO>> listOptional = InquiryFacade.findAll(Map.of("person_email","nic1@gmail.com"), connectionPool);
        assertTrue(listOptional.isPresent());
        assertEquals(2, listOptional.get().size());
    }

}