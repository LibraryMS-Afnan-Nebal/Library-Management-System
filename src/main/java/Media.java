public abstract class Media {
    private String title;
    private boolean isBorrowed;

    public Media(String title) {
        if (title == null || title.isEmpty())
            throw new IllegalArgumentException("Title cannot be empty");
        this.title = title;
        this.isBorrowed = false;
    }

    public String getTitle() { return title; }
    public boolean getIsBorrowed() { return isBorrowed; }
    public void setIsBorrowed(boolean b) { this.isBorrowed = b; }

    // *** POLYMORPHIC METHODS ***
    public abstract int getLoanDurationDays();      // 28 for books, 7 for CDs
    public abstract double getDailyFineRate();      // 10 for books, 20 for CDs
}
