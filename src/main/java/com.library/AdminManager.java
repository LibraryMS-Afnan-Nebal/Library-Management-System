package com.library;

/**
 * Singleton class responsible for managing Admin accounts in the Library Management System.
 * <p>
 * Extends {@link AccountManager} to provide account creation, login, logout,
 * and username/password management specifically for Admin users.
 * </p>
 * <p>
 * Uses the singleton pattern to ensure only one instance of AdminManager exists.
 * </p>
 *
 * @see AccountManager
 */
public class AdminManager extends AccountManager<Admin> {
    private static AdminManager instance = null;
    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private AdminManager() {}

    /**
     * Returns the singleton instance of AdminManager.
     * If the instance does not exist yet, it will be created.
     *
     * @return the single AdminManager instance
     */
    public static AdminManager getInstance() {
        if (instance == null) instance = new AdminManager();
        return instance;
    }

    /**
     * Builds a new Admin account with the specified details.
     * <p>
     * This method is called by the generic {@link AccountManager#signUp(String, String, String)}
     * method to create a specific Admin instance.
     * </p>
     *
     * @param id the unique ID for the admin account
     * @param username the username of the admin
     * @param password the password of the admin
     * @param email the email of the admin
     * @return a new Admin instance
     */
    @Override
    protected Admin buildAccount(int id, String username, String password, String email) {
        return new Admin(id, username, password, email);
    }
}


