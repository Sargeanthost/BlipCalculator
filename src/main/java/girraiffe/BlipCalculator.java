package girraiffe;

public class BlipCalculator {
    //TODO most likely static class
    private final TierHelper tierHelper;

    public BlipCalculator() {
        tierHelper = new TierHelper();
    }

    public void calculateBlip(
            int chain,
            float startingBlipHeight,
            float blipTopHeight,
            float blipBottomHeight,
            Controller controller) {
        float heightDelta;
        float jumpApex = 0;
        float nearestCombinedOffset = 0;

        for (int i = 0; i < chain; i++) {
            heightDelta = startingBlipHeight - blipTopHeight;
            tierHelper.calculateOffsets(heightDelta);
            nearestCombinedOffset = startingBlipHeight + tierHelper.get_offset();
            jumpApex = tierHelper.calculateJumpApex(nearestCombinedOffset);
            startingBlipHeight = nearestCombinedOffset;
        }

        // TODO
        controller.setBlipCalculatorOutput(
                (tierHelper.isBlipPossible(nearestCombinedOffset, blipBottomHeight) ? "Yes" : "No"),
                String.valueOf(nearestCombinedOffset),
                String.valueOf(jumpApex),
                String.valueOf(tierHelper.getMinimumBottomBlipHeight()));

        printOffsets(nearestCombinedOffset);
    }

    private void printOffsets(float lastBlipHeight) {
        System.out.println("Offsets (if any): ");
        tierHelper.offsetList(lastBlipHeight, true).stream()
                .map(tierHelper::entranceGenerator)
                .filter(s -> !s.equals(""))
                .forEach(System.out::println);
        System.out.println();
    }
}
