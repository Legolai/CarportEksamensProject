package dk.cphbusiness.dat.carporteksamensproject.model.services;

import java.util.ArrayList;
import java.util.List;

public class CarportAlgorithm {



    public List<Object> flatRoofAlgo() {
            //These are temporary
        boolean shack = false;
        int shackwidth = 270;
        int shacklength = 270;
        int width = 300;
        int length = 300;

        boolean strongerPosts = false;
        int postAmounts = 4;  //skal den måske starte på 5, for en ekstra?
        int spaerAmounts = (int) Math.ceil(length/56.5) + 1; // længde skal være lig med width
        int remAmounts = 2;

        


        int laengteForDoor = 1;     // for the z on door, amount can be adjusted directly?
                                    // So people can choose how many doors they want?
        int loestholterSides = (int) Math.ceil(shacklength/270) * 4;
        // length would be shacklength / ((int) Math.ceil(shacklength/270))
        int loestholterGavler = (int) Math.ceil(shackwidth/270) * 6;
        // length would be shacklength / ((int) Math.ceil(shackwidth/270))
        int shackTotalLength = (shacklength * 2 + shackwidth * 2);
        int skackBeklaedning = (int) ((shackTotalLength * 1.56) - (shackTotalLength * 1.56)%15+15);

        int tagpladeAmounts = (int) Math.ceil(width/100);
        // if length > 600, tagpladeAmounts * 2;
        // tagpladeLength = 600 if length <= 600 else needs 180 overlap.



        if (!shack) {   //no shack
            if (width > 390) {
                strongerPosts = true;
            }
            if (length > 510) {
                postAmounts += 2;
                remAmounts += 2; // lengthOfRem would be length+60/2
            }
        } else {
            postAmounts += 2;
            if (length > 510) {
                remAmounts += 2; // lengthOfRem would have 2 different lengths most likely
                //length of shack rem would be shacklength+30
                //if lengthofshackrem would be 300, then it can be doubled to 600
                //and we can then do remAmounts -= 1;
                //length of carport rem would be length-shacklength+30
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

        List<Object> temp = new ArrayList<>();
        return temp;

    }




}
