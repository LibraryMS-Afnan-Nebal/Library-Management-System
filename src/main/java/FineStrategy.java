/**
 * Strategy interface for calculating fines in the Library Management System.
 * <p>
 * Implementations of this interface define different ways to calculate
 * fines for overdue media items based on a base fine amount.
 * </p>
 * <p>
 * This interface is used by {@link FineCalculator} to apply flexible fine rules.
 * </p>
 *
 * @see FineCalculator
 */
public interface FineStrategy {
    /**
     * Calculates the final fine based on a given base fine amount.
     *
     * @param baseFine the initial fine amount (e.g., days overdue × daily rate)
     * @return the adjusted fine according to the implemented strategy
     */
    double calculateFine(double baseFine);
}
