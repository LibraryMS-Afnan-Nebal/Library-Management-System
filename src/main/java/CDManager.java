import java.util.ArrayList;
import java.util.List;

public class CDManager implements MediaManager {
    static List<CD> listOfCDs = new ArrayList<>();

    public static List<CD> getListOfCDs() {
        return listOfCDs;
    }

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
