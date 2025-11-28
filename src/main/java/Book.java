import java.time.LocalDate;

public class Book extends Media{

    private static int nextId = 1;
    private final int bookId;
   // private final String title;
    private final String author;
    private final String ISBN;
   // private boolean isBorrowed;

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
        super(title);
        this.bookId = nextId++;
        this.author = validateAuthor(author);
        String isbn = "978"+isbnPart;
        this.ISBN = validateIsbn(isbn);
    }

    @Override
    public int getLoanDurationDays() {
        return 28;
    }

    @Override
    public double getDailyFineRate() {
        return 10;
    }

    public Book(Book copy) {
        super(copy.getTitle());
        this.bookId = nextId++;
        this.author = copy.author;
        this.ISBN = copy.ISBN;
        this.setIsBorrowed(copy.getIsBorrowed());
    }

    static String validateTitle(String title)
    {
        if (title.isEmpty())
        {
            throw new IllegalArgumentException("Title can't be empty");
        }
        return title;
    }

    static String validateAuthor(String author)
    {
        if (author.isEmpty()) {
            throw new IllegalArgumentException("Author can't be empty");
        }
        return author;
    }

    public String validateIsbn(String isbn)
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

        return isbn;
    }

    public static int validateCopies(int copies)
    {
        if (copies <= 0)
            throw new IllegalArgumentException("Copies must be positive");

        return copies;
    }

    public int getBookId() {return bookId;}
    public String getTitle() {return  super.getTitle();}
    public String getAuthor() {return author;}
    public String getISBN() {return ISBN;}
    public boolean getIsBorrowed() {return super.getIsBorrowed();}
    public void setIsBorrowed(boolean status) {super.setIsBorrowed(status);}
}
