import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AdminManagerTest {
    private AdminManager manager;
    private Admin admin1;

    @BeforeEach
    void setUp() {
        manager = AdminManager.getInstance();
        manager.getAdmins().clear();
        manager.usernameToId().clear();

    }


    @Test
    void testSingleton() {
        AdminManager anotherInstance = AdminManager.getInstance();
        assertSame(manager, anotherInstance, "AdminManager should be a singleton");
    }


    @Test
    void GetAdminByUsername_NullUsername() {
        assertNull(manager.getAdminByUsername(null), "Should return null");

    }
    @Test
    void GetAdminByUsername_NonExistentUsername() {
        assertNull(manager.getAdminByUsername("nonExistent"), "Should return null");


    }
    @Test
    void GetAdminByUsername_success() {
        manager.addAdmin("admin1", "pass1");
        admin1 = manager.getAdminByUsername("admin1");
        assertEquals("admin1", admin1.getUsername());
       }

    @Test
    void testGetAdminById_NoAdmins(){
        assertNull(manager.getAdminById(1), "Should return null if ID does not exist");

    }
    @Test
    void testGetAdminById_Success() {
        manager.addAdmin("admin1", "pass1");
        Admin admin1 = manager.getAdminByUsername("admin1");
        assertSame(admin1, manager.getAdminById(admin1.getAdminId()), "Should retrieve correct admin by ID");
         }
    @Test
    void testGetAdminById_InvalidId() {
        assertNull(manager.getAdminById(999), "Should return null for non-existent ID");

    }

}