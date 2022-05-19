package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialLineItemDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductVariantDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarportAlgorithmTest {

    static CarportDTO carportDTO;

    @BeforeEach
    void setUp() {
        Carport carport = new Carport(0, 600, 780, 210, RoofType.FLAT, 0, LocalDateTime.now(), 0);
        Shack shack = new Shack(0, 540, 210, true);
        carportDTO = new CarportDTO(carport, Optional.of(shack));
    }

    @Test
    void testFlatRoofItemList() {
//        Spær, tagplader
        List<BillOfMaterialLineItemDTO> expected = new ArrayList<>();
        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 6, "tagplader monteres på spær", 43),
                new ProductVariantDTO(
                        new ProductVariant(43, 8, 14, false),
                        new ProductDTO(
                                new Product(8, "Plastmo Ecolite blåtonet", 10, Unit.METER, AmountUnit.PIECE, 0, false),
                                new ProductType(6, "Tagplade", false)
                        ),
                        new Size (14, 600, SizeType.LENGTH, false)
                )
        ));
        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 6, "tagplader monteres på spær", 0),
                new ProductVariantDTO(
                        new ProductVariant(0, 0, 0, false),
                        new ProductDTO(
                                new Product(0, "Plastmo Ecolite blåtonet", 10, Unit.METER, AmountUnit.PIECE, 0, false),
                                new ProductType(0, "Tagplade", false)
                        ),
                        new Size (0, 360, SizeType.LENGTH, false)
                )
        ));
        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 15, "Spær, monteres på rem", 43),
                new ProductVariantDTO(
                        new ProductVariant(43, 8, 14, false),
                        new ProductDTO(
                                new Product(8, "45x195 mm. spærtræ ubh.", 12, Unit.METER, AmountUnit.PIECE, 0, false),
                                new ProductType(0, "Spærtræ", false)
                        ),
                        new Size (14, 600, SizeType.LENGTH, false)
                )
        ));


        CarportAlgorithmFactory factory = new CarportAlgorithmFactory();
        ICarportAlgorithm carportAlgorithm = factory.createCarportAlgorithm(carportDTO);
        List<BillOfMaterialLineItemDTO> actual = carportAlgorithm.calcRoof(carportDTO);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));
    }
    @Test
    void testBaseItemList() {
//
        List<BillOfMaterialLineItemDTO> expected = new ArrayList<>();
        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 11, "Stolper nedgraves 90 cm. i jord", 0),
                new ProductVariantDTO(
                        new ProductVariant(0, 0, 0, false),
                        new ProductDTO(
                                new Product(0, "97x97 mm. trykimp. Stolpe", 15, Unit.METER, AmountUnit.PIECE, 0, false),
                                new ProductType(0, "Stolpe", false)
                        ),
                        new Size (0, 300, SizeType.LENGTH, false)
                )
        ));

        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 2, "Remme i sider, sadles ned i stolper", 0),
                new ProductVariantDTO(
                        new ProductVariant(0, 0, 0, false),
                        new ProductDTO(
                                new Product(0, "45x195 mm. spærtræ ubh.", 10, Unit.METER, AmountUnit.PIECE, 0, false),
                                new ProductType(0, "Spærtre", false)
                        ),
                        new Size (0, 600, SizeType.LENGTH, false)
                )
        ));
        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 1, "Remme i sider, sadles ned i stolper (skur del, deles)", 0),
                new ProductVariantDTO(
                        new ProductVariant(0, 0, 0, false),
                        new ProductDTO(
                                new Product(0, "45x195 mm. spærtræ ubh.", 10, Unit.METER, AmountUnit.PIECE, 0, false),
                                new ProductType(0, "Spærtre", false)
                        ),
                        new Size (0, 480, SizeType.LENGTH, false)
                )
        ));


        CarportAlgorithmFactory factory = new CarportAlgorithmFactory();
        ICarportAlgorithm carportAlgorithm = factory.createCarportAlgorithm(carportDTO);
        List<BillOfMaterialLineItemDTO> actual = carportAlgorithm.calcBase(carportDTO);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));
    }
    @Test
    void testShackItemList() {
//      Til lås på dør i skur
//      Til skurdør
//      Til montering af løsholter i skur
//      til beklædning af skur 1 på 2
//      til z på bagside af dør
//      løsholter til skur gavle
//      løsholter til skur sider

        List<BillOfMaterialLineItemDTO> expected = new ArrayList<>();
        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 1, "til z på bagside af dør", 0),
                new ProductVariantDTO(
                        new ProductVariant(0, 0, 0, false),
                        new ProductDTO(
                                new Product(0, "38x73 mm. Lægte ubh.", 15, Unit.METER, AmountUnit.PIECE, 0, false),
                                new ProductType(0, "Lægte", false)
                        ),
                        new Size (0, 420, SizeType.LENGTH, false)
                )
        ));

        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 12, "løsholter til skur gavle", 0),
                new ProductVariantDTO(
                        new ProductVariant(0, 0, 0, false),
                        new ProductDTO(
                                new Product(0, "45x95 mm. Reglar ub.", 10, Unit.METER, AmountUnit.PIECE, 0, false),
                                new ProductType(0, "Træ", false)
                        ),
                        new Size (0, 270, SizeType.LENGTH, false)
                )
        ));
        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 4, "løsholter til skur sider", 0),
                new ProductVariantDTO(
                        new ProductVariant(0, 0, 0, false),
                        new ProductDTO(
                                new Product(0, "45x95 mm. Reglar ub.", 10, Unit.METER, AmountUnit.PIECE, 0, false),
                                new ProductType(0, "Træ", false)
                        ),
                        new Size (0, 240, SizeType.LENGTH, false)
                )
        ));
        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 200, "til beklædning af skur 1 på 2", 0),
                new ProductVariantDTO(
                        new ProductVariant(0, 0, 0, false),
                        new ProductDTO(
                                new Product(0, "19x100 mm. trykimp. Brædt", 10, Unit.METER, AmountUnit.PIECE, 0, false),
                                new ProductType(0, "Brædt", false)
                        ),
                        new Size (0, 210, SizeType.LENGTH, false)
                )
        ));
        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 1, "Til lås på dør i skur", 0),
                new ProductVariantDTO(
                        new ProductVariant(0, 0, 0, false),
                        new ProductDTO(
                                new Product(0, "stalddørsgreb 50x75", 10, Unit.PIECE, AmountUnit.SET, 0, false),
                                new ProductType(0, "Træ", false)
                        ),
                        new Size (0, 1, SizeType.PIECES, false)
                )
        ));
        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 2, "Til skurdør", 0),
                new ProductVariantDTO(
                        new ProductVariant(0, 0, 0, false),
                        new ProductDTO(
                                new Product(0, "t hængsel 390 mm", 10, Unit.PIECE, AmountUnit.PIECE, 0, false),
                                new ProductType(0, "Hængsel", false)
                        ),
                        new Size (0, 1, SizeType.PIECES, false)
                )
        ));
        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 32, "Til montering af løsholter i skur", 0),
                new ProductVariantDTO(
                        new ProductVariant(0, 0, 0, false),
                        new ProductDTO(
                                new Product(0, "vinkelbeslag 35", 10, Unit.PIECE, AmountUnit.PIECE, 0, false),
                                new ProductType(0, "Beslag", false)
                        ),
                        new Size (0, 1, SizeType.PIECES, false)
                )
        ));




        CarportAlgorithmFactory factory = new CarportAlgorithmFactory();
        ICarportAlgorithm carportAlgorithm = factory.createCarportAlgorithm(carportDTO);
        List<BillOfMaterialLineItemDTO> actual = carportAlgorithm.calcBase(carportDTO);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));
        assertEquals(expected.get(3), actual.get(3));
        assertEquals(expected.get(4), actual.get(4));
        assertEquals(expected.get(5), actual.get(5));
        assertEquals(expected.get(6), actual.get(6));
    }

    @Test
    void testSternsItemList() {
//      understernbrædder til for & bag ende
//      understernbrædder til siderne
//      oversternbrædder til forenden
//      oversternbrædder til siderne
//      vandbrædt på stern i sider
//      vandbrædt på stern i forende

        List<BillOfMaterialLineItemDTO> expected = new ArrayList<>();
        expected.add(new BillOfMaterialLineItemDTO(
                new BillOfMaterialLineItem(0, 0, 1, "til z på bagside af dør", 0),
                new ProductVariantDTO(
                        new ProductVariant(0, 0, 0, false),
                        new ProductDTO(
                                new Product(0, "38x73 mm. Lægte ubh.", 15, Unit.METER, AmountUnit.PIECE, 0, false),
                                new ProductType(0, "Lægte", false)
                        ),
                        new Size (0, 420, SizeType.LENGTH, false)
                )
        ));
        CarportAlgorithmFactory factory = new CarportAlgorithmFactory();
        ICarportAlgorithm carportAlgorithm = factory.createCarportAlgorithm(carportDTO);
        List<BillOfMaterialLineItemDTO> actual = carportAlgorithm.calcBase(carportDTO);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));

    }

    @Test
    void testScreewsItemList() {
//        Skruer til tagplader
//        Til vindkryds på spær
//        Til montering af spær på rem
//        Til montering af spær på rem
//        Til montering af stern&vandbrædt
//        Til montering af universalbeslag + hulbånd Til montering af rem på stolper
//        Til montering af rem på stolper
//        til montering af yderste beklædning
//        til montering af inderste beklædning

    }




}