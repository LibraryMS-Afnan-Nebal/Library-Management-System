import java.util.ArrayList;
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


    public void displaySearchByTitle(String title, Object person) {
        displaySearchResults(searchByTitle(title), person);
    }

    public void displaySearchByAuthor(String author, Object person) {
        displaySearchResults(searchByAuthor(author), person);
    }

    private void displaySearchResults(List<CD> found, Object person) {
        if (found.isEmpty()) {
            System.out.println("This CD not found");
            return;
        }

        if (!hasAvailableCopies(found)) {
            System.out.println("All copies was Borrowed");
            return;
        }

        Map<String, List<CD>> grouped = groupByTitleAuthor(found);

        if (person instanceof User) {
            System.out.printf("%-25s %-20s %-10s\n", "Title", "Author", "Status");
            for (List<CD> group : grouped.values()) {
                CD c = group.stream().filter(x -> !x.getIsBorrowed()).findFirst().get();
                System.out.printf("%-25s %-20s %-10s\n",
                        c.getTitle(), c.getAuthor(), "Available");
            }
        } else if (person instanceof Admin) {
            System.out.printf("%-25s %-20s %-10s %-10s %-10s\n",
                    "Title", "Author", "Total", "Borrowed", "Available");
            for (List<CD> group : grouped.values()) {
                CD c = group.get(0);
                long total = group.size();
                long borrowed = group.stream().filter(CD::getIsBorrowed).count();
                long available = total - borrowed;
                System.out.printf("%-25s %-20s %-10d %-10d %-10d\n",
                        c.getTitle(), c.getAuthor(), total, borrowed, available);
            }
        }
    }
}
