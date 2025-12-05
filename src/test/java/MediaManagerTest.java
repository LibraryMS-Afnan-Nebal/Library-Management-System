import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MediaManagerTest {
    static class TestMedia extends Media {
        public TestMedia(String title, String author) {
            super(title, author);
        }

        public TestMedia(TestMedia other) {
            super(other.getTitle(), other.getAuthor());
            this.setIsBorrowed(other.getIsBorrowed());
        }

        @Override
        public int getLoanDurationDays() {
            return 1;
        }

        @Override
        public double getDailyFineRate() {
            return 0;
        }
    }

    static class TestMediaManager extends MediaManager<TestMedia> {

        @Override
        protected TestMedia createCopy(TestMedia original) {
            return new TestMedia(original);
        }

        @Override
        protected boolean supports(Media media) {
            return media instanceof TestMedia;
        }

        public List<TestMedia> getListOfItems() {
            return listOfItems;
        }

        public Map<String, List<TestMedia>> groupByTitleAuthorPublic(List<TestMedia> items) {
            return groupByTitleAuthor(items);
        }

        public boolean hasAvailableCopiesPublic(List<TestMedia> items) {
            return hasAvailableCopies(items);
        }
    }

    private TestMediaManager mediaManager;

    @BeforeEach
    void setUp()
    {
        mediaManager = new TestMediaManager();
        mediaManager.listOfItems.clear();
    }

    @Test
    void add_null_throwsNPE() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> mediaManager.add(null, 1));
        assertEquals("media cannot be null", exception.getMessage());
    }

    @Test
    void add_unsupportedMedia_throwsException() {

        Media unsupportedMedia = new Media("Unknown", "Author") {
            @Override
            public int getLoanDurationDays() { return 0; }
            @Override
            public double getDailyFineRate() { return 0; }
        };

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                mediaManager.add(unsupportedMedia, 1)
        );

        assertEquals("Unsupported media type", exception.getMessage());
    }

    @Test
    void add_Copieslessthanzero() {
        Exception exception = assertThrows((IllegalArgumentException.class), () -> mediaManager.add(new TestMedia("testTitle","testAuthor"), 0));
        assertEquals("Copies must be positive", exception.getMessage());
    }

    @Test
    void add_DupliactedItemForBookOnly() {
        Book book1 = new Book("book1","author1","1234567895");
        Book book2 = new Book("book2","author2","1234567895");
        BookManager bookManager = BookManager.getInstance();
        bookManager.add(book1,1);
        Exception exception = assertThrows((IllegalArgumentException.class), () -> bookManager.add(book2,1));
        assertEquals("ISBN should be Unique", exception.getMessage());
    }

    @Test
    void add_oneCopy_storesSameInstance() {
        TestMedia m = new TestMedia("T1", "A1");
        mediaManager.add(m, 1);
        assertSame(m, mediaManager.getListOfItems().get(0));
    }

    @Test
    void add_multipleCopies_createsCopies() {
        TestMedia m = new TestMedia("T1", "A1");
        mediaManager.add(m, 3);

        assertNotSame(m, mediaManager.getListOfItems().get(0));
        assertNotSame(mediaManager.getListOfItems().get(0), mediaManager.getListOfItems().get(1));
        assertEquals(3, mediaManager.getListOfItems().size());
    }

    @Test
    void searchByTitle_nullInput_returnsEmptyList() {
        BookManager manager = BookManager.getInstance();
        manager.listOfItems.clear();
        manager.add(new Book("Book A","Author", "1234567890"), 1);

        List<Book> result = manager.searchByTitle(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void searchByAuthor_nullInput_returnsEmptyList() {
        BookManager manager = BookManager.getInstance();
        manager.listOfItems.clear();
        manager.add(new Book("Book A","John", "1234567890"), 1);

        List<Book> result = manager.searchByAuthor(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void searchByTitle_works() {
        mediaManager.add(new TestMedia("Hello", "A"), 1);
        mediaManager.add(new TestMedia("HELLO", "B"), 1);

        List<TestMedia> result = mediaManager.searchByTitle("hello");
        assertEquals(2, result.size());
    }

    @Test
    void searchByAuthor_works() {
        mediaManager.add(new TestMedia("Book1", "Alice"), 1);
        mediaManager.add(new TestMedia("Book2", "Alice"), 1);
        mediaManager.add(new TestMedia("Book3", "Bob"), 1);

        List<TestMedia> result = mediaManager.searchByAuthor("Alice");
        assertEquals(2, result.size());

        result = mediaManager.searchByAuthor("Bob");
        assertEquals(1, result.size());
    }

    @Test
    void groupByTitleAuthor_groupsCorrectly() {
        mediaManager.add(new TestMedia("Song", "A"), 1);
        mediaManager.add(new TestMedia("Song", "A"), 1);
        mediaManager.add(new TestMedia("Song", "B"), 1);

        Map<String, List<TestMedia>> map =
                mediaManager.groupByTitleAuthorPublic(mediaManager.getListOfItems());

        assertEquals(2, map.size());
        assertEquals(2, map.get("Song###A").size());
        assertEquals(1, map.get("Song###B").size());
    }

    @Test
    void hasAvailableCopies_works() {
        TestMedia a = new TestMedia("X", "Y");
        TestMedia b = new TestMedia("X2", "Y2");
        b.setIsBorrowed(true);

        mediaManager.add(a, 1);
        mediaManager.add(b, 1);

        assertTrue(mediaManager.hasAvailableCopiesPublic(mediaManager.getListOfItems()));

        a.setIsBorrowed(true);
        assertFalse(mediaManager.hasAvailableCopiesPublic(mediaManager.getListOfItems()));
    }
}
