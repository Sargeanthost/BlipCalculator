package girraiffe;

public class TierCalculator {
    public void calculateTier(float startingHeight, boolean selected) {
        TierHelper tierHelper = new TierHelper();
        if (!selected) {
            tierHelper.offsetList(startingHeight, false).stream()
                    .filter(f -> f > 0)
                    .map(height -> tierHelper.entranceGenerator(height, false))
                    .forEach(System.out::println);
            System.out.println();
        } else{
            tierHelper.offsetList(startingHeight, true).stream()
                    .filter(f -> f > 0) //some how we get up to negative 4...
                    .map(height -> tierHelper.entranceGenerator(height, false))
                    .forEach(System.out::println);
            System.out.println();
        }
    }
}
