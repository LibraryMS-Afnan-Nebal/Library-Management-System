public class RegularFineStrategy implements FineStrategy {
    @Override
    public double calculateFine(double baseFine) {
        return baseFine; // no discount
    }
}
