import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookTest {

    @Test
    void validateTitle()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                    Book book2 = new Book ("","Ibrahim bin Omar Al-Sakran","1234567899");
                }
        );
        assertEquals("Title can't be empty", exception.getMessage());
    }

    @Test
    void validateAuthor()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                    Book book2 = new Book ("Raqa’iq Al-Qur’an","","1234567899");
                }
        );
        assertEquals("Author can't be empty", exception.getMessage());
    }


    @Test
    void validateIsbn_IsbnContainsSpaces()
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
    void validateIsbn_isbnContainsNonDigits()
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
    void validateIsbn_isbnLengthNot10()
    {
        Book book2 = new Book ("Raqa’iq Al-Qur’an","Ibrahim bin Omar Al-Sakran","1234567890");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                    Book book3 = new Book ("Raqa’iq Al-Qur’an","Ibrahim bin Omar Al-Sakran","12345678910");
                }
        );
        assertEquals("ISBN must have 10 digits after the 978 prefix", exception.getMessage());
    }

    @Test
    void validateCopies_copiesEqualZero()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->
                {
                   Book.validateCopies(0);
                }
        );
        assertEquals("Copies must be positive", exception.getMessage());
    }

    @Test
    void validateCopies()
    {
       int copies = Book.validateCopies(5);
        assertEquals(5,copies);
    }
}

