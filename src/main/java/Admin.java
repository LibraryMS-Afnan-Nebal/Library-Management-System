import java.util.ArrayList;

/**
 * Represents an administrator in the Library Management System.
 * Each admin has an ID, username, password, and login status.
 * Provides methods to log in and log out.
 *
 * @author Nebal
 * @version 1.0
 */
public class Admin extends Role{
    private int adminId;
    private String username;
    private String password;
    private boolean isLoggedIn;
    private String email;

    public Admin(int id, String username, String password, String email) {
        super(id, username, password, email);
    }

    //make sure to update this
    /*
    /**
     * Allows an admin to unregister a user.
     * The admin must be logged in to perform this action.
     *
     * @param userId the ID of the user to unregister
     * @return true if the user was removed, false otherwise
     *//*
    public boolean unregisterUser(int userId) {
        if (!this.isLoggedIn()) return false;

        return UserManager.getInstance().unregisterUser(userId);
    }*/
}





