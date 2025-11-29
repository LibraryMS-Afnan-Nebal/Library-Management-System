import java.util.HashMap;


/**
 * Provides authentication services for users and admins.
 * Handles sign-up, login, and logout actions using HashMaps from their managers.
 *
 * @author Nebal
 * @version 1.0
 */

public class Authentication {
    private static Authentication instance;

    private Authentication() {}
    public static Authentication getInstance() {
        if (instance == null) instance = new Authentication();
        return instance;
    }

    public <T> boolean signUp(String username, String password, AccountManager<T> manager) {
        return manager.signUp(username, password);
    }
    public boolean signUpUser(String username, String password, String email, UserManager userManager) {
        return userManager.signUp(username, password, email);
    }

    public <T> boolean login(String username, String password, AccountManager<T> manager) {
        return manager.login(username, password);
    }

    public <T> boolean logout(String username, AccountManager<T> manager) {
        return manager.logout(username);
    }

    public <T> boolean changeUsername(String oldUsername, String newUsername, AccountManager<T> manager) {
        return manager.changeUsername(oldUsername, newUsername);
    }

    public <T> boolean changePassword(String username, String newPassword, AccountManager<T> manager) {
        return manager.changePassword(username, newPassword);
    }
}
/*
    public static boolean addAccount(String username, String password, Object manager) {
        if (username == null || username.isBlank() || password == null || password.isBlank())
            return false;

        if (manager instanceof UserManager um) {
            String uname = username.toLowerCase();
            if (um.usernameToId().containsKey(uname)) return false;

            int id = um.getNextUserId();
            User u = new User(id, username,"", password);
            um.getUsers().put(id, u);
            um.usernameToId().put(uname, id);
            return true;
        }

        if (manager instanceof AdminManager am) {
            String uname = username.toLowerCase();
            if (am.usernameToId().containsKey(uname)) return false;

            int id = am.getNextAdminId();
            Admin a = new Admin(id, username, password);
            am.getAdmins().put(id, a);
            am.usernameToId().put(uname, id);
            return true;
        }

        return false;
    }

    /**
     * Attempts to log in an account with the given credentials.
     *
     * @param username the username
     * @param password the password
     * @param accounts the map of registered accounts
     * @return true if login succeeds; false otherwise
     *//*
    public static <T> boolean login(String username, String password, HashMap<Integer, T> accounts, HashMap<String, Integer> usernameToId) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            System.out.println("Username or password cannot be empty.");
        return false;
    }

    Integer id = usernameToId.get(username.toLowerCase());
    if (id == null) {
        System.out.println("No account found with that username.");
        return false;
    }

    T account = accounts.get(id);

    if (account instanceof Admin a) {
        if (!a.getPassword().equals(password)) {
            System.out.println("Incorrect password.");
            return false;
        }
        a.setLoggedIn(true);
        System.out.println("Admin logged in successfully.");
        return true;
    }

    if (account instanceof User u) {
        if (!u.getPassword().equals(password)) {
            System.out.println("Incorrect password.");
            return false;
        }
        u.setLoggedIn(true);
        System.out.println("User logged in successfully.");
        return true;
    }

    return false;
    }


    /**
     * Logs out an account if it exists and is logged in.
     *
     * @param username the account username
     * @param accounts the map of accounts (key = id)
     * @return true if logout succeeds; false otherwise
     */
/*
    public static <T> boolean logout(String username, HashMap<Integer, T> accounts, HashMap<String, Integer> usernameToId) {
        if (username == null || username.isBlank()) {
            System.out.println("Logout Failed");
            return false;
        }

        Integer id = usernameToId.get(username.toLowerCase());
        if (id == null) {
            System.out.println("Account not found.");
            return false;
        }

        T account = accounts.get(id);

        if (account instanceof Admin a) {
            if (!a.isLoggedIn()) {
                System.out.println(" Admin is not currently logged in.");
                return false;
            }
            a.setLoggedIn(false);
            System.out.println("Admin logged out successfully.");
            return true;
        }

        if (account instanceof User u) {
            if (!u.isLoggedIn()) {
                System.out.println(" User is not currently logged in.");
                return false;
            }
            u.setLoggedIn(false);
            System.out.println("User logged out successfully.");
            return true;
        }

        return false;
    }

    public static <T> boolean changeUsername(String oldUsername, String newUsername, HashMap<Integer, T> accounts, HashMap<String, Integer> usernameToId) {
        if (oldUsername == null || oldUsername.isBlank() || newUsername == null || newUsername.isBlank()) {
            System.out.println("Invalid username input.");
            return false;
        }

        String oldKey = oldUsername.toLowerCase();
        String newKey = newUsername.toLowerCase();

        Integer id = usernameToId.get(oldKey);
        if (id == null) {
            System.out.println("Account not found.");
            return false;
        }

        if (usernameToId.containsKey(newKey)) {
            System.out.println("Username already taken.");
            return false;
        }

        T account = accounts.get(id);
        if (account instanceof Admin a) {
            if (!a.isLoggedIn()) {
                System.out.println("Admin must be logged in to change username.");
                return false;
            }
            a.setUsername(newUsername);
        } else if (account instanceof User u) {
            if (!u.isLoggedIn()) {
                System.out.println("User must be logged in to change username.");
                return false;
            }
            u.setUsername(newUsername);
        } else {
            System.out.println("Unknown account type.");
            return false;
        }

        usernameToId.remove(oldKey);
        usernameToId.put(newKey, id);

        System.out.println("Username changed successfully to: " + newUsername);
        return true;
    }

    public static <T> boolean changePassword(String username, String newPassword, HashMap<Integer, T> accounts, HashMap<String, Integer> usernameToId) {
        if (username == null || username.isBlank() || newPassword == null || newPassword.isBlank()) {
            System.out.println("Invalid username or password input.");
            return false;
        }

        Integer id = usernameToId.get(username.toLowerCase());
        if (id == null) {
            System.out.println("Account not found.");
            return false;
        }

        T account = accounts.get(id);
        if (account instanceof Admin a) {
            if (!a.isLoggedIn()) {
                System.out.println("Admin must be logged in to change password.");
                return false;
            }
            a.setPassword(newPassword);
            System.out.println("Admin password changed successfully.");
            return true;
        } else if (account instanceof User u) {
            if (!u.isLoggedIn()) {
                System.out.println("User must be logged in to change password.");
                return false;
            }
            u.setPassword(newPassword);
            System.out.println("User password changed successfully.");
            return true;
        }

        System.out.println("Unknown account type.");
        return false;
    }
    */
