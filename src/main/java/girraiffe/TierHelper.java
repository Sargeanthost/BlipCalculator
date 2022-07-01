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
        //values obtained from https://www.mcpk.wiki/wiki/Tiers
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

    public List<Float> newGenerateOffset(float startingHeight) {
        final double MM_CUTOFF_1_8 = 0.005;
        final double MM_CUTOFF_1_9 = 0.003;
        var momentum = 0.42f;
        var nextOffset = momentum;
        var offset = 0.0f;
        var ticks = 1;
        List<Float> newOffsetArray = new ArrayList<>();

        while (true) {
            if (ticks < 12 || startingHeight > Math.abs(nextOffset)) {
                ++ticks;
                offset += momentum;
                momentum -= 0.08f;
                momentum *= 0.98f;

                //1.8 value for mm cutoff
                if (Math.abs(momentum) >= MM_CUTOFF_1_8) {
                    nextOffset += momentum;
                } else {
                    momentum = 0;
                }
                newOffsetArray.add(startingHeight + offset);
                continue;
            }
            break;
        }
        return newOffsetArray;
    }

    public String entranceGenerator(float startingHeight) {
        if (startingHeight > 0) {
            if (startingHeight % 1 < .0125) {
                return startingHeight + " - td ceiling";
            } else if (startingHeight % 1 > .1875 && startingHeight % 1 < .2) {
                return startingHeight + " - td floor";
            } else if (startingHeight % 1 > .5 && startingHeight % 1 < .5125) {
                return startingHeight + " - cake + bean";
            } else if (startingHeight % 1 > .5625 && startingHeight % 1 < .575) {
                return startingHeight + " - bed + piston";
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