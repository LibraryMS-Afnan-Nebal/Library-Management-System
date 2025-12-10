public class Book extends Media {
    private final String ISBN;
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
        super(title,author);
        String isbn = "978"+isbnPart;
        this.ISBN = validateIsbn(isbn);
    }

    public Book(Book copy) {
        super(copy.getTitle(), copy.getAuthor());
        this.ISBN = copy.ISBN;
        this.setIsBorrowed(copy.getIsBorrowed());
    }

    @Override
    public int getLoanDurationDays() {
        return 28;
    }

    @Override
    public double getDailyFineRate() {
        return 10;
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
   @Override
    public String getTitle() {return  super.getTitle();}
    @Override
    public String getAuthor() {return super.getAuthor();}
    public String getISBN() {return ISBN;}
    @Override
    public boolean getIsBorrowed() {return super.getIsBorrowed();}
    @Override
    public void setIsBorrowed(boolean status) {super.setIsBorrowed(status);}
}
