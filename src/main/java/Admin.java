/**
 * Represents an administrator in the Library Management System.
 * <p>
 * Each admin has an ID, username, password, email, and login status.
 * Admins can perform privileged operations such as unregistering users.
 * </p>
 *
 *
 * @author Neb * <p>Note: Admin operations require the admin to be logged in.</p>al
 * @version 1.0
 */
public class Admin extends Role {
    /**
     * Constructs a new Admin with the specified ID, username, password, and email.
     *
     * @param id the unique ID of the admin
     * @param username the username of the admin
     * @param password the password of the admin
     * @param email the email of the admin
     */
    public Admin(int id, String username, String password, String email) {
        super(id, username, password, email);
    }

    /**
     * Unregisters a user from the system.
     * <p>
     * The admin must be logged in to perform this action.
     * This method delegates the operation to {@link UserManager}.
     * </p>
     *
     * @param userId the ID of the user to unregister
     * @return true if the user was successfully removed, false otherwise
     */
    public boolean unregisterUser(int userId) {
        if (!this.isLoggedIn()) return false;

        return UserManager.getInstance().unregisterUser(userId);
    }
}





