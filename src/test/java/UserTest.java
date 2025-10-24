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
        user = new User(122, "user1", "pass");
    }

    @AfterEach
    void tearDown() {
  }
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
    void getUserId() {
        assertEquals(122,user.getUserId());
    }
    @Test
    void getUsername() {
        assertEquals("user1",user.getUsername());
    }

//    @Test
//    void setBorrowedBooks() {
//        List<Book> books = new ArrayList<>();
//        Book book1 = new Book("123", "Book One","1234567890");
//        Book book2 = new Book("456", "Book Two","1234567891");
//        books.add(book1);
//        books.add(book2);
//
//        assertTrue(user.setBorrowedBooks(books));
//        assertEquals(2, user.getBorrowedBooks().size());
//        assertTrue(user.getBorrowedBooks().contains(book1));
//        assertTrue(user.getBorrowedBooks().contains(book2));
//    }
//    @Test
//    void setBorrowedBooksNull() {
//        List<Book> books = null;
//        assertFalse(user.setBorrowedBooks(books));
//
//    }
}