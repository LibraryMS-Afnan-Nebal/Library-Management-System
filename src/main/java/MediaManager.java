import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Interface defining common operations for managing media in the library.
 * <p>
 * Implementing classes handle adding media items, searching for available items,
 * and retrieving all items with a given title.
 * </p>
 *
 * @see Media
 */
public abstract class MediaManager<T extends Media>  {
    protected List<T> listOfItems = new ArrayList<>();
    /**
     * Add a media item (or multiple copies) to the repository.
     * The constructor of the media type is expected to validate the fields
     * (title/author/isbn etc.). This method handles repository-level rules
     * like uniqueness (via isDuplicate hook) and copy creation.
     *
     * @param media  the media instance to add (must be of the supported type)
     * @param copies number of copies to add (must be > 0)
     */
    public void add(Media media, int copies) {
        Objects.requireNonNull(media, "media cannot be null");
        if (!supports(media)) throw new IllegalArgumentException("Unsupported media type");
        if (copies <= 0) throw new IllegalArgumentException("Copies must be positive");

        T item = (T) media;

        if (isDuplicate(item)) throw new IllegalArgumentException("ISBN should be Unique");

        for (int i = 0; i < copies; i++) {
            listOfItems.add(copies == 1 ? item : createCopy(item));
        }
    }

    /** Create and return a shallow copy of the original item (used when adding multiple copies). */
    protected abstract T createCopy(T original);

    /** Return true if this manager supports the runtime media instance. */
    protected abstract boolean supports(Media media);

    /**
     * Repository-level duplicate check. Default: no duplication rule.
     * Subclasses should override when they need uniqueness constraints (e.g. ISBN).
     */
    protected boolean isDuplicate(T item) { return false; }


    protected List<T> search(Predicate<T> filter) {
        return listOfItems.stream().filter(filter).toList();
    }
    public List<T> searchByTitle(String title) {
        return search(item -> title != null && title.equalsIgnoreCase(item.getTitle()));
    }

    public List<T> searchByAuthor(String author) {
        return search(item -> author != null && author.equalsIgnoreCase(item.getAuthor()));
    }

    protected Map<String, List<T>> groupByTitleAuthor(List<T> items) {
        return items.stream()
                .collect(Collectors.groupingBy(i -> i.getTitle() + "###" + i.getAuthor()));
    }

    protected boolean hasAvailableCopies(List<T> items) {
        return items.stream().anyMatch(i -> !i.getIsBorrowed());
    }


}