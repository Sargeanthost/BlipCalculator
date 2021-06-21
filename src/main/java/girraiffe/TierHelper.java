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
    
    private final NavigableMap<Float, Integer> downwardsTierOffsetMap;
    float offset = 0;
    private double[] offsetArray;
    public float minimumBottomBlipHeight;

    public TierHelper() {
        /* TierOffset, Tier */
        downwardsTierOffsetMap = new TreeMap<>();

        initDownwardsTreeMap();
        initOffsetArray();
        // higher v
        // lower ^
        // ture if it preserves stack order
        //TODO reverse the previous and next methods
    }

    /**
     * Gets the offset from the {@link #downwardsTierOffsetMap} table
     * <p>
     *     139 -106 = 33
     *     getOffset(33) => -32.7383
     * </p>
     *
     * @param heightDelta the height difference for which to perform a tier lookup on
     * @return returns the offset from the table
     */
    public float getOffset(float heightDelta) {
        return downwardsTierOffsetMap.ceilingKey(heightDelta) == null
                ? 0
                : downwardsTierOffsetMap.ceilingKey(heightDelta);
    }

    /**
     * Implicitly calculates the new jump apex based off of the last blip calculation.
     *
     * @param startingHeight the height from which to preform the jump calculation
     * @return returns the new jump apex
     */
    public float getJumpApex(float startingHeight) {
        //TODO not working. 53 27 26 => 27.4723 *21.5109* 25.7343
        return startingHeight + offset + 1.2492f;
    }

    /**
     * Gets the tier based of of the delta in height.
     *
     * @param heightDelta the height distance to compute the tier. Make sure this has the
     *     correct sign
     * @return the tier. Note: will most likely be negative
     */
    public int getTier(float heightDelta) {
        var offset = getOffset(heightDelta);
        return downwardsTierOffsetMap.get(offset) == null
                ? 0
                : downwardsTierOffsetMap.get(offset);
    }

    /**
     * Gets the nearest offset
     *
     * @param blipTopHeight the height at which you start the jump to begin the blip
     * @param heightDelta the delta between the starting height and the blip height
     * @return returns the offset from the {@link #downwardsTierOffsetMap} table added to the blip
     *     height inputted into the gui
     */
    public float getNearestOffset(float blipTopHeight, float heightDelta) {
         offset = downwardsTierOffsetMap.ceilingKey(heightDelta) == null
                        ? 0
                        : downwardsTierOffsetMap.ceilingKey(heightDelta);
        return blipTopHeight + offset;
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
            float predictedOffset,
            float blipTopHeight,
            float blipBottomHeight) {
        var offsetDelta =
                Math.abs(
                        getPreviousOffset(predictedOffset) - predictedOffset
                );
        minimumBottomBlipHeight = blipTopHeight - offsetDelta;
        // any value lower than this will cause the blip to fail

        return blipTopHeight - offsetDelta <= blipBottomHeight;
        // <= blipBottomHeight;
    }

    //TODO fix these docs

    /**
     * Gets the tier next  the one given.
     *
     * flip
     * <p>E.G given -19.0299, this function will return -17.5238.
     *
     * @param key offset to index on
     * @return returns the previous offset
     */
    public float getNextOffset(float key) {
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
    public float getPreviousOffset(float key) {
        // goes up on the map
        return downwardsTierOffsetMap.lowerKey(key);
    }

    public void initDownwardsTreeMap(){
        downwardsTierOffsetMap.put(1.2492f, 5);
        downwardsTierOffsetMap.put(1.1708f, 4);
        downwardsTierOffsetMap.put(1.0156f, 3);
        downwardsTierOffsetMap.put(0.7850f, 2);
        downwardsTierOffsetMap.put(0.4807f, 1);
        downwardsTierOffsetMap.put(0.1041f, 0);
        downwardsTierOffsetMap.put(-0.3434f, -1);
        downwardsTierOffsetMap.put(-0.8604f, -2);
        downwardsTierOffsetMap.put(-1.4454f, -3);
        downwardsTierOffsetMap.put(-2.0971f, -4);
        downwardsTierOffsetMap.put(-2.8142f, -5);
        downwardsTierOffsetMap.put(-3.5953f, -6);
        downwardsTierOffsetMap.put(-4.4392f, -7);
        downwardsTierOffsetMap.put(-5.3446f, -8);
        downwardsTierOffsetMap.put(-6.3104f, -9);
        downwardsTierOffsetMap.put(-7.3352f, -10);
        downwardsTierOffsetMap.put(-8.4179f, -11);
        downwardsTierOffsetMap.put(-9.5573f, -12);
        downwardsTierOffsetMap.put(-10.7524f, -13);
        downwardsTierOffsetMap.put(-12.0020f, -14);
        downwardsTierOffsetMap.put(-13.3050f, -15);
        downwardsTierOffsetMap.put(-14.6603f, -16);
        downwardsTierOffsetMap.put(-16.0669f, -17);
        downwardsTierOffsetMap.put(-17.5238f, -18);
        downwardsTierOffsetMap.put(-19.0299f, -19);
        downwardsTierOffsetMap.put(-20.5843f, -20);
        downwardsTierOffsetMap.put(-22.1861f, -21);
        downwardsTierOffsetMap.put(-23.8341f, -22);
        downwardsTierOffsetMap.put(-25.5277f, -23);
        downwardsTierOffsetMap.put(-27.2657f, -24);
        downwardsTierOffsetMap.put(-29.0474f, -25);
        downwardsTierOffsetMap.put(-30.8719f, -26);
        downwardsTierOffsetMap.put(-32.7383f, -27);
        downwardsTierOffsetMap.put(-34.6457f, -28);
        downwardsTierOffsetMap.put(-36.5934f, -29);
        downwardsTierOffsetMap.put(-38.5806f, -30);
        downwardsTierOffsetMap.put(-40.6064f, -31);
        downwardsTierOffsetMap.put(-42.6701f, -32);
        downwardsTierOffsetMap.put(-44.7709f, -33);
        downwardsTierOffsetMap.put(-46.9081f, -34);
        downwardsTierOffsetMap.put(-49.0810f, -35);
        downwardsTierOffsetMap.put(-51.2888f, -36);
        downwardsTierOffsetMap.put(-53.5308f, -37);
        downwardsTierOffsetMap.put(-55.8064f, -38);
        downwardsTierOffsetMap.put(-58.1149f, -39);
        downwardsTierOffsetMap.put(-60.4556f, -40);
        downwardsTierOffsetMap.put(-62.8279f, -41);
        downwardsTierOffsetMap.put(-65.2312f, -42);
        downwardsTierOffsetMap.put(-67.6648f, -43);
        downwardsTierOffsetMap.put(-70.1281f, -44);
        downwardsTierOffsetMap.put(-72.6205f, -45);
        downwardsTierOffsetMap.put(-75.1416f, -46);
        downwardsTierOffsetMap.put(-77.6905f, -47);
        downwardsTierOffsetMap.put(-80.2670f, -48);
        downwardsTierOffsetMap.put(-82.8702f, -49);
        downwardsTierOffsetMap.put(-85.4998f, -50);
        downwardsTierOffsetMap.put(-88.1553f, -51);
        downwardsTierOffsetMap.put(-90.8360f, -52);
        downwardsTierOffsetMap.put(-93.5415f, -53);
        downwardsTierOffsetMap.put(-96.2713f, -54);
        downwardsTierOffsetMap.put(-99.0249f, -55);
        downwardsTierOffsetMap.put(-101.8018f, -56);
        downwardsTierOffsetMap.put(-104.6016f, -57);
        downwardsTierOffsetMap.put(-107.4237f, -58);
        downwardsTierOffsetMap.put(-110.2679f, -59);
        downwardsTierOffsetMap.put(-113.1336f, -60);
        downwardsTierOffsetMap.put(-116.0203f, -61);
        downwardsTierOffsetMap.put(-118.9277f, -62);
        downwardsTierOffsetMap.put(-121.8554f, -63);
        downwardsTierOffsetMap.put(-124.8029f, -64);
        downwardsTierOffsetMap.put(-127.7698f, -65);
        downwardsTierOffsetMap.put(-130.7559f, -66);
        downwardsTierOffsetMap.put(-133.7606f, -67);
        downwardsTierOffsetMap.put(-136.7836f, -68);
        downwardsTierOffsetMap.put(-139.8245f, -69);
        downwardsTierOffsetMap.put(-142.8831f, -70);
        downwardsTierOffsetMap.put(-145.9588f, -71);
        downwardsTierOffsetMap.put(-149.0515f, -72);
        downwardsTierOffsetMap.put(-152.1606f, -73);
        downwardsTierOffsetMap.put(-155.2861f, -74);
        downwardsTierOffsetMap.put(-158.4273f, -75);
        downwardsTierOffsetMap.put(-161.5842f, -76);
        downwardsTierOffsetMap.put(-164.7564f, -77);
        downwardsTierOffsetMap.put(-167.9434f, -78);
        downwardsTierOffsetMap.put(-171.1452f, -79);
        downwardsTierOffsetMap.put(-174.3613f, -80);
        downwardsTierOffsetMap.put(-177.5915f, -81);
        downwardsTierOffsetMap.put(-180.8355f, -82);
        downwardsTierOffsetMap.put(-184.0930f, -83);
        downwardsTierOffsetMap.put(-187.3638f, -84);
        downwardsTierOffsetMap.put(-190.6475f, -85);
        downwardsTierOffsetMap.put(-193.9440f, -86);
        downwardsTierOffsetMap.put(-197.2529f, -87);
        downwardsTierOffsetMap.put(-200.5741f, -88);
        downwardsTierOffsetMap.put(-203.9072f, -89);
        downwardsTierOffsetMap.put(-207.2521f, -90);
        downwardsTierOffsetMap.put(-210.6085f, -91);
        downwardsTierOffsetMap.put(-213.9761f, -92);
        downwardsTierOffsetMap.put(-217.3548f, -93);
        downwardsTierOffsetMap.put(-220.7443f, -94);
        downwardsTierOffsetMap.put(-224.1445f, -95);
        downwardsTierOffsetMap.put(-227.5550f, -96);
        downwardsTierOffsetMap.put(-230.9757f, -97);
        downwardsTierOffsetMap.put(-234.4064f, -98);
        downwardsTierOffsetMap.put(-237.8469f, -99);
        downwardsTierOffsetMap.put(-241.2970f, -100);
        downwardsTierOffsetMap.put(-244.7565f, -101);
        downwardsTierOffsetMap.put(-248.2252f, -102);
        downwardsTierOffsetMap.put(-251.7029f, -103);
        downwardsTierOffsetMap.put(-255.1895f, -104);
        downwardsTierOffsetMap.put(-258.685f, -105);
    }

    public void initOffsetArray(){
        //easiest way i thought of
        offsetArray = new double[]{
                0.0f,
                0.4200f,
                0.7532f,
                1.0013f,
                1.1661f,
                1.2492f,
                1.2492f,
                1.1708f,
                1.0156f,
                0.7850f,
                0.4807f,
                0.1041f,
                -0.3434f,
                -0.8604f,
                -1.4454f,
                -2.0971f,
                -2.8142f,
                -3.5953f,
                -4.4392f,
                -5.3446f,
                -6.3104f,
                -7.3352f,
                -8.4179f,
                -9.5573f,
                -10.7524f,
                -12.0020f,
                -13.3050f,
                -14.6603f,
                -16.0669f,
                -17.5238f,
                -19.0299f,
                -20.5843f,
                -22.1861f,
                -23.8341f,
                -25.5277f,
                -27.2657f,
                -29.0474f,
                -30.8719f,
                -32.7383f,
                -34.6457f,
                -36.5934f,
                -38.5806f,
                -40.6064f,
                -42.6701f,
                -44.7709f,
                -46.9081f,
                -49.0810f,
                -51.2888f,
                -53.5308f,
                -55.8064f,
                -58.1149f,
                -60.4556f,
                -62.8279f,
                -65.2312f,
                -67.6648f,
                -70.1281f,
                -72.6205f,
                -75.1416f,
                -77.6905f,
                -80.2670f,
                -82.8702f,
                -85.4998f,
                -88.1553f,
                -90.8360f,
                -93.5415f,
                -96.2713f,
                -99.0249f,
                -101.8018f,
                -104.6016f,
                -107.4237f,
                -110.2679f,
                -113.1336f,
                -116.0203f,
                -118.9277f,
                -121.8554f,
                -124.8029f,
                -127.7698f,
                -130.7559f,
                -133.7606f,
                -136.7836f,
                -139.8245f,
                -142.8831f,
                -145.9588f,
                -149.0515f,
                -152.1606f,
                -155.2861f,
                -158.4273f,
                -161.5842f,
                -164.7564f,
                -167.9434f,
                -171.1452f,
                -174.3613f,
                -177.5915f,
                -180.8355f,
                -184.0930f,
                -187.3638f,
                -190.6475f,
                -193.9440f,
                -197.2529f,
                -200.5741f,
                -203.9072f,
                -207.2521f,
                -210.6085f,
                -213.9761f,
                -217.3548f,
                -220.7443f,
                -224.1445f,
                -227.5550f,
                -230.9757f,
                -234.4064f,
                -237.8469f,
                -241.2970f,
                -244.7565f,
                -248.2252f,
                -251.7029f,
                -255.1895f};
    }

    public DoubleStream getOffsetArrayStream(){
        return Arrays.stream(offsetArray);
    }
}
