import java.util.List;
/**
 * Interface defining common operations for managing media in the library.
 * <p>
 * Implementing classes handle adding media items, searching for available items,
 * and retrieving all items with a given title.
 * </p>
 *
 * @see Media
 */
public interface MediaManager {
    /**
     * Adds one or more copies of a media item to the system.
     *
     * @param media the media item to add
     * @param copies the number of copies to add (must be positive)
     * @throws IllegalArgumentException if the media type is not supported or copies ≤ 0
     */
    void add(Media media, int copies);
    /**
     * Finds the first available (not borrowed) media item with the given title.
     *
     * @param title the title to search for
     * @return an available media item with the specified title, or null if none is available
     */
    Media findAvailableByTitle(String title);

    /**
     * Finds all media items with the specified title, regardless of their availability.
     *
     * @param title the title to search for
     * @return a list of media items matching the title; empty list if none found
     */
    List<? extends Media> findAllByTitle(String title);
}