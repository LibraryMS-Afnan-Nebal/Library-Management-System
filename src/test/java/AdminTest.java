import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void loginSuccess() {
        //1. create variables + expected result
        String username = "admin1";
        String password ="123";
        boolean er= true;
        //2. create obj from the class
        Admin admin = new Admin (username ,password);
        //3. call the methode
        boolean ar= admin.login(username,password);
        //4. compare expected to actual result
        assertTrue(ar==er);
    }
    @Test
    void loginFailureWrongUsername() {

        String username = "admin1";
        String password ="123";
        String wrongName ="Wrong";
        Admin admin = new Admin (username ,password);
        assertFalse(admin.login(wrongName,password));
    }
   @Test
    void loginFailureWrongPassword() {

        String username = "admin1";
        String password ="123";
        String wrongPassword ="Wrong";
        Admin admin = new Admin (username ,password);
        assertFalse( admin.login(username,wrongPassword));
    }
    @Test
    void logoutSuccess() {
        Admin admin = new Admin ("userN","pass");
        admin.login("userN","pass");
        assertTrue(admin.logout() );

    }
    @Test
    //the admin is already logged out
    void logoutFailure() {
        Admin admin = new Admin ("userN","pass");
        admin.login("userN","pass");
        admin.logout() ;
        assertFalse(admin.logout());

    }

    @Test
    void isLoggedInSuccess() {
        Admin admin=new Admin ("name", "pass");
        admin .login("name", "pass");
        assertTrue(admin.isLoggedIn());
    }
    @Test
    void isLoggedInFailure() {
        Admin admin=new Admin ("name", "pass");
        assertFalse(admin.isLoggedIn());
    }

    @Test
    void getUsername() {
        Admin admin = new Admin("userN", "pass");
        assertEquals("userN", admin.getUsername());
    }
}