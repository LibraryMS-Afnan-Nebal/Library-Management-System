import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CDTest {

    private CD cd;

    @BeforeEach
    void setUp() {cd = new CD("Greatest Hits", "Famous Artist");}

    @Test
    void constructor_setsTitleAuthorAndId() {
        assertEquals("Greatest Hits", cd.getTitle());
        assertEquals("Famous Artist", cd.getAuthor());
        assertTrue(cd.getId() > 0);
        assertFalse(cd.getIsBorrowed());
    }

    @Test
    void copyConstructor_createsNewInstanceWithSameData() {
        cd.setIsBorrowed(true);
        CD copy = new CD(cd);

        assertNotSame(cd, copy, "Copy should be a new object");
        assertEquals(cd.getTitle(), copy.getTitle());
        assertEquals(cd.getAuthor(), copy.getAuthor());
        assertEquals(cd.getIsBorrowed(), copy.getIsBorrowed());
        assertNotEquals(cd.getId(), copy.getId(), "ID should be unique for copy");
    }

    @Test
    void getLoanDurationDays_returns7() {
        assertEquals(7, cd.getLoanDurationDays());
    }

    @Test
    void getDailyFineRate_returns20() {
        assertEquals(20, cd.getDailyFineRate());
    }
}
