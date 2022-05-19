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
    private int stolpe1X;
    private int stolpe2X;
    private int stolpe3X;
    private int stolpe4X;
    private int frontStolpe = 60;
    private int backStolpe = 50;
    int stolpeDist = 30;

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

        //bottom arrows
        int textY = (int) (svgHeight - heightOffset*0.4);
        int bArrowsY = (int) (svgHeight - heightOffset*0.3);
        int topLine = (int) (svgHeight - heightOffset*0.7);
        int bottomLine= (int) (svgHeight - heightOffset*0.1);
        int rightStart = svgWidth-leftOffset;
            //base lines, the two at outermost side, and a long on along the arrows
        svg.addLine(rightOffset, bArrowsY, rightStart, bArrowsY,1);
        svg.addLine(rightOffset, heightOffset, rightOffset, bottomLine,1);
        svg.addLine(rightStart, heightOffset, rightStart, bottomLine,1);
            //middle lines
        int line1X = (int) (stolpe1X*0.8)+rightOffset;
        int line2X = (int) (stolpe2X*0.8)+rightOffset;
        int line3X = (int) (stolpe3X*0.8)+rightOffset;
        int line4X = (int) (stolpe4X*0.8)+rightOffset;
        svg.addLine(line1X, topLine, line1X, bottomLine,1);
        svg.addLine(line2X, topLine, line2X, bottomLine,1);
        svg.addLine(line3X, topLine, line3X, bottomLine,1);
        svg.addLine(line4X, topLine, line4X, bottomLine,1);
            //for text strings
        int rightmostStolpeText;
        String text01;
        int text01X;
        int text12;
        int text23;
        if (carportDTO.shack().isPresent()) {
            text01X = svgWidth-((svgWidth-rightStart)/2);
            text01 = overhang+"**";
            text12 = shacklength;
            if (stolpe4X == 0) {
                text23 = length - overhang - shacklength;
            } else {
                text23 = length - shacklength - overhang - ((length) - shacklength - overhang - backStolpe);
            }
            rightmostStolpeText = frontStolpe;
        } else {
            text01X = line1X+(rightStart - line1X)/2;
            text01 = stolpeDist+"";
            if (stolpe3X == 0) {
                text12 = length - stolpeDist;
            } else {
                text12 = length - stolpeDist - length/2;
            }
            text23 = length - stolpeDist - length/2;
            rightmostStolpeText = stolpeDist;
        }
            //arrows
        svg.addDoubleArrow(rightStart,bArrowsY, line1X, bArrowsY,1);
        svg.addDoubleArrow(line1X,bArrowsY, line2X, bArrowsY,1);
        svg.addText(""+text01, text01X, textY, 0);
        svg.addText(""+text12, line2X+(line1X - line2X)/2, textY, 0);
        int rightMostStolpe = line2X;
        if (stolpe3X != 0) {
            svg.addDoubleArrow(line2X,bArrowsY, line3X, bArrowsY,1);
            svg.addText(""+text23, line3X+(line2X - line3X)/2, textY, 0);
            rightMostStolpe = line3X;
        }
        if (stolpe4X != 0) {
            svg.addDoubleArrow(line3X,bArrowsY, line4X, bArrowsY,1);
            svg.addText(""+(length - shacklength - overhang - frontStolpe - backStolpe), line4X+(line3X - line4X)/2, textY, 0);
            rightMostStolpe = line4X;
        }
        svg.addDoubleArrow(rightMostStolpe,bArrowsY, rightOffset, bArrowsY,1);
        svg.addText(""+rightmostStolpeText, rightOffset+(rightMostStolpe - rightOffset)/2, textY, 0);
    }

    public void calcSVGCarport(CarportDTO carportDTO, SVG svg) {
        int postAmounts = calcStolper();

        // Top rem og stern
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
        stolpe1X = svgWidth-overhangRatio;
        stolpe2X = svgWidth-overhangRatio-shacklengthRatio;
            //shack rems
        svg.addRect(stolpe1X,stolpeY,stolpeWidth,stolpeHeight, 2);
        svg.addRect(stolpe2X,stolpeY,stolpeWidth,stolpeHeight, 2);

            //shack sides
        int gap = (int) (shacklengthRatio*0.4);
        int side = shacklengthRatio-gap;
        int gapx = (int) (Math.ceil(shacklength/30*1.5));
        int sidex = gapx-1;
        svg.addRect(stolpe2X+6,stolpeY-6,shacklengthRatio,stolpeHeight+4, 1);
        for (int i = 0; i < gapx; i++) {
            int placement = stolpeWidth+svgWidth-overhangRatio-shacklengthRatio+(gap/gapx)+(gap/gapx)*i+(side/sidex)*i;
            svg.addRect(placement,stolpeY-6,(side/sidex),stolpeHeight+4, 1);
        }

        if (shackwidth > 270) {stolperUsed +=2;}
        if (shacklength > 270) {stolperUsed +=2;}
        if (postAmounts - stolperUsed == 4+1) {
            int carportlength = length - shacklength - overhang;
            if (carportlength - frontStolpe > 400) {
                frontStolpe = 100;
            }
            if (carportlength - frontStolpe - backStolpe > 310) {
                backStolpe = carportlength - frontStolpe - 310;
            }
            stolpe3X = (int) ((carportlength - backStolpe)*widthRatio);
            stolpe4X = (int) (frontStolpe*widthRatio);
            svg.addRect(stolpe3X,stolpeY,stolpeWidth,stolpeHeight, 2);
            svg.addRect(stolpe4X,stolpeY,stolpeWidth,stolpeHeight, 2);
        } else if (postAmounts - stolperUsed == 2+1) {
            stolpe3X = overhangRatio;
            svg.addRect(stolpe3X,stolpeY,stolpeWidth,stolpeHeight, 2);
        } else {
            //error
        }
    }

    private void calcSVGBaseNoShack(int postAmounts, SVG svg) {
        if (postAmounts == 4+1) {
            if (length > 300) {stolpeDist += 30;}
            if (length > 420) {stolpeDist += 30;}

            stolpe1X = (int) ((length - stolpeDist)*widthRatio);
            stolpe2X = (int) (stolpeDist*widthRatio);
            svg.addRect(stolpe1X,stolpeY,stolpeWidth,stolpeHeight, 2);
            svg.addRect(stolpe2X,stolpeY,stolpeWidth,stolpeHeight, 2);

        } else if (postAmounts == 6+1) {
            stolpeDist = 50;
            if (length/2 > 300) {stolpeDist += 50;}

            stolpe1X = (int) ((length - stolpeDist)*widthRatio);
            stolpe3X = (int) (stolpeDist*widthRatio);
            svg.addRect(stolpe1X,stolpeY,stolpeWidth,stolpeHeight, 2);
            svg.addRect(stolpe3X,stolpeY,stolpeWidth,stolpeHeight, 2);

            stolpe2X = (int) ((length/2)*widthRatio);
            svg.addRect(stolpe2X,stolpeY,stolpeWidth,stolpeHeight, 2);

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
