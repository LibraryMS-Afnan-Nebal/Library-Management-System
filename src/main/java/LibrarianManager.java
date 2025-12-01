public class LibrarianManager extends AccountManager<Librarian> {
    private static LibrarianManager instance = null;

    private LibrarianManager() {}
    public static LibrarianManager getInstance() {
        if (instance == null) instance = new LibrarianManager();
        return instance;
    }



    @Override
    protected Librarian buildAccount(int id, String username, String password, String email) {
        return new Librarian(id, username, password, email);
    }
}

