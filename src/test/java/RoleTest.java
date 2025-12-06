import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    // Dummy subclass
    static class TestRole extends Role {
        public TestRole(int id, String username, String password, String email) {
            super(id, username, password, email);
        }
    }

    private Role role;

    @BeforeEach
    void setUp() {
        role = new TestRole(1, "Nebal", "1234", "nebal@example.com");
    }
    @Test
    void testConstructorInitializesFieldsCorrectly() {
        assertEquals(1, role.getId());
        assertEquals("Nebal", role.getUsername());
        assertEquals("1234", role.getPassword());
        assertEquals("nebal@example.com", role.getEmail());
        assertFalse(role.isLoggedIn(), "Login status should start as false");
    }
    @Test
    void testSetUsername() {
        role.setUsername("newUser");
        assertEquals("newUser", role.getUsername());
    }

    @Test
    void testSetPassword() {
        role.setPassword("newPass");
        assertEquals("newPass", role.getPassword());
    }

    @Test
    void testValidEmailUpdate() {
        role.setEmail("newmail@test.com");
        assertEquals("newmail@test.com", role.getEmail());
    }

    @Test
    void testInvalidEmailThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> role.setEmail("invalidEmail")
        );
        assertThrows(IllegalArgumentException.class,
                () -> role.setEmail("noatsign.com")
        );
        assertThrows(IllegalArgumentException.class,
                () -> role.setEmail("nodot@com")
        );
        assertThrows(IllegalArgumentException.class,
                () -> role.setEmail(null)
        );
    }

    @Test
    void testLoggedIn() {
        assertFalse(role.isLoggedIn());

    }
    @Test
    void testNotLoggedIn() {
        role.setLoggedIn(true);
        assertTrue(role.isLoggedIn());
    }

    @AfterEach
    void tearDown() {
        //no clean up needed
        //
    }
}