/**
 * Represents a CD in the Library Management System.
 * <p>
 * Each CD has a unique ID, a title, and a borrowed status.
 * CDs can be borrowed for a fixed duration and have a specific daily fine rate
 * when overdue.
 * </p>
 *
 * <p>This class extends {@link Media} and implements the CD-specific loan rules.</p>
 *
 * @see Media
 */
public class CD extends Media {
    /**
     * Constructs a new CD with the specified title.
     * The CD is assigned a unique ID automatically.
     *
     * @param title the title of the CD
     */
    public CD(String title , String author) {
        super(title,author);
    }
    /**
     * Copy constructor to create a new CD from an existing CD.
     * <p>
     * The new CD will have a unique ID and the same title and borrowed status
     * as the original.
     * </p>
     *
     * @param copy the CD to copy
     */
    public CD(CD copy) {
        super(copy.getTitle(),copy.getAuthor());
        this.setIsBorrowed(copy.getIsBorrowed());
    }
    /**
     * Returns the maximum loan duration for a CD in days.
     *
     * @return the loan duration, always 7 days for CDs
     */
    @Override
    public int getLoanDurationDays() {
        return 7;
    }
    /**
     * Returns the daily fine rate for an overdue CD.
     *
     * @return the daily fine, 20 units per day
     */
    @Override
    public double getDailyFineRate() {
        return 20;
    }

}
