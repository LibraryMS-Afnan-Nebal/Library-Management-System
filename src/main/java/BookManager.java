import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookManager   {
    private final  List<Book> listOfBooks ;
    private  List<Book> searchedBooks;

    public BookManager ()
    {
        listOfBooks = new ArrayList<>();
        searchedBooks = new ArrayList<>();
    }

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


    /**
     * Allows a user to borrow a book from the system.
     * <p>The process ensures that:</p>
     * <ol>
     *   <li>The entered ISBN is valid.</li>
     *   <li>The book is not already borrowed.</li>
     *   <li>The book exists in the system.</li>
     * </ol>
     *
     * <p>If all conditions are met, the book is marked as borrowed by the specified user,
     * and a due date (28 days from today) is assigned.</p>
     *
     * @param isbnPart the last part of the ISBN entered by the user (the system prefixes it with "978")
     * @param user the user borrowing the book (the borrowed book will be added to their list)
     * @return {@code true} if the borrowing process succeeds; {@code false} otherwise
     * @since 0.0.2
     */
    public boolean borrowBook (String isbnPart,User user)
    {
        String isbn ="978" + isbnPart;

        //sure that isbn valid
        Book.validateIsbn(isbn);

        for (Book book : listOfBooks)
        {
            if(book.getISBN().equals(isbn))
            {
                if(book.getIsBorrowed())
                {
                    System.out.println("Sorry.This Book is already borrowed");
                    return false;
                }

                //book exist but not borrowed
                book.setIsBorrowed(true);
                LocalDate today = LocalDate.now();
                LocalDate dueDate = today.plusDays(28);
                book.setDueDate(dueDate);
                user.setBorrowedBooks(book); //this is need test case in UserManagerTest
                System.out.println("You successfully borrowed the book.\nReturn it by: "+book.getDueDate());
                return true;
            }
        }

        //Book not in the system
            System.out.println("Book with this ISBN not found");
            return false;
    }


    /**
     * Detects all books that are overdue and calculates fines for them.
     * <p>If a book is currently borrowed and its due date has passed,
     * it is marked as overdue, and a fine is calculated based on the
     * number of days past the due date.</p>
     *
     * @param currentDate the current date used to determine if a book is overdue
     * @return a list of books that are overdue
     * @since 0.0.2
     */
    public List<Book> detectOverdueBooks (LocalDate currentDate)
    {
        List <Book> overdueBooks = new ArrayList<>();
        for (Book book : listOfBooks)
        {
            if (book.getIsBorrowed())
            {
                // Difference between current date and due date (negative means overdue)
                double daysDifference  = ChronoUnit.DAYS.between(currentDate, book.getDueDate());
                if (daysDifference  < 0)
                {
                    book.setIsOverdue(true);
                    double fine = daysDifference  * -1;
                    book.setFine(fine);
                    overdueBooks.add(book);
                }
            }
        }
        System.out.println("Overdue status updated successfully.");
        return overdueBooks;
    }




}