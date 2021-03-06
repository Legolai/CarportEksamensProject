package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialLineItemDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductVariantDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.BillOfMaterialLineItem;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.product.ProductVariantMapper;

import java.util.*;

public class FlatRoofAlgorithm implements ICarportAlgorithm {
    ProductVariantMapper mapper;

    public FlatRoofAlgorithm() {
        EntityManager entityManager = new EntityManager(new ConnectionPool());
        mapper = new ProductVariantMapper(entityManager);
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcCarport(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();
        list.addAll(calcBase(carportDTO));
        list.addAll(calcRoof(carportDTO));
        list.addAll(calcSterns(carportDTO));
        if (carportDTO.shack().isPresent()) {
            list.addAll(calcShack(carportDTO));
        }
        list.addAll(calcFittingsAndScrews(carportDTO));

        return list;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcRoof(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();
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

        list.addAll(getFromDB("product_description", "Plastmo Ecolite bl??tonet", tagpladeLength, tagpladeAmounts, "tagplader monteres p?? sp??r"));
        if (tagpladeLength2 != 0) {
            list.addAll(getFromDB("product_description", "Plastmo Ecolite bl??tonet", tagpladeLength2, tagpladeAmounts, "tagplader monteres p?? sp??r"));
        }

        int spaerAmounts = (int) Math.ceil(length / 56.5 - 0.35) + 1; // l??ngde skal v??re lig med width
        int spaerLength = width;    // space between spaer is Math.ceil((length-10)/(spaerAmounts-1))

        list.addAll(getFromDB("product_description", "45x195 mm. Sp??rtr?? ubh.", spaerLength, spaerAmounts, "Sp??r, monteres p?? rem"));


        return list;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcBase(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();

        int height = carportDTO.carport().getHeight();
        int width = carportDTO.carport().getWidth();
        int length = carportDTO.carport().getLength();
        Shack shack;
        int shackwidth = 0;
        int shacklength = 0;
        if (carportDTO.shack().isPresent()) {
            shack = carportDTO.shack().get();
            shackwidth = shack.getWidth();
            shacklength = shack.getLength();
        }


        boolean strongerPosts = false;
        int postAmounts = 4 + 1;  // + 1 for an extra
        int postHeight = height + 90;
        int remAmounts = 2;
        int remLength = length;
        boolean shackRem = false;   //Shack rems for the rems on the shack
        int shackRems = 0;
        int shackRemLength = 0;

        if (!carportDTO.shack().isPresent()) {   //no shack
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
                shackRemLength = shacklength + 30;
                if (shackRemLength <= 240) {
                    shackRemLength = shackRemLength * 2;
                    shackRems = 1;
                }
                remLength = length - shacklength + 30;
                // shackRemLength seems like it can have 2 'different' kind of lengths
                // shackRemLength would default to shacklength+30
                // if lengthofshackrem would be 300 or under, then it can be doubled to 600 (or lower)
                // and we can then do shackRems = 1;
                // length of carport rem would be length-shacklength+30
            }
            if (length - shacklength > 330) {
                postAmounts += 2;
            }
            if (shacklength > 270) {
                postAmounts += 2;
            }
            if (shackwidth > 270) {
                postAmounts += 2;
            }
        }

        list.addAll(getFromDB("product_description", "97x97 mm. trykimp. Stolpe", postHeight, postAmounts, "Stolper nedgraves 90 cm. i jord"));
        list.addAll(getFromDB("product_description", "45x195 mm. Sp??rtr?? ubh.", remLength, remAmounts, "remme i sider, sadles ned i stolper"));
        if (shackRem) {
            list.addAll(getFromDB("product_description", "45x195 mm. Sp??rtr?? ubh.", shackRemLength, shackRems, "remme i sider, sadles ned i stolper (skur del, deles)"));
        }

        return list;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcSterns(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();

        int width = carportDTO.carport().getWidth();
        int length = carportDTO.carport().getLength();

        int overSternBoardsFront = 2; // length is (width + 120)/2
        int sternBoardsFrontBackLength = (width + 120) / 2;
        int underSternBoardsFrontBack = 4; // length is same as above
        int overSternBoardsSides = 4; // length is ~ 33% more than length, rounded to nearest mod 30
        int sternBoardsSidesLength = (int) Math.ceil((Math.ceil(length * 1.33) / 2) / 30.0) * 30;
        int underSternBoardsSides = 4; // length is same as above

        list.addAll(getFromDB("product_description", "25x200 mm. trykimp. Br??dt", sternBoardsFrontBackLength, underSternBoardsFrontBack, "Understernbr??dder til for & bag ende"));
        list.addAll(getFromDB("product_description", "25x200 mm. trykimp. Br??dt", sternBoardsSidesLength, underSternBoardsSides, "Understernbr??dder til siderne"));
        list.addAll(getFromDB("product_description", "25x125 mm. trykimp. Br??dt", sternBoardsFrontBackLength, overSternBoardsFront, "oversternbr??dder til forenden"));
        list.addAll(getFromDB("product_description", "25x125 mm. trykimp. Br??dt", sternBoardsSidesLength, overSternBoardsSides, "oversternbr??dder til siderne"));


        int waterBoardFront = 2; // same as overSternBoardsFront
        int waterBoardSides = 4; // same as overSternBoardsSides

        list.addAll(getFromDB("product_description", "19x100 mm. trykimp. Br??dt", sternBoardsSidesLength, waterBoardSides, "Vandbr??dt p?? stern til sider"));
        list.addAll(getFromDB("product_description", "19x100 mm. trykimp. Br??dt", sternBoardsFrontBackLength, waterBoardFront, "Vandbr??dt p?? stern til forende"));


        return list;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcShack(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();

        int height = carportDTO.carport().getHeight();
        Shack shack = carportDTO.shack().get();
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

        list.addAll(getFromDB("product_description", "45x95 mm. regular ub.", loestholterGavlerLength, loestholterGavler, "L??stholter til skur gavle"));
        list.addAll(getFromDB("product_description", "45x95 mm. regular ub.", loestholterSidesLength, loestholterSides, "L??stholter til skur sider"));
        list.addAll(getFromDB("product_description", "Vinkelbeslag 35", 1, vinkelBeslag, "Til montering af l??sholter i skur"));
        list.addAll(getFromDB("product_description", "19x100 mm. trykimp. Br??dt", skackBeklaedningLength, skackBeklaedning, "Til bekl??dning af skur 1 p?? 2"));


        int laegteForDoor = 1;     // for the z on door, amount can not be adjusted directly
        int laegteForDoorLength = 420; // I think this is just constant
        int doergreb = laegteForDoor;  // maybe people can choose how many doors they want?
        int doerHaengsel = laegteForDoor * 2;

        list.addAll(getFromDB("product_description", "38x73 mm. L??gte ubh.", laegteForDoorLength, laegteForDoor, "Til Z p?? bagside af d??r"));
        list.addAll(getFromDB("product_description", "Staldd??rsgreb 50x75", 1, doergreb, "Til l??s p?? d??r til skur"));
        list.addAll(getFromDB("product_description", "T h??ngsel 390 mm.", 1, doerHaengsel, "Til skurd??r"));


        return list;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcFittingsAndScrews(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();

        int width = carportDTO.carport().getWidth();
        int length = carportDTO.carport().getLength();
        int tagpladeAmounts = (int) Math.ceil(width / 100);
        int spaerAmounts = (int) Math.ceil(length / 56.5) + 1; // l??ngde skal v??re lig med width
        int postAmounts = 4 + 1;  // + 1 for an extra
        int skackBeklaedning = 0;
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
            skackBeklaedning = (int) ((shackTotalLength * 1.56) - (shackTotalLength * 1.56) % 30);
        }


        int plastmoskruer200stk = tagpladeAmounts / 2; // /2 again if length > 600
        int hulbaand = 2; // just always 2
        int universalRight = spaerAmounts;
        int universalLeft = spaerAmounts;
        int skruer45x60 = 1; // always just 1 pack
        int beslagskruer4x50 = (int) Math.ceil(spaerAmounts / 5); // 3 for 15, 2 for 10
        int boltForRemOnPosts = (int) Math.ceil(postAmounts * 1.8); // if we add 1 extra post, then -1 first
        int skiverForRemOnPosts = postAmounts + 2; // if we add 1 extra, then only +1

        int skruerYderBeklaedning400 = skackBeklaedning / 100; // with 200 braedt for skur beklaedning
        // 800 skuer is needed, 1 pack is 400
        int skuerInnerBeklaedning300 = skruerYderBeklaedning400; // 300 a pack


        list.addAll(getFromDB("product_description", "Plastmo bundskruer 200 stk.", 200, plastmoskruer200stk, "Skruer til tagplader"));
        list.addAll(getFromDB("product_description", "Hulb??nd 1x20 mm. 10 mtr", 1, hulbaand, "Til vindkryds p?? sp??r"));
        list.addAll(getFromDB("product_description", "Universal 190 mm. h??jre", 1, universalRight, "Til montering af sp??r p?? rem"));
        list.addAll(getFromDB("product_description", "Universal 190 mm. venstre", 1, universalLeft, "Til montering af sp??r p?? rem"));
        list.addAll(getFromDB("product_description", "4,5x60 mm. Skruer 200 stk.", 200, skruer45x60, "Til montering af stern og vandbr??ndt"));
        list.addAll(getFromDB("product_description", "4,0x50 mm. Beslagskruer 250 stk.", 250, beslagskruer4x50, "Til montering af universalbeslag + hulb??nd"));
        list.addAll(getFromDB("product_description", "Br??ddebolt 10x120 mm.", 1, boltForRemOnPosts, "Til montering af rem p?? stolper"));
        list.addAll(getFromDB("product_description", "Firkantskiver 40x40x11 mm.", 1, skiverForRemOnPosts, "Til montering af rem p?? stolper"));
        list.addAll(getFromDB("product_description", "4,5x70 mm. Skruer 400 stk.", 400, skruerYderBeklaedning400, "Til montering af yderste bekl??dning"));
        list.addAll(getFromDB("product_description", "4,5x50 mm. Skruer 300 stk.", 300, skuerInnerBeklaedning300, "Til montering af inderste bekl??dning"));


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
