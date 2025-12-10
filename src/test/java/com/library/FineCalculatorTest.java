package com.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class FineCalculatorTest {

    private FineCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new FineCalculator(new RegularFineStrategy());
    }
    @Test
    void testGetAndSetFineStrategy() {
        FineCalculator calc = new FineCalculator(new RegularFineStrategy());

        // initial strategy
        assertTrue(calc.getFineStrategy() instanceof RegularFineStrategy);

        // change strategy
        calc.setFineStrategy(new SilverFineStrategy());
        assertTrue(calc.getFineStrategy() instanceof SilverFineStrategy);

        // change again
        calc.setFineStrategy(new GoldFineStrategy());
        assertTrue(calc.getFineStrategy() instanceof GoldFineStrategy);
    }


    @Test
    void testApplyStrategy_NullStrategyDefaultsToRegular() {
        calculator.setFineStrategy(null);
        double result = calculator.applyStrategy(50);
        assertEquals(50, result);
        assertNotNull(calculator.getFineStrategy());
    }
    @Test
    void testApplyStrategy_Regular() {
        assertEquals(100, calculator.applyStrategy(100));
    }
    @Test
    void testApplyStrategy_Silver() {
        calculator.setFineStrategy(new SilverFineStrategy());
        assertEquals(90, calculator.applyStrategy(100));
    }
    @Test
    void testApplyStrategy_Gold() {
        calculator.setFineStrategy(new GoldFineStrategy());
        assertEquals(80, calculator.applyStrategy(100));
    }
    /*
    @Test
    void testEstimateOngoingFine_EmptyList() {
        List<Loan> emptyList = new ArrayList<>();

        assertEquals(0.0, calculator.estimateOngoingFine(emptyList, 10));
    }
    @Test
    void testEstimateOngoingFine_LoanNotOverdue() {
        User user = new User(1, "A", "x", "a@test.com");
        Media media = new Book("B", "Auth", "1234567892");
        Loan loan = new Loan(media, user);


        loan.setDueDate(LocalDate.now().plusDays(1));
        List<Loan> loans = List.of(loan);

        assertEquals(0, calculator.estimateOngoingFine(loans, 10));
    }
    @Test
    void testEstimateOngoingFine_MultipleLoansWithSilverStrategy() {
        calculator.setFineStrategy(new SilverFineStrategy());
        User user = new User(3, "A", "x", "a@test.com");
        Media media = new Book("B", "Auth", "1234567890");

        Loan l1 = new Loan(media, user);
        Loan l2 = new Loan(media, user);

        l1.setDueDate(LocalDate.now().minusDays(1)); //
        l2.setDueDate(LocalDate.now().minusDays(3)); //

        List<Loan> loans = List.of(l1, l2);

        // Base fines: 1*10 + 3*10 = 40
        // Silver = 10% discount → 40 * 0.9 = 36
        assertEquals(36, calculator.estimateOngoingFine(loans, 10));
    }*/
  /*  @Test
    void testCalculateFine_LoanNotReturned() {
        User user = new User(4, "A", "x", "a@test.com");
        Media media = new Book("B", "Auth", "1234567895");
        Loan loan = new Loan(media, user);

        loan.setReturned(false);
        loan.setReturnDate(null);

        assertEquals(0, calculator.calculateFine(loan, 10));
    }
*/

}