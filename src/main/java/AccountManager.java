import java.util.HashMap;

public abstract class AccountManager<T extends Role> {
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
    public boolean signUp(String username, String password, String email) {
        String uname = username.toLowerCase();
        if (usernameToId.containsKey(uname)) return false;

        int accountId = getNextId();
        T account = buildAccount(accountId, username, password, email);

        accounts.put(accountId, account);
        usernameToId.put(uname, accountId);

        return true;
    }
    protected boolean checkPassword(T account, String password) {
        return account.getPassword().equals(password);
    }

    protected void setLoggedIn(T account, boolean status) {
        account.setLoggedIn(status);
    }

    protected boolean isLoggedIn(T account) {
        return account.isLoggedIn();
    }

    protected void setUsername(T account, String newUsername) {
        account.setUsername(newUsername);
    }

    protected void setPassword(T account, String newPassword) {
        account.setPassword(newPassword);
    }
    // Abstract methods للاختلافات بين الـ subclasses
    protected abstract T buildAccount(int id, String username, String password, String email);


}

