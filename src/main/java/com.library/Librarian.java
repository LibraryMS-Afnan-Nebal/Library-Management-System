package com.library;

import java.util.List;
/**
 * Represents a librarian in the library system.
 * <p>
 * Provides methods for detecting overdue media and applying fines.
 * </p>
 */
public class Librarian  extends Role {
    private final LoanManager loanManager;

    public Librarian(int id, String username, String password, String email) {
        super(id, username, password, email);
        this.loanManager = LoanManager.getInstance();
    }

    public Librarian(int id, String username, String password, String email, LoanManager loanManager) {
        super(id, username, password, email);
        this.loanManager = loanManager; // inject mock
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
        List<Loan> listOfOverdueLoans = loanManager.getOverdueLoans();

        for (Loan loan : listOfOverdueLoans) {
            Media media = loan.getMedia();
            double dailyFine = media.getDailyFineRate();
            loanManager.accrueFines(dailyFine, loan);
        }

        return listOfOverdueLoans;
    }



}
 