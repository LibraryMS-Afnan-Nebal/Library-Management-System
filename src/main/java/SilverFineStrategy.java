/**
 * Implementation of {@link FineStrategy} for Silver members.
 * <p>
 * Applies a 10% discount on the base fine for overdue items.
 * </p>
 *
 * @see FineStrategy
 * @see FineCalculator
 */
public class SilverFineStrategy implements FineStrategy{
    /**
     * Implementation of {@link FineStrategy} for Silver members.
     * <p>
     * Applies a 10% discount on the base fine for overdue items.
     * </p>
     *
     * @see FineStrategy
     * @see FineCalculator
     */
    @Override
    public double calculateFine(double baseFine) {
        return baseFine * 0.9; // 10% discount
    }
}
