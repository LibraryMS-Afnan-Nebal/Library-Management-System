import java.time.LocalDate;

public class Book {
    private final String title;
    private final String author;
    private final String ISBN;
    private boolean isBorrowed;
    private LocalDate dueDate ;
    private boolean isOverdue;
    private double fine;

    /**
     * Creates a new Book with the given title, author, and ISBN part.
     * The full ISBN will be prefixed with "978".
     *
     * @param title the title of the book
     * @param author the author of the book
     * @param isbnPart the 10-digit part of the ISBN (without the "978" prefix)
     * @throws IllegalArgumentException if isbnPart contains spaces,
     *         is not all digits, or does not have exactly 10 digits
     */
    public Book (String title,String author,String isbnPart)
    {
        if (title.isEmpty())
        {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title=title;

        if (author.isEmpty())
        {
            throw new IllegalArgumentException("Author cannot be empty");
        }
        this.author=author;

        String isbn = "978"+isbnPart;
        validateIsbn(isbn);
        this.ISBN = isbn;

        this.isBorrowed = false;
        this.isOverdue = false;
        this.fine = 0.0;
    }

    public static void validateIsbn(String isbn)
    {
        if (isbn.contains(" "))
        {
            throw new IllegalArgumentException("ISBN cannot contain spaces");
        }
        if (!isbn.matches("\\d+"))
        {
            throw new IllegalArgumentException("ISBN must contain digits only");
        }
        if (isbn.length() != 13)
        {
            throw new IllegalArgumentException("ISBN must have 10 digits after the 978 prefix");
        }
    }

    /**
     * @return the title of the book
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @return the author of the book
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     * @return the full ISBN of the book
     */
    public String getISBN()
    {
        return ISBN;
    }

    public boolean getIsBorrowed()
    {
        return isBorrowed;
    }

    public LocalDate getDueDate()
    {
       return this.dueDate;
    }

    public void setIsOverdue(boolean overdue)
    {
        this.isOverdue = overdue;
    }

    public double getFine()
    {
        return fine;
    }

    public void setIsBorrowed(boolean status)
    {
        this.isBorrowed = status;
    }

    public void setDueDate(LocalDate dueDate)
    {
        this.dueDate = dueDate;
    }

    public void setFine(double fine)
    {
        this.fine = fine;
    }


    /**
     * @return a string representation of the book
     * including title, author, and ISBN
     */
    @Override
    public String toString()
    {
        return String.format("Book{Title='%s', Author='%s', Isbn='%s'}", title,author, ISBN);
    }



}
