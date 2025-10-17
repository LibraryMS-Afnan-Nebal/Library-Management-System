public class Book {
    private final String title;
    private final String author;
    private final String ISBN;
    private final boolean isAvailable;

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
        this.title=title;
        this.author=author;
        if (isbnPart.contains(" "))
        {
            throw new IllegalArgumentException("ISBN cannot contain spaces");
        }
        if (!isbnPart.matches("\\d+"))
        {
            throw new IllegalArgumentException("ISBN must contain digits only");
        }
        if (isbnPart.length() != 10)
        {
            throw new IllegalArgumentException("ISBN must have 10 digits after the 978 prefix");
        }
        this.ISBN = "978" + isbnPart;
        this.isAvailable=true;
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
