import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
    private Admin admin;
    private UserManager userManager;
    @BeforeEach
    void setUp() {
        admin = new Admin(1, "Admin1", "pass", "admin1@test.com");
        userManager = UserManager.getInstance();
        userManager.accounts.clear();
        userManager.usernameToId.clear();
        LoanManager.getInstance().getLoanList().clear();
    }
    @Test
    void testConstructorInitializesFieldsCorrectly() {
        assertEquals(1, admin.getId());
        assertEquals("Admin1", admin.getUsername());
        assertEquals("pass", admin.getPassword());
        assertEquals("admin1@test.com", admin.getEmail());
        assertFalse(admin.isLoggedIn());
    }
    @Test
    void testUnregisterUserFailsWhenNotLoggedIn() {
        admin.setLoggedIn(false);
        assertFalse(admin.unregisterUser(10),
                "Should return false if admin is not logged in");
    }
    
    @Test
    void testUnregisterFailsWhenUserManagerReturnsFalse() {
        admin.setLoggedIn(true);
        boolean result = admin.unregisterUser(999);
        assertFalse(result, "Unregister should fail when the user does not exist");
    }

}