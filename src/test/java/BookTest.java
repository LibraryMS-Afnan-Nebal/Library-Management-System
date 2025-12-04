import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    void testBookCreationValid()
    {
        Book b = new Book("Clean Code", "Robert Martin", "1234567890");

        assertEquals("Clean Code", b.getTitle());
        assertEquals("Robert Martin", b.getAuthor());
        assertEquals("9781234567890", b.getISBN());
        assertFalse(b.getIsBorrowed());
    }

    @Test
    void testBorrowStatus()
    {
        Book b = new Book("Test Book", "Author", "1234567890");

        assertFalse(b.getIsBorrowed());
        b.setIsBorrowed(true);
        assertTrue(b.getIsBorrowed());
    }

    @Test
    void testLoanDuration()
    {
        Book b = new Book("Test Book", "Author", "1234567890");
        assertEquals(28, b.getLoanDurationDays());
    }

    @Test
    void testDailyFineRate()
    {
        Book b = new Book("Test Book", "Author", "1234567890");
        assertEquals(10, b.getDailyFineRate());
    }

    @Test
    void testInvalidIsbnSpaces()
    {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new Book("Test", "Author", "123 4567890")
        );
        assertEquals("ISBN cannot contain spaces", ex.getMessage());
    }

    @Test
    void testInvalidIsbnNotDigits()
    {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new Book("Test", "Author", "12345ABC90")
        );
        assertEquals("ISBN must contain digits only", ex.getMessage());
    }

    @Test
    void testInvalidIsbnLength()
    {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new Book("Test", "Author", "12345")
        );
        assertEquals("ISBN must have 10 digits after the 978 prefix", ex.getMessage());
    }

    @Test
    void testCopyConstructor()
    {
        Book original = new Book("Original", "Author", "1234567890");
        original.setIsBorrowed(true);

        Book copy = new Book(original);

        assertEquals("Original", copy.getTitle());
        assertEquals("Author", copy.getAuthor());
        assertEquals("9781234567890", copy.getISBN());
        assertTrue(copy.getIsBorrowed());
        assertNotEquals(original.getId(), copy.getId());
    }
}
