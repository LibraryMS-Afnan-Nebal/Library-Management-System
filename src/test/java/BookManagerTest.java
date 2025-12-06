import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookManagerTest {

    private BookManager bookManager;
    private Book book1, book2, book3;
    private User user;
    private Admin admin;

    @BeforeEach
    void setUp() {
        bookManager = BookManager.getInstance();

        bookManager.listOfItems.clear();

        book1 = new Book("Book One", "Author A", "1234567890");
        book2 = new Book("Book Two", "Author A", "1234567891");
        book3 = new Book("Book Three", "Author B", "1234567892");

        bookManager.add(book1, 2);
        bookManager.add(book2, 1);
        bookManager.add(book3, 1);

        user = new User(1, "u1", "pass", "user@test.com");
        admin = new Admin(1, "admin1", "pass", "admin@test.com");
    }

    @Test
    void testCreateCopy() { //OKAY
        Book copy = bookManager.createCopy(book1);
        assertNotNull(copy);
        assertEquals(book1.getTitle(), copy.getTitle());
        assertEquals(book1.getAuthor(), copy.getAuthor());
        assertEquals(book1.getISBN(), copy.getISBN());
        assertNotSame(book1, copy);
    }

    @Test
    void testSupports() { //OKAY
        assertTrue(bookManager.supports(book1));
        assertFalse(bookManager.supports(new Media("Title","Author") {
            @Override public int getLoanDurationDays() { return 0; }
            @Override public double getDailyFineRate() { return 0; }
        }));
    }

    @Test
    void testIsDuplicate() {
        assertTrue(bookManager.isDuplicate(book1));
    }

    @Test
    void testIsDuplicate_BookIsNull() {
        assertFalse(bookManager.isDuplicate(null));
    }

    @Test
    void testDisplaySearchByTitleUser() {
        String output = bookManager.displaySearchByTitle("Book One", user);
        assertTrue(output.contains("Book One"));
        assertTrue(output.contains("Author A"));
        assertTrue(output.contains("Available"));
    }

    @Test
    void testDisplaySearchByTitleAdmin() {
        String output = bookManager.displaySearchByTitle("Book One", admin);
        assertTrue(output.contains("Book One"));
        assertTrue(output.contains("Author A"));
        assertTrue(output.contains("Total"));
        assertTrue(output.contains("Borrowed"));
        assertTrue(output.contains("Available"));
    }

    @Test
    void testDisplaySearchByAuthorUser() {
        String output = bookManager.displaySearchByAuthor("Author A", user);
        assertTrue(output.contains("Book One"));
        assertTrue(output.contains("Book Two"));
        assertTrue(output.contains("Available"));
    }

    @Test
    void testDisplaySearchByAuthorAdmin() {
        String output = bookManager.displaySearchByAuthor("Author A", admin);
        assertTrue(output.contains("Book One"));
        assertTrue(output.contains("Book Two"));
        assertTrue(output.contains("Total"));
        assertTrue(output.contains("Borrowed"));
        assertTrue(output.contains("Available"));
    }

    @Test
    void testDisplaySearchByISBNUser() {
        String output = bookManager.displaySearchByISBN("9781234567892", user);
        assertTrue(output.contains("Book Three"));
        assertTrue(output.contains("Author B"));
        assertTrue(output.contains("Available"));
    }

    @Test
    void testDisplaySearchByISBNAdmin() {
        String output = bookManager.displaySearchByISBN("9781234567892", admin);
        assertTrue(output.contains("Book Three"));
        assertTrue(output.contains("Author B"));
        assertTrue(output.contains("Total"));
        assertTrue(output.contains("Borrowed"));
        assertTrue(output.contains("Available"));
    }

    @Test
    void testDisplaySearchNotFound() {
        String output = bookManager.displaySearchByTitle("Nonexistent Book", user);
        assertTrue(output.contains("This book not found"));
    }

    @Test
    void testDisplayAllCopiesBorrowed() {
        for (Book b : bookManager.searchByTitle("Book One")) {
            b.setIsBorrowed(true);
        }
        String output = bookManager.displaySearchByTitle("Book One", user);
        assertTrue(output.contains("All copies was Borrowed"));
    }

    @Test
    void getSearchResultsString_personNotUserOrAdmin() {
        Librarian librarian = new Librarian(1, "l", "p", "l.@");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                bookManager.displaySearchByAuthor("Author A", librarian)
        );

        assertEquals("Person must be either a User or an Admin", exception.getMessage());
    }

    @Test
    void testGetSearchResultsString_skipsGroupWithNoAvailableCopies_forUser() {
        for (Book b : bookManager.searchByTitle("Book One")) {
            b.setIsBorrowed(true);
        }

        String output = bookManager.displaySearchByAuthor("Author A", user);
        assertTrue(output.contains("Book Two"));
        assertTrue(output.contains("Available"));
        assertFalse(output.contains("Book One"));
        assertFalse(output.contains("All copies was Borrowed"));
    }




}
