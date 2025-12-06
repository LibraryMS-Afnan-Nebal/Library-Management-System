
import java.util.List;
import java.util.Map;

public class CDManager extends MediaManager<CD> {
    private static CDManager instance = null;

    private CDManager() {}
    public static CDManager getInstance() {
        if (instance == null) {instance = new CDManager();}
        return instance;
    }


    @Override
    protected CD createCopy(CD original) {return new CD(original);}

    @Override
    protected boolean supports(Media media) {return media instanceof CD;}


    public String displaySearchByTitle(String title, Object person) {
        return displaySearchResults(searchByTitle(title), person);
    }

    public String displaySearchByAuthor(String author, Object person) {
        return displaySearchResults(searchByAuthor(author), person);
    }

    private String displaySearchResults(List<CD> found, Object person) {
        StringBuilder sb = new StringBuilder();

        if (found.isEmpty()) {
            sb.append("This CD not found");
            return sb.toString();
        }

        if (!hasAvailableCopies(found)) {
            sb.append("All copies was Borrowed");
            return sb.toString();
        }

        Map<String, List<CD>> grouped = groupByTitleAuthor(found);

        if (person instanceof User) {
            sb.append(String.format("%-25s %-20s %-10s%n", "Title", "Author", "Status"));
            for (List<CD> group : grouped.values()) {
                group.stream()
                        .filter(x -> !x.getIsBorrowed())
                        .findFirst()
                        .ifPresent(c -> sb.append(String.format("%-25s %-20s %-10s%n",
                                c.getTitle(), c.getAuthor(), "Available")));
            }
        }

         else if (person instanceof Admin) {
            sb.append(String.format("%-25s %-20s %-10s %-10s %-10s\n",
                    "Title", "Author", "Total", "Borrowed", "Available"));
            for (List<CD> group : grouped.values()) {
                CD c = group.get(0);
                long total = group.size();
                long borrowed = group.stream().filter(CD::getIsBorrowed).count();
                long available = total - borrowed;
                sb.append(String.format("%-25s %-20s %-10d %-10d %-10d\n",
                        c.getTitle(), c.getAuthor(), total, borrowed, available));
            }
        } else {
            throw new IllegalArgumentException("Person must be either a User or an Admin");
        }

        return sb.toString();
    }

}
