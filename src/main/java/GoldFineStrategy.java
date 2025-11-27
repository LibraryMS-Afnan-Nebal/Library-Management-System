public class GoldFineStrategy implements FineStrategy{
    @Override
    public double calculateFine(double baseFine) {
        return baseFine * 0.8; // 20% discount
    }
}
