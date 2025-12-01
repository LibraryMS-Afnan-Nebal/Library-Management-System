import java.util.ArrayList;
import java.util.List;
/**
 * Manages CD media in the Library Management System.
 * <p>
 * This class keeps track of all CDs in the library, supports adding new CDs,
 * and provides methods to search for CDs by title. It implements the {@link MediaManager}
 * interface.
 * </p>
 *
 * <p>All CDs are stored in a static list, so they are shared across all instances.</p>
 *
 * @see CD
 * @see MediaManager
 */
public class CDManager implements MediaManager {
    /** List containing all CD objects in the system */
    static List<CD> listOfCDs = new ArrayList<>();
    /**
     * Returns the list of all CDs in the system.
     *
     * @return a list of CDs
     */
    public static List<CD> getListOfCDs() {
        return listOfCDs;
    }
    /**
     * Adds a specified number of copies of a CD to the system.
     * <p>
     * If multiple copies are added, the CD copy constructor is used to
     * create new instances.
     * </p>
     *
     * @param media the CD to add (must be an instance of {@link CD})
     * @param copies the number of copies to add (must be positive)
     * @throws IllegalArgumentException if media is not a CD or copies ≤ 0
     */
    @Override
    public void add(Media media, int copies) {
        if (!(media instanceof CD))
            throw new IllegalArgumentException("Only CD objects allowed");

        CD cd = (CD) media;

        if (copies <= 0)
            throw new IllegalArgumentException("Copies must be positive");

        if (copies == 1) {
            listOfCDs.add(cd);
        } else {
            for (int i = 0; i < copies; i++) {
                listOfCDs.add(new CD(cd));  // uses copy constructor
            }
        }
    }
    /**
     * Finds the first available (not borrowed) CD with the specified title.
     *
     * @param title the title of the CD to search for
     * @return an available CD with the given title, or null if none is found
     */
    @Override
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
     * Finds all CDs with the specified title, regardless of their borrowed status.
     *
     * @param title the title of the CDs to search for
     * @return a list of CDs matching the given title; empty list if none found
     */
    @Override
    public List<CD> findAllByTitle(String title) {
        List<CD> result = new ArrayList<>();
        if (title == null) return result;

        for (CD cd : listOfCDs) {
            if (cd.getTitle().equalsIgnoreCase(title)) {
                result.add(cd);
            }
        }
        return result;
    }
}
