public interface AccountManager<T> {
    boolean signUp(String username, String password);
    boolean login(String username, String password);
    boolean logout(String username);

    // Shared operations
    boolean changeUsername(String oldUsername, String newUsername);
    boolean changePassword(String username, String newPassword);
}