import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
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

    /**
     * Estimates the ongoing fine for a list of loans without mutating the loans or users.
     * <p>
     * Useful for sending reminders or displaying current overdue fines.
     * </p>
     *
     * @param loans the list of loans to calculate fines for
     * @param baseRatePerDay the base fine rate per day for each overdue item
     * @return the total estimated fine for all loans
     */
    public double estimateOngoingFine(List<Loan> loans, double baseRatePerDay) {
       if (loans.isEmpty())return 0.0;
       double fines = 0.0;
       for(Loan loan:loans)
       {
           LocalDate dueDate = loan.getDueDate();
           LocalDate today = LocalDate.now();
           long overdueDays = ChronoUnit.DAYS.between(dueDate, today);
           if (overdueDays <= 0) return 0;
           double baseFine = overdueDays * baseRatePerDay;
           fines+= applyStrategy(baseFine);
       }
       return fines;
    }
    /**
     * Calculates the fine for a specific loan based on its return date and base rate.
     *
     * @param loan the loan to calculate the fine for
     * @param baseRatePerDay the base fine rate per day
     * @return the total fine for the loan; 0 if not returned or not overdue
     */
    public double calculateFine(Loan loan, double baseRatePerDay) {
        LocalDate dueDate = loan.getDueDate();
        LocalDate returnDate = loan.getReturnDate();
        if (returnDate == null || !loan.getReturned()) return 0;
        long overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (overdueDays <= 0) return 0;
        double baseFine = overdueDays * baseRatePerDay;
        return applyStrategy(baseFine);
    }
}
