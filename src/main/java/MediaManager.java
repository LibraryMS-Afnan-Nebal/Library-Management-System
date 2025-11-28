public class MediaManager {

    private static MediaManager instance = null;
    private MediaManager() {}

    public static MediaManager getInstance() {
        if (instance == null) instance = new MediaManager();
        return instance;
    }

    public Media findAvailableMedia(String title) {
        // 1. Search books
        for (Book b : BookManager.getListOfBooks()) {
            if (b.getTitle().equalsIgnoreCase(title) && !b.getIsBorrowed()) {
                return b;
            }
        }

        // 2. Search CDs
        for (CD c : CDManager.getListOfCDs()) {
            if (c.getTitle().equalsIgnoreCase(title) && !c.getIsBorrowed()) {
                return c;
            }
        }

        return null;
    }
}
