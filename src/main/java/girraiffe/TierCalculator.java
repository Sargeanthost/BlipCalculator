package girraiffe;

public class TierCalculator {
    public void calculateTier(float startingHeight){
        TierHelper tierHelper = new TierHelper();
        tierHelper.offsetList(startingHeight, false).stream()
                .filter(f -> f > 0)
                .map(String::valueOf)
                .forEach(System.out::println);
        System.out.println();
    }
}
