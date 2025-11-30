public class AdminManagerNew extends AccountManagerNew<Admin> {
    private static AdminManagerNew instance = null;

    private AdminManagerNew() {}
    public static AdminManagerNew getInstance() {
        if (instance == null) instance = new AdminManagerNew();
        return instance;
    }

    @Override
    protected Admin createAccount(String username, String password) {
        return new Admin(getNextId(), username, password);
    }

    @Override
    protected boolean checkPassword(Admin account, String password) {
        return account.getPassword().equals(password);
    }

    @Override
    protected void setLoggedIn(Admin account, boolean status) {
        account.setLoggedIn(status);
    }

    @Override
    protected boolean isLoggedIn(Admin account) {
        return account.isLoggedIn();
    }

    @Override
    protected void setUsername(Admin account, String newUsername) {
        account.setUsername(newUsername);
    }

    @Override
    protected void setPassword(Admin account, String newPassword) {
        account.setPassword(newPassword);
    }

    public boolean signUp(String username, String password) {
        if (usernameToId.containsKey(username.toLowerCase())) return false;
        Admin admin = createAccount(username, password);
        accounts.put(admin.getAdminId(), admin);
        usernameToId.put(username.toLowerCase(), admin.getAdminId());
        return true;
    }
}

