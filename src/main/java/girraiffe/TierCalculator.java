package girraiffe;

public class TierCalculator {
    private final boolean selected;
    private final float startingHeight;

    public TierCalculator(float startingHeight, boolean selected){
        this.startingHeight = startingHeight;
        this.selected = selected;
    }
    public void calculateAndOutputTier() {
        TierHelper tierHelper = new TierHelper();
        if (!selected) {
            System.out.println("Offsets: ");
            tierHelper.offsetList(startingHeight, false).stream()
                    .filter(f -> f > 0)
                    .map(height -> tierHelper.entranceGenerator(height, false))
                    .forEach(System.out::println);
            System.out.println();
        } else{
            System.out.println("Offsets: ");
            tierHelper.offsetList(startingHeight, true).stream()
                    .filter(f -> f > 0) //somehow we get up to negative 4...
                    .map(height -> tierHelper.entranceGenerator(height, false))
                    .forEach(System.out::println);
            System.out.println();
        }
    }
}
