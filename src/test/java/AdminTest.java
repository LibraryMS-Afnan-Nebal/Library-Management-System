import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
   private Admin admin;
    private AdminManager manager;

    @BeforeEach
    void setUp() {
        manager = AdminManager.getInstance();
        manager.getAdmins().clear();
        manager.usernameToId().clear();

        admin = new Admin(manager.getNextAdminId(), "username", "password");
        manager.getAdmins().put(admin.getAdminId(), admin);
        manager.usernameToId().put("username", admin.getAdminId());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testLogin() {
        assertTrue( admin.login("username", "password"));
        assertTrue(admin.isLoggedIn());
    }
    @Test
    void testLogout() {
        admin.login("username", "password");
        assertTrue(admin.logout());
        assertFalse(admin.isLoggedIn());
    }
    @Test
    void testChangePassword() {
        admin.login("username", "password");
        admin.changePassword("newPass");
        assertEquals("newPass", admin.getPassword());
    }
    @Test
    void testChangeUsername() {
        admin.login("username", "password");
        admin.changeUsername("newUsername");
        assertEquals("newUsername", admin.getUsername());
    }

    @Test
    void testIsLoggedInInitiallyFalse() {
        assertFalse(admin.isLoggedIn());
    }
    @Test
    void testDefaultConstructor() {
        Admin defaultAdmin = new Admin();

        assertEquals(0, defaultAdmin.getAdminId(), "Default adminId should be 0");
        assertNull(defaultAdmin.getUsername(), "Default username should be null");
        assertNull(defaultAdmin.getPassword(), "Default password should be null");
        assertFalse(defaultAdmin.isLoggedIn(), "Default loggedIn should be false");


        assertFalse(defaultAdmin.login("any", "any"), "Login should fail ");
        assertFalse(defaultAdmin.logout(), "Logout should fail ");
    }

}