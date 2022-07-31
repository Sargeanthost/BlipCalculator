// Credit goes to Drakou111
package girraiffe;

public class BackwardsSpeedBlockCalculator {
    private final int numJump;
    private final int tierMomentum;
    private final float jumpAngle;
    private final boolean strafe;
    private final String jumpType;
    private double speedMultiplier = 0.98f;
    private double combinedPotion; // combination of swiftness and slowness
    private double speedLvlX;
    private double x;

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
            case "45 Strafe" ->
                // normal 45
                    this.speedMultiplier = 1.0;
            case "Half Angle" ->
                // consult half angle list
                    this.speedMultiplier = 1.000048;
            case "Cyn 45" ->
                // turning to like 9023402394789 degrees
                    this.speedMultiplier = 1.00306323;
            case "Optifine 45" ->
                // fastmath
                    this.speedMultiplier = 1.08802;
        }

        combinedPotion = (1 + 0.2 * swiftnessLvl) * (1 - 0.15 * slownessLvl);
        if (combinedPotion < 0) {
            combinedPotion = 0;
        }
        speedLvlX = -initialSpeedBackwards;
        x = -initialSpeedBackwards;
    }

    private void print(double x) {
        System.out.println(x - .6 + " bm");
        speedLvlX = speedLvlX * 0.91 + .026 * speedMultiplier;
        System.out.println(speedLvlX + " b/t");
    }

    public void calculateBackwardsSpeed() {
        switch (jumpType) {
            case ("Pessi"):
                int count = 0;
                while (count < numJump) {
                    count++;
                    double movementMultiplier = 1.3;
                    int n = 0;
                    if (count == 1) {
                        speedLvlX *= .546;
                        x += speedLvlX;
                        movementMultiplier = 1;
                    } else {
                        speedLvlX = speedLvlX * 0.91 + .1274 * combinedPotion + 0.2;
                        x += speedLvlX;
                    }

                    speedLvlX = speedLvlX * .546 + (0.02 * movementMultiplier * speedMultiplier);
                    x += speedLvlX;

                    if (numJump - count >= 1) {
                        n--;
                    }
                    while (n < tierMomentum - 3) {
                        // Inertia management
                        if (speedLvlX < .0054945055 && speedLvlX > -.0054945055) {
                            speedLvlX = 0;
                        }

                        speedLvlX = speedLvlX * 0.91 + .026 * speedMultiplier;
                        x += speedLvlX;
                        n++;
                    }
                }
                print(x);
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
                                                    * Math.cos(Math.toRadians(jumpAngle + 45.0));
                        } else {
                            speedLvlX =
                                    speedLvlX * .546
                                            + (.098 * combinedPotion)
                                                    * Math.cos(Math.toRadians(jumpAngle));
                        }
                    } else {
                        speedLvlX = speedLvlX * 0.91 + .1274 * combinedPotion + 0.2;
                    }
                    x += speedLvlX;

                    speedLvlX = speedLvlX * .546 + (0.02 * movementMultiplier * speedMultiplier);
                    x += speedLvlX;

                    if (numJump - count >= 1) {
                        n--;
                    }
                    while (n < tierMomentum - 3) {
                        // Inertia management
                        if (speedLvlX < .0054945055 && speedLvlX > -.0054945055) {
                            speedLvlX = 0;
                        }

                        speedLvlX = speedLvlX * 0.91 + .026 * speedMultiplier;
                        x += speedLvlX;
                        n++;
                    }
                }
                print(x);
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
                                                    * Math.cos(Math.toRadians(jumpAngle + 45.0))
                                            + 0.2 * Math.cos(Math.toRadians(jumpAngle));
                        } else {
                            speedLvlX = speedLvlX * .546 + (.1274 * combinedPotion + 0.2) * Math.cos(Math.toRadians(jumpAngle));

                        }
                    } else {
                        speedLvlX = speedLvlX * 0.91 + .1274 * combinedPotion + 0.2;
                    }
                    x += speedLvlX;

                    speedLvlX = speedLvlX * .546 + (0.02 * movementMultiplier * speedMultiplier);
                    x += speedLvlX;

                    if (numJump - count >= 1) {
                        n--;
                    }
                    while (n < tierMomentum - 3) {
                        // Inertia management
                        if (speedLvlX < .0054945055 && speedLvlX > -.0054945055) {
                            speedLvlX = 0;
                        }

                        speedLvlX = speedLvlX * 0.91 + .026 * speedMultiplier;
                        x += speedLvlX;
                        n++;
                    }
                }
                print(x);
                break;
            default:
                System.out.println("Unrecognized jump type.");
        }
    }
}
