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
    private final int overhang = 30;
    private final int stolpeDim = 14;
    
    private int width = 0;
    private int length = 0;
    private CarportDTO carportDTO;
    private int shackwidth = 0;
    private int shacklength = 0;
    private int spaerAmounts = 5;
    private int lengthBetween = 55;
    private int dottedX = 0;
    public void setStats(CarportDTO carportDTO) {
        width = carportDTO.carport().getWidth();
        length = carportDTO.carport().getLength();
        this.carportDTO = carportDTO;
        if (carportDTO.shack().isPresent()){
            shackwidth = carportDTO.shack().get().getWidth();
            shacklength = carportDTO.shack().get().getLength();
        }
        spaerAmounts = (int) Math.ceil(length/56.5-0.35) + 1;
        lengthBetween = (int) Math.ceil((length - 10)/(spaerAmounts-1));
    }


    public SVG calcSVG(CarportDTO carportDTO) {
        int height = width;   //600
        int width = length;   //780
        SVG svg = new SVG(0,0, "0 0 "+width+" "+height, "520", "430");
        svg.addRect(0,0, width, height,3);

        int heightoffset = (int) (height*0.075);
        int widthoffset = (int) (width*0.15);

        SVG carportSVG = new SVG(widthoffset, heightoffset, "0 0 "+width+" "+height, ""+(width*0.9), ""+(height*0.85));
        carportSVG.addRect(0,0, width, height,3);
        calcSVGCarport(carportDTO, carportSVG);
        svg.addSVG(carportSVG);

        calcSVGCarportArrows(carportDTO, svg);


        return svg;
    }

    private void calcSVGCarportArrows(CarportDTO carportDTO, SVG svg) {
        int height = width;   //600
        int width = length;   //780
        int heightoffset = (int) (height*0.075);
        int widthoffset = (int) (width*0.15);
        svg.addDoubleArrow(widthoffset, height-heightoffset/2, width, height-heightoffset/2, 2);
        svg.addText(""+width+" cm", widthoffset+((width-widthoffset)/2), height-heightoffset/2+5, 0);
    }

    public void calcSVGCarport(CarportDTO carportDTO, SVG svg) {
        int postAmounts = calcStolper();

        // Side ram
        svg.addRect(0,overhang, length, 12, 1);
        svg.addRect(0,width-overhang, length, 11, 1);

        // Stolper and
        if (carportDTO.shack().isPresent()) {
            calcSVGBaseWithShack(postAmounts, svg);
            dottedX = length - shacklength - overhang;
        } else {
            calcSVGBaseNoShack(postAmounts, svg);
            dottedX = (spaerAmounts - 2)*lengthBetween;
        }

        // hulbaand, the dotted X that is
        svg.addDottedLine(lengthBetween, overhang+12, dottedX, width-overhang,3);
        svg.addDottedLine(dottedX, overhang+12, lengthBetween, width-overhang,3);

        // spaer
        svg.addRect(0,0, 5, width, 1);
        for (int i = 1; i < spaerAmounts;i++) {
            svg.addRect(lengthBetween*i,0, 5, width, 1);
        }


    }

    private void calcSVGBaseWithShack(int postAmounts, SVG svg) {
        //shack rems
        svg.addRect(length-overhang-shacklength,overhang, 12, width-60, 1);
        svg.addRect(length-overhang,overhang, 12, width-60, 1);

        int stolperUsed = 4;
        svg.addRect(length-overhang-shacklength-1,overhang-1,stolpeDim,stolpeDim, 3);
        svg.addRect(length-overhang-1,overhang-1, stolpeDim,stolpeDim, 3);
        svg.addRect(length-overhang-shacklength-1,width-overhang-1,stolpeDim,stolpeDim, 3);
        svg.addRect(length-overhang-1,width-overhang-1, stolpeDim,stolpeDim, 3);

        if (shackwidth > 270) {
            stolperUsed +=2;
            svg.addRect(length-overhang-shacklength-1,width/2-8-1,stolpeDim,stolpeDim, 3);
            svg.addRect(length-overhang-1,width/2-8-1, stolpeDim,stolpeDim, 3);
        }
        if (shacklength > 270) {
            stolperUsed +=2;
            svg.addRect(length-overhang-shacklength/2-1,overhang-1,17,17, 3);
            svg.addRect(length-overhang-shacklength/2-1,width-overhang-1,17,17, 3);
        }

        if (postAmounts - stolperUsed == 4+1) {
            int carportlength = length - shacklength - overhang;
            int frontStolpe = 60;
            if (carportlength - frontStolpe > 400) {
                frontStolpe = 100;
            }
            int backStolpe = 50;
            if (carportlength - frontStolpe - backStolpe > 310) {
                backStolpe = carportlength - frontStolpe - 310;
            }
            svg.addRect(frontStolpe,overhang-1,stolpeDim,stolpeDim, 3);
            svg.addRect(carportlength - backStolpe,overhang-1, stolpeDim,stolpeDim, 3);
            svg.addRect(frontStolpe,width-overhang-1,stolpeDim,stolpeDim, 3);
            svg.addRect(carportlength - backStolpe,width-overhang-1, stolpeDim,stolpeDim, 3);
        } else if (postAmounts - stolperUsed == 2+1) {
            int placement = overhang;
            svg.addRect(placement,overhang-1,stolpeDim,stolpeDim, 3);
            svg.addRect(placement,width-overhang-1,stolpeDim,stolpeDim, 3);
        } else {
            //error
        }
    }

    private void calcSVGBaseNoShack(int postAmounts, SVG svg) {
        if (postAmounts == 4+1) {
            int stolpeDist = 30;
            if (length > 300) {stolpeDist += 30;}
            if (length > 420) {stolpeDist += 30;}

            svg.addRect(stolpeDist,overhang-1,stolpeDim,stolpeDim, 3);
            svg.addRect(length - stolpeDist,overhang-1, stolpeDim,stolpeDim, 3);
            svg.addRect(stolpeDist,width-overhang-1,stolpeDim,stolpeDim, 3);
            svg.addRect(length - stolpeDist,width-overhang-1, stolpeDim,stolpeDim, 3);
        } else if (postAmounts == 6+1) {
            svg.addRect(length/2,overhang-1,stolpeDim,stolpeDim, 3);
            svg.addRect(length/2,width-overhang-1, stolpeDim,stolpeDim, 3);

            int stolpeDist = 50;
            if (length/2 > 300) {stolpeDist += 50;}

            svg.addRect(stolpeDist,overhang-1,stolpeDim,stolpeDim, 3);
            svg.addRect(length - stolpeDist,overhang-1, stolpeDim,stolpeDim, 3);
            svg.addRect(stolpeDist,width-overhang-1,stolpeDim,stolpeDim, 3);
            svg.addRect(length - stolpeDist,width-overhang-1, stolpeDim,stolpeDim, 3);
        } else {
            //error
        }

    }

    private int calcStolper() {
        int stolperAmounts = 4+1;  // + 1 for an extra
        if (!carportDTO.shack().isPresent()) {   //no shack
            if (length > 510) {
                stolperAmounts += 2;
            }
        } else {
            stolperAmounts += 2;
            if (length - shacklength > 330) {
                stolperAmounts += 2;
            }
            if (shacklength > 270) {
                stolperAmounts += 2;
            }
            if (shackwidth > 270) {
                stolperAmounts += 2;
            }
        }
        return stolperAmounts;
    }



}
