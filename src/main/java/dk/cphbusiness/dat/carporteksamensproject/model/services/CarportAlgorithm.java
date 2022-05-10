package dk.cphbusiness.dat.carporteksamensproject.model.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CarportAlgorithm {



    public LinkedHashMap<String,Object> flatRoofAlgo(int width, int length, boolean shack, int shackwidth, int shacklength) {
            //These are temporary
//        boolean shack = false;
//        int shackwidth = 270;
//        int shacklength = 270;
//        int width = 300;
//        int length = 300;

        LinkedHashMap<String,Object> list = new LinkedHashMap<>();

        if (width - shackwidth < 30 || length - shacklength < 30) {
            list.put("Error", "The Shack was too big compared to the carport!\n" +
                    "If the carport is 300 x 300, then the shack can at most be 270 x 270");
            for( String key : list.keySet() ){
                System.out.println(key+": "+list.get(key));
            }
            return list;
        }


        carportBase(list, width, length, shack, shackwidth, shacklength);

        carportSterns(list, width, length, shack, shackwidth, shacklength);

        carportShack(list, width, length, shack, shackwidth, shacklength);



        list.put("--------------------Fittings and screws", "--------------------");

        int plastmoskruer200stk = (int) list.get("tagpladeAmounts")/2; // /2 again if length > 600
        int hulbaand = 2; // just always 2
        int universalRight = (int) list.get("rafterAmounts");
        int universalLeft = (int) list.get("rafterAmounts");
        int skruer45x60 = 1; // always just 1 pack
        int beslagskruer4x50 = (int) Math.ceil((int) list.get("rafterAmounts")/5); // 3 for 15, 2 for 10
        int boltForRemOnPosts = (int) Math.ceil((int) list.get("postAmounts")*1.8); // if we add 1 extra post, then -1 first
        int skiverForRemOnPosts = (int) list.get("postAmounts")+2; // if we add 1 extra, then only +1
        int skruerYderBeklaedning400 = (int) list.get("skackBeklaedning") / 100; // with 200 braedt for skur beklaedning
                                                            // 800 skuer is needed, 1 pack is 400
        int skuerInnerBeklaedning300 = skruerYderBeklaedning400; // 300 a pack
        int vinkelBeslag = ((int) list.get("loestholterSides") + (int) list.get("loestholterGavler")) * 2;




        list.put("plastmoskruer200stk",plastmoskruer200stk);
        list.put("hulbaand",hulbaand);
        list.put("universalRight",universalRight);
        list.put("universalLeft",universalLeft);
        list.put("skruer45x60",skruer45x60);
        list.put("beslagskruer4x50",beslagskruer4x50);
        list.put("boltForRemOnPosts",boltForRemOnPosts);
        list.put("skiverForRemOnPosts",skiverForRemOnPosts);
        list.put("skruerYderBeklaedning400",skruerYderBeklaedning400);
        list.put("skuerInnerBeklaedning300",skuerInnerBeklaedning300);
        list.put("vinkelBeslag",vinkelBeslag);

        //printListOfObject(list);
        for( String key : list.keySet() ){
            System.out.println(key+": "+list.get(key));
        }
        return list;
    }

    public LinkedHashMap<String,Object> carportBase(LinkedHashMap<String,Object> list, int width, int length, boolean shack, int shackwidth, int shacklength) {
        int height = 210; // this changeable for customers?

        list.put("--------------------CarportBase", "--------------------");

        boolean strongerPosts = false;
        int postAmounts = 4+1;  //skal den måske starte på 5, for en ekstra?
        int postHeight = height + 90;
        int rafterAmounts = (int) Math.ceil(length/56.5) + 1; // længde skal være lig med width
        int rafterLength = width;
        int remAmounts = 2;
        int remLength = length;
        boolean shackRem = false;
        int shackRems = 0;
        int shackRemLength = 0;

        if (!shack) {   //no shack
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

        list.put("strongerPosts",strongerPosts);
        list.put("postAmounts",postAmounts);
        list.put("postHeight(currently set to be height + 90, where height = 210)",postHeight);
        list.put("rafterAmounts",rafterAmounts);
        list.put("rafterLength",rafterLength);
        list.put("remAmounts",remAmounts);
        list.put("remLength",remLength);
        if (shackRem == true) {
            list.put("shackRems",shackRems);
            list.put("shackRemLength",shackRemLength);
        }
        return list;
    }

    public LinkedHashMap<String,Object> carportSterns(LinkedHashMap<String,Object> list, int width, int length, boolean shack, int shackwidth, int shacklength) {
        list.put("--------------------Sterns", "--------------------");

        int overSternBoardsFront = 2; // length is (width + 120)/2
        int overSternBoardsFrontLength = (width + 120)/2;
        int underSternBoardsFrontBack = 4; // length is same as above
        int underSternBoardsFrontBackLength = (width + 120)/2;
        int overSternBoardsSides = 4; // length is ~ 33% more than length, rounded to nearest mod 30
        int overSternBoardsSidesLength = (int) Math.ceil((Math.ceil(length * 1.33)/2)/30.0)*30;
        int underSternBoardsSides = 4; // length is same as above

        list.put("overSternBoardsFront",overSternBoardsFront);
        list.put("overSternBoardsFrontLength",overSternBoardsFrontLength);
        list.put("underSternBoardsFrontBack",underSternBoardsFrontBack);
        list.put("underSternBoardsFrontBackLength",underSternBoardsFrontBackLength);
        list.put("overSternBoardsSides",overSternBoardsSides);
        list.put("overSternBoardsSidesLength",overSternBoardsSidesLength);
        list.put("underSternBoardsSides",underSternBoardsSides);
        list.put("underSternBoardsSidesLength",overSternBoardsSidesLength);


        int waterBoardFront = 2; // same as overSternBoardsFront
        int waterBoardSides = 4; // same as overSternBoardsSides

        list.put("waterBoardFront",waterBoardFront);
        list.put("waterBoardFrontLength",overSternBoardsFrontLength);
        list.put("waterBoardSides",waterBoardSides);
        list.put("waterBoardSidesLength",overSternBoardsSidesLength);


        int tagpladeAmounts = (int) Math.ceil(width/100);
        if (length > 600) {

        }
        // if length > 600, tagpladeAmounts * 2;
        // tagpladeLength = 600 if length <= 600 else needs 180 overlap.

        list.put("tagpladeAmounts",tagpladeAmounts);

        return list;
    }

    public LinkedHashMap<String,Object> carportShack(LinkedHashMap<String,Object> list, int width, int length, boolean shack, int shackwidth, int shacklength) {
        list.put("--------------------Shack if it exists", "--------------------");

        int loestholterSides = (int) Math.ceil(shacklength/270.0) * 4;
        // length would be shacklength / ((int) Math.ceil(shacklength/270))
        int loestholterGavler = (int) Math.ceil(shackwidth/270.0) * 6;
        // length would be shacklength / ((int) Math.ceil(shackwidth/270))
        int shackTotalLength = (shacklength * 2 + shackwidth * 2)/10;
        int skackBeklaedning = (int) ((shackTotalLength * 1.56) - (shackTotalLength * 1.56)%30);

        int laengteForDoor = 1;     // for the z on door, amount can be adjusted directly?
        int doergreb = laengteForDoor;  // maybe people can choose how many doors they want?
        int doerHaengsel = laengteForDoor*2;



        list.put("loestholterSides",loestholterSides);
        list.put("loestholterGavler",loestholterGavler);
        list.put("shackTotalLength",shackTotalLength);
        list.put("skackBeklaedning",skackBeklaedning);

        list.put("laengteForDoor",laengteForDoor);
        list.put("doergreb",doergreb);
        list.put("doerHaengsel",doerHaengsel);

        return list;
    }

}
