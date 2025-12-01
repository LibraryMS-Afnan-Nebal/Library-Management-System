import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a user in the library system.
 * Each user has an ID, username, password, fine balance, borrowing status, array of borrowed books, and login status
 * Users cannot borrow books if they have unpaid fines.
 *
 * @author Nebal
 * @version 1.0
 */
public class User extends Role{

    private double fineBalance;
    private boolean canBorrow;
    private List<Media> borrowedMedia;
    private FineCalculator fineCalculator;
    private int totalBorrowedCount = 0;

    public User(int id, String username, String password, String email) {
        super(id, username, password, email);
        this.fineBalance = 0;
        this.canBorrow = true;
        this.borrowedMedia = new ArrayList<>();
        this.fineCalculator = new FineCalculator(new RegularFineStrategy());
    }




    public void setCanBorrow(boolean canBorrow) {
        this.canBorrow = canBorrow;
    }
    public double getFineBalance() {
        return fineBalance;
    }
    public boolean canBorrow() {
        return canBorrow;
    }
    public List<Media> getBorrowedMedia() {
        return borrowedMedia;
    }
    public void addBorrowedMedia(Media m) {
        this.borrowedMedia.add(m);
    }
    public void removeBorrowedMedia(Media m) {
        borrowedMedia.remove(m);
    }
    public FineCalculator getFineCalculator() {
        return fineCalculator;
    }
    public int getTotalBorrowedCount() {
        return totalBorrowedCount;
    }
    public void setFineBalance(double fineBalance) { this.fineBalance = fineBalance; }





    /**
     * Adds an arbitrary fine amount to the user's balance (used by accrual).
     */
    public boolean addFineAmount(double amount) {
        if (amount <= 0) return false;
        this.fineBalance += amount;
        if (this.fineBalance > 0) this.canBorrow = false;
        return true;
    }

    public void incrementTotalBorrowedCount() {
        totalBorrowedCount++;
    }


    /**
     * Pays part or all of the fine.
     * Borrowing is allowed only when balance is 0.
     *
     * @param amount amount to pay
     * @return true if payment is valid, false otherwise
     */
    public boolean payFine(double amount) {
        if (amount <= 0 || amount > fineBalance) return false;
        fineBalance -= amount;
        if (fineBalance == 0) canBorrow = true;
        return true;
    }

}