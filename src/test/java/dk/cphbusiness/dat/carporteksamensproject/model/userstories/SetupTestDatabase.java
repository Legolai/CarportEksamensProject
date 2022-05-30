package dk.cphbusiness.dat.carporteksamensproject.model.userstories;

import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class SetupTestDatabase {
    private final static String USER = "teacher";
    private final static String PASSWORD = "rootuser";
    private final static String URL = "jdbc:mysql://localhost:3306/carportprojecttestdb?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";
    private static ConnectionPool connectionPool;

    private static LocalDateTime time;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);
    }

    @BeforeEach
    void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                List<String> list = Arrays.asList("Bill_of_material_line_item", "Inquiry", "Account", "Person",
                        "Address", "Carport", "Shack", "Product_variant", "Product", "Size", "Product_type"
                );

                time = LocalDateTime.now().withNano(0);
                for (String s : list) {
                    stmt.execute("delete from " + s);
                }

                Collections.reverse(list);
                for (String s : list) {
                    stmt.execute("ALTER TABLE " + s + " DISABLE KEYS");
                    stmt.execute("ALTER TABLE " + s + " AUTO_INCREMENT = 1");
                    switch (s) {
                        case "Address" -> stmt.execute("INSERT INTO `Address` (`address_number`,`address_street`, " +
                                "`address_floor`, `address_zipcode`, `address_city_name`) " +
                                "VALUES ('12', 'drømmegade', null, '4000', 'andeby'), ('10', 'drømmegade', null, '4000', 'andeby')");
                        case "Person" -> stmt.execute("INSERT INTO `Person` (`person_forename`, " +
                                "`person_surname`,`person_email`,`person_phone_number`,`address_ID`," +
                                " `person_deleted`) VALUES ('andersine', 'and', 'andersine.and@email.com', null, 1, 0);");
                        case "Account" -> stmt.execute("INSERT INTO `Account` (`account_created`," +
                                "`account_deleted`, `account_password`,`person_ID`,`account_role`) " +
                                "VALUES ('" + time + "', 0, 'tomcat1234', 1, 'EMPLOYEE');");
                        case "Product_type" -> stmt.execute("INSERT INTO Product_type (product_type_name) values " +
                                "('Stolpe'), ('Spærtræ'), ('Brædt'), ('Træ'), ('Lægte'), ('Tagplade'), ('Skrue'), " +
                                "('Bolte'), ('Skive'), ('Bånd'), ('Universal'), ('Dørgreb'), ('Hængsel'), ('Beslag');");
                        case "Product" -> stmt.execute("INSERT INTO Product (product_description, product_unit_price," +
                                " product_unit, product_type_ID, product_amount_unit)\n" +
                                "values ('97x97 mm. trykimp. Stolpe', 50, 'METER', 1, 'PIECE'),\n" +
                                "('45x195 mm. Spærtræ ubh.', 50, 'METER', 2, 'PIECE'),\n" +
                                "('25x200 mm. trykimp. Brædt', 40, 'METER', 3, 'PIECE'),\n" +
                                "('25x125 mm. trykimp. Brædt', 30, 'METER', 3, 'PIECE'),\n" +
                                "('19x100 mm. trykimp. Brædt', 20, 'METER', 3, 'PIECE'),\n" +
                                "('45x95 mm. regular ub.', 30, 'METER', 4, 'PIECE'),\n" +
                                "('38x73 mm. Lægte ubh.', 10, 'METER', 5, 'PIECE'),\n" +
                                "('Plastmo Ecolite blåtonet', 15, 'METER', 6, 'PIECE'),\n" +
                                "('Plastmo bundskruer 200 stk.', 5, 'PIECE', 7, 'PACK'),\n" +
                                "('4,0x50 mm. Beslagskruer 250 stk.', 5, 'PIECE', 7, 'PACK'),\n" +
                                "('4,5x50 mm. Skruer\t300\tstk.', 5, 'PIECE', 7, 'PACK'),\n" +
                                "('4,5x60 mm. Skruer 200 stk.', 5, 'PIECE', 7, 'PACK'),\n" +
                                "('4,5x70 mm. Skruer 400 stk.', 5, 'PIECE', 7, 'PACK'),\n" +
                                "('Bræddebolt 10x120 mm.', 10, 'PIECE', 8, 'PIECE'),\n" +
                                "('Firkantskiver 40x40x11 mm.', 15, 'PIECE', 9, 'PIECE'),\n" +
                                "('Hulbånd 1x20 mm. 10 mtr', 5, 'METER', 10, 'ROLL'),\n" +
                                "('Universal 190 mm. højre', 10, 'PIECE', 11, 'PIECE'),\n" +
                                "('Universal 190 mm. venstre', 10, 'PIECE', 11, 'PIECE'),\n" +
                                "('Stalddørsgreb 50x75', 35, 'PIECE', 12, 'SET'),\n" +
                                "('T hængsel 390 mm.', 15, 'PIECE', 13, 'PIECE'),\n" +
                                "('Vinkelbeslag 35', 15, 'PIECE', 14, 'PIECE');");
                        case "Size" -> stmt.execute("INSERT INTO Size (size_detail, size_type)\n" +
                                "values (210, 'LENGTH'), (240, 'LENGTH'), (270, 'LENGTH'),\n" +
                                "(300, 'LENGTH'), (330, 'LENGTH'), (360, 'LENGTH'), (390, 'LENGTH'), (420, 'LENGTH'), " +
                                "(450, 'LENGTH'), (480, 'LENGTH'), (510, 'LENGTH'), (540, 'LENGTH'), (570, 'LENGTH'), " +
                                "(600, 'LENGTH'), (100, 'PIECES'), (150, 'PIECES'), (200, 'PIECES'), (250, 'PIECES'), " +
                                "(300, 'PIECES'), (350, 'PIECES'), (400, 'PIECES');");
                        case "Product_variant" -> stmt.execute("INSERT INTO Product_variant (product_ID, size_ID) " +
                                "values (1, 4), (1, 5), (2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6), (2, 7), " +
                                "(2, 8), (2, 9), (2, 10), (2, 11), (2, 12), (2, 13), (2, 14), (3, 4), (3, 6), (3, 8), " +
                                "(3, 10), (3, 12), (3, 14), (4, 4), (4, 6), (4, 8), (4, 10), (4, 12), (4, 14), (5, 4), " +
                                "(5, 6), (5, 8), (5, 10), (5, 12), (5, 14), (6, 1), (6, 2), (6, 3), (6, 4), (7, 8), " +
                                "(8, 2), (8, 6), (8, 10), (8, 14), (9, 17), (10, 18), (11, 17), (10, 19), (10, 21), " +
                                "(12, 17), (12, 19), (12, 21), (13, 17), (12, 19), (12, 21);");
                        case "Shack" -> stmt.execute("INSERT INTO `Shack` (`shack_length`,`shack_left_align`," +
                                "`shack_width`,`shack_updated`) VALUES (200, 1, 300, '" + time + "');");

                        case "Carport" -> stmt.execute("INSERT INTO `Carport` (`carport_height`, `carport_width`, " +
                                "`carport_length`, `carport_roof_type`, `carport_updated`, `shack_ID`, `carport_roof_material`)\n" +
                                "VALUES (210, 700, 600, 'FLAT', '" + time + "', 1, 8);");
                        case "Inquiry" -> stmt.execute("INSERT INTO `Inquiry` (`inquiry_comment`,`person_ID`," +
                                "`inquiry_created`,`inquiry_updated`,`inquiry_status`,`carport_ID`,`inquiry_price`)" +
                                "VALUES ('', 1, '" + time + "', '"+ time +"', 'OPEN', 1, 30000);");

                    }
                    stmt.execute("ALTER TABLE " + s + " ENABLE KEYS");
                }
            }
        } catch (SQLException throwables) {
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

    public static ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public static LocalDateTime getTime() {
        return time;
    }
}
