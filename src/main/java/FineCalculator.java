/**
 * Calculates fines for overdue media items using a flexible strategy.
 * <p>
 * The {@link FineCalculator} uses the Strategy design pattern to apply different
 * fine calculation strategies. You can change the strategy at runtime using
 * {@link #setFineStrategy(FineStrategy)}.
 * </p>
 *
 * @see FineStrategy
 * @see RegularFineStrategy
 */
public class FineCalculator {
    /** The strategy used to calculate fines */
    private FineStrategy fineStrategy;

    /**
     * Constructs a FineCalculator with the specified fine strategy.
     *
     * @param fineStrategy the fine strategy to use
     */

    public FineCalculator(FineStrategy fineStrategy) {
        this.fineStrategy = fineStrategy;
    }
    /**
     * Sets a new fine strategy.
     *
     * @param fineStrategy the new fine strategy to use
     */
    public void setFineStrategy(FineStrategy fineStrategy) {
        this.fineStrategy = fineStrategy;
    }
    /**
     * Returns the current fine strategy.
     *
     * @return the current fine strategy
     */
    public FineStrategy getFineStrategy() { return fineStrategy; }

    /**
     * Applies the current fine strategy to a base fine amount.
     *
     * @param baseFine the base fine to apply the strategy to
     * @return the adjusted fine according to the current strategy
     */
    public double applyStrategy(double baseFine) {
        if (fineStrategy == null) fineStrategy = new RegularFineStrategy();
        return fineStrategy.calculateFine(baseFine);
    }
}
