import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MediaTest {

    static class TestMedia extends Media {
        public TestMedia(String title, String author) {super(title, author);}
        @Override
        public int getLoanDurationDays() {return 5;}
        @Override
        public double getDailyFineRate() {return 15;}
    }

    private TestMedia media;
    @BeforeEach
    void setUp() {
        media = new TestMedia("My Title", "My Author");
    }

    @Test
    void constructor_setsTitleAuthorAndId() {
        assertEquals("My Title", media.getTitle());
        assertEquals("My Author", media.getAuthor());
        assertTrue(media.getId() > 0);
        assertFalse(media.getIsBorrowed());
    }

    @Test
    void constructor_nullOrEmptyTitle_throwsException() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> new TestMedia(null, "Author"));
        assertEquals("Title cannot be empty", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> new TestMedia("", "Author"));
        assertEquals("Title cannot be empty", ex2.getMessage());
    }

    @Test
    void constructor_nullOrEmptyAuthor_throwsException() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> new TestMedia("Title", null));
        assertEquals("Author can't be empty", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> new TestMedia("Title", ""));
        assertEquals("Author can't be empty", ex2.getMessage());
    }

    @Test
    void setIsBorrowed_changesStatusCorrectly() {
        assertFalse(media.getIsBorrowed());
        media.setIsBorrowed(true);
        assertTrue(media.getIsBorrowed());
        media.setIsBorrowed(false);
        assertFalse(media.getIsBorrowed());
    }

    @Test
    void abstractMethods_returnExpectedValues() {
        assertEquals(5, media.getLoanDurationDays());
        assertEquals(15, media.getDailyFineRate());
    }
}
