/**
 * Abstract class representing a general media item in the library system.
 * <p>
 * Each media item has a title and a borrowed status. Subclasses such as
 * {@link Book} and {@link CD} provide specific loan durations and fine rates.
 * </p>
 *
 * <p>This class provides basic getters and setters for title and borrowing status,
 * and defines abstract methods for polymorphic behavior regarding loan duration
 * and daily fine rate.</p>
 *
 * @author Nebal
 * @version 1.0
 * @see Book
 * @see CD
 */

public abstract class Media {
    private String title;
    private boolean isBorrowed;
    /**
     * Constructs a media item with the specified title.
     *
     * @param title the title of the media item (cannot be null or empty)
     * @throws IllegalArgumentException if title is null or empty
     */
    public Media(String title) {
        if (title == null || title.isEmpty())
            throw new IllegalArgumentException("Title cannot be empty");
        this.title = title;
        this.isBorrowed = false;
    }

    /** Returns the title of the media item */
    public String getTitle() {
        return title;
    }

    /** Returns true if the media item is currently borrowed */
    public boolean getIsBorrowed() {
        return isBorrowed;
    }

    /** Sets the borrowed status of the media item */
    public void setIsBorrowed(boolean b) {
        this.isBorrowed = b;
    }

    /**
     * Returns the maximum loan duration for this media item in days.
     * <p>
     * Subclasses define the actual duration (e.g., 28 days for books, 7 days for CDs).
     * </p>
     *
     * @return the loan duration in days
     */
    public abstract int getLoanDurationDays();

    /**
     * Returns the daily fine rate for this media item when overdue.
     * <p>
     * Subclasses define the actual rate (e.g., 10 for books, 20 for CDs).
     * </p>
     *
     * @return the daily fine rate
     */
    public abstract double getDailyFineRate();
}
