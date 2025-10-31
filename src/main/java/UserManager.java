import java.util.HashMap;
/**
 * Stores and manages all user accounts.
 * Provides access to the user map for authentication and system use.
 *
 * @author Nebal
 * @version 1.0
 */

public class UserManager{
    private static UserManager instance = null;
    private HashMap<Integer, User> users = new HashMap<>();
    private final HashMap<String, Integer> usernameToId = new HashMap<>();
    private int nextUserId = 1;

    public UserManager() {}

     public static UserManager getInstance() {
         if (instance == null) instance = new UserManager();
         return instance;
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