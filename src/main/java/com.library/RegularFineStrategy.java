package com.library;

/**
 * Implementation of {@link FineStrategy} for regular (non-member or standard) users.
 * <p>
 * This strategy applies no discount; the fine is returned as the base amount.
 * </p>
 *
 * @see FineStrategy
 * @see FineCalculator
 */
public class RegularFineStrategy implements FineStrategy {
    /**
     * Calculates the final fine for a regular user.
     * <p>
     * This implementation returns the base fine without any modification.
     * </p>
     *
     * @param baseFine the initial fine amount (e.g., days overdue × daily rate)
     * @return the adjusted fine, same as the baseFine
     */
    @Override
    public double calculateFine(double baseFine) {
        return baseFine; // no discount
    }
}
