import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanManagerTest {

    private LoanManager loanManager;
    private User user;
    private Book book1;
    private Book book2;
    private Clock fixedClock;

    @BeforeEach
    void setUp() {
        loanManager = LoanManager.getInstance();
        loanManager.getLoanList().clear(); // Reset singleton list

        user = new User(1, "user1", "pass", "user@test.com");
        book1 = new Book("Book One", "Author A", "1234567890");
        book2 = new Book("Book Two", "Author B", "0987654321");

        // Clock fixed at 2025-12-01
        fixedClock = Clock.fixed(Instant.parse("2025-12-01T00:00:00Z"), ZoneId.systemDefault());
    }
    @Test
    void testBorrow_MediaOrUserNull() {
        loanManager.borrow(book1, user);
        assertFalse( loanManager.borrow(null, user));
        assertFalse( loanManager.borrow(book1, null));
    }

    @Test
    void testBorrow_Success() {
        boolean result = loanManager.borrow(book1, user);
        assertTrue(result);
        assertTrue(book1.getIsBorrowed());
        assertEquals(1, loanManager.getLoanList().size());
        assertEquals(1, user.getBorrowedMedia().size());
    }

    @Test
    void testBorrow_Fails_WhenUserHasOverdueItems()
    {
        Loan loan = new Loan(book1, user);
        loan.setDueDate(LocalDate.now().minusDays(3));
        loanManager.getLoanList().add(loan);
        assertFalse(loanManager.borrow(book2, user));
    }
    @Test
    void testBorrow_Succeeds_WhenDueDateNotOverdue() {
        Loan loan = new Loan(book1, user);
        loan.setDueDate(LocalDate.now().plusDays(5)); // not overdue
        loanManager.getLoanList().add(loan);

        assertTrue(loanManager.borrow(book2, user));
    }
    @Test
    void testBorrow_Succeeds_WhenLoanIsReturned() {
        Loan loan = new Loan(book1, user);
        loan.setDueDate(LocalDate.now().minusDays(3)); // overdue
        loan.setReturned(true); // but returned → should ignore
        loanManager.getLoanList().add(loan);

        assertTrue(loanManager.borrow(book2, user));
    }
    @Test
    void testBorrow_Succeeds_WhenLoanBelongsToAnotherUser() {
        User other = new User(99, "x", "y", "z@test.com");

        Loan loan = new Loan(book1, other);
        loan.setDueDate(LocalDate.now().minusDays(3)); // overdue but for OTHER user
        loanManager.getLoanList().add(loan);

        assertTrue(loanManager.borrow(book2, user));
    }

    @Test
    void testBorrow_Fails_WhenUserHasFine() {
        user.addFineAmount(50);
        boolean result = loanManager.borrow(book1, user);
        assertFalse(result);
        assertFalse(book1.getIsBorrowed());
    }

    @Test
    void testBorrow_Fails_WhenMediaAlreadyBorrowed() {
        book1.setIsBorrowed(true);
        boolean result = loanManager.borrow(book1, user);
        assertFalse(result);
    }

@Test
void testBorrow_WhenUserSilverMember() {
    user.getFineCalculator().setFineStrategy(new SilverFineStrategy());
    assertTrue(loanManager.borrow(book1, user));
}

    @Test
    void testBorrow_WhenUserGoldenMember() {
        user.getFineCalculator().setFineStrategy(new GoldFineStrategy());
        assertTrue(loanManager.borrow(book1, user));
    }

    @Test
    void testReturnMedia_MediaOrUserNull() {
        assertFalse(loanManager.returnMedia(null, user));
        assertFalse(loanManager.returnMedia(book1, null));
    }

    @Test
    void testReturnMedia_Success() {
        loanManager.borrow(book1, user);
        boolean returned = loanManager.returnMedia(book1, user);
        assertTrue(returned);
        assertFalse(book1.getIsBorrowed());
        assertTrue(loanManager.getLoanList().get(0).getReturned());
        assertEquals(0, user.getBorrowedMedia().size());
    }

    @Test
    void testReturnMedia_Fails_WhenNotBorrowed() {
        // No loan exists for this user & media
        boolean returned = loanManager.returnMedia(book1, user);
        assertFalse(returned);
    }
    @Test
    void testReturnMedia_Fails_WhenMediaDoesNotMatchLoan() {
        Book book2 = new Book("Book Two", "Author B", "0987654321");
        loanManager.borrow(book2, user);

        boolean returned = loanManager.returnMedia(book1, user);
        assertFalse(returned);
    }

    @Test
    void testReturnMedia_Fails_WhenAlreadyReturned() {
        loanManager.borrow(book1, user);
        Loan loan = loanManager.getLoanList().get(0);
        loan.setReturned(true); // already returned

        boolean returned = loanManager.returnMedia(book1, user);
        assertFalse(returned);
    }

    @Test
    void testReturnMedia_Fails_WhenLoanBelongsToAnotherUser() {
        User other = new User(2, "other", "pass", "other@test.com");
        loanManager.borrow(book1, other);

        // current user did not borrow it
        boolean returned = loanManager.returnMedia(book1, user);
        assertFalse(returned);
    }

    @Test
    void testGetOverdueLoans_WithOverdue() {
        loanManager.borrow(book1, user);
        Loan loan = loanManager.getLoanList().get(0);
        loan.setDueDate(LocalDate.now().minusDays(3)); // overdue

        List<Loan> overdue = loanManager.getOverdueLoans();
        assertEquals(1, overdue.size());
        assertEquals(book1, overdue.get(0).getMedia());
    }

    @Test
    void testGetOverdueLoans_NoOverdue() {
        loanManager.borrow(book1, user);
        Loan loan = loanManager.getLoanList().get(0);
        loan.setDueDate(LocalDate.now().plusDays(5)); // not overdue

        List<Loan> overdue = loanManager.getOverdueLoans();
        assertTrue(overdue.isEmpty());
    }

    @Test
    void testGetOverdueLoans() {
        // Borrow book1 and set due date in past
        loanManager.borrow(book1, user);
        Loan loan = loanManager.getLoanList().get(0);
        loan.setDueDate(LocalDate.now().minusDays(5)); // overdue
        List<Loan> overdue = loanManager.getOverdueLoans();
        assertEquals(1, overdue.size());
        assertEquals(book1, overdue.get(0).getMedia());
    }

    @Test
    void testAccrueFinesForLoan_daysToAccrueZeroOrNegative() {
        Loan loan = new Loan(book1, user);
        loan.setDueDate(LocalDate.now().minusDays(1)); // overdue
        loan.setLastAccruedDate(LocalDate.now()); // lastAccrued بعد اليوم (daysToAccrue <= 0)
        loanManager.getLoanList().add(loan);

        loanManager.accrueFinesForLoan(loan, book1.getDailyFineRate(), LocalDate.now());

        assertEquals(0.0, user.getFineBalance(), 0.001);

        assertEquals(LocalDate.now(), loan.getLastAccruedDate());
    }


    @Test
    void testGenerateOverdueReport() {
        loanManager.borrow(book1, user);
        Loan loan = loanManager.getLoanList().get(0);
        loan.setDueDate(LocalDate.now().minusDays(2)); // overdue
        String report = loanManager.generateOverdueReport(user);
        assertTrue(report.contains("Book One"));
        assertTrue(report.contains("overdue 2 days"));
    }


    @Test
    void testAccrueFines_advancesLastAccruedAndAddsFine() {
        // Create a loan whose due date was some days ago and lastAccruedDate is dueDate
        Loan loan = new Loan(book1, user);
        loan.setDueDate(LocalDate.now().minusDays(5));
        loan.setLastAccruedDate(loan.getDueDate()); // accrual starts from due date
        loanManager.getLoanList().add(loan);

        double baseRatePerDay = book1.getDailyFineRate(); // assume e.g. 10
        // call the generic accrue method
        loanManager.accrueFines(baseRatePerDay, loan);

        // We expect  (today - dueDate) days of fines applied
        long days = java.time.temporal.ChronoUnit.DAYS.between(loan.getDueDate(), LocalDate.now());
        double expectedBase = days * baseRatePerDay;
        double expectedFinal = user.getFineCalculator().applyStrategy(expectedBase);

        assertEquals(expectedFinal, user.getFineBalance(), 0.001);
        // lastAccruedDate should be advanced to today
        assertEquals(LocalDate.now(), loan.getLastAccruedDate());
    }

    @Test
    void testAccrueFines_NoOverdue() {
        Loan loan = new Loan(book1, user);
        loan.setDueDate(LocalDate.now());
        loan.setLastAccruedDate(loan.getDueDate());
        loanManager.getLoanList().add(loan);
        double baseRatePerDay = book1.getDailyFineRate(); // مثال: 10
        loanManager.accrueFines(baseRatePerDay, loan);
        long days = java.time.temporal.ChronoUnit.DAYS.between(loan.getDueDate(), LocalDate.now());
        double expectedBase = days * baseRatePerDay;
        double expectedFinal = user.getFineCalculator().applyStrategy(expectedBase);
        assertEquals(expectedFinal, user.getFineBalance(), 0.001);
        assertEquals(LocalDate.now(), loan.getLastAccruedDate());
    }



    @Test
    void testAccrueFinesForLoan_withUptoBeforeOrEqualDue_doesNothing() {
        Loan loan = new Loan(book1, user);
        LocalDate due = LocalDate.now().plusDays(2); // not yet due
        loan.setDueDate(due);
        loan.setLastAccruedDate(due);
        loanManager.getLoanList().add(loan);

        loanManager.accrueFinesForLoan(loan, book1.getDailyFineRate(), LocalDate.now()); // uptoDate before due
        assertEquals(0.0, user.getFineBalance(), 0.001);
        // lastAccruedDate unchanged
        assertEquals(due, loan.getLastAccruedDate());
    }


    @Test
    void testAccrueFinesForLoan_whenLoanAlreadyReturned_doesNothing() {
        Loan loan = new Loan(book1, user);
        loan.setDueDate(LocalDate.now().minusDays(4));
        loan.setLastAccruedDate(loan.getDueDate());
        loan.setReturned(true); // already returned
        loanManager.getLoanList().add(loan);

        loanManager.accrueFinesForLoan(loan, book1.getDailyFineRate(), LocalDate.now());
        assertEquals(0.0, user.getFineBalance(), 0.001);
    }

    @Test
    void testAccrueFinesForLoan_LoanOverdue() {
        Loan overdue = new Loan(book1, user);
        overdue.setDueDate(LocalDate.now().minusDays(3));
        overdue.setLastAccruedDate(overdue.getDueDate());
        loanManager.getLoanList().add(overdue);

        loanManager.accrueFinesForLoan(overdue, book1.getDailyFineRate(), LocalDate.now());
        assertEquals(30.0, user.getFineBalance());
        assertEquals(LocalDate.now(), overdue.getLastAccruedDate());
    }

    @Test
    void testGenerateOverdueReport_NoLoans() {
        // لا يوجد أي قروض → should return "No overdue items."
        loanManager.getLoanList().clear();
        String report = loanManager.generateOverdueReport(user);
        assertTrue(report.contains("No overdue items."));
    }

    @Test
    void testGenerateOverdueReport_OverdueLoan_Unreturned() {
        // قرض متأخر ولم يرجع → يجب أن يدخل الفرع
        Loan overdueLoan = new Loan(book1, user);
        overdueLoan.setDueDate(LocalDate.now().minusDays(5));
        overdueLoan.setReturned(false);
        loanManager.getLoanList().add(overdueLoan);

        String report = loanManager.generateOverdueReport(user);
        assertTrue(report.contains(book1.getTitle()));
        assertTrue(report.contains("overdue 5 days"));
    }

    @Test
    void testGenerateOverdueReport_OverdueLoan_AlreadyReturned() {
        Loan returnedLoan = new Loan(book2, user);
        returnedLoan.setDueDate(LocalDate.now().minusDays(3));
        returnedLoan.setReturned(true);
        loanManager.getLoanList().add(returnedLoan);

        String report = loanManager.generateOverdueReport(user);
        assertFalse(report.contains(book2.getTitle()));
    }

    @Test
    void testGenerateOverdueReport_LoanForAnotherUser() {
        User other = new User(99, "other", "pass", "other@test.com");
        Loan otherLoan = new Loan(book1, other);
        otherLoan.setDueDate(LocalDate.now().minusDays(4));
        otherLoan.setReturned(false);
        loanManager.getLoanList().add(otherLoan);

        String report = loanManager.generateOverdueReport(user);
        assertFalse(report.contains(book1.getTitle()));
    }

    @Test
    void testGenerateOverdueReport_NotOverdueLoan() {
        Loan notOverdueLoan = new Loan(book2, user);
        notOverdueLoan.setDueDate(LocalDate.now().plusDays(2));
        notOverdueLoan.setReturned(false);
        loanManager.getLoanList().add(notOverdueLoan);

        String report = loanManager.generateOverdueReport(user);
        assertFalse(report.contains(book2.getTitle()));
    }


}
