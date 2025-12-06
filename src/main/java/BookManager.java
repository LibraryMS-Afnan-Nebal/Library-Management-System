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

        if (person instanceof User)
        {
            sb.append(String.format("%-15s %-25s %-20s %-10s%n", "ISBN", "Title", "Author", "Status"));
            for (List<Book> group : grouped.values()) {
                Book b = group.stream()
                        .filter(x -> !x.getIsBorrowed())
                        .findFirst()
                        .orElse(null);

                if (b == null)
                    continue;
                sb.append(String.format("%-15s %-25s %-20s %-10s%n",
                        b.getISBN(), b.getTitle(), b.getAuthor(), "Available"));
            }
        }
        else if (person instanceof Admin) {
            sb.append(String.format("%-15s %-25s %-20s %-10s %-10s %-10s%n",
                    "ISBN", "Title", "Author", "Total", "Borrowed", "Available"));

            for (List<Book> group : grouped.values()) {
                Book b = group.getFirst();
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



}