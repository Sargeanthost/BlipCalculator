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
        ArrayList<Float> apexArray = new ArrayList<>();
        // flips motionY, make new method?
        //        print();
    }

    /**
     * prints the entrances for each jumnp off of the slime
     *
     * @param apexes jl
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
