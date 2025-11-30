import java.util.HashMap;

public abstract class AccountManagerNew<T> {
    protected HashMap<Integer, T> accounts = new HashMap<>();
    protected HashMap<String, Integer> usernameToId = new HashMap<>();
    protected int nextId = 1;

    protected int getNextId() { return nextId++; }

    public boolean login(String username, String password)
    {
        Integer id = usernameToId.get(username.toLowerCase());
        if (id == null) return false;
        T account = accounts.get(id);
        if (!checkPassword(account, password)) return false;
        setLoggedIn(account, true);
        return true;
    }

    public boolean logout(String username)
    {
        Integer id = usernameToId.get(username.toLowerCase());
        if (id == null) return false;
        setLoggedIn(accounts.get(id), false);
        return true;
    }

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

    public boolean changePassword(String username, String newPassword)
    {
        Integer id = usernameToId.get(username.toLowerCase());
        if (id == null) return false;
        T account = accounts.get(id);
        if (!isLoggedIn(account)) return false;
        setPassword(account, newPassword);
        return true;
    }

    // Abstract methods للاختلافات بين الـ subclasses
    protected abstract T createAccount(String username, String password);
    protected abstract boolean checkPassword(T account, String password);
    protected abstract void setLoggedIn(T account, boolean status);
    protected abstract boolean isLoggedIn(T account);
    protected abstract void setUsername(T account, String newUsername);
    protected abstract void setPassword(T account, String newPassword);
}

