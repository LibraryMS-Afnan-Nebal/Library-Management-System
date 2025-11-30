import java.util.HashMap;
/**
 * Stores and manages all user accounts.
 * Provides access to the user map for authentication and system use.
 *
 * @author Nebal
 * @version 1.0
 */

public class UserManager implements AccountManager<User>{
    private static UserManager instance = null;
    private HashMap<Integer, User> users = new HashMap<>();
    private final HashMap<String, Integer> usernameToId = new HashMap<>();
    private int nextUserId = 1;

    private UserManager() {}

     public static UserManager getInstance() {
         if (instance == null) instance = new UserManager();
         return instance;
     }
    @Override
    public boolean signUp(String username, String password) {
        throw new UnsupportedOperationException(
                "Use signUp(username, password, email) for User accounts."
        );
    }

    // New method with email
    public boolean signUp(String username, String password, String email) {
        if (username == null || username.isBlank() || password == null || password.isBlank() ||
                email == null || email.isBlank()) return false;

        String uname = username.toLowerCase();
        if (usernameToId.containsKey(uname)) return false;

        int id = nextUserId++;
        User u = new User(id, username, email, password);
        users.put(id, u);
        usernameToId.put(uname, id);
        return true;
    }

    @Override
    public boolean login(String username, String password) {
        Integer id = usernameToId.get(username.toLowerCase());
        if (id == null) return false;
        User u = users.get(id);
        if (!u.getPassword().equals(password)) return false;
        u.setLoggedIn(true);
        return true;
    }

    @Override
    public boolean logout(String username) {
        Integer id = usernameToId.get(username.toLowerCase());
        if (id == null) return false;
        User u = users.get(id);
        u.setLoggedIn(false);
        return true;
    }

    @Override
    public boolean changeUsername(String oldUsername, String newUsername) {
        Integer id = usernameToId.get(oldUsername.toLowerCase());
        if (id == null || usernameToId.containsKey(newUsername.toLowerCase())) return false;

        User u = users.get(id);
        if (!u.isLoggedIn()) return false;

        usernameToId.remove(oldUsername.toLowerCase());
        usernameToId.put(newUsername.toLowerCase(), id);
        u.setUsername(newUsername);
        return true;
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        Integer id = usernameToId.get(username.toLowerCase());
        if (id == null) return false;

        User u = users.get(id);
        if (!u.isLoggedIn()) return false;

        u.setPassword(newPassword);
        return true;
    }

    /**
     * @return all registered users (by ID)
     */
    public HashMap<Integer, User> getUsers() {
        return users;
    }
    public HashMap<String, Integer> usernameToId() {
        return usernameToId;
    }
    /**
     * Finds an admin by their ID.
     */
    public User getUserById(int id) {
        return users.get(id);
    }

    /**
     * Generates and assigns a new unique user ID.
     * Used by Authentication.signUp when creating new users.
     */
    public int getNextUserId() {
        return nextUserId++;
    }

    /**
     * Finds a user by username (case-insensitive).
     */
    public User getUserByUsername(String username) {
        if (username == null) return null;
        Integer id = usernameToId.get(username.toLowerCase());
        return id == null ? null : users.get(id);
    }

    /**
     * Evaluates the user's total borrowing count and promotes their card type.
     * Regular -> Silver -> Gold depending on thresholds.
     */
    public void evaluateAndPromoteUser(User user) {
        int count = user.getTotalBorrowedCount();

        // access the user's FineCalculator
        FineCalculator calculator = user.getFineCalculator();

        if (count >= 15) {
            calculator.setFineStrategy(new GoldFineStrategy());
            System.out.println(user.getUsername() + " is now a GOLD member! ");
        } else if (count >= 5) {
            calculator.setFineStrategy(new SilverFineStrategy());
            System.out.println(user.getUsername() + " is now a SILVER member!");
        } else {
            calculator.setFineStrategy(new RegularFineStrategy());
        }
    }

    /**
     * Checks if a user can be unregistered.
     * A user can only be removed if they have:
     * - No unpaid fines
     * - No active (not returned) loans
     *
     * @param user the user to check
     * @return true if the user can be unregistered, false otherwise
     */
    public boolean canUnregister(User user) {
        if (user == null) return false;
        if (user.getFineBalance() > 0) {
            return false;
        }
        for (Loan loan : LoanManager.getInstance().getLoanList()) {
            if (loan.getUser().equals(user) && !loan.getReturned()) {
                return false;
            }
        }

        return true;
    }


    /**
     * Removes a user from the system if they meet the conditions
     * (no unpaid fines and no active loans).
     *
     * @param userId the ID of the user to remove
     * @return true if the user was successfully removed, false if not
     */
    public boolean unregisterUser(int userId) {
        User user = users.get(userId);
        if (user == null) return false;
        if (!canUnregister(user)) {
            return false;
        }
        users.remove(userId);
        usernameToId.remove(user.getUsername().toLowerCase());

        return true;
    }

/*
     /**
      * Attempts to borrow a book for the specified user while enforcing borrowing restrictions.
      * <p>
      * This method ensures that the user meets all borrowing conditions before delegating
      * the actual borrowing operation to the {@link BookManager}. A user cannot borrow a book if:
      * <ul>
      *     <li>They have unpaid fines (fineBalance > 0).</li>
      *     <li>Their borrowing privileges are revoked (canBorrow == false).</li>
      * </ul>
      * If all conditions are satisfied, the method calls {@link BookManager#borrowBook(String, User)}
      * to complete the borrowing process.
      * </p>
      *
      * @param isbnPart     The last part of the ISBN (without the "978" prefix).
      * @param user         The user attempting to borrow the book.
      * @param bookManager  The {@link BookManager} responsible for managing book operations.
      * @return {@code true} if the borrowing is successful; {@code false} otherwise.

     */
/*
    public boolean borrowBookForUser(String isbnPart, User user, BookManager bookManager) {
        if (user.getFineBalance() > 0) {
           System.out.println("Borrowing blocked: You have unpaid fines.");
         return false;
        }

        if (!user.canBorrow()) {
            System.out.println("Borrowing blocked: You have overdue books or borrowing privileges revoked.");
            return false;
        }

        return bookManager.borrowBook(isbnPart, user);

        /////this will be replaced with a loan obj
    }*/

 }