package com.library;

/**
 * Singleton class that manages all librarian accounts in the Library Management System.
 * <p>
 * Extends {@link AccountManager} to provide account creation, login, logout,
 * and username/password updates for librarian accounts.
 * </p>
 * <p>
 * This class ensures that only a single instance of LibrarianManager exists
 * throughout the system (singleton pattern).
 * </p>
 *
 * @author Nebal
 * @version 1.0
 * @see AccountManager
 * @see Librarian
 */
public class LibrarianManager extends AccountManager<Librarian> {
    private static LibrarianManager instance = null;

    /** Private constructor to enforce singleton pattern */
    private LibrarianManager() {}

    /**
     * Returns the singleton instance of LibrarianManager.
     *
     * @return the single instance of LibrarianManager
     */
    public static LibrarianManager getInstance() {
        if (instance == null) instance = new LibrarianManager();
        return instance;
    }


    /**
     * Creates a new Librarian account with the specified details.
     *
     * @param id the unique ID of the librarian
     * @param username the username
     * @param password the password
     * @param email the email address
     * @return the created Librarian object
     */
    @Override
    protected Librarian buildAccount(int id, String username, String password, String email) {
        return new Librarian(id, username, password, email);
    }
}

