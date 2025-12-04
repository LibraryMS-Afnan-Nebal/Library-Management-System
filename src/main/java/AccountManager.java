import java.util.HashMap;

/**
 * Abstract class that provides common account management functionality
 * for different types of roles (e.g., Admin, Librarian, User).
 * <p>
 * This class manages accounts, usernames, passwords, login/logout status,
 * and account creation. Subclasses must implement {@link #buildAccount(int, String, String, String)}
 * to define how accounts of type T are created.
 *
 * @param <T> the type of Role this manager handles
 */

public abstract class AccountManager<T extends Role> {
    /** Stores accounts mapped by their unique ID */
    protected HashMap<Integer, T> accounts = new HashMap<>();
    /** Maps lowercase usernames to their account IDs */
    protected HashMap<String, Integer> usernameToId = new HashMap<>();
    /** The next available ID for new accounts */
    protected int nextId = 1;
    /**
     * Returns the next unique account ID and increments the counter.
     *
     * @return the next available ID
     */
    protected int getNextId() { return nextId++; }
    /**
     * Logs in a user by username and password.
     *
     * @param username the username of the account
     * @param password the password of the account
     * @return true if login succeeds, false otherwise
     */
    public boolean login(String username, String password)
    {
        if (username.isEmpty() || password.isEmpty())
            throw new IllegalArgumentException("All fields are required");

        Integer id = usernameToId.get(username.toLowerCase());

        if (id == null) return false;

        T account = accounts.get(id);
        if (!checkPassword(account, password)) return false;

        setLoggedIn(account, true);
        return true;
    }
    /**
     * Logs out the user with the given username.
     *
     * @param username the username of the account
     * @return true if logout succeeds, false otherwise
     */
    public boolean logout(String username)
    {
        Integer id = usernameToId.get(username.toLowerCase());
        if (id == null) return false;
        setLoggedIn(accounts.get(id), false);
        return true;
    }
    /**
     * Changes the username of a logged-in account.
     *
     * @param oldUsername the current username
     * @param newUsername the new username to set
     * @return true if the change succeeds, false otherwise
     */
    public boolean changeUsername(String oldUsername, String newUsername)
    {
        Integer id = usernameToId.get(oldUsername.toLowerCase());
        if (id == null || usernameToId.containsKey(newUsername.toLowerCase())) return false;
        T account = accounts.get(id);
        if (!isLoggedIn(account)) return false;
        usernameToId.remove(oldUsername.toLowerCase());
        usernameToId.put(newUsername.toLowerCase(), id);
        setUsername(account, newUsername);
        return true;
    }
    /**
     * Logs out the user with the given username.
     *
     * @param username the username of the account
     * @return true if logout succeeds, false otherwise
     */
    public boolean changePassword(String username, String newPassword)
    {
        Integer id = usernameToId.get(username.toLowerCase());
        if (id == null) return false;
        T account = accounts.get(id);
        if (!isLoggedIn(account)) return false;
        setPassword(account, newPassword);
        return true;
    }
    /**
     * Registers a new account with the given username, password, and email.
     *
     * @param username the username of the new account
     * @param password the password of the new account
     * @param email the email of the new account
     * @return true if the account is successfully created, false if the username already exists
     */
    public boolean signUp(String username, String password, String email) {
        if (username.isEmpty() || password.isEmpty() || email.isEmpty())
            throw new IllegalArgumentException("All fields are required");

        String uname = username.toLowerCase();
        if (usernameToId.containsKey(uname))
            throw new IllegalArgumentException("Username should be unique");

        int accountId = getNextId();
        T account = buildAccount(accountId, username, password, email);

        accounts.put(accountId, account);
        usernameToId.put(uname, accountId);
        return true;
    }
    /**
     * Checks if the given password matches the account's password.
     *
     * @param account the account to check
     * @param password the password to verify
     * @return true if the password matches, false otherwise
     */
    protected boolean checkPassword(T account, String password) {
        return account.getPassword().equals(password);
    }
    /**
     * Sets the login status of the given account.
     *
     * @param account the account to update
     * @param status true to log in, false to log out
     */
    protected void setLoggedIn(T account, boolean status) {
        account.setLoggedIn(status);
    }
    /**
     * Returns whether the account is currently logged in.
     *
     * @param account the account to check
     * @return true if logged in, false otherwise
     */
    protected boolean isLoggedIn(T account) {
        return account.isLoggedIn();
    }
    /**
     * Updates the username of the account.
     *
     * @param account the account to update
     * @param newUsername the new username
     */
    protected void setUsername(T account, String newUsername) {
        account.setUsername(newUsername);
    }
    /**
     * Updates the password of the account.
     *
     * @param account the account to update
     * @param newPassword the new password
     */
    protected void setPassword(T account, String newPassword) {
        account.setPassword(newPassword);
    }
    /**
     * Abstract method to build a new account of type T.
     * Subclasses must implement this to define how accounts are created.
     *
     * @param id the unique ID for the account
     * @param username the username of the account
     * @param password the password of the account
     * @param email the email of the account
     * @return a new account instance of type T
     */
    protected abstract T buildAccount(int id, String username, String password, String email);


}

