package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialLineItemDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductVariantDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.BillOfMaterialLineItem;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Carport;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.product.ProductVariantMapper;

import java.util.*;

public class FlatRoofAlgorithm implements ICarportAlgorithm {
    ProductVariantMapper mapper;

    private record Measurement(int size, int amount) {
    }

    public FlatRoofAlgorithm() {
        EntityManager entityManager = new EntityManager(new ConnectionPool());
        mapper = new ProductVariantMapper(entityManager);
    }


    public List<BillOfMaterialLineItemDTO> calcCarport(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();
        list.addAll(getCarportBaseMaterials(carportDTO));
        list.addAll(getRoofMaterials(carportDTO));
        list.addAll(getSterns(carportDTO));
        carportDTO.shack()
                .ifPresent(shack -> list.addAll(getShackMaterials(carportDTO.carport(), shack)));
        list.addAll(getFittingsAndScrews(carportDTO));

        return list;
    }


    private List<Measurement> calcRoofMeasurements(CarportDTO carportDTO) {
        List<Measurement> measurements = new ArrayList<>();

        int roofPlateOverlay = 120;     // This was just arbitarily selected
        int width = carportDTO.carport().getWidth();
        int length = carportDTO.carport().getLength();
        int tagpladeAmounts = (int) Math.ceil(width / 100);

        int tagpladeLength = length;
        int tagpladeLength2 = 0;

        if (length > 600) {             // if length > 600, tagpladeAmounts * 2;
            tagpladeLength = 600;       // tagpladeLength = 600 if length <= 600 else needs 180 overlap.
            tagpladeLength2 = length - 600 + roofPlateOverlay;
        }

        measurements.add(new Measurement(tagpladeLength, tagpladeAmounts));

        if (tagpladeLength2 != 0) {
            measurements.add(new Measurement(tagpladeLength2, tagpladeAmounts));
        }

        int spaerAmounts = (int) Math.ceil(length / 56.5 - 0.35) + 1; // længde skal være lig med width
        int spaerLength = width;    // space between spaer is Math.ceil((length-10)/(spaerAmounts-1))

        measurements.add(new Measurement(spaerLength, spaerAmounts));

        return measurements;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> getRoofMaterials(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();
        List<Measurement> measurements = calcRoofMeasurements(carportDTO);

        Measurement tagplade = measurements.get(0);

        list.addAll(getFromDB("product_description", "Plastmo Ecolite blåtonet", tagplade.size(), tagplade.amount(), "tagplader monteres på spær"));

        if (measurements.size() > 2) {
            Measurement tagpladeSmall = measurements.get(1);
            Measurement spear = measurements.get(2);
            list.addAll(getFromDB("product_description", "Plastmo Ecolite blåtonet", tagpladeSmall.size(), tagpladeSmall.amount(), "tagplader monteres på spær"));
            list.addAll(getFromDB("product_description", "45x195 mm. Spærtræ ubh.", spear.size(), spear.amount(), "Spær, monteres på rem"));
        } else {
            Measurement spear = measurements.get(1);
            list.addAll(getFromDB("product_description", "45x195 mm. Spærtræ ubh.", spear.size(), spear.amount(), "Spær, monteres på rem"));
        }


        return list;
    }

    public List<Measurement> calcBaseMeasurements(CarportDTO carportDTO) {
        List<Measurement> measurements = new ArrayList<>();

        int height = carportDTO.carport().getHeight();
        int width = carportDTO.carport().getWidth();
        int length = carportDTO.carport().getLength();
        Optional<Shack> optionalShack = carportDTO.shack();

        Shack shack;
        int shackWidth = 0;
        int shackLength = 0;

        if (optionalShack.isPresent()) {
            shack = optionalShack.get();
            shackWidth = shack.getWidth();
            shackLength = shack.getLength();
        }


        boolean strongerPosts = false;
        int postAmounts = 4 + 1;  // + 1 for an extra
        int postHeight = height + 90;
        int remAmounts = 2;
        int remLength = length;
        boolean shackRem = false;   //Shack rems for the rems on the shack
        int shackRems = 0;
        int shackRemLength = 0;

        if (optionalShack.isEmpty()) {   //no shack
            if (width > 390) {
                strongerPosts = true;
            }
            if (length > 510) {
                postAmounts += 2;
                if (length > 600) {
                    remAmounts += 2; // lengthOfRem would be length+60/2
                    remLength = (length + 60) / 2;
                }
            }
        } else {
            postAmounts += 2;
            if (length > 510) {
                shackRem = true;

                shackRems = 2;
                shackRemLength = shackLength + 30;
                remLength = length - shackLength + 30;
                if (shackRemLength <= 240) {
                    shackRemLength = shackRemLength * 2;
                    shackRems = 1;
                }
                // shackRemLength seems like it can have 2 'different' kind of lengths
                // shackRemLength would default to shackLength+30
                // if lengthofshackrem would be 300 or under, then it can be doubled to 600 (or lower)
                // and we can then do shackRems = 1;
                // length of carport rem would be length-shackLength+30
            }
            if (length - shackLength > 330) {
                postAmounts += 2;
            }
            if (shackLength > 270) {
                postAmounts += 2;
            }
            if (shackWidth > 270) {
                postAmounts += 2;
            }
        }

        measurements.add(new Measurement(postHeight, postAmounts));
        measurements.add(new Measurement(remLength, remAmounts));
        if (shackRem) {
            measurements.add(new Measurement(shackRemLength, shackRems));
        }

        return measurements;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> getCarportBaseMaterials(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();
        List<Measurement> measurements = calcBaseMeasurements(carportDTO);

        Measurement post = measurements.get(0);
        Measurement rem = measurements.get(1);

        list.addAll(getFromDB("product_description", "97x97 mm. trykimp. Stolpe", post.size(), post.amount(), "Stolper nedgraves 90 cm. i jord"));
        list.addAll(getFromDB("product_description", "45x195 mm. Spærtræ ubh.", rem.size(), rem.amount(), "remme i sider, sadles ned i stolper"));

        if (measurements.size() == 3) {
            Measurement shackRem = measurements.get(2);
            list.addAll(getFromDB("product_description", "45x195 mm. Spærtræ ubh.", shackRem.size(), shackRem.amount(), "remme i sider, sadles ned i stolper (skur del, deles)"));
        }

        return list;
    }

    public List<Measurement> calcSternMeasurements(CarportDTO carportDTO) {
        List<Measurement> measurements = new ArrayList<>();

        int width = carportDTO.carport().getWidth();
        int length = carportDTO.carport().getLength();

        int overSternBoardsFront = 2; // length is (width + 120)/2
        int sternBoardsFrontBackLength = (width + 120) / 2;
        int underSternBoardsFrontBack = 4; // length is same as above
        int overSternBoardsSides = 4; // length is ~ 33% more than length, rounded to nearest mod 30
        int sternBoardsSidesLength = (int) Math.ceil((Math.ceil(length * 1.33) / 2) / 30.0) * 30;
        int underSternBoardsSides = 4; // length is same as above

        int waterBoardFront = 2; // same as overSternBoardsFront
        int waterBoardSides = 4; // same as overSternBoardsSides

        measurements.add(new Measurement(sternBoardsFrontBackLength, underSternBoardsFrontBack));
        measurements.add(new Measurement(sternBoardsSidesLength, underSternBoardsSides));
        measurements.add(new Measurement(sternBoardsFrontBackLength, overSternBoardsFront));
        measurements.add(new Measurement(sternBoardsSidesLength, overSternBoardsSides));

        measurements.add(new Measurement(sternBoardsSidesLength, waterBoardSides));
        measurements.add(new Measurement(sternBoardsFrontBackLength, waterBoardFront));

        return measurements;
    }


    @Override
    public List<BillOfMaterialLineItemDTO> getSterns(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();

        List<Measurement> measurements = calcSternMeasurements(carportDTO);
        Measurement sternBoardsFrontBack = measurements.get(0);
        Measurement underSternBoardsSides = measurements.get(1);
        Measurement overSternBoardsFront = measurements.get(2);
        Measurement overSternBoardsSides = measurements.get(3);
        Measurement waterBoardSides = measurements.get(4);
        Measurement waterBoardFront = measurements.get(5);

        list.addAll(getFromDB("product_description", "25x200 mm. trykimp. Brædt", sternBoardsFrontBack.size(), sternBoardsFrontBack.amount(), "Understernbrædder til for & bag ende"));
        list.addAll(getFromDB("product_description", "25x200 mm. trykimp. Brædt", underSternBoardsSides.size(), underSternBoardsSides.amount(), "Understernbrædder til siderne"));
        list.addAll(getFromDB("product_description", "25x125 mm. trykimp. Brædt", overSternBoardsFront.size(), overSternBoardsFront.amount(), "oversternbrædder til forenden"));
        list.addAll(getFromDB("product_description", "25x125 mm. trykimp. Brædt", overSternBoardsSides.size(), overSternBoardsSides.amount(), "oversternbrædder til siderne"));

        list.addAll(getFromDB("product_description", "19x100 mm. trykimp. Brædt", waterBoardSides.size(), waterBoardSides.amount(), "Vandbrædt på stern til sider"));
        list.addAll(getFromDB("product_description", "19x100 mm. trykimp. Brædt", waterBoardFront.size(), waterBoardFront.amount(), "Vandbrædt på stern til forende"));

        return list;
    }


    public List<Measurement> calcShackMeasurements(Carport carport, Shack shack) {
        List<Measurement> measurements = new ArrayList<>();

        int height = carport.getHeight();
        int shackwidth = shack.getWidth();
        int shacklength = shack.getLength();
        int shackRems = 2;
        int shackRemLength = shacklength + 30;
        if (shackRemLength <= 240) {
            shackRemLength = shackRemLength * 2;
            shackRems = 1;
        }

        int loestholterSides = (int) Math.ceil(shacklength / 270.0) * 4;
        int loestholterSidesLength = shacklength + 30;
        int loestholterGavler = (int) Math.ceil(shackwidth / 270.0) * 6;
        int loestholterGavlerLength = ((shackRemLength / 2) * shackRems) + 30;
        double beklaedningOverlap = 7.5;
        int skackBeklaedning = (int) ((shacklength * 2 + shackwidth * 2) / beklaedningOverlap);
        int skackBeklaedningLength = height;    //210
        int vinkelBeslag = (loestholterSides + loestholterGavler) * 2;

        int laegteForDoor = 1;     // for the z on door, amount can not be adjusted directly
        int laegteForDoorLength = 420; // I think this is just constant
        int doergreb = laegteForDoor;  // maybe people can choose how many doors they want?
        int doerHaengsel = laegteForDoor * 2;

        measurements.add(new Measurement(loestholterGavlerLength, loestholterGavler));
        measurements.add(new Measurement(loestholterSidesLength, loestholterSides));
        measurements.add(new Measurement(1, vinkelBeslag));
        measurements.add(new Measurement(skackBeklaedningLength, skackBeklaedning));

        measurements.add(new Measurement(laegteForDoorLength, laegteForDoor));
        measurements.add(new Measurement(1, doergreb));
        measurements.add(new Measurement(1, doerHaengsel));

        return measurements;
    }

    public List<BillOfMaterialLineItemDTO> getShackMaterials(Carport carport, Shack shack) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();
        List<Measurement> measurements = calcShackMeasurements(carport, shack);

        Measurement loestholterGavler = measurements.get(0);
        Measurement loestholterSides = measurements.get(1);
        Measurement vinkelBeslag = measurements.get(2);
        Measurement skackBeklaedning = measurements.get(3);
        Measurement laegteForDoor = measurements.get(4);
        Measurement doergreb = measurements.get(5);
        Measurement doerHaengsel = measurements.get(6);

        list.addAll(getFromDB("product_description", "45x95 mm. regular ub.", loestholterGavler.size(), loestholterGavler.amount(), "Løstholter til skur gavle"));
        list.addAll(getFromDB("product_description", "45x95 mm. regular ub.", loestholterSides.size(), loestholterSides.amount(), "Løstholter til skur sider"));
        list.addAll(getFromDB("product_description", "Vinkelbeslag 35", vinkelBeslag.size(), vinkelBeslag.amount(), "Til montering af løsholter i skur"));
        list.addAll(getFromDB("product_description", "19x100 mm. trykimp. Brædt", skackBeklaedning.size(), skackBeklaedning.amount(), "Til beklædning af skur 1 på 2"));

        list.addAll(getFromDB("product_description", "38x73 mm. Lægte ubh.", laegteForDoor.size(), laegteForDoor.amount(), "Til Z på bagside af dør"));
        list.addAll(getFromDB("product_description", "Stalddørsgreb 50x75", doergreb.size(), doergreb.amount(), "Til lås på dør til skur"));
        list.addAll(getFromDB("product_description", "T hængsel 390 mm.", doerHaengsel.size(), doerHaengsel.amount(), "Til skurdør"));


        return list;
    }

    public List<Measurement> calcFittingsAndScrewsMeasurements(CarportDTO carportDTO) {
        List<Measurement> measurements = new ArrayList<>();

        int width = carportDTO.carport().getWidth();
        int length = carportDTO.carport().getLength();
        int tagpladeAmounts = (int) Math.ceil(width / 100);
        int spaerAmounts = (int) Math.ceil(length / 56.5) + 1; // længde skal være lig med width
        int postAmounts = 4 + 1;  // + 1 for an extra
        int shackBeklaedning = 0;

        if (carportDTO.shack().isEmpty()) {   //no shack
            if (length > 510) {
                postAmounts += 2;
            }
        } else {
            postAmounts += 2;
            if (length - carportDTO.shack().get().getLength() > 330) {
                postAmounts += 2;
            }
            if (carportDTO.shack().get().getLength() > 270) {
                postAmounts += 2;
            }
            if (carportDTO.shack().get().getWidth() > 270) {
                postAmounts += 2;
            }
            int shackTotalLength = (carportDTO.shack().get().getLength() * 2 + carportDTO.shack()
                    .get()
                    .getWidth() * 2) / 10;
            shackBeklaedning = (int) ((shackTotalLength * 1.56) - (shackTotalLength * 1.56) % 30);
        }


        int plastmoskruer200stk = tagpladeAmounts / 2; // /2 again if length > 600
        int hulbaand = 2; // just always 2
        int universalRight = spaerAmounts;
        int universalLeft = spaerAmounts;
        int skruer45x60 = 1; // always just 1 pack
        int beslagskruer4x50 = (int) Math.ceil(spaerAmounts / 5); // 3 for 15, 2 for 10
        int boltForRemOnPosts = (int) Math.ceil(postAmounts * 1.8); // if we add 1 extra post, then -1 first
        int skiverForRemOnPosts = postAmounts + 2; // if we add 1 extra, then only +1

        int skruerYderBeklaedning400 = shackBeklaedning / 100; // with 200 braedt for skur beklaedning
        // 800 skuer is needed, 1 pack is 400
        int skuerInnerBeklaedning300 = skruerYderBeklaedning400; // 300 a pack


        measurements.add(new Measurement(200, plastmoskruer200stk));
        measurements.add(new Measurement(1, hulbaand));
        measurements.add(new Measurement(1, universalRight));
        measurements.add(new Measurement(1, universalLeft));
        measurements.add(new Measurement(200, skruer45x60));
        measurements.add(new Measurement(250, beslagskruer4x50));
        measurements.add(new Measurement(1, boltForRemOnPosts));
        measurements.add(new Measurement(1, skiverForRemOnPosts));
        measurements.add(new Measurement(400, skruerYderBeklaedning400));
        measurements.add(new Measurement(300, skuerInnerBeklaedning300));

        return measurements;
    }

    public List<BillOfMaterialLineItemDTO> getFittingsAndScrews(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();
        List<Measurement> measurements = calcFittingsAndScrewsMeasurements(carportDTO);

        Measurement plastmoskruer = measurements.get(0);
        Measurement hulbaand = measurements.get(1);
        Measurement universalRight = measurements.get(2);
        Measurement universalLeft = measurements.get(3);
        Measurement skruer45x60 = measurements.get(4);
        Measurement beslagskruer4x50 = measurements.get(5);
        Measurement boltForRemOnPosts = measurements.get(6);
        Measurement skiverForRemOnPosts = measurements.get(7);
        Measurement skruerYderBeklaedning = measurements.get(8);
        Measurement skuerInnerBeklaedning = measurements.get(9);

        list.addAll(getFromDB("product_description", "Plastmo bundskruer 200 stk.", plastmoskruer.size(), plastmoskruer.amount(), "Skruer til tagplader"));
        list.addAll(getFromDB("product_description", "Hulbånd 1x20 mm. 10 mtr", hulbaand.size(), hulbaand.amount(), "Til vindkryds på spær"));
        list.addAll(getFromDB("product_description", "Universal 190 mm. højre", universalRight.size(), universalRight.amount(), "Til montering af spær på rem"));
        list.addAll(getFromDB("product_description", "Universal 190 mm. venstre", universalLeft.size(), universalLeft.amount(), "Til montering af spær på rem"));
        list.addAll(getFromDB("product_description", "4,5x60 mm. Skruer 200 stk.", skruer45x60.size(), skruer45x60.amount(), "Til montering af stern og vandbrændt"));
        list.addAll(getFromDB("product_description", "4,0x50 mm. Beslagskruer 250 stk.", beslagskruer4x50.size(), beslagskruer4x50.amount(), "Til montering af universalbeslag + hulbånd"));
        list.addAll(getFromDB("product_description", "Bræddebolt 10x120 mm.", boltForRemOnPosts.size(), boltForRemOnPosts.amount(), "Til montering af rem på stolper"));
        list.addAll(getFromDB("product_description", "Firkantskiver 40x40x11 mm.", skiverForRemOnPosts.size(), skiverForRemOnPosts.amount(), "Til montering af rem på stolper"));
        list.addAll(getFromDB("product_description", "4,5x70 mm. Skruer 400 stk.", skruerYderBeklaedning.size(), skruerYderBeklaedning.amount(), "Til montering af yderste beklædning"));
        list.addAll(getFromDB("product_description", "4,5x50 mm. Skruer 300 stk.", skuerInnerBeklaedning.size(), skuerInnerBeklaedning.amount(), "Til montering af inderste beklædning"));


        return list;
    }


    public List<BillOfMaterialLineItemDTO> getFromDB(String in, String name, int compare1, int amount, String comment) {

        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();
        Optional<List<ProductVariantDTO>> toGet;
        try {
            toGet = mapper.findAll(Map.of(in, name));
            if (toGet.isPresent()) {
                List<ProductVariantDTO> sorted = toGet.get();
                sorted.sort(Comparator.comparingInt(a -> a.size().getDetail()));
                for (ProductVariantDTO x : sorted) {
                    if (x.size().getDetail() >= compare1) {
                        BillOfMaterialLineItem y = new BillOfMaterialLineItem(0, 0, amount, comment, x.variant()
                                .getId());
                        list.add(new BillOfMaterialLineItemDTO(y, x));
                        break;
                    }
                }
            }
        } catch (DatabaseException ex) {
            ex.printStackTrace();
        }
        return list;
    }

}
