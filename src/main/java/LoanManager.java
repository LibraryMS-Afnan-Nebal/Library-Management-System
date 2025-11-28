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

    public List<Loan>getLoanList(){return this.listOfLoans;}

    public boolean borrowBook(String title, String author, User user) {
        Book.validateTitle(title);
        Book.validateAuthor(author);
        if (user == null)
        {
            System.out.println("User cannot be null");
            return false;
        }

        if (user.getFineBalance() > 0) {
            System.out.println("Borrowing blocked: You have unpaid fines. Please pay them first.");
            return false;
        }

        for (Loan loan : LoanManager.getInstance().getLoanList()) {
            if (loan.getUser().equals(user) && !loan.getReturned()) {
                LocalDate due = loan.getDueDate();
                if (due.isBefore(LocalDate.now())) {
                    System.out.println("Borrowing blocked: You have overdue books. Return them first.");
                    return false;
                }
            }
        }

        boolean found = false;
        for (Book book : BookManager.listOfBooks)
        {
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author))
            {
                found = true;
                if (!book.getIsBorrowed())
                {
                    book.setIsBorrowed(true);
                    Loan loan = new Loan(book, user);
                    listOfLoans.add(loan);
                    user.setBorrowedBooks(book);
                    user.incrementTotalBorrowedCount();
                    UserManager.getInstance().evaluateAndPromoteUser(user);
                    System.out.println("You successfully borrowed the book.\nReturn it by: " + loan.getDueDate());
                    return true;
                }
            }
        }
        if (found)
            System.out.println("Sorry, all copies of this book are currently borrowed");
        else
            System.out.println("Book with this title and author not found");
        return false;
    }

    public boolean returnBook(String title, String author, User user)
    {
        Book.validateTitle(title);
        Book.validateAuthor(author);
        if (user == null) return false;
        boolean foundBook = false;

        for (Book book : new ArrayList<>(user.getBorrowedBooks())) {
            if (book.getTitle().equalsIgnoreCase(title) &&
                    book.getAuthor().equalsIgnoreCase(author)) {
                foundBook = true;
                book.setIsBorrowed(false);

                for (Loan loan : listOfLoans) {
                    if (loan.getUser().equals(user) &&
                            loan.getBook().equals(book) &&
                            !loan.getReturned()) {

                        loan.setReturnDate(LocalDate.now());
                        //new
                        accrueFinesForLoan(loan, 10, LocalDate.now());
                        loan.setReturned(true);
                        book.setIsBorrowed(false);
                        user.getBorrowedBooks().remove(book);

                        System.out.println("You successfully returned the book.");
                        return true;
                    }
                }

                System.out.println("Book found but no matching loan record — data inconsistency!");
                return false;
            }
        }

        if (!foundBook)
            System.out.println("You didn’t borrow this book.");
        return false;
    }

    public List<Loan> getOverdueLoans()
    {
        if (listOfLoans.isEmpty())
        {
            System.out.println("There are no loans");
            return null;
        }

        List<Loan> overdueLoans = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for(Loan loan : listOfLoans )
        {
            if (today.isAfter(loan.getDueDate()))
                overdueLoans.add(loan);
        }
        return overdueLoans;
    }

    /**
     * Accrues fines for all overdue loans up to today.
     * baseRatePerDay for books = 10, for CDs = 20, etc.
     */
    public void accrueFines(double baseRatePerDay , Loan loan) {
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

        System.out.println("Accrued " + finalFine + " NIS for user " + user.getUsername()
                + " on loan " + loan.getLoanId() + " (" + daysToAccrue + " day(s)).");
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

        System.out.println("Accrued for return: " + finalFine + " NIS for user " + user.getUsername());
    }
}