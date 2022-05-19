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
        int svgHeight = 430 - ((20-(height/30))*10);
        int svgWidth = 520 - ((26-(width/30))*10);
        SVG svg = new SVG(0,0, "0 0 "+width+" "+height, ""+svgWidth,""+svgHeight);
        //svg.addRect(0,0, width, height,2); // box to show svg dimensions
        if (svgHeight < 350) {
            svg.setTextStyle("9px");
        }

        int heightoffset = (int) (height*0.075);
        int widthoffset = (int) (width*0.15);

        SVG carportSVG = new SVG(widthoffset, heightoffset, "0 0 "+width+" "+height, "85%", "85%");
        carportSVG.addRect(0,0, width, height,2);
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

        //bottom arrow
        svg.addDoubleArrow(widthoffset, height-heightoffset/2+5, width-5, height-heightoffset/2+5, 1);
        svg.addText(""+width/100.0, widthoffset+((width-widthoffset)/2), height-heightoffset/2, 0);
        svg.addLine(widthoffset,height-heightoffset*5/6+5,widthoffset,height-heightoffset*1/6+1,1);
        svg.addLine(width-5,height-heightoffset*5/6+5,width-5,height-heightoffset*1/6+1,1);

        //big side arrow
        svg.addDoubleArrow(widthoffset/3, heightoffset, widthoffset/3, height-heightoffset, 1);
        svg.addText(""+height/100.0, widthoffset/3-5, height/2, -90);
        svg.addLine(widthoffset*1/6,heightoffset,widthoffset*5/6,heightoffset,1);
        svg.addLine(widthoffset*1/6,height-heightoffset,widthoffset*5/6,height-heightoffset,1);

        //smaller side arrow (int) (600*(0.075+(30.0/600.0*0.85)));
        int rempos = (int) ((double)(overhang)*0.85);
        svg.addDoubleArrow(widthoffset*2/3, heightoffset+rempos, widthoffset*2/3, height-heightoffset-rempos, 1);
        svg.addText(""+(height-overhang*2)/100.0+"*", widthoffset*2/3-5, height/2, -90);
        svg.addLine(widthoffset*3/6,heightoffset+rempos,widthoffset*5/6,heightoffset+rempos,1);
        svg.addLine(widthoffset*3/6,height-heightoffset-rempos,widthoffset*5/6,height-heightoffset-rempos,1);

        //the numerous small arrows
        int spaerBetween = (int) ((double)(lengthBetween)*0.85);
        for (int i = 0; i < spaerAmounts-1; i++) {
            int daX = widthoffset+spaerBetween*i+i;
            svg.addDoubleArrow(daX, heightoffset/2+5, daX+spaerBetween, heightoffset/2+5, 1);
            svg.addText(""+lengthBetween/100.0+"**", widthoffset+spaerBetween/2+spaerBetween*i+i+1, heightoffset/2+5-5, 0);
            svg.addLine(daX,heightoffset*5/6,daX,heightoffset*2/6,1);
            svg.addLine(daX+spaerBetween+1,heightoffset*5/6,daX+spaerBetween+1,heightoffset*2/6,1);
        }
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
        svg.addRect(length-overhang-shacklength-1,overhang-1,stolpeDim,stolpeDim, 2);
        svg.addRect(length-overhang-1,overhang-1, stolpeDim,stolpeDim, 2);
        svg.addRect(length-overhang-shacklength-1,width-overhang-1,stolpeDim,stolpeDim, 2);
        svg.addRect(length-overhang-1,width-overhang-1, stolpeDim,stolpeDim, 2);

        if (shackwidth > 270) {
            stolperUsed +=2;
            svg.addRect(length-overhang-shacklength-1,width/2-8-1,stolpeDim,stolpeDim, 2);
            svg.addRect(length-overhang-1,width/2-8-1, stolpeDim,stolpeDim, 2);
        }
        if (shacklength > 270) {
            stolperUsed +=2;
            svg.addRect(length-overhang-shacklength/2-1,overhang-1,17,17, 2);
            svg.addRect(length-overhang-shacklength/2-1,width-overhang-1,17,17, 2);
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
            svg.addRect(frontStolpe,overhang-1,stolpeDim,stolpeDim, 2);
            svg.addRect(carportlength - backStolpe,overhang-1, stolpeDim,stolpeDim, 2);
            svg.addRect(frontStolpe,width-overhang-1,stolpeDim,stolpeDim, 2);
            svg.addRect(carportlength - backStolpe,width-overhang-1, stolpeDim,stolpeDim, 2);
        } else if (postAmounts - stolperUsed == 2+1) {
            int placement = overhang;
            svg.addRect(placement,overhang-1,stolpeDim,stolpeDim, 2);
            svg.addRect(placement,width-overhang-1,stolpeDim,stolpeDim, 2);
        } else {
            //error
        }
    }

    private void calcSVGBaseNoShack(int postAmounts, SVG svg) {
        if (postAmounts == 4+1) {
            int stolpeDist = 30;
            if (length > 300) {stolpeDist += 30;}
            if (length > 420) {stolpeDist += 30;}

            svg.addRect(stolpeDist,overhang-1,stolpeDim,stolpeDim, 2);
            svg.addRect(length - stolpeDist,overhang-1, stolpeDim,stolpeDim, 2);
            svg.addRect(stolpeDist,width-overhang-1,stolpeDim,stolpeDim, 2);
            svg.addRect(length - stolpeDist,width-overhang-1, stolpeDim,stolpeDim, 2);
        } else if (postAmounts == 6+1) {
            svg.addRect(length/2,overhang-1,stolpeDim,stolpeDim, 2);
            svg.addRect(length/2,width-overhang-1, stolpeDim,stolpeDim, 2);

            int stolpeDist = 50;
            if (length/2 > 300) {stolpeDist += 50;}

            svg.addRect(stolpeDist,overhang-1,stolpeDim,stolpeDim, 2);
            svg.addRect(length - stolpeDist,overhang-1, stolpeDim,stolpeDim, 2);
            svg.addRect(stolpeDist,width-overhang-1,stolpeDim,stolpeDim, 2);
            svg.addRect(length - stolpeDist,width-overhang-1, stolpeDim,stolpeDim, 2);
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
