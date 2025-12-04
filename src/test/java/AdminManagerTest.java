import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AdminManagerTest {

    @Test
    void testSingletonInstance() {
        AdminManager m1 = AdminManager.getInstance();
        AdminManager m2 = AdminManager.getInstance();

        assertSame(m1, m2, "AdminManager should return the same singleton instance.");
    }
    @Test
    void testBuildAccountCreatesAdmin() {
        AdminManager manager = AdminManager.getInstance();

        Admin admin = manager.buildAccount(1, "AdminA", "pass123", "adminA@test.com");

        assertNotNull(admin);
        assertEquals(1, admin.getId());
        assertEquals("AdminA", admin.getUsername());
        assertEquals("pass123", admin.getPassword());
        assertEquals("adminA@test.com", admin.getEmail());
    }
}