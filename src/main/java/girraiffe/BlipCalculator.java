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
        controller.setLastBlipHeight(nearestCombinedOffset);
        //        bcBlipPossibleTf.setText(
        //                tierHelper.isBlipPossible(nearestCombinedOffset, blipBottomHeight) ? "Yes"
        // : "No");
        //        bcNearestTierTf.setText(String.valueOf(nearestCombinedOffset));
        //        bcJumpApexTf.setText(String.valueOf(jumpApex));
        //
        // bcMinBottomBlipHeightTf.setText(String.valueOf(tierHelper.getMinimumBottomBlipHeight()));

        //        lastBlipHeight = nearestCombinedOffset;
    }
}
