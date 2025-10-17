import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private final  List<Book> listOfBooks = new ArrayList<>();
    private  List<Book>searchedBooks = new ArrayList<>();

    public List<Book> getListOfBooks()
    {
        return listOfBooks;
    }
    public List<Book> getSearchedBooks()
    {
        return searchedBooks;
    }

    /**
     * Adds a book to the library with a unique ISBN
     *
     * @param book the book to add to the library
     * @since 0.0.1
     */
    public void addBook(Book book)
    {
        for(Book b: listOfBooks)
        {
            if(b.getISBN().equals(book.getISBN()))
            {
                return;
            }
        }
        listOfBooks.add(book);
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
    private List<Book> searchBooksByField(String type, String key)
    {
        boolean match;
        List<Book> listOfSearchedBooks = new ArrayList<>();

        for (Book b: listOfBooks)
        {
            if (type.equals("title"))
            {
                match = b.getTitle().equals(key);
            }
            else if (type.equals("author"))
            {
                match = b.getAuthor().equals(key);
            }
            else
            {
                match = b.getISBN().equals(key);
            }


            if (match)
            {
                listOfSearchedBooks.add(b);
            }
        }

        return listOfSearchedBooks;
    }


    /**
     * Shows the books from the given list
     * If the list is empty, it prints "Book not found"
     *
     * @param searchedBooks the list of books matching the user's search criteria
     * @since 0.0.1
     */
   private void displaySearchedBooks(List<Book> searchedBooks )
   {
       if (searchedBooks.isEmpty())
       {
           System.out.print("Book not found");
       }

       else
       {
           for (Book b : searchedBooks)
           {
               System.out.print(b);
           }

       }
   }


    /**
     * Searches for a book by its title
     *
     * @param title the title of the book to search for
     * @since 0.0.1
     */
    public void searchBookByTitle(String title)
    {
        searchedBooks = searchBooksByField("title",title );
        displaySearchedBooks(searchedBooks);
    }


    /**
     * Searches for a book by its author
     *
     * @param author the author of the book to search for
     * @since 0.0.1
     */
    public void searchBookByAuthor(String author)
    {
        searchedBooks = searchBooksByField("author",author );
        displaySearchedBooks(searchedBooks);
    }


    /**
     * Searches for a book by its ISBN
     *
     * @param isbn the ISBN of the book to search for
     * @since 0.0.1
     */
    public void searchBookByIsbn(String isbn)
    {
        searchedBooks = searchBooksByField("isbn",isbn );
        displaySearchedBooks(searchedBooks);
    }







































































}