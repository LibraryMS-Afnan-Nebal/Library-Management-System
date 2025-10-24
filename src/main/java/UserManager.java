import java.util.ArrayList;
/**
 * Manages multiple users in the Library Management System.
 * Handles user addition, login, logout, and listing.
 * Acts as the main service layer for user-related operations.
 *
 * @author Nebal
 * @version 1.0
 */
public class UserManager {
    private ArrayList<User> users = new ArrayList<>();


    /**
     * Adds a new user to the system.
     *
     * @param user the User object to be added
     */
    public void addUser(User user) {
        users.add(user);
    }



    /**
     * Returns a list of all registered users usernames.
     *
     * @return ArrayList of usernames
     */
    public ArrayList<String> getUsersUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        for (User user : users) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }
}
