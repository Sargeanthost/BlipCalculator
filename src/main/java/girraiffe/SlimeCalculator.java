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
        List<String> positions = new ArrayList<>();
        apexArray.add(startingHeight);
        positions.add("Bounce: 1");
        float momentum = jump ? 0.42f : 0.0f;
        float nextMomentum;
        final double MM_CUTOFF_1_8 = 0.005;
        float position = startingHeight;

        while (true) {
            momentum = (momentum - 0.08f) * 0.98f;
            nextMomentum = (momentum - 0.08f) * 0.98f;

            // Inertia, unnecessary?
            if (Math.abs(momentum) < MM_CUTOFF_1_8) {
                momentum = 0;
            }

            // checks for local maximum
            if (position < position + momentum
                    && position + momentum > position + momentum + nextMomentum) {
                apexArray.add(position + momentum);
                positions.add("Bounce: " + (apexArray.size()));
            }

            // slime is finished bouncing
            if (position == slimeHeight && position + momentum < slimeHeight) {
                break;
            }

            position += momentum;
            if (position > 0) {
                positions.add(format(position));
            }

            if ((position < slimeHeight)) {
                momentum = -momentum;
                position = slimeHeight;
            }
        }
        positions.forEach(System.out::println);
        System.out.println();
    }

    private String format(float height) {
        TierHelper tierHelper = new TierHelper();
        return tierHelper.entranceGenerator(height, false);
    }
}
