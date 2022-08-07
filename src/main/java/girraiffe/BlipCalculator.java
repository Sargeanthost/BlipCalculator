package girraiffe;

/** Calculates all statistics for a blip. */
public class BlipCalculator {
    private final TierHelper tierHelper;

    public BlipCalculator() {
        tierHelper = new TierHelper();
    }

    /**
     * Calls {@code TierHelper#calculateEndOfBlipPosition} chain number of times and outputs the
     * final run's variables.
     *
     * @param chain how many times to chain the blip
     * @param startingHeight the starting height before you jump into a blip
     * @param blipTopHeight the top of the blip
     * @param blipBottomHeight the bottom of the blip
     * @param controller the javafx controller
     */
    public void calculateBlip(
            int chain,
            float startingHeight,
            float blipTopHeight,
            float blipBottomHeight,
            Controller controller) {
        float position = startingHeight;
        float jumpApex = 0;
        for (int i = 0; i < chain; i++) {
            tierHelper.calculateEndOfBlipPosition(blipBottomHeight, position);
            position = tierHelper.getPosition();
            jumpApex = tierHelper.calculateJumpApex(position);
            if (!isBlipPossible(
                    blipTopHeight, blipBottomHeight, position, tierHelper.getNextPosition())) {
                // a blip can incorrectly be called possible even though a previous blip in the
                // chain failed, this "serializes" the blips (I think. at the least it doesn't hurt)
                break;
            }
        }

        setControllerBlipCalculatorOutput(
                controller,
                (isBlipPossible(
                                blipTopHeight,
                                blipBottomHeight,
                                position,
                                tierHelper.getNextPosition())
                        ? "Yes"
                        : "No"),
                String.valueOf(position),
                String.valueOf(jumpApex),
                String.valueOf(tierHelper.getMinimumBottomBlipHeight()),
                position);
    }

    private void setControllerBlipCalculatorOutput(
            Controller controller,
            String blipPossibleTf,
            String landing,
            String jumpApexTf,
            String minimumBottomBlipHeight,
            float position) {
        controller.setBlipCalculatorOutput(
                blipPossibleTf, landing, jumpApexTf, minimumBottomBlipHeight);
        printOffsets(position);
    }

    @SuppressWarnings("SpellCheckingInspection")
    private void printOffsets(float lastBlipHeight) {
        // TODO don't print offsets when lastblipheight doesn't contain anything
        System.out.println("Offsets: ");
        tierHelper.offsetList(lastBlipHeight, true).stream()
                .map(height -> tierHelper.entranceGenerator(height, true))
                .filter(s -> !s.equals(""))
                .forEach(System.out::println);
        System.out.println();
    }

    private boolean isBlipPossible(
            float blipTopHeight, float blipBottomHeight, float position, float nextPosition) {
        if (blipBottomHeight >= blipTopHeight || position < blipTopHeight) {
            return false;
        }
        if (position > blipTopHeight && nextPosition < blipBottomHeight) {
            return true;
        }
        System.out.println("Unexpected blip condition");
        return true;
    }
}
