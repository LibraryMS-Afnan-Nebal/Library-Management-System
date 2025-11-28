import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BookManager   {
    static List<Book> listOfBooks = new ArrayList<>();
    public static List<Book> getListOfBooks() {return listOfBooks;}

    /**
     * Adds a book to the library with a unique ISBN
     *
     * @param book the book to add to the library
     * @since 0.0.1
     */
    public void addBook(Book book , int copies)
    {
        for(Book b: listOfBooks)
        {
            if(b.getISBN().equals(book.getISBN()))
            {
               throw new IllegalArgumentException("The ISBN should be uniqe");
            }
        }
        Book.validateCopies(copies);
        if (copies>1)
        {
            for (int i=1; i<= copies;i++)
            {
                Book copyBook = new Book(book);
                listOfBooks.add(copyBook);
            }
        }
        else listOfBooks.add(book);
    }

    /**
     * Searches the list of books based on the specified field (title, author, or ISBN)
     * and the given search keyword
     * <p>
     * The method iterates over all books and collects those that match
     * the search criteria into a list, which is then returned
     *
     * @param type the search field ("title", "author", or "isbn")
     * @param key the keyword to search for
     * @return a list of books that match the search criteria
     * @since 0.0.1
     */
     List<Book> findBooksByField(String type, String key)
    {
        boolean match;
        List<Book> listOfSearchedBooks = new ArrayList<>();
        for (Book book: listOfBooks)
        {
            if (type.equals("title"))
            {
                match = book.getTitle().equals(key);
            }
            else if (type.equals("author"))
            {
                match = book.getAuthor().equals(key);
            }
            else
            {
                match = book.getISBN().equals(key);
            }
            if (match)
            {
                listOfSearchedBooks.add(book);
            }
        }
        return listOfSearchedBooks;
    }

     List<Book> filterAvailableBooksForUser(List<Book> books)
    {
        List<Book> available = new ArrayList<>();
        Set<String> addedISBNs = new HashSet<>();

        for (Book b : books)
        {
            if (!b.getIsBorrowed()&& addedISBNs.add(b.getISBN())) {
                available.add(b);
            }
        }
        return available;
    }

     List<BookStats> summarizeBooksForAdmin(List<Book> books)
    {
        List<BookStats> stats = new ArrayList<>();
        Set<String> addedISBNs = new HashSet<>();

        for (Book book : books)
        {
            if (addedISBNs.add(book.getISBN()))
            {
                int total = 0, borrowed = 0;

                for (Book b : books)
                {
                    if (b.getISBN().equals(book.getISBN()))
                    {
                        total++;
                        if (b.getIsBorrowed()){borrowed++;}
                    }
                }

                int available = total - borrowed;
                stats.add(new BookStats(book.getISBN(), book.getTitle(), book.getAuthor(), total, borrowed, available));
            }
        }
        return stats;
    }


    private String formatUserView(List<Book> books)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s %-25s %-20s %-10s\n", "ISBN", "Title", "Author", "Status"));
        for (Book b : books)
        {
            sb.append(String.format("%-15s %-25s %-20s %-10s\n", b.getISBN(), b.getTitle(), b.getAuthor(), "Borrowed"));
        }
        return sb.toString();
    }

    private String formatAdminStats(List<BookStats> stats)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s %-25s %-19s %12s %12s %12s\n", "ISBN", "Title", "Author", "Total", "Borrowed", "Available"));
        for (BookStats s : stats)
        {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

     String searchBooksByField(String type, String key, Object person)
    {
        List<Book> foundBooks = findBooksByField(type, key);

        if (foundBooks.isEmpty())
        {
            return "Book not found\n";
        }
        if (person instanceof User)
        {
            List<Book> available = filterAvailableBooksForUser(foundBooks);
            return formatUserView(available);
        }
        else if (person instanceof Admin)
        {
            List<BookStats> stats = summarizeBooksForAdmin(foundBooks);
            return formatAdminStats(stats);
        }
        else
        {
            return "Invalid user type\n";
        }
    }

    public void searchBookByTitle(String title, Object person)
    {
        System.out.print(searchBooksByField("title", title, person));
    }

    public void searchBookByAuthor(String author, Object person)
    {
        System.out.print( searchBooksByField("author", author, person));
    }

    public void searchBookByIsbn(String isbn, Object person)
    {
        System.out.print(searchBooksByField("isbn", isbn, person));
    }

}