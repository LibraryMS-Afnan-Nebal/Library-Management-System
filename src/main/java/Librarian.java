import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a librarian in the library system.
 * <p>
 * Provides methods for detecting overdue media and applying fines.
 * </p>
 */
public class Librarian  extends Role {
    public Librarian(int id, String username, String password, String email) {
        super(id, username, password, email);
    }

    /**
     * Detects all overdue media (books and CDs) and accrues fines accordingly.
     * <p>
     * Iterates through overdue loans, calculates the fine using each media's daily rate,
     * and applies it to the associated user.
     * </p>
     *
     * @return a list of all overdue loans
     */
    public List<Loan> detectOverdueMedia()
    {
        LoanManager loanManager = LoanManager.getInstance();

        List<Loan> listOfOverdueLoans = new ArrayList<>(loanManager.getOverdueLoans());

        for (Loan loan : listOfOverdueLoans) {
            Media media = loan.getMedia();
            double dailyFine = media.getDailyFineRate();
            loanManager.accrueFines(dailyFine, loan);
        }

        return listOfOverdueLoans;
    }



}
 