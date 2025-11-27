import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FineCalculator {
    private FineStrategy fineStrategy;

    public FineCalculator(FineStrategy fineStrategy) {
        this.fineStrategy = fineStrategy;
    }

    public void setFineStrategy(FineStrategy fineStrategy) {
        this.fineStrategy = fineStrategy;
    }
    public FineStrategy getFineStrategy() { return fineStrategy; }

    //new
    // Apply current strategy to a base fine amount
    public double applyStrategy(double baseFine) {
        if (fineStrategy == null) fineStrategy = new RegularFineStrategy();
        return fineStrategy.calculateFine(baseFine);
    }

    // Estimate ongoing fine for reminders (does not mutate loan/user)
    public double estimateOngoingFine(Loan loan, double baseRatePerDay) {
        LocalDate dueDate = loan.getDueDate();
        LocalDate today = LocalDate.now();
        long overdueDays = ChronoUnit.DAYS.between(dueDate, today);
        if (overdueDays <= 0) return 0;
        double baseFine = overdueDays * baseRatePerDay;
        return applyStrategy(baseFine);
    }
    // Keep the final-return calculation (optional)
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
