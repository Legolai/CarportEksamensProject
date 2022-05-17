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

public class SVGAlgorithm {     //TODO: Is mapper needed for SVG algo?
    ProductVariantMapper mapper;

    public SVGAlgorithm () {
        EntityManager entityManager = new EntityManager(new ConnectionPool());
        mapper = new ProductVariantMapper(entityManager);
    }


    public void calcSVGCarport(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();
        (calcSVGBase(carportDTO);
        calcSVGRoof(carportDTO);
        calcSVGSterns(carportDTO);
        if (carportDTO.shack().isPresent()){
            calcSVGShack(carportDTO);
        }

        //return list;
    }

    public void calcSVGBase(CarportDTO carportDTO) {
        int height = carportDTO.carport().getHeight();
        int width = carportDTO.carport().getWidth();
        int length = carportDTO.carport().getLength();
        Shack shack;
        int shackwidth = 0;
        int shacklength = 0;
        if (carportDTO.shack().isPresent()){
            shack = carportDTO.shack().get();
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
                shackRemLength = shacklength+30;
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
            } else if (shacklength > 270) {
                postAmounts += 2;
            }
            if (shackwidth > 270) {
                postAmounts += 2;
            }
        }




    }

    public void calcSVGRoof(CarportDTO carportDTO) {

    }

    public void calcSVGSterns(CarportDTO carportDTO) {

    }

    public void calcSVGShack(CarportDTO carportDTO) {

    }



}
