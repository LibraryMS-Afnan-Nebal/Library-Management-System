import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AdminManagerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void loginSuccess() {
        AdminManager AM = new AdminManager();
        Admin admin = new Admin("username", "password");
        AM.addAdmin(admin);
        assertTrue(AM.login("username", "password"));

    }

    @Test
    void loginFailureWrongUsername() {
        AdminManager AM = new AdminManager();
        Admin admin = new Admin("username", "password");
        AM.addAdmin(admin);
        assertFalse(AM.login("wrongusername", "password"));
    }

    @Test
    void loginFailureWrongPassword() {
        AdminManager AM = new AdminManager();
        Admin admin = new Admin("username", "password");
        AM.addAdmin(admin);
        assertFalse(AM.login("username", "wrongpassword"));
    }


    @Test
    //username exists and admin is logged in
    void logoutSuccess() {
        AdminManager AM = new AdminManager();
        Admin admin = new Admin("username", "password");
        AM.addAdmin(admin);
        AM.login("username", "password");
        assertTrue(AM.logout("username"));
    }

    @Test
    //username exists but admin is not logged in
    void notLoggedinUsername() {
        AdminManager AM = new AdminManager();
        Admin admin = new Admin("username", "password");
        AM.addAdmin(admin);
        assertFalse(AM.logout("username"));
    }
  //username does not exist at all
  @Test
  void onExistentUsername() {
      AdminManager AM = new AdminManager();
      Admin admin = new Admin("username", "password");
      AM.addAdmin(admin);
      assertFalse(AM.logout("nonexistent")); // username does not exist
  }

    @Test
    void listAdminsTest() {
        AdminManager AM = new AdminManager();
        Admin admin1 = new Admin("user1", "pass1");
        Admin admin2 = new Admin("user2", "pass2");
        AM.addAdmin(admin1);
        AM.addAdmin(admin2);

        ArrayList<String> usernames = AM.getAdminUsernames();
        assertTrue(usernames.contains("user1"));
        assertTrue(usernames.contains("user2"));
        assertEquals(2, usernames.size());
    }
}