public abstract class Role {
    protected int id;
    protected String username;
    protected String password;
    protected String email;
    protected boolean isLoggedIn;

    public Role(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        setEmail(email);
        this.isLoggedIn = false;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public boolean isLoggedIn() { return isLoggedIn; }
    public void setLoggedIn(boolean loggedIn) { this.isLoggedIn = loggedIn; }
    public Integer getId(){return id;}
}