package girraiffe;

public class BackwardsSpeedCalculator {
    private int numJump;
    private double initialSpeedBackwards;
    private int tierMomentum;
    private float jumpAngle;
    private boolean strafe;
    private String jumpType;
    private double speedMultiplier = 0.98f;

    private double combinedPotion; // combination of swiftness and slowness
    private double speedLvlX;
    private double x;

    public BackwardsSpeedCalculator(
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
        this.initialSpeedBackwards = initialSpeedBackwards;
        this.jumpAngle = jumpAngle;
        this.jumpType = jumpType;
        this.strafe = strafe;

        if (mmType.equals("45 Strafe")) {
            // normal 45
            this.speedMultiplier = 1.0;
        } else if (mmType.equals("Half Angle")) {
            // consult half angle list
            this.speedMultiplier = 1.000048;
        } else if (mmType.equals("Cyn 45")) {
            // turning to like 9023402394789 degrees
            this.speedMultiplier = 1.00306323;
        } else if (mmType.equals("Optifine 45")) {
            // fastmath
            this.speedMultiplier = 1.08802;
        }

        combinedPotion = (1 + 0.2 * swiftnessLvl) * (1 - 0.15 * slownessLvl);
        if (combinedPotion < 0) {
            combinedPotion = 0;
        }
        speedLvlX = -this.initialSpeedBackwards;
        x = -this.initialSpeedBackwards;
    }

    private void print(double x) {
        System.out.println(x - .6 + " bm");
        speedLvlX = speedLvlX * 0.91 + .026 * speedMultiplier;
        System.out.println(speedLvlX + " b/t");
    }

    public void calculateBackwardsSpeed() {
        switch (jumpType) {
            case ("Pessi"):
                System.out.println("pessi");
                int count = 0;
                float x = 0.0f;
                while (count < numJump) {
                    count++;
                    double k = 1.3;
                    int n = 0;
                    if (count == 1) {
                        speedLvlX *= .546f;
                        x += speedLvlX;
                        k = 1;
                    } else {
                        speedLvlX = speedLvlX * 0.91 + .1274 * combinedPotion + 0.2;
                        x += speedLvlX;
                    }

                    speedLvlX = speedLvlX * .546 + (0.02 * k * speedMultiplier);
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
                System.out.println("fmm");
                break;
            case "Jam":
                System.out.println("jam");
                break;
            default:
                System.out.println("Unrecognized jump type.");
        }
    }
}
