package girraiffe;

import java.util.*;
//import java.util.Map;


//import static java.util.Map.entry;

public class TierHelper {
        public final NavigableMap<Double, Integer> tierMap;
        private double tier = 0;

        public TierHelper(){
                /** Tier, Height */
                tierMap = new TreeMap<>();

                tierMap.put( -255.1895, -104);
                tierMap.put( -251.7029, -103);
                tierMap.put( -248.2252, -102);
                tierMap.put( -244.7565, -101);
                tierMap.put( -241.2970, -100);
                tierMap.put( -237.8469, -99);
                tierMap.put( -234.4064, -98);
                tierMap.put( -230.9757, -97);
                tierMap.put( -227.5550, -96);
                tierMap.put( -224.1445, -95);
                tierMap.put( -220.7443, -94);
                tierMap.put( -217.3548, -93);
                tierMap.put( -213.9761, -92);
                tierMap.put( -210.6085, -91);
                tierMap.put( -207.2521, -90);
                tierMap.put( -203.9072, -89);
                tierMap.put( -200.5741, -88);
                tierMap.put( -197.2529, -87);
                tierMap.put( -193.9440, -86);
                tierMap.put( -190.6475, -85);
                tierMap.put( -187.3638, -84);
                tierMap.put( -184.0930, -83);
                tierMap.put( -180.8355, -82);
                tierMap.put( -177.5915, -81);
                tierMap.put( -174.3613, -80);
                tierMap.put( -171.1452, -79);
                tierMap.put( -167.9434, -78);
                tierMap.put( -164.7564, -77);
                tierMap.put( -161.5842, -76);
                tierMap.put( -158.4273, -75);
                tierMap.put( -155.2861, -74);
                tierMap.put( -152.1606, -73);
                tierMap.put( -149.0515, -72);
                tierMap.put( -145.9588, -71);
                tierMap.put( -142.8831, -70);
                tierMap.put( -139.8245, -69);
                tierMap.put( -136.7836, -68);
                tierMap.put( -133.7606, -67);
                tierMap.put( -130.7559, -66);
                tierMap.put( -127.7698, -65);
                tierMap.put( -124.8029, -64);
                tierMap.put( -121.8554, -63);
                tierMap.put( -118.9277, -62);
                tierMap.put( -116.0203, -61);
                tierMap.put( -113.1336, -60);
                tierMap.put( -110.2679, -59);
                tierMap.put( -107.4237, -58);
                tierMap.put( -104.6016, -57);
                tierMap.put( -101.8018, -56);
                tierMap.put( -99.0249, -55);
                tierMap.put( -96.2713, -54);
                tierMap.put( -93.5415, -53);
                tierMap.put( -90.8360, -52);
                tierMap.put( -88.1553, -51);
                tierMap.put( -85.4998, -50);
                tierMap.put( -82.8702, -49);
                tierMap.put( -80.2670, -48);
                tierMap.put( -77.6905, -47);
                tierMap.put( -75.1416, -46);
                tierMap.put( -72.6205, -45);
                tierMap.put( -70.1281, -44);
                tierMap.put( -67.6648, -43);
                tierMap.put( -65.2312, -42);
                tierMap.put( -62.8279, -41);
                tierMap.put( -60.4556, -40);
                tierMap.put( -58.1149, -39);
                tierMap.put( -55.8064, -38);
                tierMap.put( -53.5308, -37);
                tierMap.put( -51.2888, -36);
                tierMap.put( -49.0810, -35);
                tierMap.put( -46.9081, -34);
                tierMap.put( -44.7709, -33);
                tierMap.put( -42.6701, -32);
                tierMap.put( -40.6064, -31);
                tierMap.put( -38.5806, -30);
                tierMap.put( -36.5934, -29);
                tierMap.put( -34.6457, -28);
                tierMap.put( -32.7383, -27);
                tierMap.put( -30.8719, -26);
                tierMap.put( -29.0474, -25);
                tierMap.put( -27.2657, -24);
                tierMap.put( -25.5277, -23);
                tierMap.put( -23.8341, -22);
                tierMap.put( -22.1861, -21);
                tierMap.put( -20.5843, -20);
                tierMap.put( -19.0299, -19);
                tierMap.put( -17.5238, -18);
                tierMap.put( -16.0669, -17);
                tierMap.put( -14.6603, -16);
                tierMap.put( -13.3050, -15);
                tierMap.put( -12.0020, -14);
                tierMap.put( -10.7524, -13);
                tierMap.put( -9.5573, -12);
                tierMap.put( -8.4179, -11);
                tierMap.put( -7.3352, -10);
                tierMap.put( -6.3104, -9);
                tierMap.put( -5.3446, -8);
                tierMap.put( -4.4392, -7);
                tierMap.put( -3.5953, -6);
                tierMap.put( -2.8142, -5);
                tierMap.put( -2.0971, -4);
                tierMap.put( -1.4454, -3);
                tierMap.put( -0.8604, -2);
                tierMap.put( -0.3434, -1);
                tierMap.put(0.1041,	0);
                tierMap.put(0.4807,	1);
                tierMap.put(0.7850,	2);
                tierMap.put(1.0156,	3);
                tierMap.put(1.1708,	4);
                tierMap.put(1.2492,	5);
        }

        /** Given the difference in height from the starting y and the top of the blip medium, this function will return
         * the tier the tick before landing
         *
         * @return returns the tier at witch the blip takes place
         */
        public double getBlipTierOffset(double heightDifference){
                tier = tierMap.ceilingKey(heightDifference) == null ? 0 : tierMap.ceilingKey(heightDifference);
                return tier;
        }

        public double getNewApex(double startingHeight){
                //change to explicit?
                return startingHeight + tier + 1.2492 ;
        }


        public int getTier(double heightDifference){
                double tierOffset = getBlipTierOffset(heightDifference);
                return tierMap.get(tierOffset) == null ? 0 : tierMap.get(tierOffset);
        }

        //tier 1: blip at - starting should be .1041
        public double getDifferenceFromTierAndStartHeight(double height, double heightDifference){
//not sure the point of this. could get the absolute value of height-nearestTier
                tier = tierMap.ceilingKey(heightDifference) == null ? 0 : tierMap.ceilingKey(heightDifference);
                return height + tier;
        }
}








