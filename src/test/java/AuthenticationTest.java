import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {

    private UserManager userManager;
    private AdminManager adminManager;
    private User user;
    private Admin admin;

    @BeforeEach
    void setUp() {
        userManager = UserManager.getInstance();
        adminManager = AdminManager.getInstance();

        userManager.getUsers().clear();
        userManager.usernameToId().clear();

        adminManager.getAdmins().clear();
        adminManager.usernameToId().clear();

        user = new User(userManager.getNextUserId(), "user1", "pass1");
        userManager.getUsers().put(user.getUserId(), user);
        userManager.usernameToId().put("user1", user.getUserId());

        admin = new Admin(adminManager.getNextAdminId(), "admin1", "pass1");
        adminManager.getAdmins().put(admin.getAdminId(), admin);
        adminManager.usernameToId().put("admin1", admin.getAdminId());
    }

    @AfterEach
    void tearDown() {

    }

    // Add account Tests
    @Test
    void addAccountSuccess_ForUserAndAdmin(){
       assertTrue( Authentication.addAccount("user2","pw",userManager));
       assertNotNull(userManager.usernameToId().get("user2"));

        assertTrue( Authentication.addAccount("admin2","pw",adminManager));
        assertNotNull(adminManager.usernameToId().get("admin2"));
    }
    @Test
    void addAccountFailure_InvalidInput_ForUserAndAdmin(){
        assertFalse( Authentication.addAccount("user",null,userManager));
        assertFalse( Authentication.addAccount(null,"pw",userManager));

        assertFalse( Authentication.addAccount("admin","",adminManager));
        assertFalse( Authentication.addAccount("","pw",adminManager));
    }
    @Test
    void addAccountFailure_Duplicate_ForUserAndAdmin() {
        assertFalse(Authentication.addAccount("user1", "anyPass", userManager));

        assertFalse(Authentication.addAccount("admin1", "anyPass", adminManager));

    }
    @Test
    void addAccountFailure_InvalidManager() {
        assertFalse(Authentication.addAccount("u", "p", new Object()));
    }

//log In tests
    @Test
    void logInSuccess_ForUserAndAdmin(){
     assertTrue(Authentication.login("user1","pass1",userManager.getUsers(),userManager.usernameToId()));
     assertTrue(user.isLoggedIn());

     assertTrue(Authentication.login("admin1","pass1",adminManager.getAdmins(),adminManager.usernameToId()));
     assertTrue(admin.isLoggedIn());
}

    @Test
    void logInFailure_WrongPassword_ForUserAndAdmin(){
        assertFalse(Authentication.login("user1","wrongPass",userManager.getUsers(),userManager.usernameToId()));
        assertFalse(user.isLoggedIn());

        assertFalse(Authentication.login("admin1","wrongPass",adminManager.getAdmins(),adminManager.usernameToId()));
        assertFalse(admin.isLoggedIn());
    }

    @Test
    void logInFailure_UsernameNotFound_ForUserAndAdmin(){
        assertFalse(Authentication.login("unknownUser","pass1",userManager.getUsers(),userManager.usernameToId()));
        assertFalse(user.isLoggedIn());

        assertFalse(Authentication.login("unknownAdmin1","pass1",adminManager.getAdmins(),adminManager.usernameToId()));
        assertFalse(admin.isLoggedIn());
    }
    @Test
    void logInFailure_InvalidInput_ForUserAndAdmin(){
        assertFalse( Authentication.login("user1",null,userManager.getUsers(),userManager.usernameToId()));
        assertFalse( Authentication.login(null,"pass1",userManager.getUsers(),userManager.usernameToId()));

        assertFalse( Authentication.login("admin1","",adminManager.getAdmins(),adminManager.usernameToId()));
        assertFalse( Authentication.login("","pass1",adminManager.getAdmins(),adminManager.usernameToId()));
    }
    @Test
    void logInFailure_UnknownAccount() {
        HashMap<Integer, Object> accounts = new HashMap<>();
        HashMap<String, Integer> usernameToId = new HashMap<>();
         //account is neither an admin nor user
        accounts.put(1, new Object());
        usernameToId.put("random", 1);

        assertFalse(Authentication.login("random", "pw", accounts, usernameToId));
    }


    //logout tests
    @Test
    void logoutSuccess_ForAdminAndUser(){
        Authentication.login("user1","pass1",userManager.getUsers(),userManager.usernameToId());
        assertTrue(user.isLoggedIn());
        assertTrue(Authentication.logout("user1",userManager.getUsers(),userManager.usernameToId()));
        assertFalse(user.isLoggedIn());

        Authentication.login("admin1","pass1",adminManager.getAdmins(),adminManager.usernameToId());
        assertTrue(admin.isLoggedIn());
        assertTrue(Authentication.logout("admin1",adminManager.getAdmins(),adminManager.usernameToId()));
        assertFalse(admin.isLoggedIn());
    }
    @Test
    void logoutFailure_InvalidUsername_ForAdminAndUser(){
        Authentication.login("user1","pass1",userManager.getUsers(),userManager.usernameToId());
        assertTrue(user.isLoggedIn());
        assertFalse(Authentication.logout("",userManager.getUsers(),userManager.usernameToId()));
        assertTrue(user.isLoggedIn());

        Authentication.login("admin1","pass1",adminManager.getAdmins(),adminManager.usernameToId());
        assertTrue(admin.isLoggedIn());
        assertFalse(Authentication.logout(null,adminManager.getAdmins(),adminManager.usernameToId()));
        assertTrue(admin.isLoggedIn());
    }
    @Test
    void logoutFailure_UsernameNotFound_ForAdminAndUser(){
        Authentication.login("user1","pass1",userManager.getUsers(),userManager.usernameToId());
        assertTrue(user.isLoggedIn());
        assertFalse(Authentication.logout("unknown",userManager.getUsers(),userManager.usernameToId()));
        assertTrue(user.isLoggedIn());

        Authentication.login("admin1","pass1",adminManager.getAdmins(),adminManager.usernameToId());
        assertTrue(admin.isLoggedIn());
        assertFalse(Authentication.logout("unknown",adminManager.getAdmins(),adminManager.usernameToId()));
        assertTrue(admin.isLoggedIn());
    }
    @Test
    void logoutFailure_NotLoggedInAccount_ForAdminAndUser(){
        assertFalse(user.isLoggedIn());
        assertFalse(Authentication.logout("user1",userManager.getUsers(),userManager.usernameToId()));
        assertFalse(user.isLoggedIn());

        assertFalse(admin.isLoggedIn());
        assertFalse(Authentication.logout("admin1",adminManager.getAdmins(),adminManager.usernameToId()));
        assertFalse(admin.isLoggedIn());
    }
    @Test
    void logoutFailure_UnknownAccount() {
        HashMap<Integer, Object> accounts = new HashMap<>();
        HashMap<String, Integer> usernameToId = new HashMap<>();
        //account is neither an admin nor user
        accounts.put(1, new Object());
        usernameToId.put("random", 1);

        assertFalse(Authentication.logout("random", accounts, usernameToId));
    }

    //change username tests
    @Test
    void changeUsernameSuccess_ForAdminAndUser(){
        Authentication.login("user1","pass1",userManager.getUsers(),userManager.usernameToId());
        assertTrue(Authentication.changeUsername("user1","newUsername",userManager.getUsers(),userManager.usernameToId()));
        assertEquals("newUsername", user.getUsername());
        assertTrue(userManager.usernameToId().containsKey("newusername"));
        assertFalse(userManager.usernameToId().containsKey("user1"));

        Authentication.login("admin1","pass1",adminManager.getAdmins(),adminManager.usernameToId());
        assertTrue(Authentication.changeUsername("admin1","newUsername",adminManager.getAdmins(),adminManager.usernameToId()));
        assertEquals("newUsername", admin.getUsername());
        assertTrue(adminManager.usernameToId().containsKey("newusername"));
        assertFalse(adminManager.usernameToId().containsKey("admin1"));
    }
    @Test
    void changeUsernameFailure_NotLoggedInAccount_ForAdminAndUser(){
        assertFalse(Authentication.changeUsername("user1","newUsername",userManager.getUsers(),userManager.usernameToId()));
        assertTrue(userManager.usernameToId().containsKey("user1"));

        assertFalse(Authentication.changeUsername("admin1","newUsername",adminManager.getAdmins(),adminManager.usernameToId()));
        assertTrue(adminManager.usernameToId().containsKey("admin1"));

    }
    @Test
    void changeUsernameFailure_UsernameTaken_ForAdminAndUser(){
        userManager.getUsers().put(2, new User(2, "user2", "pass2"));
        userManager.usernameToId().put("user2", 2);
        assertFalse(Authentication.changeUsername("user1","user2",userManager.getUsers(),userManager.usernameToId()));
        assertTrue(userManager.usernameToId().containsKey("user1"));

        adminManager.getAdmins().put(2, new Admin(2, "admin2", "pass2"));
        adminManager.usernameToId().put("admin2", 2);
        assertFalse(Authentication.changeUsername("admin1","admin",adminManager.getAdmins(),adminManager.usernameToId()));
        assertTrue(adminManager.usernameToId().containsKey("admin1"));

    }
    @Test
    void changeUsername_InvalidInput_ForAdminAndUser(){
        Authentication.login("user1","pass1",userManager.getUsers(),userManager.usernameToId());
        assertFalse(Authentication.changeUsername("","user3",userManager.getUsers(),userManager.usernameToId()));
        assertFalse(Authentication.changeUsername("user1","",userManager.getUsers(),userManager.usernameToId()));

        Authentication.login("admin1","pass1",adminManager.getAdmins(),adminManager.usernameToId());
        assertFalse(Authentication.changeUsername(null,"admin3",adminManager.getAdmins(),adminManager.usernameToId()));
        assertFalse(Authentication.changeUsername("admin1",null,adminManager.getAdmins(),adminManager.usernameToId()));

    }
    @Test
    void changeUsernameFailure_UsernameNotFound_ForAdminAndUser(){
        assertFalse(Authentication.changeUsername("user3","user4",userManager.getUsers(),userManager.usernameToId()));

        assertFalse(Authentication.changeUsername("admin3","admin4",adminManager.getAdmins(),adminManager.usernameToId()));

    }
    @Test
    void testChangeUsername_UnknownAccount_ForUserAndAdmin() {
        HashMap<Integer, Object> accounts = new HashMap<>();
        HashMap<String, Integer> usernameToId = new HashMap<>();
        accounts.put(1, new Object());
        usernameToId.put("weird", 1);
        assertFalse(Authentication.changeUsername("weird", "newName", accounts, usernameToId));
    }
    //change password test
    @Test
    void changePasswordSuccess_ForAdminAndUser(){
        Authentication.login("user1","pass1",userManager.getUsers(),userManager.usernameToId());
        assertTrue(Authentication.changePassword("user1","newPW",userManager.getUsers(),userManager.usernameToId()));
        assertEquals("newPW", user.getPassword());


        Authentication.login("admin1","pass1",adminManager.getAdmins(),adminManager.usernameToId());
        assertTrue(Authentication.changePassword("admin1","newPW",adminManager.getAdmins(),adminManager.usernameToId()));
        assertEquals("newPW", admin.getPassword());
    }
    @Test
    void changePasswordFailure_NotLoggedInAccount_ForAdminAndUser(){
        assertFalse(Authentication.changePassword("user1","newPW",userManager.getUsers(),userManager.usernameToId()));

        assertFalse(Authentication.changePassword("admin1","newPW",adminManager.getAdmins(),adminManager.usernameToId()));
    }
    @Test
    void changePassword_InvalidInput_ForAdminAndUser(){
        Authentication.login("user1","pass1",userManager.getUsers(),userManager.usernameToId());
        assertFalse(Authentication.changePassword("","newPW",userManager.getUsers(),userManager.usernameToId()));
        assertFalse(Authentication.changePassword("user1","",userManager.getUsers(),userManager.usernameToId()));

        Authentication.login("admin1","pass1",adminManager.getAdmins(),adminManager.usernameToId());
        assertFalse(Authentication.changePassword(null,"newPw",adminManager.getAdmins(),adminManager.usernameToId()));
        assertFalse(Authentication.changePassword("admin1",null,adminManager.getAdmins(),adminManager.usernameToId()));

    }
    @Test
    void changePasswordFailure_UsernameNotFound_ForAdminAndUser(){
        assertFalse(Authentication.changePassword("user3","newPW",userManager.getUsers(),userManager.usernameToId()));

        assertFalse(Authentication.changePassword("admin3","newPW",adminManager.getAdmins(),adminManager.usernameToId()));

    }
    @Test
    void testChangePassword_UnknownAccount_ForUserAndAdmin() {
        HashMap<Integer, Object> accounts = new HashMap<>();
        HashMap<String, Integer> usernameToId = new HashMap<>();
        accounts.put(1, new Object());
        usernameToId.put("weird", 1);
        assertFalse(Authentication.changePassword("weird", "newPW", accounts, usernameToId));
    }

    @Test
    void testGetInstance_NotNull() {
        Authentication auth = Authentication.getInstance();
        assertNotNull(auth, "getInstance() should never return null");
    }

    @Test
    void testGetInstance_SameInstance() {
        Authentication firstCall = Authentication.getInstance();
        Authentication secondCall = Authentication.getInstance();
        assertSame(firstCall, secondCall, "getInstance() should return the same instance every time");
    }


}