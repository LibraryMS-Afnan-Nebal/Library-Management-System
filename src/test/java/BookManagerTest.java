import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookManagerTest {

    static BookManager BM;
    static Book book1;

    @BeforeEach
    void setUp() {
        BM =new BookManager();
        book1 = new Book ("Ghorbat Al-Yasmeen","Khawla Hamdi","1234567890");
        BM.addBook(book1);
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
    void addBookWithEmptyTitle()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                    Book book2 = new Book ("","Ibrahim bin Omar Al-Sakran","1234567899");
                }
        );
        assertEquals("Title cannot be empty", exception.getMessage());
    }


    @Test
    void addBookWithEmptyAuthor()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                    Book book2 = new Book ("Raqa’iq Al-Qur’an","","1234567899");
                }
        );
        assertEquals("Author cannot be empty", exception.getMessage());
    }


    @Test
    void addBookWithDuplicateIsbnTest()
    {
        Book book2 = new Book ("Raqa’iq Al-Qur’an","Ibrahim bin Omar Al-Sakran","1234567890");

        BM.addBook(book2);

        assertEquals(1, BM.getListOfBooks().size());
    }


    @Test
    void addBookWithoutDuplicateIsbnTest()
    {
        Book book2 = new Book ("Raqa’iq Al-Qur’an","Ibrahim bin Omar Al-Sakran","1234567891");

        BM.addBook(book2);

        assertEquals(2, BM.getListOfBooks().size());
    }


    @Test
    void addBookWithIsbnContainsSpacesTest()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                    Book book2 = new Book ("Raqa’iq Al-Qur’an","Ibrahim bin Omar Al-Sakran","1 23456789");
                }
        );
        assertEquals("ISBN cannot contain spaces", exception.getMessage());
    }


    @Test
    void addBookWithIsbn_isbnContainsNonDigitsTest()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                    Book book2 = new Book ("Raqa’iq Al-Qur’an","Ibrahim bin Omar Al-Sakran","1A23456789");
                }
        );
        assertEquals("ISBN must contain digits only", exception.getMessage());
    }


    @Test
    void addBookWithIsbn_isbnLengthNot10()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                    Book book2 = new Book ("Raqa’iq Al-Qur’an","Ibrahim bin Omar Al-Sakran","12345678910");
                }
        );
        assertEquals("ISBN must have 10 digits after the 978 prefix", exception.getMessage());
    }


    @Test
    void searchBookByTitleTest()
    {
        String title ="Ghorbat Al-Yasmeen";

        BM.searchBookByTitle(title);

        assertEquals(1, BM.getSearchedBooks().size());
    }


    @Test
    void searchByTitle_bookNotFound()
    {
        String title ="My Aunt's House";

        BM.searchBookByTitle(title);

        assertTrue(BM.getSearchedBooks().isEmpty());
    }


    @Test
    void searchBookByAuthorTest()
    {
        String author ="Khawla Hamdi";

        BM.searchBookByAuthor(author);

        assertEquals(1, BM.getSearchedBooks().size());
    }


    @Test
    void searchBookByIsbnTest()
    {
        String isbn ="9781234567890";

        BM.searchBookByIsbn(isbn);

        assertEquals(1, BM.getSearchedBooks().size());
    }


    @Test
    void borrowBook_throwsException_whenIsbnContainsSpaces()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                   BM.borrowBook("12 3456789",new User("u1","afnan","36"));
                }
        );
        assertEquals("ISBN cannot contain spaces", exception.getMessage());
    }


    @Test
    void borrowBook_whenBookExistsAndNotBorrowed()
    {
       assertTrue(BM.borrowBook("1234567890" , new User("u1","afnan","36"))               );
    }


    @Test
    void borrowBook_whenBookAlreadyBorrowed()
    {
       BM.borrowBook("1234567890" , new User("u1","afnan","36"));
       assertFalse(BM.borrowBook("1234567890" , new User("u1","afnan","36"))                              );
    }


    @Test
    void borrowBook_whenBookDoesNotExist()
    {
        assertFalse(BM.borrowBook("1234567895",new User("u1","afnan","36")));
    }


    @Test
    void detectOverdueBooks_whenNoBooksBorrowed()
    {
        List<Book> overdueBooks = BM.detectOverdueBooks(LocalDate.now());
        assertTrue(overdueBooks.isEmpty());
    }


    @Test
    void detectOverdueBooks_whenAllBooksWithinDueDate()
    {
        BM.borrowBook("1234567890",new User("u1","afnan","36"));
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        List<Book> overdueBooks = BM.detectOverdueBooks(tomorrow);

        assertTrue(overdueBooks.isEmpty());
    }


    @Test
    void detectOverdueBooks_whenBookPastDueDate()
    {
        BM.borrowBook("1234567890",new User("u1","afnan","36"));
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(30);

        List <Book> overdueBooks = BM.detectOverdueBooks(tomorrow);

        assertEquals(1,overdueBooks.size());
        assertTrue(overdueBooks.get(0).getFine() > 0);
    }

    @Test
    void detectOverdueBooks_shouldCalculateFineCorrectly_whenBookIsOverdue()
    {
        BM.borrowBook("1234567890",new User("u1","afnan","36"));
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(30);

        List <Book> overdueBooks = BM.detectOverdueBooks(tomorrow);
        double expectedFine = 2;
        double actualFine = overdueBooks.get(0).getFine();

        assertEquals(expectedFine,actualFine);
    }






































































}