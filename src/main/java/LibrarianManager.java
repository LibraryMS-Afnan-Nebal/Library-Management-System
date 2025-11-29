import java.util.HashMap;

public class LibrarianManager implements AccountManager<Librarian> {
    private static LibrarianManager instance = null;
    private HashMap<Integer, Librarian> librarians = new HashMap<>();
    private final HashMap<String, Integer> usernameToId = new HashMap<>();
    private int nextLibrarianId = 1;

    private LibrarianManager() {}

    public static LibrarianManager getInstance() {
        if (instance == null) instance = new LibrarianManager();
        return instance;
    }

    @Override
    public boolean signUp(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank())
            return false;

        String uname = username.toLowerCase();
        if (usernameToId.containsKey(uname)) return false;

        int id = nextLibrarianId++;
        Librarian librarian = new Librarian(id, username, password);
        librarians.put(id, librarian);
        usernameToId.put(username.toLowerCase(), id);
        return true;
    }

    @Override
    public boolean login(String username, String password) {
        Integer id = usernameToId.get(username.toLowerCase());
        if (id == null) return false;

        Librarian librarian = librarians.get(id);
        if (!librarian.getPassword().equals(password)) return false;

        librarian.setLoggedIn(true);
        return true;
    }

    @Override
    public boolean logout(String username) {
        Integer id = usernameToId.get(username.toLowerCase());
        if (id == null) return false;

        Librarian librarian = librarians.get(id);
        librarian.setLoggedIn(false);
        return true;
    }

    @Override
    public boolean changeUsername(String oldUsername, String newUsername) {
        Integer id = usernameToId.get(oldUsername.toLowerCase());
        if (id == null || usernameToId.containsKey(newUsername.toLowerCase())) return false;

        Librarian librarian = librarians.get(id);
        if (!librarian.isLoggedIn()) return false;

        usernameToId.remove(oldUsername.toLowerCase());
        usernameToId.put(newUsername.toLowerCase(), id);
        librarian.setUsername(newUsername);
        return true;
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        Integer id = usernameToId.get(username.toLowerCase());
        if (id == null) return false;

        Librarian librarian = librarians.get(id);
        if (!librarian.isLoggedIn()) return false;

        librarian.setPassword(newPassword);
        return true;
    }

    // Optional getters
    public HashMap<Integer, Librarian> getLibrarians() {
        return librarians;
    }

    public HashMap<String, Integer> usernameToId() {
        return usernameToId;
    }
}
