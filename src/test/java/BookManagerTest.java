import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookManagerTest {

    static BookManager BM;
    static Book book1;

    @BeforeEach
    void setUp() {
        BookManager.listOfBooks.clear();
        BM = new BookManager();
        book1 = new Book("Ghorbat Al-Yasmeen","Khawla Hamdi","1234567890");
        BM.addBook(book1,1);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void addBookTest()
    {
        assertEquals(1, BM.getListOfBooks().size());
        assertEquals(book1, BM.getListOfBooks().get(0));
    }

    @Test
    void addBookWithDuplicateIsbnTest()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                    Book book2 = new Book ("title","Ibrahim bin Omar Al-Sakran","1234567890");
                    BM.addBook(book2,1);
                }
        );
        assertEquals("The ISBN should be uniqe", exception.getMessage());
    }


    @Test
    void addBookWithoutDuplicateIsbnTest()
    {
        Book book2 = new Book ("Raqa’iq Al-Qur’an","Ibrahim bin Omar Al-Sakran","1234567891");

        BM.addBook(book2,1);

        assertEquals(2, BM.getListOfBooks().size());
    }

    @Test
    void addBookWithMultipleCopies_assignsUniqueIdsAndSameData() {
        Book book2 = new Book("Raqa’iq Al-Qur’an", "Ibrahim bin Omar Al-Sakran", "1234567892");
        int copiesToAdd = 5;

        int initialSize = BM.getListOfBooks().size();
        BM.addBook(book2, copiesToAdd);

        assertEquals(initialSize + copiesToAdd, BM.getListOfBooks().size());

        for (int i = initialSize; i < initialSize + copiesToAdd; i++)
        {
            Book copy = BM.getListOfBooks().get(i);
            assertEquals(book2.getTitle(), copy.getTitle());
            assertEquals(book2.getAuthor(), copy.getAuthor());
            assertEquals(book2.getISBN(), copy.getISBN());
            assertNotEquals(book2.getBookId(), copy.getBookId());
        }
    }


    @Test
    void FindBooksByField_title()
    {
        List<Book> result = BM.findBooksByField("title", "Ghorbat Al-Yasmeen");
        assertEquals(1, result.size());
    }

    @Test
    void FindBooksByField_author()
    {
        List<Book> result = BM.findBooksByField("author", "Khawla Hamdi");
        assertEquals(1, result.size());
    }

    @Test
    void FindBooksByField_isbn()
    {
        List<Book> result = BM.findBooksByField("isbn", "9781234567890");
        assertEquals(1, result.size());
    }

    @Test
    void FindBooksByField_nonExisting_returnsEmpty()
    {
        List<Book> result = BM.findBooksByField("title", "C++");
        assertTrue(result.isEmpty());
    }

    @Test
    void filterAvailableBooksForUser()
    {
        Book book2 = new Book ("Ghorbat Al-Yasmeen","author1","1234567891");
        Book book3 = new Book ("Ghorbat Al-Yasmeen","author2","1234567892");
        book3.setIsBorrowed(true);
        BM.addBook(book2,2);   BM.addBook(book3,1);
        List<Book> allGhorbatAlYasmeen =  BM.findBooksByField("title","Ghorbat Al-Yasmeen");

       List<Book>available = BM.filterAvailableBooksForUser(allGhorbatAlYasmeen);

       assertEquals(2,available.size());
       assertFalse(available.get(0).getIsBorrowed());
    }

    @Test
    void summarizeBooksForAdmin()
    {
        Book book2 = new Book ("Ghorbat Al-Yasmeen","author1","1234567891");
        Book book3 = new Book ("Ghorbat Al-Yasmeen","author2","1234567892");
        book3.setIsBorrowed(true);
        BM.addBook(book2,2);   BM.addBook(book3,1);
        List<Book> allGhorbatAlYasmeen =  BM.findBooksByField("title","Ghorbat Al-Yasmeen");

        List<BookStats>stats = BM.summarizeBooksForAdmin(allGhorbatAlYasmeen);

        assertEquals(3,stats.size());
        assertEquals(2,stats.get(1).getTotalCopies());
        assertEquals(0,stats.get(1).getBorrowedCopies());
        assertEquals(2,stats.get(1).getAvailableCopies());
        assertEquals(1,stats.get(2).getBorrowedCopies());
        assertEquals(0,stats.get(2).getAvailableCopies());
    }

    @Test
    void searchBooksByField_searchNonExisting_returnsBookNotFound()
    {
        String result = BM.searchBooksByField("title", "Python",new User(1,"user","email","111"));
        assertEquals("Book not found\n", result);
    }

    @Test
    void searchBooksByField_asUser_returnsAvailableBooks()
    {
        String result = BM.searchBooksByField("title", "Ghorbat Al-Yasmeen", new User(1,"user","email","111"));

        assertFalse(result.contains("Available"));
        assertTrue(result.contains("Borrowed"));
    }

    @Test
    void searchBooksByField_asAdmin_returnsStats()
    {
        String result = BM.searchBooksByField("title", "Ghorbat Al-Yasmeen", new Admin(1,"admin","122"));
        assertTrue(result.contains("Total"));
        assertTrue(result.contains("Borrowed"));
    }

    @Test
    void searchBooksByField_returnsInvalidUserType()
    {
        String result = BM.searchBooksByField("title", "Ghorbat Al-Yasmeen",new String());
        assertEquals("Invalid user type\n", result);
    }

    @Test
    void searchBooksByTitle()
    {
        BM.searchBookByTitle("Ghorbat Al-Yasmeen",new User(1,"user","email","111"));
    }

    @Test
    void searchBooksByAuthor()
    {
        BM.searchBookByAuthor("Khawla Hamdi",new User(1,"user","email","111"));
    }

    @Test
    void searchBooksByIsbn()
    {
        BM.searchBookByIsbn("9781234567890",new User(1,"user","email","111"));
    }








































































}