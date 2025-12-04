import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CDManagerTest {

    private CDManager cdManager;
    private CD cd1;
    private CD cd2;
    private User user;
    private Admin admin;

    @BeforeEach
    void setUp() {
        cdManager = CDManager.getInstance();

        cdManager.listOfItems.clear();

        cd1 = new CD("CD One", "Author A");
        cd2 = new CD("CD Two", "Author B");
        user = new User(1, "user1", "pass", "user@test.com");
        admin = new Admin(1, "admin1", "pass", "admin@test.com");
    }

    @Test
    void testCreateCopy() {
        CD copy = cdManager.createCopy(cd1);
        assertNotSame(cd1, copy);
        assertEquals(cd1.getTitle(), copy.getTitle());
        assertEquals(cd1.getAuthor(), copy.getAuthor());
        assertEquals(cd1.getIsBorrowed(), copy.getIsBorrowed());
    }

    @Test
    void testSupports() {
        assertTrue(cdManager.supports(cd1));
        Book book = new Book("Book1", "Author1", "1234567890");
        assertFalse(cdManager.supports(book));
    }

    @Test
    void testDisplaySearchByTitleFoundUser() {
        cdManager.add(cd1, 1);
        String result = cdManager.displaySearchByTitle("CD One", user);
        assertTrue(result.contains("CD One"));
        assertTrue(result.contains("Author A"));
        assertTrue(result.contains("Available"));
    }

    @Test
    void testDisplaySearchByAuthorFoundUser() {
        cdManager.add(cd1, 1);
        cdManager.add(cd2, 1);
        String result = cdManager.displaySearchByAuthor("Author A", user);
        assertTrue(result.contains("CD One"));
        assertTrue(result.contains("Author A"));
        assertTrue(result.contains("Available"));
        assertFalse(result.contains("CD Two"));
    }

    @Test
    void testDisplaySearchByTitleNotFound() {
        cdManager.add(cd1, 1);
        String result = cdManager.displaySearchByTitle("Nonexistent", user);
        assertEquals("This CD not found", result);
    }

    @Test
    void testDisplaySearchAllCopiesBorrowed() {
        cd1.setIsBorrowed(true);
        cdManager.add(cd1, 1);
        String result = cdManager.displaySearchByTitle("CD One", user);
        assertEquals("All copies was Borrowed", result);
    }

    @Test
    void testDisplaySearchByTitleFoundAdmin() {
        cdManager.add(cd1, 2);
        cd1.setIsBorrowed(true);
        String result = cdManager.displaySearchByTitle("CD One", admin);
        assertTrue(result.contains("CD One"));
        assertTrue(result.contains("Author A"));
        assertTrue(result.contains("Total"));
        assertTrue(result.contains("Borrowed"));
        assertTrue(result.contains("Available"));
    }

    @Test
    void testDisplaySearchPersonNotUserOrAdmin() {
        cdManager.add(cd1, 1);
        assertThrows(IllegalArgumentException.class,
                () -> cdManager.displaySearchByTitle("CD One", new Librarian(1,"lib","pass","lib@test.com")));
    }
}
