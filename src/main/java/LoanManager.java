import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class LoanManager {
    private static LoanManager instance = null;
    private List<Loan> listOfLoans;

    private LoanManager() {
        listOfLoans = new ArrayList<>();
    }

    public static LoanManager getInstance() {
        if (instance == null) {
            instance = new LoanManager();
        }
        return instance;
    }

    public List<Loan> getLoanList() {
        return this.listOfLoans;
    }


    public boolean borrow(Media media, User user) {

        if (media == null || user == null) return false;

        // Rule 1: User must have no fines
        if (user.getFineBalance() > 0) {
            System.out.println("You must pay your fines before borrowing.");
            return false;
        }

        // Rule 2: User must not have overdue items
        for (Loan loan : listOfLoans) {
            if (loan.getUser().equals(user) && !loan.getReturned() &&
                    loan.getDueDate().isBefore(LocalDate.now())) {

                System.out.println("Borrowing blocked: You have overdue items.");
                return false;
            }
        }

        // Rule 3: Media must be available
        if (media.getIsBorrowed()) {
            System.out.println("This item is currently borrowed.");
            return false;
        }

        // Borrowing logic
        media.setIsBorrowed(true);
        Loan loan = new Loan(media, user);
        listOfLoans.add(loan);
        user.incrementTotalBorrowedCount();
        UserManager.getInstance().evaluateAndPromoteUser(user);
        String membership;
        FineStrategy strategy = user.getFineCalculator().getFineStrategy();

        if (strategy instanceof GoldFineStrategy) {
            membership = "GOLD member — 20% fine discount";
        }
        else if (strategy instanceof SilverFineStrategy) {
            membership = "SILVER member — 10% fine discount";
        }
        else {
            membership = "REGULAR member — No discount";
        }

        user.addBorrowedMedia(media);
        System.out.println("Borrowed successfully. Return by: " + loan.getDueDate());
        System.out.println("Your membership tier: " + membership);
        return true;
    }

    public boolean returnMedia(Media media, User user) {

        if (media == null || user == null) return false;

        for (Loan loan : listOfLoans) {
            if (loan.getUser().equals(user) &&
                    loan.getMedia().equals(media) &&
                    !loan.getReturned()) {

                media.setIsBorrowed(false);

                loan.setReturnDate(LocalDate.now());
                accrueFinesForLoan(loan, media.getDailyFineRate(), LocalDate.now());
                loan.setReturned(true);

                user.removeBorrowedMedia(media);

                System.out.println("Returned successfully.");
                return true;
            }
        }

        System.out.println("You did not borrow this item.");
        return false;
    }

    public List<Loan> getOverdueLoans() {
        List<Loan> overdueLoans = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Loan loan : listOfLoans) {
            if (today.isAfter(loan.getDueDate())) {
                overdueLoans.add(loan);
            }
        }
        return overdueLoans;
    }

    /**
     * Accrues fines for all overdue loans up to today.
     * baseRatePerDay for books = 10, for CDs = 20, etc.
     */
    public void accrueFines(double baseRatePerDay, Loan loan) {
        LocalDate today = LocalDate.now();

        LocalDate lastAccrued = loan.getLastAccruedDate();
        // daysToAccrue = days between lastAccrued (exclusive) and today (inclusive)
        long daysToAccrue = ChronoUnit.DAYS.between(lastAccrued, today);
        if (daysToAccrue <= 0) return;

        double baseFine = daysToAccrue * baseRatePerDay;
        User user = loan.getUser();
        double finalFine = user.getFineCalculator().applyStrategy(baseFine);

        user.addFineAmount(finalFine);

        // move lastAccruedDate forward to today to avoid double counting
        loan.setLastAccruedDate(today);

      //  System.out.println("Accrued " + finalFine + " NIS for user " + user.getUsername()
        //        + " on loan " + loan.getLoanId() + " (" + daysToAccrue + " day(s)).");
    }

    /**
     * Accrue fines for a single loan up to a specific date (use returnDate before marking returned).
     */
    public void accrueFinesForLoan(Loan loan, double baseRatePerDay, LocalDate uptoDate) {
        if (loan.getReturned()) return;
        LocalDate due = loan.getDueDate();
        if (!uptoDate.isAfter(due)) return;

        LocalDate lastAccrued = loan.getLastAccruedDate();
        long daysToAccrue = ChronoUnit.DAYS.between(lastAccrued, uptoDate);
        if (daysToAccrue <= 0) return;

        double baseFine = daysToAccrue * baseRatePerDay;
        User user = loan.getUser();
        double finalFine = user.getFineCalculator().applyStrategy(baseFine);

        user.addFineAmount(finalFine);
        loan.setLastAccruedDate(uptoDate);

        //System.out.println("Accrued for return: " + finalFine + " NIS for user " + user.getUsername());
    }

    public String generateOverdueReport(User user) {
        List<Loan> userOverdueLoans = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // جمع القروض المتأخرة
        for (Loan loan : listOfLoans) {
            if (loan.getUser().equals(user) && !loan.getReturned() && loan.getDueDate().isBefore(today)) {
                userOverdueLoans.add(loan);
                // تأكد من تحديث الفاين لمرة واحدة فقط
                accrueFinesForLoan(loan, loan.getMedia().getDailyFineRate(), today);
            }
        }

        if (userOverdueLoans.isEmpty()) {
            return "No overdue items.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("============ Overdue Items ============\n");

        double totalFine = 0;

        for (Loan loan : userOverdueLoans) {
            Media m = loan.getMedia();
            long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), today);

            double fine = overdueDays * m.getDailyFineRate();
            sb.append(String.format("%s | overdue %d days | fine: %.2f NIS\n", m.getTitle(), overdueDays, fine));
            totalFine += fine;
        }

        sb.append("=======================================\n");
        sb.append(String.format("Total accrued fines: %.2f NIS\n", totalFine));
        sb.append(String.format("Amount already paid: %.2f NIS\n", totalFine - user.getFineBalance()));
        sb.append(String.format("Remaining balance: %.2f NIS\n", user.getFineBalance()));

        return sb.toString();
    }
}
