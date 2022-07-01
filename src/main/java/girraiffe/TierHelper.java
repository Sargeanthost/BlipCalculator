package girraiffe;

import java.util.ArrayList;
import java.util.List;

public class TierHelper {
    private float _offset = 0.0f;
    private float nextOffset = 0.0f;
    private float minimumBottomBlipHeight = 0.0f;

    public boolean isBlipPossible(float predictedBlipHeight, float blipBottomHeight) {
        var offsetDelta = Math.abs(getNextOffset() - get_offset());
        setMinimumBottomBlipHeight(predictedBlipHeight - offsetDelta);

        return predictedBlipHeight - offsetDelta <= blipBottomHeight;
    }

    public void calculateOffsets(float heightDelta) {
        // values obtained from https://www.mcpk.wiki/wiki/Tiers
        final double MM_CUTOFF_1_8 = 0.005;

        var momentum = 0.42f;
        var nextOffset = momentum;
        var offset = 0.0f;
        var ticks = 1;

        while (true) {
            if (ticks < 12 || heightDelta > Math.abs(nextOffset)) {
                ++ticks;
                offset += momentum;
                momentum -= 0.08f;
                momentum *= 0.98f;

                if (Math.abs(momentum) >= MM_CUTOFF_1_8) {
                    nextOffset += momentum;
                } else {
                    momentum = 0;
                }
                continue;
            }
            break;
        }
        setOffsets(offset, nextOffset);
    }

    // make so takes in bool of jump or no jump. jump is current behaviour
    public List<Float> offsetList(float startingHeight, boolean hasJumped) {
        final double MM_CUTOFF_1_8 = 0.005;
        float momentum = 0.42f;
        if (!hasJumped) {
            momentum = 0.0f;
        }
        float nextOffset = momentum;
        float offset = 0.0f;
        int ticks = 1;
        List<Float> newOffsetArray = new ArrayList<>();

        while (true) {
            if (ticks < 12 || startingHeight > Math.abs(nextOffset)) {
                ++ticks;
                offset += momentum;
                momentum -= 0.08f;
                momentum *= 0.98f;

                if (Math.abs(momentum) >= MM_CUTOFF_1_8) {
                    nextOffset += momentum;
                } else {
                    momentum = 0;
                }
                if (offset != 0.0f) {
                    newOffsetArray.add(startingHeight + offset);
                }
                continue;
            }
            break;
        }
        return newOffsetArray;
    }

    public String entranceGenerator(float height) {
        if (height > 0) {
            if (height % 1 < .0125) {
                return height + " - td ceiling";
            } else if (height % 1 > .1875 && height % 1 < .2) {
                return height + " - td floor";
            } else if (height % 1 > .5 && height % 1 < .5125) {
                return height + " - cake + bean";
            } else if (height % 1 > .5625 && height % 1 < .575) {
                return height + " - bed + piston";
            }
        }
        return "";
    }

    public float calculateJumpApex(double startingBlipHeight) {
        return (float) (startingBlipHeight + 1.2491871);
    }

    public float get_offset() {
        return _offset;
    }

    public float getNextOffset() {
        return nextOffset;
    }

    public float getMinimumBottomBlipHeight() {
        return minimumBottomBlipHeight;
    }

    private void setOffsets(float offset, float nextOffset) {
        this._offset = offset;
        this.nextOffset = nextOffset;
    }

    private void setMinimumBottomBlipHeight(float minimumBottomBlipHeight) {
        this.minimumBottomBlipHeight = minimumBottomBlipHeight;
    }
}
