import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;
    private UserManager manager;
/*
    private User user;
    private UserManager MediaManager;
    private User user2;
*/
    @BeforeEach
    void setUp() {
        manager = UserManager.getInstance();
        manager.getUsers().clear();
        manager.usernameToId().clear();

        user = new User(manager.getNextUserId(), "username","email", "password");
        manager.getUsers().put(user.getUserId(), user);
        manager.usernameToId().put("username", user.getUserId());
/*
        MediaManager = UserManager.getInstance();
        MediaManager.getUsersUsernames().clear();
        user = new User(1, "username", "password");
        MediaManager.addUser(user);
        user2 = new User(2, "username2", "password2");
        MediaManager.addUser(user2);

 */
    }

    @AfterEach
    void tearDown() {
  }

    @Test
    void getUsername() {
        assertEquals("username",user.getUsername());
    }

    @Test
    void setBorrowedBooks() {

        Book book1 = new Book("123", "Book One","1234567890");
        user.setBorrowedBooks(book1);
        assertEquals(1, user.getBorrowedBooks().size());
        assertTrue(user.getBorrowedBooks().contains(book1));
    }

    @Test
    void testLogin() {
        assertTrue( user.login("username", "password"));
        assertTrue(user.isLoggedIn());
    }
    @Test
    void testLogout() {
        user.login("username", "password");
        assertTrue(user.logout());
        assertFalse(user.isLoggedIn());
    }
    @Test
    void testChangePassword() {
        user.login("username", "password");
        user.changePassword("newPass");
        assertEquals("newPass", user.getPassword());
    }
    @Test
    void testChangeUsername() {
        user.login("username", "password");
        user.changeUsername("newUsername");
        assertEquals("newUsername", user.getUsername());
    }

    @Test
    void testIsLoggedInInitiallyFalse() {
        assertFalse(user.isLoggedIn());
    }
    @Test
    void testDefaultConstructor() {
        User defaultUser = new User();

        assertEquals(0, defaultUser.getUserId(), "Default userId should be 0");
        assertNull(defaultUser.getUsername(), "Default username should be null");
        assertNull(defaultUser.getPassword(), "Default password should be null");
        assertFalse(defaultUser.isLoggedIn(), "Default loggedIn should be false");


        assertFalse(defaultUser.login("any", "any"), "Login should fail ");
        assertFalse(defaultUser.logout(), "Logout should fail ");
    }
    @Test
    void testSignUp() {
        assertTrue( user.signUp("usernameNebal", "password"));
        UserManager manager = UserManager.getInstance();
        Integer userId = manager.usernameToId().get("usernamenebal");
        assertNotNull(userId, "Username should be registered in usernameToId map");
        User registeredUser = manager.getUsers().get(userId);
        assertNotNull(registeredUser, "User object should exist in users map");
        assertEquals("usernameNebal", registeredUser.getUsername(), "User object should have the correct username");
        assertEquals("password", registeredUser.getPassword(), "User object should have the correct password");

    }
    /*
    @Test
    void addFinePositive() {
        assertTrue(user.addFine(50));
        assertEquals(50,user.getFineBalance());
        assertFalse(user.canBorrow());
    }
    @Test
    void addFineMultipleTimes() {
        assertTrue(user.addFine(30));
        assertTrue(user.addFine(20));
        assertEquals(50, user.getFineBalance());
        assertFalse(user.canBorrow());
    }

    @Test
    void addFineZero() {
        assertFalse(user.addFine(0));
        assertEquals(0,user.getFineBalance());
        assertTrue(user.canBorrow());
    }
    @Test
    void addFineNegative() {
        assertFalse(user.addFine(-50));
        assertEquals(0,user.getFineBalance());
        assertTrue(user.canBorrow());
    }
    @Test
    void payFinePartial() {
        user.addFine(100);
        assertTrue(user.payFine(50));
        assertEquals(50,user.getFineBalance());
        assertFalse(user.canBorrow());
    }

    @Test
    void payFineFull() {
        user.addFine(100);
        assertTrue(user.payFine(100));
        assertEquals(0,user.getFineBalance());
        assertTrue(user.canBorrow());
    }
    @Test
    void payFineZero() {
        user.addFine(100);
        assertFalse(user.payFine(0));
        assertEquals(100,user.getFineBalance());
        assertFalse(user.canBorrow());
    }
    @Test
    void payFineNegative() {
        user.addFine(100);
        assertFalse(user.payFine(-50));
        assertEquals(100,user.getFineBalance());
        assertFalse(user.canBorrow());
    }
    @Test
    void payFineMoreThanBalance() {
        user.addFine(100);
        assertFalse(user.payFine(200));
        assertEquals(100,user.getFineBalance());
        assertFalse(user.canBorrow());
    }


    @Test
    void setCanBorrow(){

    }*/
}