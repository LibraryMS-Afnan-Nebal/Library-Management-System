/**
 * Implementation of {@link FineStrategy} for Gold members.
 * <p>
 * Applies a 20% discount on the base fine for overdue items.
 * </p>
 *
 * @see FineStrategy
 * @see FineCalculator
 */
public class GoldFineStrategy implements FineStrategy{
    /**
     * Calculates the final fine for a Gold member.
     * <p>
     * This implementation applies a 20% discount to the base fine amount.
     * </p>
     *
     * @param baseFine the initial fine amount (e.g., days overdue × daily rate)
     * @return the adjusted fine after applying the 20% discount
     */
    @Override
    public double calculateFine(double baseFine) {
        return baseFine * 0.8; // 20% discount
    }
}
