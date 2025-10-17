import org.junit.jupiter.api.*;

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


































































}