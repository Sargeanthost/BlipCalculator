package girraiffe;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.DoubleStream;

public class TierHelper {
    //! naming conventions
    
    //combined- both offset and height
    //height- the y coord
    //offset - the tier mapping
    //tier - namesake
    //delta - the difference between two things
    
    private final NavigableMap<Double, Integer> downwardsTierOffsetMap;
    private final NavigableMap<Double, Integer> upwardsTierOffsetMap;
    private double tierOffset = 0;
    private double[] offsetArray;

    public TierHelper() {
        /* TierOffset, Tier */
        downwardsTierOffsetMap = new TreeMap<>();
        upwardsTierOffsetMap = new TreeMap<>();

        initDownwardsTreeMap();
        initUpwardsTreeMap();
        initOffsetArray();
        // higher v
        // lower ^
        // ture if it preserves stack order
        //TODO reverse the previous and next methods
    }

    /**
     * Gets the tier offset from the {@link #downwardsTierOffsetMap} table
     *
     * @param heightDelta the height difference for which to perform a tier lookup on
     * @return returns the offset from the table
     */
    public double getOffset(double heightDelta) {
        tierOffset =
                downwardsTierOffsetMap.ceilingKey(heightDelta) == null
                        ? 0
                        : downwardsTierOffsetMap.ceilingKey(heightDelta);
        return tierOffset;
    }

    /**
     * Implicitly calculates the new jump apex based off of the last blip calculation.
     *
     * @param startingHeight the height from which to preform the jump calculation
     * @return returns the new jump apex
     */
    public double getJumpApex(double startingHeight) {
        return startingHeight + tierOffset + 1.2492;
    }

    @SuppressWarnings("unused")
    /**
     * Gets the tier based of of the delta in height.
     *
     * @param heightDelta the height distance to compute the tier. Make sure this has the
     *     correct sign
     * @return the tier. Note: will most likely be negative
     */
    public int getTier(double heightDelta) {
        double tierOffset = getOffset(heightDelta);
        return downwardsTierOffsetMap.get(tierOffset) == null
                ? 0
                : downwardsTierOffsetMap.get(tierOffset);
    }

    /**
     * Gets the nearest to the blip height
     *
     * @param blipTopHeight the height at which you start the jump to begin the blip
     * @param heightDelta the delta between the starting height and the blip height
     * @return returns the offset from the {@link #downwardsTierOffsetMap} table added to the blip
     *     height inputted into the gui
     */
    public double getNearestOffset(double blipTopHeight, double heightDelta) {
        tierOffset =
                downwardsTierOffsetMap.ceilingKey(heightDelta) == null
                        ? 0
                        : downwardsTierOffsetMap.ceilingKey(heightDelta);
        return blipTopHeight + tierOffset;
    }

    /**
     * Calculates if the blip is possible based off the given parameters. Note: a blip is possible
     * when the delta between the top and the bottom of the blip is less than the tier difference
     * between the nearest landing tier and the next tier down.
     *
     * @param predictedOffset if you were to blip this would be your blip height
     * @param blipTopHeight the Y level of th top part of the blip
     * @param blipBottomHeight the Y level of the bottom part of the blip
     * @return returns if the blip is possible
     */
    public boolean isBlipPossible(
            double predictedOffset,
            double blipTopHeight,
            double blipBottomHeight) {
        double tierDifference =
                Math.abs(
                        (new BigDecimal(String.valueOf(getNextOffset(predictedOffset)))
                                .subtract(new BigDecimal(String.valueOf(predictedOffset)))
                                .doubleValue()));
        System.out.println(
                "\nMaximum Blip Height: "
                        + (blipTopHeight
                                - tierDifference)); // any value lower than this will cause the blip
                                                    // to fail

        return (blipTopHeight - tierDifference) <= blipBottomHeight;
    }

    //TODO fix these docs
    @SuppressWarnings("unused")
    /**
     * Gets the tier next  the one given.
     *
     * flip
     * <p>E.G given -19.0299, this function will return -17.5238.
     *
     * @param key offset to index on
     * @return returns the previous offset
     */
    public double getNextOffset(double key) {
        // goes down on the map
        return downwardsTierOffsetMap.higherKey(key);
    }

    /**
     * Gets the previous tier below the given one
     *
     * <p>E.G. given -19.0299, this function will return -20.5843
     *
     * @param key offset to index on
     * @return returns the next offset
     */
    public double getPreviousOffset(double key) {
        // goes up on the map
        return downwardsTierOffsetMap.lowerKey(key);
    }

    public void initDownwardsTreeMap(){
        downwardsTierOffsetMap.put(1.2492, 5);
        downwardsTierOffsetMap.put(1.1708, 4);
        downwardsTierOffsetMap.put(1.0156, 3);
        downwardsTierOffsetMap.put(0.7850, 2);
        downwardsTierOffsetMap.put(0.4807, 1);
        downwardsTierOffsetMap.put(0.1041, 0);
        downwardsTierOffsetMap.put(-0.3434, -1);
        downwardsTierOffsetMap.put(-0.8604, -2);
        downwardsTierOffsetMap.put(-1.4454, -3);
        downwardsTierOffsetMap.put(-2.0971, -4);
        downwardsTierOffsetMap.put(-2.8142, -5);
        downwardsTierOffsetMap.put(-3.5953, -6);
        downwardsTierOffsetMap.put(-4.4392, -7);
        downwardsTierOffsetMap.put(-5.3446, -8);
        downwardsTierOffsetMap.put(-6.3104, -9);
        downwardsTierOffsetMap.put(-7.3352, -10);
        downwardsTierOffsetMap.put(-8.4179, -11);
        downwardsTierOffsetMap.put(-9.5573, -12);
        downwardsTierOffsetMap.put(-10.7524, -13);
        downwardsTierOffsetMap.put(-12.0020, -14);
        downwardsTierOffsetMap.put(-13.3050, -15);
        downwardsTierOffsetMap.put(-14.6603, -16);
        downwardsTierOffsetMap.put(-16.0669, -17);
        downwardsTierOffsetMap.put(-17.5238, -18);
        downwardsTierOffsetMap.put(-19.0299, -19);
        downwardsTierOffsetMap.put(-20.5843, -20);
        downwardsTierOffsetMap.put(-22.1861, -21);
        downwardsTierOffsetMap.put(-23.8341, -22);
        downwardsTierOffsetMap.put(-25.5277, -23);
        downwardsTierOffsetMap.put(-27.2657, -24);
        downwardsTierOffsetMap.put(-29.0474, -25);
        downwardsTierOffsetMap.put(-30.8719, -26);
        downwardsTierOffsetMap.put(-32.7383, -27);
        downwardsTierOffsetMap.put(-34.6457, -28);
        downwardsTierOffsetMap.put(-36.5934, -29);
        downwardsTierOffsetMap.put(-38.5806, -30);
        downwardsTierOffsetMap.put(-40.6064, -31);
        downwardsTierOffsetMap.put(-42.6701, -32);
        downwardsTierOffsetMap.put(-44.7709, -33);
        downwardsTierOffsetMap.put(-46.9081, -34);
        downwardsTierOffsetMap.put(-49.0810, -35);
        downwardsTierOffsetMap.put(-51.2888, -36);
        downwardsTierOffsetMap.put(-53.5308, -37);
        downwardsTierOffsetMap.put(-55.8064, -38);
        downwardsTierOffsetMap.put(-58.1149, -39);
        downwardsTierOffsetMap.put(-60.4556, -40);
        downwardsTierOffsetMap.put(-62.8279, -41);
        downwardsTierOffsetMap.put(-65.2312, -42);
        downwardsTierOffsetMap.put(-67.6648, -43);
        downwardsTierOffsetMap.put(-70.1281, -44);
        downwardsTierOffsetMap.put(-72.6205, -45);
        downwardsTierOffsetMap.put(-75.1416, -46);
        downwardsTierOffsetMap.put(-77.6905, -47);
        downwardsTierOffsetMap.put(-80.2670, -48);
        downwardsTierOffsetMap.put(-82.8702, -49);
        downwardsTierOffsetMap.put(-85.4998, -50);
        downwardsTierOffsetMap.put(-88.1553, -51);
        downwardsTierOffsetMap.put(-90.8360, -52);
        downwardsTierOffsetMap.put(-93.5415, -53);
        downwardsTierOffsetMap.put(-96.2713, -54);
        downwardsTierOffsetMap.put(-99.0249, -55);
        downwardsTierOffsetMap.put(-101.8018, -56);
        downwardsTierOffsetMap.put(-104.6016, -57);
        downwardsTierOffsetMap.put(-107.4237, -58);
        downwardsTierOffsetMap.put(-110.2679, -59);
        downwardsTierOffsetMap.put(-113.1336, -60);
        downwardsTierOffsetMap.put(-116.0203, -61);
        downwardsTierOffsetMap.put(-118.9277, -62);
        downwardsTierOffsetMap.put(-121.8554, -63);
        downwardsTierOffsetMap.put(-124.8029, -64);
        downwardsTierOffsetMap.put(-127.7698, -65);
        downwardsTierOffsetMap.put(-130.7559, -66);
        downwardsTierOffsetMap.put(-133.7606, -67);
        downwardsTierOffsetMap.put(-136.7836, -68);
        downwardsTierOffsetMap.put(-139.8245, -69);
        downwardsTierOffsetMap.put(-142.8831, -70);
        downwardsTierOffsetMap.put(-145.9588, -71);
        downwardsTierOffsetMap.put(-149.0515, -72);
        downwardsTierOffsetMap.put(-152.1606, -73);
        downwardsTierOffsetMap.put(-155.2861, -74);
        downwardsTierOffsetMap.put(-158.4273, -75);
        downwardsTierOffsetMap.put(-161.5842, -76);
        downwardsTierOffsetMap.put(-164.7564, -77);
        downwardsTierOffsetMap.put(-167.9434, -78);
        downwardsTierOffsetMap.put(-171.1452, -79);
        downwardsTierOffsetMap.put(-174.3613, -80);
        downwardsTierOffsetMap.put(-177.5915, -81);
        downwardsTierOffsetMap.put(-180.8355, -82);
        downwardsTierOffsetMap.put(-184.0930, -83);
        downwardsTierOffsetMap.put(-187.3638, -84);
        downwardsTierOffsetMap.put(-190.6475, -85);
        downwardsTierOffsetMap.put(-193.9440, -86);
        downwardsTierOffsetMap.put(-197.2529, -87);
        downwardsTierOffsetMap.put(-200.5741, -88);
        downwardsTierOffsetMap.put(-203.9072, -89);
        downwardsTierOffsetMap.put(-207.2521, -90);
        downwardsTierOffsetMap.put(-210.6085, -91);
        downwardsTierOffsetMap.put(-213.9761, -92);
        downwardsTierOffsetMap.put(-217.3548, -93);
        downwardsTierOffsetMap.put(-220.7443, -94);
        downwardsTierOffsetMap.put(-224.1445, -95);
        downwardsTierOffsetMap.put(-227.5550, -96);
        downwardsTierOffsetMap.put(-230.9757, -97);
        downwardsTierOffsetMap.put(-234.4064, -98);
        downwardsTierOffsetMap.put(-237.8469, -99);
        downwardsTierOffsetMap.put(-241.2970, -100);
        downwardsTierOffsetMap.put(-244.7565, -101);
        downwardsTierOffsetMap.put(-248.2252, -102);
        downwardsTierOffsetMap.put(-251.7029, -103);
        downwardsTierOffsetMap.put(-255.1895, -104);
    }

    private void initUpwardsTreeMap(){
        upwardsTierOffsetMap.put(1.2492, 6);
        upwardsTierOffsetMap.put(1.1661, 5);
        upwardsTierOffsetMap.put(1.0013, 4);
        upwardsTierOffsetMap.put(0.7532, 3);
        upwardsTierOffsetMap.put(0.4200, 2);
        upwardsTierOffsetMap.put(0.0,    1);
    }

    public void initOffsetArray(){
        //easiest way i thought of
        offsetArray = new double[]{
                0.0,
                0.4200,
                0.7532,
                1.0013,
                1.1661,
                1.2492,
                1.2492,
                1.1708,
                1.0156,
                0.7850,
                0.4807,
                0.1041,
                -0.3434,
                -0.8604,
                -1.4454,
                -2.0971,
                -2.8142,
                -3.5953,
                -4.4392,
                -5.3446,
                -6.3104,
                -7.3352,
                -8.4179,
                -9.5573,
                -10.7524,
                -12.0020,
                -13.3050,
                -14.6603,
                -16.0669,
                -17.5238,
                -19.0299,
                -20.5843,
                -22.1861,
                -23.8341,
                -25.5277,
                -27.2657,
                -29.0474,
                -30.8719,
                -32.7383,
                -34.6457,
                -36.5934,
                -38.5806,
                -40.6064,
                -42.6701,
                -44.7709,
                -46.9081,
                -49.0810,
                -51.2888,
                -53.5308,
                -55.8064,
                -58.1149,
                -60.4556,
                -62.8279,
                -65.2312,
                -67.6648,
                -70.1281,
                -72.6205,
                -75.1416,
                -77.6905,
                -80.2670,
                -82.8702,
                -85.4998,
                -88.1553,
                -90.8360,
                -93.5415,
                -96.2713,
                -99.0249,
                -101.8018,
                -104.6016,
                -107.4237,
                -110.2679,
                -113.1336,
                -116.0203,
                -118.9277,
                -121.8554,
                -124.8029,
                -127.7698,
                -130.7559,
                -133.7606,
                -136.7836,
                -139.8245,
                -142.8831,
                -145.9588,
                -149.0515,
                -152.1606,
                -155.2861,
                -158.4273,
                -161.5842,
                -164.7564,
                -167.9434,
                -171.1452,
                -174.3613,
                -177.5915,
                -180.8355,
                -184.0930,
                -187.3638,
                -190.6475,
                -193.9440,
                -197.2529,
                -200.5741,
                -203.9072,
                -207.2521,
                -210.6085,
                -213.9761,
                -217.3548,
                -220.7443,
                -224.1445,
                -227.5550,
                -230.9757,
                -234.4064,
                -237.8469,
                -241.2970,
                -244.7565,
                -248.2252,
                -251.7029,
                -255.1895};
    }

    public DoubleStream getOffsetArrayStream(){
        return Arrays.stream(offsetArray);
    }
}
