import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {
    private UserManager manager;
    private User user1;

    @BeforeEach
    void setUp() {
        manager = UserManager.getInstance();
        manager.getUsers().clear();
        manager.usernameToId().clear();

    }


    @Test
    void testSingleton() {
        UserManager anotherInstance = UserManager.getInstance();
        assertSame(manager, anotherInstance, "userManager should be a singleton");
    }


    @Test
    void GetUserByUsername_NullUsername() {
        assertNull(manager.getUserByUsername(null), "Should return null");

    }
    @Test
    void GetUserByUsername_NonExistentUsername() {
        assertNull(manager.getUserByUsername("nonExistent"), "Should return null");


    }
    @Test
    void GetUserByUsername_success() {
        Authentication.addAccount("user1", "pass1",manager);
        User user1 = manager.getUserByUsername("user1");
        assertEquals("user1", user1.getUsername());
    }

    @Test
    void testGetUserById_NoAdmins(){
        assertNull(manager.getUserById(1), "Should return null if ID does not exist");

    }
    @Test
    void testGetUserById_Success() {
        Authentication.addAccount("user1", "pass1",manager);
        User user1 = manager.getUserByUsername("user1");
        assertSame(user1, manager.getUserById(user1.getUserId()), "Should retrieve correct admin by ID");
    }
    @Test
    void testGetUserById_InvalidId() {
        assertNull(manager.getUserById(999), "Should return null for non-existent ID");

    }



/*
    @Test
    void borrowBookForUser_FineBalancePositive(){
       user.addFine(100) ;
       assertFalse(UM.borrowBookForUser("1234567890",user ,BM));
    }
    @Test
    void borrowBookForUser_FineBalanceZero(){
        assertFalse(UM.borrowBookForUser("1234567890",user ,BM));
    }
    @Test
    void borrowBookForUser_canBorrowTrue(){
        Book book1=new Book("TT","TT","1234567890");
        BM.addBook(book1);
        assertTrue(UM.borrowBookForUser("1234567890",user ,BM));
    }
    @Test
    void borrowBookForUser_canBorrowFalse(){
        user.setCanBorrow(false);
        assertFalse(UM.borrowBookForUser("1234567890",user ,BM));
    }
    */
}