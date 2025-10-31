import java.util.HashMap;

/**
 * Stores and manages administrator accounts.
 * Provides access to the map of all registered admins.
 *
 * @author Nebal
 * @version 1.0
 */

public class AdminManager {
    private static AdminManager instance = null;
    private HashMap<Integer, Admin> admins = new HashMap<>();
    private final HashMap<String, Integer> usernameToId = new HashMap<>();
    private int nextAdminId = 1;

    public AdminManager() {}

    /** @return the single instance of AdminManager */
   public static AdminManager getInstance() {
        if (instance == null) instance = new AdminManager();
        return instance;
    }

    /** Generates and returns the next admin ID */
    protected int getNextAdminId() {
        return nextAdminId++;
    }

    /**
     * Finds an admin by username (case-insensitive).
     */
   public Admin getAdminByUsername(String username) {
        if (username == null) return null;
        Integer id = usernameToId.get(username.toLowerCase());
        return id == null ? null : admins.get(id);
    }

    /**
     * Finds an admin by their ID.
     */
    public Admin getAdminById(int id) {
        return admins.get(id);
    }

    /**
     * @return all registered admins (by ID)
     */
   public HashMap<Integer, Admin> getAdmins() {
        return admins;
    }

    public HashMap<String, Integer> usernameToId() {
        return usernameToId;
    }

    /**
     * Adds a new admin manually to the system.
     *
     * @param username the admin's username (must be unique)
     * @param password the admin's password
     * @return true if the admin was added successfully; false if username already exists
     */
    public boolean addAdmin( String username, String password) {
        return Authentication.addAccount(username, password, this);
    }

}
