import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FineCalculatorTest {
    static class DummyUser extends User {
        public DummyUser() { super(122, "dummy","email","pass"); }
    }
    static class DummyBook extends Book {
        public DummyBook() { super("dummy title", "dummy author",""); }
    }

    private Loan createLoan(LocalDate due, LocalDate returned, boolean isReturned) {
        Loan loan = new Loan(new DummyBook(), new DummyUser());
        loan.setDueDate(due);
        loan.setReturnDate(returned);
        loan.setReturned(isReturned);
        return loan;
    }

    @Test
    void testNoFineWhenNotReturnedYet() {
        Loan loan = createLoan(LocalDate.now(), null, false);
        FineCalculator calculator = new FineCalculator(new RegularFineStrategy());
        assertEquals(0, calculator.calculateFine(loan, 10));
    }

    @Test
    void testNoFineIfReturnedOnTime() {
        LocalDate today = LocalDate.now();
        Loan loan = createLoan(today, today, true);
        FineCalculator calculator = new FineCalculator(new RegularFineStrategy());
        assertEquals(0, calculator.calculateFine(loan, 10));
    }

    @Test
    void testRegularFineStrategy() {
        LocalDate due = LocalDate.now();
        LocalDate returned = due.plusDays(3); // 3 days late
        Loan loan = createLoan(due, returned, true);

        FineCalculator calculator = new FineCalculator(new RegularFineStrategy());
        assertEquals(3 * 10, calculator.calculateFine(loan, 10)); // no discount
    }

    @Test
    void testSilverFineStrategy() {
        LocalDate due = LocalDate.now();
        LocalDate returned = due.plusDays(2); // 2 days late
        Loan loan = createLoan(due, returned, true);

        FineCalculator calculator = new FineCalculator(new SilverFineStrategy());
        assertEquals(2 * 10 * 0.9, calculator.calculateFine(loan, 10)); // 10% off
    }

    @Test
    void testGoldFineStrategy() {
        LocalDate due = LocalDate.now();
        LocalDate returned = due.plusDays(5); // 5 days late
        Loan loan = createLoan(due, returned, true);

        FineCalculator calculator = new FineCalculator(new GoldFineStrategy());
        assertEquals(5 * 10 * 0.8, calculator.calculateFine(loan, 10)); // 20% off
    }

    @Test
    void testDefaultStrategyWhenNull() {
        LocalDate due = LocalDate.now();
        LocalDate returned = due.plusDays(1);
        Loan loan = createLoan(due, returned, true);

        FineCalculator calculator = new FineCalculator(null);
        assertEquals(10, calculator.calculateFine(loan, 10)); // defaults to regular
    }
}