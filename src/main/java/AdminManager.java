public class AdminManager extends AccountManager<Admin> {
    private static AdminManager instance = null;

    private AdminManager() {}
    public static AdminManager getInstance() {
        if (instance == null) instance = new AdminManager();
        return instance;
    }


    @Override
    protected Admin buildAccount(int id, String username, String password, String email) {
        return new Admin(id, username, password, email);
    }
}


