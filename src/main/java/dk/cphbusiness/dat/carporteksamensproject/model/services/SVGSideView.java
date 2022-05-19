package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;

public class SVGSideView extends SVGAlgorithmsBase {
    private double heightRatio;
    private double widthRatio;
    private int svgHeight;
    private int svgWidth;
    private int remHeight;
    private int stolpeWidth;
    private int overhangRatio;
    private int shacklengthRatio;
    private int stolpeY;
    private int stolpeHeight;

    public SVGSideView(CarportDTO carportDTO) {
        super(carportDTO);
        svgHeight = 180;
        svgWidth = 550 - ((26-(length/30))*10);     //svg width is equal to carport length
        heightRatio = (double) svgHeight/carportDTO.carport().getHeight();
        widthRatio = (double) svgWidth/length;
        remHeight = ((int) (20*heightRatio))-5;
        stolpeWidth = (int) (stolpeDim*widthRatio);
        overhangRatio = (int) (overhang*widthRatio);
        shacklengthRatio = (int) (shacklength*widthRatio);
        stolpeY = remHeight*2+2;
        stolpeHeight = svgHeight-stolpeY-1;

        //TODO: remove System.out.println("heigh and width ratio is: "+heightRatio+ " and "+widthRatio);
    }

    public SVG calcSVG() {

        SVG svg = new SVG(0,0, "0 0 "+svgWidth+" "+svgHeight, ""+svgWidth,""+svgHeight);
        //svg.addRect(0,0, svgWidth, svgHeight,2); // rect to show svg dimensions

        int widthoffset = (int) (svgWidth*0.2);

        SVG carportSVG = new SVG((int) (widthoffset*0.6), 1, "0 0 "+svgWidth+" "+svgHeight, "80%", "80%");
        //carportSVG.addRect(0,0, svgWidth, svgHeight,2); //rect to show the carportSVG dimensions
        calcSVGCarport(carportDTO, carportSVG);
        svg.addSVG(carportSVG);

        calcSVGCarportArrows(carportDTO, svg);

        return svg;
    }

    private void calcSVGCarportArrows(CarportDTO carportDTO, SVG svg) {
        int heightOffset = (int) (svgHeight*0.2);
        int widthOffset = (int) (svgWidth*0.2);
        int rightOffset = (int) (widthOffset*0.6);
        int leftOffset = widthOffset - rightOffset;
        int carportHeight = carportDTO.carport().getHeight();

        //right arrows
        svg.addDoubleArrow((int) (rightOffset*0.3), 1, (int) (rightOffset*0.3), svgHeight-heightOffset, 1);
        svg.addText(""+(carportHeight+20)/100.0, (int) (rightOffset*0.2), (svgHeight-heightOffset)/2, -90);
        svg.addDoubleArrow((int) (rightOffset*0.8), remHeight+1, (int) (rightOffset*0.8), svgHeight-heightOffset, 1);
        svg.addText(""+(carportHeight)/100.0, (int) (rightOffset*0.7), (svgHeight-heightOffset)/2, -90);
        svg.addLine((int) (rightOffset*0.4), remHeight+1, (int) (rightOffset*0.8), remHeight+1,1);
        svg.addLine((int) (rightOffset*0.2), 1, (int) (rightOffset*0.8), 1,1);
        svg.addLine((int) (rightOffset*0.2),svgHeight-heightOffset,(int) (rightOffset*0.9),svgHeight-heightOffset,1);

        //left arrows
        svg.addDoubleArrow(svgWidth - (int) (leftOffset*0.3), 1, svgWidth - (int) (leftOffset*0.3), svgHeight-heightOffset, 1);
        svg.addText(""+(carportHeight+10)/100.0+"*", svgWidth - (int) (leftOffset*0.4), (svgHeight-heightOffset)/2, -90);
        svg.addLine(svgWidth - (int) (leftOffset*0.7), 1, svgWidth - (int) (leftOffset*0.2), 1,1);
        svg.addLine(svgWidth - (int) (leftOffset*0.7),svgHeight-heightOffset,svgWidth - (int) (leftOffset*0.2),svgHeight-heightOffset,1);


//        //the numerous small arrows
//        int spaerBetween = (int) ((double)(lengthBetween)*0.85);
//        for (int i = 0; i < spaerAmounts-1; i++) {
//            int daX = widthOffset+spaerBetween*i+i;
//            svg.addDoubleArrow(daX, heightOffset/2+5, daX+spaerBetween, heightOffset/2+5, 1);
//            svg.addText(""+lengthBetween/100.0+"**", widthOffset+spaerBetween/2+spaerBetween*i+i+1, heightOffset/2+5-5, 0);
//            svg.addLine(daX,heightOffset*5/6,daX,heightOffset*2/6,1);
//            svg.addLine(daX+spaerBetween+1,heightOffset*5/6,daX+spaerBetween+1,heightOffset*2/6,1);
//        }
    }

    public void calcSVGCarport(CarportDTO carportDTO, SVG svg) {
        int postAmounts = calcStolper();

        // Side ram
        svg.addRect(0,0, svgWidth, remHeight, 1);
        svg.addRect(0,remHeight+2, svgWidth, remHeight, 1);

        // Stolper
        if (carportDTO.shack().isPresent()) {
            calcSVGBaseWithShack(postAmounts, svg);
        } else {
            calcSVGBaseNoShack(postAmounts, svg);
        }
    }

    private void calcSVGBaseWithShack(int postAmounts, SVG svg) {
        int stolperUsed = 2*2;
        svg.addRect(svgWidth-overhangRatio-shacklengthRatio,stolpeY,stolpeWidth,stolpeHeight, 2);
        svg.addRect(svgWidth-overhangRatio,stolpeY,stolpeWidth,stolpeHeight, 2);

        int gap = (int) (shacklengthRatio*0.4);
        int side = shacklengthRatio-gap;
        int gapx = (int) (Math.ceil(shacklength/30*1.5));
        int sidex = gapx-1;
        svg.addRect(svgWidth-overhangRatio-shacklengthRatio+6,stolpeY-5,shacklengthRatio,stolpeHeight+5, 1);
        for (int i = 0; i < gapx; i++) {
            int placement = stolpeWidth+svgWidth-overhangRatio-shacklengthRatio+(gap/gapx)+(gap/gapx)*i+(side/sidex)*i;
            svg.addRect(placement,stolpeY-5,(side/sidex),stolpeHeight+5, 1);
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
            svg.addRect((int) (frontStolpe*widthRatio),stolpeY,stolpeWidth,stolpeHeight, 2);
            svg.addRect((int) ((carportlength - backStolpe)*widthRatio),stolpeY,stolpeWidth,stolpeHeight, 2);
        } else if (postAmounts - stolperUsed == 2+1) {
            svg.addRect(overhangRatio,stolpeY,stolpeWidth,stolpeHeight, 2);
        } else {
            //error
        }
    }

    private void calcSVGBaseNoShack(int postAmounts, SVG svg) {
        if (postAmounts == 4+1) {
            int stolpeDist = 30;
            if (length > 300) {stolpeDist += 30;}
            if (length > 420) {stolpeDist += 30;}

            svg.addRect((int) (stolpeDist*widthRatio),stolpeY,stolpeWidth,stolpeHeight, 2);
            svg.addRect((int) ((length - stolpeDist)*widthRatio),stolpeY,stolpeWidth,stolpeHeight, 2);

        } else if (postAmounts == 6+1) {
            svg.addRect((int) ((length/2)*widthRatio),stolpeY,stolpeWidth,stolpeHeight, 2);

            int stolpeDist = 50;
            if (length/2 > 300) {stolpeDist += 50;}

            svg.addRect((int) (stolpeDist*widthRatio),stolpeY,stolpeWidth,stolpeHeight, 2);
            svg.addRect((int) ((length - stolpeDist)*widthRatio),stolpeY,stolpeWidth,stolpeHeight, 2);

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
        }
        return stolperAmounts;
    }

}
