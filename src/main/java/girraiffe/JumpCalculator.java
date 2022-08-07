package girraiffe;

/**
 * Calculates possible jumps.
 */
public class JumpCalculator {
    private static final double PLAYER_WIDTH = 0.6;
    private static final double PIXEL = 0.0625;
    private final float slipperiness;
    private final boolean prevAirborne; // Run a tick before jumping (true/false)
    private final double initialSpeed;         // Initial speed (your speed when Y=0, not Y=0.42)
    private final double yLimit; // How far down you want to check for (in blocks) //-895b for max down tier (jump
    // boost
    // 128)
    private final double minMakeDist;
    private final double maxMakeDist;
    private final String fortyFiveType;
    // position on ground?
    private double pogY;
    private double velocityX;
    private double velocityY;
    // local inside second while loop
    private int count;
    private double acceleration;
    // hardest jump
    private int pixelDistancePb;
    private int tierPb;
    // replace with INTEGER.MAX_VALUE?
    private double xPb = 1000;
    private double yPb;
    // weird smallest distance indicator, could refactor?
    private boolean ah = false;
    // count variable to indicate pogy < ylimit
    private int b;
    private double pogX;
    private int pixelDistance = 1;

    /**
     * Calculates possible jumps.
     *
     * @param prevAirborne  whether you are airborne on the previous tick
     * @param initialSpeed  the initial speed of the player
     * @param yLimit        how low you want to check for possible jump (-895b for max down tier)
     * @param minMakeDist   Every jump must be possible by MORE than this value (more than PLAYER_WIDTH makes no jump possible)
     * @param maxMakeDist   Every jump must be possible by LESS than this value (more than PLAYER_WIDTH is every tier)
     * @param fortyFiveType the 45 type
     */
    public JumpCalculator(
        int swiftnessLvl, int slownessLvl, double initialSpeed, double yLimit, double minMakeDist, double maxMakeDist,
        String floorType, String fortyFiveType, boolean prevAirborne) {
        double combinedPotion = (1 + 0.2 * swiftnessLvl) * (1 - 0.15 * slownessLvl);
        if (combinedPotion < 0) {
            combinedPotion = 0;
        }
        this.initialSpeed = initialSpeed;
        this.yLimit = yLimit;
        this.minMakeDist = minMakeDist;
        this.maxMakeDist = maxMakeDist;
        slipperiness = switch (floorType) {
            case "Slime" -> 0.8f;
            case "Ice" -> 0.98f;
            case "Normal", default -> 0.6f;
        };
        this.fortyFiveType = fortyFiveType;
        acceleration = 0.1 * 1.3 * .98 * combinedPotion * Math.pow(PLAYER_WIDTH / slipperiness, 3);
        this.prevAirborne = prevAirborne;
        pogX = this.initialSpeed;
    }

    public void calculateJump() {
        while (b != 1) {
            while (pogX < (pixelDistance * PIXEL) - PLAYER_WIDTH) {
                // mvt formula
                double drag;
                if (count == 0) {
                    if (prevAirborne) {
                        drag = 0.91;
                    } else {
                        drag = 0.91 * slipperiness;
                    }
                    velocityX = velocityX * drag + acceleration + 0.2;
                    velocityY = 0.42;
                } else if (count == 1) {
                    drag = 0.91 * slipperiness;
                    // this is hacky, change later
                    getAcceleration();
                    velocityX = velocityX * drag + acceleration;
                    // .08 is gravity, .98 is acceleration
                    velocityY = (velocityY - 0.08) * 0.98;
                } else {
                    drag = 0.91 * 1;
                    getAcceleration();
                    velocityX = velocityX * drag + acceleration;
                    velocityY = (velocityY - 0.08) * 0.98;
                }
                pogX += velocityX;
                pogY += velocityY;
                count++;
            }
            pixelDistance++;
            if (pogX - (((pixelDistance) * PIXEL) - PLAYER_WIDTH) > 0
                && pogX - (((pixelDistance) * PIXEL) - PLAYER_WIDTH) < PIXEL
                && pogX - (((pixelDistance) * PIXEL) - PLAYER_WIDTH) < minMakeDist
                && pogX - (((pixelDistance) * PIXEL) - PLAYER_WIDTH) > maxMakeDist) {
                ah = true;
                System.out.println();
                System.out.println("Poss by: " + (pogX - (pixelDistance * PIXEL - PLAYER_WIDTH)));
                System.out.println(
                    pixelDistance + "px " + "(" + (pixelDistance * PIXEL) + "b) Tier " + -(count - 11) + " (" + pogY
                        + "b)");
            }
            if (pogX - (((pixelDistance) * PIXEL) - PLAYER_WIDTH) < xPb) {
                xPb = pogX - (((pixelDistance) * 0.0625) - 0.6);
                pixelDistancePb = pixelDistance;
                yPb = pogY;
                tierPb = count;
            }
            if (pogY <= yLimit) {
                System.out.println("-----------------Smallest distance:--------------------");
                if (!ah) {
                    System.out.println("No jump poss by less than " + minMakeDist + " found.");
                }
                System.out.println("Poss by: " + (xPb + PIXEL));
                System.out.println(
                    (pixelDistancePb - 1) + "px " + "(" + ((pixelDistancePb - 1) * PIXEL) + "b) Tier " + -(tierPb - 11)
                        + "(" + yPb + "b)");
                b = 1;
            }
            // reset for next tier
            pogX = initialSpeed;
            pogY = 0;
            velocityX = initialSpeed;
            velocityY = 0;
            count = 0;
        }
    }

    public void getAcceleration() {
        acceleration = switch (fortyFiveType) {
            case "45 Strafe" -> 0.02 * 1.3;
            case "Half Angle" -> 0.02 * 1.3 * 1.000048;
            case "Cyn 45" -> 0.02 * 1.3 * 1.00306323;
            case "Optifine 45" -> 0.02 * 1.3 * 1.08802;
            default -> 0.02 * 1.3 * .98;
        };
    }
}
