/**
 * Represents an administrator in the Library Management System.
 * Each admin has a username, password, and login status.
 * Provides methods to log in and log out.
 *
 * @author Nebal
 * @version 1.0
 */
public class Admin {
    private String username;
    private String password;
    private boolean isLoggedIn = false;

    /**
     * Constructs an Admin object with the given username and password.
     *
     * @param username the admin's username
     * @param password the admin's password
     */
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Attempts to log in using the provided credentials.
     *
     * @param username the entered username
     * @param password the entered password
     * @return true if credentials match and login is successful, false otherwise
     */
    public boolean login(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            isLoggedIn = true;
            return true;
        }
        return false;
    }

    /**
     * Checks if the admin is currently logged in.
     *
     * @return true if logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Logs out the admin.
     *
     * @return true if the admin was logged in and is now logged out,
     *         false if the admin was already logged out
     */
    public boolean logout() {
        if (isLoggedIn) {
            isLoggedIn = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the username of this admin.
     *
     * @return the admin's username
     */
    public String getUsername() {
        return username;
    }
}
