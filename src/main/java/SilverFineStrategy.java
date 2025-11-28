public class SilverFineStrategy implements FineStrategy{
    @Override
    public double calculateFine(double baseFine) {
        return baseFine * 0.9; // 10% discount
    }
}
