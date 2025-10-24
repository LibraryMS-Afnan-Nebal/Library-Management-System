import java.util.ArrayList;
import java.util.List;
/**
 * Represents a user in the library system.
 * Each user has an ID, username, password, fine balance, borrowing status, and borrowed books.
 * Users cannot borrow books if they have unpaid fines.
 *
 * @author Nebal
 * @version 1.0
 */
public class User {
    private int userId;
    private String username;
    private String password ;
    private double fineBalance;
    private boolean canBorrow;
    private List<Book> borrowedBooks;

    /**
     * Creates a new user with no fines and borrowing allowed.
     *
     * @param userId   the user's unique ID
     * @param username the user's name
     * @param password the user's password
     */
    public User(int userId,String username,String password){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fineBalance = 0;
        this.canBorrow = true;
        this.borrowedBooks = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public double getFineBalance() {
        return fineBalance;
    }

    public boolean canBorrow() {
        return canBorrow;
    }

    public List<Book> getBorrowedBooks() { return borrowedBooks; }

    public void setBorrowedBooks(Book book) {
        this.borrowedBooks.add(book);
    }
    /**
     * Adds a fine to the user.
     *
     * The fine amount must be positive. Adding a fine will also disable
     * the user's ability to borrow books.
     *
     * @param amount the fine amount to add
     * @return true if the fine was added successfully, false if the amount
     *         was zero or negative
     */
    public boolean addFine(double amount) {
        if (amount <= 0) return false;
        this.fineBalance += amount;
        canBorrow = false;
        return true;
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
