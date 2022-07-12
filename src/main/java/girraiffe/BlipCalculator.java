package girraiffe;

public class BlipCalculator {
    private final TierHelper tierHelper;

    public BlipCalculator() {
        tierHelper = new TierHelper();
    }

    /**
     * Calculates blip when jumping from {@code startingHeight}
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
            tierHelper.calculateOffsets(blipBottomHeight, position);
            position = tierHelper.getPosition();
            jumpApex = tierHelper.calculateJumpApex(position);
            if (!tierHelper.isBlipPossible(
                    blipTopHeight, blipBottomHeight, position, tierHelper.getNextPosition())) {
                // a blip can incorrectly be called possible even though a previous blip in the
                // chain failed, this "serializes" the blips (I think. at the least it doesn't hurt)"
                break;
            }
        }

        setControllerBlipCalculatorOutput(
                controller,
                (tierHelper.isBlipPossible(
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

    private void printOffsets(float lastBlipHeight) {
        System.out.println("Offsets: ");
        tierHelper.offsetList(lastBlipHeight, true).stream()
                .map(height -> tierHelper.entranceGenerator(height, true))
                .filter(s -> !s.equals(""))
                .forEach(System.out::println);
        System.out.println();
    }
}
