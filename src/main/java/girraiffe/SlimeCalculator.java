package girraiffe;

import java.util.ArrayList;
import java.util.List;

public class SlimeCalculator {
    final float startingHeight;
    final float slimeHeight;
    final boolean jump;

    public SlimeCalculator(float startingHeight, float slimeHeight, boolean jump) {
        this.startingHeight = startingHeight;
        this.slimeHeight = slimeHeight;
        this.jump = jump;
    }

    /**
     * Calculates slime jump from height given through constructor. Will call print with the apex of
     * each bounce, and print function will call tier helper to print offsets down to y = 0
     */
    public void calculateSlime() {
        ArrayList<Float> apexOffsetArray = new ArrayList<>();
        apexOffsetArray.add(startingHeight);
        float momentum = jump ? 0.42f : 0.0f;
        final double MM_CUTOFF_1_8 = 0.005;
        //        float momentum = 0.42f;
        float nextOffset = momentum;
        float offset = 0.0f;
        float position = startingHeight;
//        float positionPrevTick = position; // not good for first tick
//        float positionNextTick = position; // not good for first tick

        while (true) {
            // cannot blip before 7 ticks, first blip is between tick 8-9.
            // 0.019 is to check if its equal to the first tier, this is to prevent infinite loop
            //            if (!(positionNextTick <= slimeHeight + 0.019)) {
            //                offset += momentum;
//            positionPrevTick += momentum;
            //if momentum -= 0.08f *= .98 < position +.08 you cant get another positive momentum boost and thus you have finished
            //slime bouncing
            momentum -= 0.08f;
            momentum *= 0.98f;

            // Inertia
            if (Math.abs(momentum) < MM_CUTOFF_1_8) {
                momentum = 0;
            }
            position += momentum;
            //                else {
            //                    nextOffset += momentum;
            //                }
            // if bigger than previous and bigger than next, then we have a peak; add to arraylist

//            if ((position + momentum) < position && position > positionPrevTick) {
//                apexOffsetArray.add(position);
//            }
//            position += momentum;

            //                position = startingHeight + offset;
            //                positionNextTick = startingHeight + nextOffset;
            //                positionNextTick = position + nextOffset;
            if ((position < slimeHeight)) {
                momentum = -momentum;
                position = slimeHeight;
//                positionPrevTick = position;
            }
            //                if((positionNextTick < slimeHeight)){
            //                    momentum = -momentum;
            //                    offset = momentum;
            //                    position = slimeHeight;
            //                }
            //            if ()
            //                continue;
            ////            }

            //            break;
        }
    }

    /**
     * prints the entrances for each jump off of the slime
     *
     * @param apexes the apex of each jump
     */
    private void print(List<Float> apexes) {
        // should only be true for the first one
        TierHelper tierHelper = new TierHelper();
        int size = apexes.size();
        tierHelper.offsetList(apexes.get(0), jump);
        // functional way? need to check if bigger than one then loop. filter out the first from
        // list and store that maybe...
        if (size > 1) {
            for (int i = 1; i < size - 1; i++) {
                tierHelper.offsetList(apexes.get(i), false);
            }
        }
    }
}
