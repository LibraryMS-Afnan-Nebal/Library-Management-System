import java.util.*;
public class BookManager extends MediaManager<Book> {
    private static BookManager instance = null;

    private BookManager() {}

    public static BookManager getInstance()
    {
        if (instance == null) {instance = new BookManager();}
        return instance;
    }

    @Override
    protected Book createCopy(Book original) {return new Book(original); }

    @Override
    protected boolean supports(Media media) {return media instanceof Book;}

    /**
     * Enforce ISBN uniqueness at repository level.
     * Book constructor already validates ISBN format, so here we only check repository state.
     */
    @Override
    protected boolean isDuplicate(Book item)
    {
        if (item == null) return false;
        String isbn = item.getISBN();
        return super.listOfItems.stream().anyMatch(b -> isbn.equals(b.getISBN()));
    }

    public String displaySearchByTitle(String title, Object person) {
        List<Book> found = searchByTitle(title);
        return getSearchResultsString(found, person);
    }

    public String displaySearchByAuthor(String author, Object person) {
        List<Book> found = searchByAuthor(author);
        return getSearchResultsString(found, person);
    }

    public String displaySearchByISBN(String isbn, Object person) {
        List<Book> found = listOfItems.stream()
                .filter(b -> b.getISBN().equals(isbn))
                .toList();
        return getSearchResultsString(found, person);
    }

    private String getSearchResultsString(List<Book> found, Object person) {
        StringBuilder sb = new StringBuilder();

        if (found.isEmpty()) {
            sb.append("This book not found\n");
            return sb.toString();
        }

        if (!hasAvailableCopies(found)) {
            sb.append("All copies was Borrowed\n");
            return sb.toString();
        }

        Map<String, List<Book>> grouped = groupByTitleAuthor(found);

        if (person instanceof User) {
            sb.append(String.format("%-15s %-25s %-20s %-10s%n", "ISBN", "Title", "Author", "Status"));
            for (List<Book> group : grouped.values()) {
                Book b = group.stream().filter(x -> !x.getIsBorrowed()).findFirst().get();
                sb.append(String.format("%-15s %-25s %-20s %-10s%n",
                        b.getISBN(), b.getTitle(), b.getAuthor(), "Available"));
            }
        } else if (person instanceof Admin) {
            sb.append(String.format("%-15s %-25s %-20s %-10s %-10s %-10s%n",
                    "ISBN", "Title", "Author", "Total", "Borrowed", "Available"));

            for (List<Book> group : grouped.values()) {
                Book b = group.get(0);
                long total = group.size();
                long borrowed = group.stream().filter(Book::getIsBorrowed).count();
                long available = total - borrowed;
                sb.append(String.format("%-15s %-25s %-20s %-10d %-10d %-10d%n",
                        b.getISBN(), b.getTitle(), b.getAuthor(), total, borrowed, available));
            }
        }else
        {
            throw new IllegalArgumentException("Person must be either a User or an Admin");
        }

        return sb.toString();
    }


//    /**
//     * Searches the list of books based on the specified field (title, author, or ISBN)
//     * and the given search keyword
//     * <p>
//     * The method iterates over all books and collects those that match
//     * the search criteria into a list, which is then returned
//     *
//     * @param type the search field ("title", "author", or "isbn")
//     * @param key the keyword to search for
//     * @return a list of books that match the search criteria
//     * @since 0.0.1
//     */
//     List<Book> findBooksByField(String type, String key)
//    {
//        boolean match;
//        List<Book> listOfSearchedBooks = new ArrayList<>();
//        for (Book book: listOfItems)
//        {
//            if (type.equals("title"))
//            {
//                match = book.getTitle().equals(key);
//            }
//            else if (type.equals("author"))
//            {
//                match = book.getAuthor().equals(key);
//            }
//            else
//            {
//                match = book.getISBN().equals(key);
//            }
//            if (match)
//            {
//                listOfSearchedBooks.add(book);
//            }
//        }
//        return listOfSearchedBooks;
//    }
//
//     List<Book> filterAvailableBooksForUser(List<Book> books)
//    {
//        List<Book> available = new ArrayList<>();
//        Set<String> addedISBNs = new HashSet<>();
//
//        for (Book b : books)
//        {
//            if (!b.getIsBorrowed()&& addedISBNs.add(b.getISBN())) {
//                available.add(b);
//            }
//        }
//        return available;
//    }
//
//     List<BookStats> summarizeBooksForAdmin(List<Book> books)
//    {
//        List<BookStats> stats = new ArrayList<>();
//        Set<String> addedISBNs = new HashSet<>();
//
//        for (Book book : books)
//        {
//            if (addedISBNs.add(book.getISBN()))
//            {
//                int total = 0, borrowed = 0;
//
//                for (Book b : books)
//                {
//                    if (b.getISBN().equals(book.getISBN()))
//                    {
//                        total++;
//                        if (b.getIsBorrowed()){borrowed++;}
//                    }
//                }
//
//                int available = total - borrowed;
//                stats.add(new BookStats(book.getISBN(), book.getTitle(), book.getAuthor(), total, borrowed, available));
//            }
//        }
//        return stats;
//    }
//
//
//    private String formatUserView(List<Book> books)
//    {
//        StringBuilder sb = new StringBuilder();
//        sb.append(String.format("%-15s %-25s %-20s %-10s\n", "ISBN", "Title", "Author", "Status"));
//        for (Book b : books)
//        {
//            sb.append(String.format("%-15s %-25s %-20s %-10s\n", b.getISBN(), b.getTitle(), b.getAuthor(), "Borrowed"));
//        }
//        return sb.toString();
//    }
//
//    private String formatAdminStats(List<BookStats> stats)
//    {
//        StringBuilder sb = new StringBuilder();
//        sb.append(String.format("%-15s %-25s %-19s %12s %12s %12s\n", "ISBN", "Title", "Author", "Total", "Borrowed", "Available"));
//        for (BookStats s : stats)
//        {
//            sb.append(s).append("\n");
//        }
//        return sb.toString();
//    }
//
//     String searchBooksByField(String type, String key, Object person)
//    {
//        List<Book> foundBooks = findBooksByField(type, key);
//
//        if (foundBooks.isEmpty())
//        {
//            return "Book not found\n";
//        }
//        if (person instanceof User)
//        {
//            List<Book> available = filterAvailableBooksForUser(foundBooks);
//            return formatUserView(available);
//        }
//        else if (person instanceof Admin)
//        {
//            List<BookStats> stats = summarizeBooksForAdmin(foundBooks);
//            return formatAdminStats(stats);
//        }
//        else
//        {
//            return "Invalid user type\n";
//        }
//    }
//
//    @Override
//    public void searchItemByTitle(String title, Object person)
//    {
//        System.out.print(searchBooksByField("title", title, person));
//    }
//
//    public void searchBookByAuthor(String author, Object person)
//    {
//        System.out.print( searchBooksByField("author", author, person));
//    }



}