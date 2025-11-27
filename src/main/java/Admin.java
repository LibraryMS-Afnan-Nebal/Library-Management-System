/**
 * Represents an administrator in the Library Management System.
 * Each admin has an ID, username, password, and login status.
 * Provides methods to log in and log out.
 *
 * @author Nebal
 * @version 1.0
 */
public class Admin {
    private int adminId;
    private String username;
    private String password;
    private boolean isLoggedIn;


    public Admin (){}

    /**
     * Constructs an Admin object with the given username and password.
     *
     * @param adminId   the admin's unique ID
     * @param username the admin's username
     * @param password the admin's password
     */

    public Admin(int adminId ,String username, String password) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.isLoggedIn = false;
    }


    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    public int getAdminId() {
        return adminId;
    }
    protected void setLoggedIn(boolean loggedIn) {this.isLoggedIn = loggedIn;}
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String newUsername) {this.username = newUsername;}
    public void setPassword(String newPassword) {this.password = newPassword;}

    /**
     * Requests to change this admin's username through the authentication system.
     * Ensures that the new username is unique and updates all related records
     * in the manager’s mappings.
     *
     * @param newUsername the new username to assign to this admin
     * @return true if the username was successfully changed; false otherwise
     */
    public boolean changeUsername(String newUsername) {
       return Authentication.changeUsername(this.username, newUsername, AdminManager.getInstance().getAdmins(), AdminManager.getInstance().usernameToId());
    }

    /**
     * Requests to change this admin's password through the authentication system.
     * The authentication layer validates the input and applies the change
     * directly to this admin's record.
     *
     * @param newPassword the new password to assign to this admin
     * @return true if the password was successfully changed; false otherwise
     */
    public boolean changePassword(String newPassword) {
        return Authentication.changePassword(this.username, newPassword, AdminManager.getInstance().getAdmins(), AdminManager.getInstance().usernameToId());
    }

    /**
     * Attempts to log in the admin using username and password.
     *
     * @param username the entered username
     * @param password the entered password
     * @return true if login succeeds; false otherwise
     */
    public boolean login(String username, String password) {
        return Authentication.login(username, password, AdminManager.getInstance().getAdmins(), AdminManager.getInstance().usernameToId());
    }

    /**
     * Logs out the admin.
     * @return true if logout succeeds; false otherwise
     */
    public boolean logout() {
        return Authentication.logout(this.username, AdminManager.getInstance().getAdmins(),AdminManager.getInstance().usernameToId());
    }

    /**
     * Allows an admin to unregister a user.
     * The admin must be logged in to perform this action.
     *
     * @param userId the ID of the user to unregister
     * @return true if the user was removed, false otherwise
     */
    public boolean unregisterUser(int userId) {
        if (!this.isLoggedIn()) return false;

        return UserManager.getInstance().unregisterUser(userId);
    }


}
