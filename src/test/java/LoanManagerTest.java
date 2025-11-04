import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoanManagerTest {

    static LoanManager LM;
    static User user;
    static BookManager BM;
    @BeforeEach
    void setUp() {
        BookManager.listOfBooks.clear();
        LM = new LoanManager();
        user = new User(1,"user","password");
        BM = new BookManager();
        Book book1 = new Book("java","author1","1234567890");
        Book book2 = new Book("java","author2","1234567891");
        Book book3 = new Book("java","author3","1234567892");
        BM.addBook(book1,1); BM.addBook(book2,1); BM.addBook(book3,1);
    }
    @AfterEach
    void tearDown() {
    }
    @Test
    void borrowBook_validateTitle()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                    LM.borrowBook("","author",user);
                }
        );
        assertEquals("Title can't be empty", exception.getMessage());
    }

    @Test
    void borrowBook_validateAuthor()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                    LM.borrowBook("title","",user);
                }
        );
        assertEquals("Author can't be empty", exception.getMessage());
    }

    @Test
    void borrowBook_validateUser()
    {
        assertFalse(LM.borrowBook("title","author",null));
    }

    @Test
    void borrowBook()
    {
        assertTrue(LM.borrowBook("java","author1",user));
        assertFalse(LM.borrowBook("java","author1",user));
        assertFalse(LM.borrowBook("titleNotFound","author",user));
    }

    @Test
    void returnBook_validation()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                    LM.returnBook("","author",user);
                }
        );
        assertEquals("Title can't be empty", exception.getMessage());
         exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                    LM.borrowBook("title","",user);
                }
        );
        assertEquals("Author can't be empty", exception.getMessage());
        assertFalse(LM.borrowBook("title","author",null));
    }





























}
