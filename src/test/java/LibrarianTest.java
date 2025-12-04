import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibrarianTest {
    private Librarian librarian;
    private LoanManager loanManager;
    private UserManager userManager;
    @BeforeEach
    void setUp() {
        librarian = new Librarian(1, "Lib1", "pwd", "lib1@test.com");
        loanManager = LoanManager.getInstance();
        loanManager.getLoanList().clear();

        userManager = UserManager.getInstance();
        userManager.accounts.clear();
        userManager.usernameToId.clear();
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void testConstructorInitializesRoleFields() {
        assertEquals(1, librarian.getId());
        assertEquals("Lib1", librarian.getUsername());
        assertEquals("pwd", librarian.getPassword());
        assertEquals("lib1@test.com", librarian.getEmail());
        assertFalse(librarian.isLoggedIn());
    }
    @Test
    void testDetectOverdueMedia_NoOverdueLoans() {
        // Arrange: user with a non-overdue loan
        User user = new User(10, "User1", "1234", "user1@test.com");
        userManager.accounts.put(10, user);
        userManager.usernameToId.put("user1", 10);

        Book book = new Book("Book A", "Author X", "1234567890");

        // Due date in the future → NOT overdue
        Loan loan = new Loan(book, user);
        loanManager.getLoanList().add(loan);

        double fineBefore = user.getFineBalance();

        // Act
        List<Loan> result = librarian.detectOverdueMedia();

        // Assert
        assertTrue(result.isEmpty(), "No loans should be returned when nothing is overdue.");
        assertEquals(fineBefore, user.getFineBalance(),
                "Fine balance should not change when there are no overdue loans.");
    }

    @Test
    void testDetectOverdueMedia_WithOverdueLoans_AppliesFines() {
        // Arrange: user with an overdue loan
        User user = new User(11, "User2", "abcd", "user2@test.com");
        userManager.accounts.put(11, user);
        userManager.usernameToId.put("user2", 11);

        Book book = new Book("Book B", "Author Y", "1234567891");
        CD cd = new CD ("CD c","singer");

        // Due date in the past → overdue
        Loan overdueBook = new Loan(book, user);
        Loan overdueCD = new Loan(cd, user);
        loanManager.getLoanList().add(overdueBook);
        loanManager.getLoanList().add(overdueCD);


        overdueBook.setDueDate(LocalDate.now().minusDays(1));// //10NIS per day
        overdueBook.setLastAccruedDate( overdueBook.getDueDate());
        List<Loan> overdueLoans1element = librarian.detectOverdueMedia();
        assertEquals(1, overdueLoans1element.size(), "One overdue loan should be detected.");
        assertEquals(10,user.getFineBalance(),"Fine balance should increase for users with overdue loans.");

        overdueCD.setDueDate(LocalDate.now().minusDays(1));//20NIS for per day
        overdueCD.setLastAccruedDate( overdueCD.getDueDate());
        List<Loan> overdueLoans2elements = librarian.detectOverdueMedia();
        assertEquals(2, overdueLoans2elements.size(), "Two overdue loan should be detected.");
        assertEquals(30,user.getFineBalance(),"Fine balance should increase for users with overdue loans.");


    }

}