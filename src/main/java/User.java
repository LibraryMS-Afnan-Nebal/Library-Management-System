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
public class User {
    private int userId;
    private String username;
    private String email;
    private String password ;
    private double fineBalance;
    private boolean canBorrow;
    //new
    private List<Media> borrowedMedia;
    private boolean isLoggedIn;
    private FineCalculator fineCalculator;
    private int totalBorrowedCount = 0;

    public User() {
    }

    /**
     * Creates a new user with no fines and borrowing allowed.
     *
     * @param userId   the user's unique ID
     * @param username the user's name
     * @param email the user's email
     * @param password the user's password
     */
    public User(int userId,String username,String email,String password){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fineBalance = 0;
        this.canBorrow = true;
        this.borrowedMedia = new ArrayList<>();
        this.isLoggedIn = false;
        this.fineCalculator = new FineCalculator(new RegularFineStrategy());

    }


    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setCanBorrow(boolean canBorrow) {
        this.canBorrow = canBorrow;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public double getFineBalance() {
        return fineBalance;
    }

    public boolean canBorrow() {
        return canBorrow;
    }

    //new
    public List<Media> getBorrowedMedia() {
        return borrowedMedia;
    }
    public void addBorrowedMedia(Media m) {
        this.borrowedMedia.add(m);
    }
    public void removeBorrowedMedia(Media m) {
        borrowedMedia.remove(m);
    }

    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public FineCalculator getFineCalculator() {
        return fineCalculator;
    }

    public int getTotalBorrowedCount() {
        return totalBorrowedCount;
    }

    /**
     * Requests to change this user's username through the authentication system.
     * Ensures that the new username is unique and updates all related records
     * in the manager’s mappings.
     *
     * @param newUsername the new username to assign to this user
     * @return true if the username was successfully changed; false otherwise
     */
    public boolean changeUsername(String newUsername) {
        return Authentication.changeUsername(this.username, newUsername, UserManager.getInstance().getUsers(), UserManager.getInstance().usernameToId());
    }

    /**
     * Requests to change this user's password through the authentication system.
     * The authentication layer validates the input and applies the change
     * directly to this admin's record.
     *
     * @param newPassword the new password to assign to this admin
     * @return true if the password was successfully changed; false otherwise
     */
    public boolean changePassword(String newPassword) {
        return Authentication.changePassword(this.username, newPassword, UserManager.getInstance().getUsers(), UserManager.getInstance().usernameToId());

    }

    /**
     * Logs the user into the system using the provided username and password.
     *
     * @param username the user's username for authentication
     * @param password the user's password for authentication
     * @return true if login is successful, false otherwise
     */
    public boolean login(String username, String password) {
        return Authentication.login(username, password, UserManager.getInstance().getUsers(), UserManager.getInstance().usernameToId());
    }

    /**
     * Logs the user out of the system.
     */
    public boolean logout() {
        return Authentication.logout(this.username, UserManager.getInstance().getUsers(), UserManager.getInstance().usernameToId());
    }

    /**
     * Registers a new user in the system through the UserManager.
     * Delegates the account creation to the Authentication class.
     *
     * @param username the desired username for the new user
     * @param password the desired password for the new user
     * @return true if the account was successfully created; false if the username
     * is already taken or the input is invalid
     */
    public boolean signUp(String username, String password) {
        return Authentication.addAccount(username, password, UserManager.getInstance());
    }

//new

    /**
     * Adds an arbitrary fine amount to the user's balance (used by accrual).
     */
    public boolean addFineAmount(double amount) {
        if (amount <= 0) return false;
        this.fineBalance += amount;
        if (this.fineBalance > 0) this.canBorrow = false;
        return true;
    }

    /// ////////
    public boolean addFine(Loan loan) {
        // let's say books always use 10 NIS/day
        double fine = fineCalculator.calculateFine(loan, 10);
        if (fine > 0) {
            return addFineAmount(fine);
        }
        return false;
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

    public boolean hasOverdueBooks() {
        for (Loan loan : LoanManager.getInstance().getLoanList()) {
            if (loan.getUser().equals(this)) {
                if (!loan.getReturned() && loan.getDueDate().isBefore(LocalDate.now())) {
                    return true; // overdue
                }
            }
        }
        return false;
    }
}