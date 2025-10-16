import java.util.ArrayList;

/**
 * Manages multiple administrators in the Library Management System.
 * Handles admin addition, login, logout, and listing.
 * Acts as the main service layer for admin-related operations.
 *
 * @author Nebal
 * @version 1.0
 */
public class AdminManager {
    private ArrayList<Admin> admins = new ArrayList<>();


    /**
     * Adds a new administrator to the system.
     *
     * @param admin the Admin object to be added
     */
    public void addAdmin(Admin admin) {
        admins.add(admin);
    }

    /**
     * Attempts to log in an admin using the provided credentials.
     *
     * @param username the entered username
     * @param password the entered password
     * @return true if an admin with matching credentials logs in successfully, false otherwise
     */
    public boolean login(String username, String password) {
        for (Admin admin : admins) {
            if (admin.login(username, password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Logs out the specified admin by username if they are currently logged in.
     *
     * @param username the username of the admin to log out
     */
    public boolean logout(String username) {
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.isLoggedIn()) {
                admin.logout();
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of all registered administrator usernames.
     *
     * @return ArrayList of usernames
     */
    public ArrayList<String> getAdminUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        for (Admin admin : admins) {
            usernames.add(admin.getUsername());
        }
        return usernames;
    }
}
