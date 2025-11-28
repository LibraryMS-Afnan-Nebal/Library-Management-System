import java.util.ArrayList;
import java.util.List;

public class CDManager {
    static List<CD> listOfCDs = new ArrayList<>();

    public static List<CD> getListOfCDs() {
        return listOfCDs;
    }

    /**
     * Add a CD. If copies > 1, creates copies using CD(CD) copy constructor.
     * Keep uniqueness simple (title only) — change rule later if you want.
     */
    public void addCD(CD cd, int copies) {
        if (cd == null) throw new IllegalArgumentException("CD cannot be null");
        if (copies <= 0) throw new IllegalArgumentException("copies must be positive");

        // optional: avoid adding exact duplicate title+artist (simple check)
        for (CD existing : listOfCDs) {
            if (existing.getTitle().equalsIgnoreCase(cd.getTitle())) {
                // allow duplicates as separate physical copies; comment out this check if you want duplicates
                break;
            }
        }

        if (copies == 1) {
            listOfCDs.add(cd);
        } else {
            // require the copy constructor in CD: new CD(cd)
            for (int i = 0; i < copies; i++) {
                CD copy = new CD(cd);
                listOfCDs.add(copy);
            }
        }
    }

    /**
     * Return the first available (not borrowed) CD with a matching title,
     * or null if none available. Case-insensitive match.
     */
    public CD findAvailableByTitle(String title) {
        if (title == null) return null;
        for (CD cd : listOfCDs) {
            if (!cd.getIsBorrowed() && cd.getTitle().equalsIgnoreCase(title)) {
                return cd;
            }
        }
        return null;
    }

    /**
     * Find all CDs that match the title (case-insensitive).
     */
    public List<CD> findCDsByTitle(String title) {
        List<CD> result = new ArrayList<>();
        if (title == null) return result;
        for (CD cd : listOfCDs) {
            if (cd.getTitle().equalsIgnoreCase(title)) result.add(cd);
        }
        return result;
    }
}
