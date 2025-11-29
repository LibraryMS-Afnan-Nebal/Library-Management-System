import java.util.List;

public interface MediaManager {
    void add(Media media, int copies);

    Media findAvailableByTitle(String title);

    List<? extends Media> findAllByTitle(String title);
}