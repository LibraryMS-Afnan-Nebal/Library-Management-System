import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "UserA", "pass123", "user@test.com");


    }

    @AfterEach
    void tearDown() {
        //no cleanup needed
        //no
  }


    @Test
    void testConstructorInitializesFieldsCorrectly() {
        assertEquals(1, user.getId());
        assertEquals("UserA", user.getUsername());
        assertEquals("pass123", user.getPassword());
        assertEquals("user@test.com", user.getEmail());

        assertEquals(0, user.getFineBalance());
        assertTrue(user.canBorrow());
        assertNotNull(user.getBorrowedMedia());
        assertTrue(user.getBorrowedMedia().isEmpty());
        assertNotNull(user.getFineCalculator());
        assertEquals(0, user.getTotalBorrowedCount());
    }
    @Test
    void testSetCanBorrow() {
        user.setCanBorrow(false);
        assertFalse(user.canBorrow());

        user.setCanBorrow(true);
        assertTrue(user.canBorrow());
    }
    @Test
    void testAddBorrowedMedia() {
        Media m = new Book("Book A", "Author X", "1234567890");
        user.addBorrowedMedia(m);

        assertEquals(1, user.getBorrowedMedia().size());
        assertTrue(user.getBorrowedMedia().contains(m));
    }

    @Test
    void testRemoveBorrowedMedia() {
        Media m1 = new Book("Book A", "Author X", "1234567890");
        Media m2 = new Book("Book B", "Author Y", "9999999999");

        user.addBorrowedMedia(m1);
        user.addBorrowedMedia(m2);

        user.removeBorrowedMedia(m1);

        assertEquals(1, user.getBorrowedMedia().size());
        assertFalse(user.getBorrowedMedia().contains(m1));
        assertTrue(user.getBorrowedMedia().contains(m2));
    }

    @Test
    void addFineAmount_Valid() {
        assertTrue(user.addFineAmount(50));
        assertEquals(50,user.getFineBalance());
        assertFalse(user.canBorrow());
    }
    @Test
    void addFineAmount_MultipleTimes() {
        assertTrue(user.addFineAmount(30));
        assertTrue(user.addFineAmount(20));
        assertEquals(50, user.getFineBalance());
        assertFalse(user.canBorrow());
    }

    @Test
    void addFineAmount_Invalid() {
        assertFalse(user.addFineAmount(0));
        assertFalse(user.addFineAmount(-5));
        assertEquals(0, user.getFineBalance());
        assertTrue(user.canBorrow());
    }

    @Test
    void payFine_ValidPartialPayment() {
        user.addFineAmount(100);
        assertTrue(user.payFine(50));
        assertEquals(50,user.getFineBalance());
        assertFalse(user.canBorrow());
    }

    @Test
    void payFine_ValidFullPayment() {
        user.addFineAmount(100);
        assertTrue(user.payFine(100));
        assertEquals(0,user.getFineBalance());
        assertTrue(user.canBorrow());
    }
    @Test
    void payFine_InvalidAmounts() {
        user.addFineAmount(10);

        assertFalse(user.payFine(0));
        assertFalse(user.payFine(-5));
        assertFalse(user.payFine(20));

        assertEquals(10, user.getFineBalance(), "Fine should not change for invalid payments.");
    }

    @Test
    void testIncrementTotalBorrowedCount() {
        assertEquals(0, user.getTotalBorrowedCount());

        user.incrementTotalBorrowedCount();
        user.incrementTotalBorrowedCount();

        assertEquals(2, user.getTotalBorrowedCount());
    }

    @Test
    void setFineBalance(){
        assertEquals(0,user.getFineBalance());
        user.setFineBalance(10);
        assertEquals(10,user.getFineBalance());
    }

}