// Credit goes to Drakou111

package girraiffe;

/**
 * Class for finding the blocks required for a specific backwards speed and entered inputs.
 */
public class BackwardsSpeedBlockCalculator {
    private final int numJump;
    private final int tierMomentum;
    private final float jumpAngle;
    private final boolean strafe;
    private final String jumpType;
    private final double speedMultiplier;
    private double combinedPotion; // combination of swiftness and slowness
    private double speedLvlX;
    private double tempX;

    /**
     * Class for finding the blocks required for a specific backwards speed and entered inputs.
     *
     * @param numJump the number of forward jumps, not including the transition jump from backwards to forwards
     * @param swiftnessLvl the level of swiftness potion
     * @param slownessLvl the level of slowness potion
     * @param tierMomentum the height of the blocks above the ground. Flat ground is 0 and a slab is 3
     * @param initialSpeedBackwards the initial speed of the backwards a tick before the jump.
     * @param jumpAngle the facing on the jump tick
     * @param mmType the 45 type
     * @param jumpType the inputs on the jump tick
     * @param strafe whether you strafe on the jump tick
     */
    public BackwardsSpeedBlockCalculator(
            int numJump,
            int swiftnessLvl,
            int slownessLvl,
            int tierMomentum,
            double initialSpeedBackwards,
            float jumpAngle,
            String mmType,
            String jumpType,
            boolean strafe) {
        this.numJump = numJump;
        // huh
        this.tierMomentum = -(tierMomentum - 12);
        this.jumpAngle = jumpAngle;
        this.jumpType = jumpType;
        this.strafe = strafe;

        switch (mmType) {
            case "45 Strafe", default -> this.speedMultiplier = 1.0;
            case "Half Angle" -> this.speedMultiplier = 1.000048;
            case "Cyn 45" -> this.speedMultiplier = 1.00306323;
            case "Optifine 45" -> this.speedMultiplier = 1.08802;
        }

        combinedPotion = (1 + 0.2 * swiftnessLvl) * (1 - 0.15 * slownessLvl);
        if (combinedPotion < 0) {
            combinedPotion = 0;
        }
        speedLvlX = -initialSpeedBackwards;
        tempX = -initialSpeedBackwards;
    }

    /**
     * Prints the blocks required for the backwards speed and the speed at the end of the jump.
     *
     * @param x the x coordinate of the block
     */
    private void print(double x) {
        System.out.println(x - .6 + " bm");
        speedLvlX = speedLvlX * 0.91 + .026 * speedMultiplier;
        System.out.println(speedLvlX + " b/t");
    }

    /**
     * Method for finding the blocks required for a specific backwards speed and entered inputs.
     */
    public void calculateBackwardsSpeed() {
        double cos = Math.cos(Math.toRadians(jumpAngle + 45.0));
        switch (jumpType) {
            case ("Pessi"):
                int count = 0;
                while (count < numJump) {
                    count++;
                    double movementMultiplier = 1.3;
                    int n = 0;
                    if (count == 1) {
                        speedLvlX *= .546;
                        tempX += speedLvlX;
                        movementMultiplier = 1;
                    } else {
                        speedLvlX = speedLvlX * 0.91 + .1274 * combinedPotion + 0.2;
                        tempX += speedLvlX;
                    }

                    speedLvlX = speedLvlX * .546 + (0.02 * movementMultiplier * speedMultiplier);
                    tempX += speedLvlX;

                    if (numJump - count >= 1) {
                        n--;
                    }
                    while (n < tierMomentum - 3) {
                        // Inertia management
                        if (speedLvlX < .0054945055 && speedLvlX > -.0054945055) {
                            speedLvlX = 0;
                        }

                        speedLvlX = speedLvlX * 0.91 + .026 * speedMultiplier;
                        tempX += speedLvlX;
                        n++;
                    }
                }
                print(tempX);
                break;
            case "Fmm":
                count = 0;
                while (count < numJump) {
                    count++;
                    double movementMultiplier = 1.3;
                    int n = 0;
                    if (count == 1) {
                        movementMultiplier = 1;
                        if (strafe) {
                            speedLvlX =
                                    speedLvlX * .546
                                            + (0.1 * combinedPotion)
                                                    * cos;
                        } else {
                            speedLvlX =
                                    speedLvlX * .546
                                            + (.098 * combinedPotion)
                                                    * Math.cos(Math.toRadians(jumpAngle));
                        }
                    } else {
                        speedLvlX = speedLvlX * 0.91 + .1274 * combinedPotion + 0.2;
                    }
                    tempX += speedLvlX;

                    speedLvlX = speedLvlX * .546 + (0.02 * movementMultiplier * speedMultiplier);
                    tempX += speedLvlX;

                    if (numJump - count >= 1) {
                        n--;
                    }
                    while (n < tierMomentum - 3) {
                        // Inertia management
                        if (speedLvlX < .0054945055 && speedLvlX > -.0054945055) {
                            speedLvlX = 0;
                        }

                        speedLvlX = speedLvlX * 0.91 + .026 * speedMultiplier;
                        tempX += speedLvlX;
                        n++;
                    }
                }
                print(tempX);
                break;
            case "Jam":
                count = 0;
                while (count < numJump) {
                    count++;
                    double movementMultiplier = 1.3;
                    int n = 0;
                    if (count == 1) {
                        if (strafe) {
                            speedLvlX =
                                    speedLvlX * .546
                                            + (.13 * combinedPotion)
                                                    * cos
                                            + 0.2 * Math.cos(Math.toRadians(jumpAngle));
                        } else {
                            speedLvlX = speedLvlX * .546 + (.1274 * combinedPotion + 0.2)
                                * Math.cos(Math.toRadians(jumpAngle));

                        }
                    } else {
                        speedLvlX = speedLvlX * 0.91 + .1274 * combinedPotion + 0.2;
                    }
                    tempX += speedLvlX;

                    speedLvlX = speedLvlX * .546 + (0.02 * movementMultiplier * speedMultiplier);
                    tempX += speedLvlX;

                    if (numJump - count >= 1) {
                        n--;
                    }
                    while (n < tierMomentum - 3) {
                        // Inertia management
                        if (speedLvlX < .0054945055 && speedLvlX > -.0054945055) {
                            speedLvlX = 0;
                        }

                        speedLvlX = speedLvlX * 0.91 + .026 * speedMultiplier;
                        tempX += speedLvlX;
                        n++;
                    }
                }
                print(tempX);
                break;
            default:
                System.out.println("Unrecognized jump type.");
        }
    }
}
