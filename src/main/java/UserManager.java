/**
 * Singleton class that manages all user accounts in the Library Management System.
 * <p>
 * Extends {@link AccountManager} to provide account creation, login, logout,
 * username/password updates, and user-specific operations such as
 * unregistering users and promoting them based on borrowing history.
 * </p>
 *
 * <p>Ensures that users can only be unregistered if they have no unpaid fines
 * and no active loans.</p>
 *
 * @author Nebal
 * @version 1.0
 * @see AccountManager
 * @see User
 * @see LoanManager
 * @see FineCalculator
 */
public class UserManager extends AccountManager<User> {
    private static UserManager instance = null;

    /** Private constructor to enforce singleton pattern */
    private UserManager() {}

    /**
     * Returns the singleton instance of UserManager.
     *
     * @return the single instance of UserManager
     */
    public static UserManager getInstance() {
        if (instance == null) instance = new UserManager();
        return instance;
    }

    /**
     * Creates a new User account with the specified details.
     *
     * @param id the unique ID of the user
     * @param username the username
     * @param password the password
     * @param email the email address
     * @return the created User object
     */
    @Override
    protected User buildAccount(int id, String username, String password, String email) {
        return new User(id, username, password, email);
    }

    /**
     * Checks if a user can be unregistered.
     * <p>
     * A user can only be removed if they have:
     * <ul>
     *     <li>No unpaid fines</li>
     *     <li>No active (not returned) loans</li>
     * </ul>
     * </p>
     *
     * @param user the user to check
     * @return true if the user can be unregistered, false otherwise
     */
    public boolean canUnregister(User user) {
        if (user == null) return false;
        if (user.getFineBalance() > 0) {
            System.out.println("Cannot unregister user: The user has outstanding fines.");
            return false;
        }
        for (Loan loan : LoanManager.getInstance().getLoanList()) {
            if (loan.getUser().equals(user) && !loan.getReturned()) {
                System.out.println("Cannot unregister user: The user has borrowed items that are not returned.");
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
     * @return true if the user was successfully removed, false otherwise
     */
    public boolean unregisterUser(int userId) {
        User user = accounts.get(userId);
        if (user == null) {
            System.out.println("Cannot unregister: No user found with the provided ID.");
            return false;
        }
        if (!canUnregister(user)) {
            return false;
        }
        accounts.remove(userId);
        usernameToId.remove(user.getUsername().toLowerCase());

        return true;
    }

    /**
     * Evaluates the user's borrowing history and promotes them to a membership
     * tier accordingly:
     * <ul>
     *     <li>Regular: < 5 borrowed items</li>
     *     <li>Silver: ≥ 5 borrowed items</li>
     *     <li>Gold: ≥ 15 borrowed items</li>
     * </ul>
     * <p>
     * Updates the user's {@link FineCalculator} to reflect the appropriate
     * fine strategy for their membership tier.
     * </p>
     *
     * @param user the user to evaluate and potentially promote
     */
   public void evaluateAndPromoteUser(User user) {
       int count = user.getTotalBorrowedCount();

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

    public boolean printAllUsers()
    {
        if (accounts.values().isEmpty())
        {
            System.out.println("No users found");
            return false;
        }
        System.out.println("Current users:");
        System.out.printf("%-10s | %-20s%n", "ID", "Username");
        for (User u : accounts.values())
        {
            System.out.printf("%-10s | %-20s%n", u.getId(), u.getUsername());
        }
        return true;
    }

}







