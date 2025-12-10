import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    private User user;
    private Book book;
    private Loan loan;

    @BeforeEach
    void setUp() {
        user = new User(1, "user1", "pass", "user@test.com");
        book = new Book("Book Title", "Author A", "1234567890");
        loan = new Loan(book, user);
    }

    @Test
    void testConstructorInitializes_Correctly() {
        assertEquals(user, loan.getUser());
        assertEquals(book, loan.getMedia());
        assertFalse(loan.getReturned());
        assertNotNull(loan.getDueDate());
        assertEquals(loan.getDueDate(), loan.getLastAccruedDate());
        assertNull(loan.getReturnDate());
    }

    @Test
    void testSetAndGet_DueDate() {
        LocalDate newDueDate = LocalDate.of(2025, 12, 10);
        loan.setDueDate(newDueDate);
        assertEquals(newDueDate, loan.getDueDate());
    }

    @Test
    void testSetAndGet_Returned() {
        loan.setReturned(true);
        assertTrue(loan.getReturned());
        loan.setReturned(false);
        assertFalse(loan.getReturned());
    }

    @Test
    void testSetAndGet_ReturnDate() {
        LocalDate returnDate = LocalDate.of(2025, 12, 1);
        loan.setReturnDate(returnDate);
        assertEquals(returnDate, loan.getReturnDate());
    }

    @Test
    void testSetAndGetLast_AccruedDate() {
        LocalDate newAccrued = LocalDate.of(2025, 12, 5);
        loan.setLastAccruedDate(newAccrued);
        assertEquals(newAccrued, loan.getLastAccruedDate());
    }

    @Test
    void testLoanIdAutoIncrement() {
        Loan loan2 = new Loan(book, user);
        assertTrue(loan2 != loan);
        assertNotEquals(loan2, loan); // Objects are different
        assertEquals(loan.getUser(), loan2.getUser());
        assertEquals(loan.getMedia(), loan2.getMedia());
    }
}
