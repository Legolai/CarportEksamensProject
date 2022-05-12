package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialLineItemDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductVariantDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.product.ProductVariantMapper;

import java.util.*;

public class FlatRoofAlgorithm implements ICarportAlgorithm{
    ProductVariantMapper mapper;

    public FlatRoofAlgorithm () {
        EntityManager entityManager = new EntityManager(new ConnectionPool());
        mapper = new ProductVariantMapper(entityManager);
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcCarport(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();
        list.addAll(calcBase(carportDTO));
        list.addAll(calcRoof(carportDTO));
        list.addAll(calcSterns(carportDTO));
        if (carportDTO.shack().isPresent()){
            list.addAll(calcShack(carportDTO));
        }
        list.addAll(calcFittings(carportDTO));
        list.addAll(calcScrews(carportDTO));
        return list;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcRoof(CarportDTO carportDTO) {
        int roofPlateOverlay = 120;
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();
        int width = carportDTO.carport().getWidth();
        int length = carportDTO.carport().getLength();

        int tagpladeAmounts = (int) Math.ceil(width/100);
        int tagpladeLength = length;
        int tagpladeAmounts2 = tagpladeAmounts;
        int tagpladeLength2 = 0;
        if (length > 600) {
            tagpladeLength = 600;
            tagpladeLength2 = length - 600 + roofPlateOverlay;
        }
        // if length > 600, tagpladeAmounts * 2;
        // tagpladeLength = 600 if length <= 600 else needs 180 overlap.


        Optional<List<ProductVariantDTO>> tagplader;
        try {
            tagplader = mapper.findAll(Map.of("product_type_name", "Tagplade"));
            if (tagplader.isPresent()) {
                for (ProductVariantDTO plader : tagplader.get()) {
                    System.out.println(plader.product().product().getDescription());
                    System.out.println(plader.size().getDetail());
                }
            }

        } catch (DatabaseException ex) {

        }

        //list.put("tagpladeAmounts",tagpladeAmounts);


        int rafterAmounts = (int) Math.ceil(length/56.5) + 1; // længde skal være lig med width
        int rafterLength = width;

//        list.put("rafterAmounts",rafterAmounts);
//        list.put("rafterLength",rafterLength);

        return null;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcBase(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();

        int height = carportDTO.carport().getHeight();
        int width = carportDTO.carport().getWidth();
        int length = carportDTO.carport().getLength();
        boolean shackExists = false;
        int shackwidth = 0;
        int shacklength = 0;
        if (carportDTO.shack().isPresent()){
            Shack shack = carportDTO.shack().get();
            shackExists = true;
            shackwidth = shack.getWidth();
            shacklength = shack.getLength();
        }


        boolean strongerPosts = false;
        int postAmounts = 4+1;  // + 1 for an extra
        int postHeight = height + 90;
        int remAmounts = 2;
        int remLength = length;
        boolean shackRem = false;   //Shack rems for the rems on the shack
        int shackRems = 0;
        int shackRemLength = 0;

        if (!shackExists) {   //no shack
            if (width > 390) {
                strongerPosts = true;
            }
            if (length > 510) {
                postAmounts += 2;
                remAmounts += 2; // lengthOfRem would be length+60/2
                remLength = (length + 60) / 2;
            }
        } else {
            postAmounts += 2;
            if (length > 510) {
                shackRem = true;

                shackRems = 2;
                shackRemLength = shacklength+30;
                if (shackRemLength <= 300) {
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
            } else if (shacklength > 270) {
                postAmounts += 2;
            }
            if (shackwidth > 270) {
                postAmounts += 2;
            }
        }

//        list.put("strongerPosts",strongerPosts);
//        list.put("postAmounts",postAmounts);
//        list.put("postHeight(currently set to be height + 90, where height = 210)",postHeight);
//        list.put("remAmounts",remAmounts);
//        list.put("remLength",remLength);
//        if (shackRem == true) {
//            list.put("shackRems",shackRems);
//            list.put("shackRemLength",shackRemLength);
//        }
//        return list;
        return null;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcSterns(CarportDTO carportDTO) {
        return null;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcShack(CarportDTO carportDTO) {
        return null;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcFittings(CarportDTO carportDTO) {
        return null;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcScrews(CarportDTO carportDTO) {
        return null;
    }
}
