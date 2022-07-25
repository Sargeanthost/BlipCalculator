package girraiffe;

public class BackwardsSpeedCalculator {
    private int numJump = 1;
    private float initialSpeedBackwards = 0.0f;
    private int tierMomentum = -2;
    private int swiftnessLvl = 0;
    private int slownessLvl = 0;
    private float jumpAngle = 0.0f;
    private boolean strafe = false;
    private String jumpType = "";
    private float mmType = 0.98f;

    private double combinedPotion; // combination of swiftness and slowness
    private double distance;
    private double speedLvlX = -initialSpeedBackwards;
    private double x = -initialSpeedBackwards;
    private int n;

    public BackwardsSpeedCalculator(
            int numJump,
            int swiftnessLvl,
            int slownessLvl,
            float initialSpeedBackwards,
            float jumpAngle,
            String mmType,
            String jumpType,
            boolean strafe) {
        this.numJump = numJump;
        //after your mm, the max speed vector. this program calculates the block you need for the mm given
        this.initialSpeedBackwards = initialSpeedBackwards;
        this.swiftnessLvl = swiftnessLvl;
        this.slownessLvl = slownessLvl;
        // .98  1  1.000048  1.00306323  1.08802
        //"No 45 Strafe", "45 Strafe", "Half Angle", "Cyn 45", "Optifine 45"
        if (mmType.equals("45 Strafe")){
            //normal 45
            this.mmType = 1.0f;
        } else if (mmType.equals("Half Angle")){
            //consult half angle list
            this.mmType = 1.000048f;
        } else if (mmType.equals("Cyn 45")){
            //turning to like 9023402394789 degrees
            this.mmType = 1.00306323f;
        } else if (mmType.equals("Optifine 45")){
            //fastmath

        }
        this.mmType = mmType;
        this.jumpAngle = jumpAngle;
        this.jumpType = jumpType;
        this.strafe = strafe;

        tierMomentum = -(tierMomentum - 12);
        combinedPotion = ((1 + 0.2f * this.swiftnessLvl) * (1 - 0.15f * slownessLvl));
        if (combinedPotion < 0) {
            combinedPotion = 0;
        }
    }

    public void print(float x, float speedMultiplier) {
        System.out.println(x - .6 + " bm");
        speedLvlX = speedLvlX * 0.91f + .026f * speedMultiplier;
        System.out.println(speedLvlX + " b/t");
    }

    private void calculateBackwardsSpeed() {
        switch (jumpType) {
            case ("Pessi"):
                int count = 0;
                int k = 1.3f;
                while (count < numJump) {
                    count++;
                    if (count == 1) {
                            speedLvlX *= .546f;
                            x += speedLvlX;
                            k = 1;
                    } else {
                        speedLvlX = speedLvlX * 0.91f + .1274f * combinedPotion + 0.2f;
                        x += speedLvlX;
                    }
                    speedLvlX = speedLvlX * .546 + (0.02 * k * speedmul);
                    x = x + speedLvlX;
                    n = 0;

                    if (numjump - count >= 1) {
                        n--;
                    }
                }
                break;
            case "Fmm":
                break;
            case "Jam":
                break;
            default:
                System.out.println("Unrecognized jump type.");
        }
        //        print();
    }
}
