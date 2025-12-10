import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FineStrategyTest {

    @Test
    void testRegularFineStrategy() {
        FineStrategy strategy = new RegularFineStrategy();
        double result = strategy.calculateFine(100);
        assertEquals(100, result); // no discount
    }

    @Test
    void testSilverFineStrategy() {
        FineStrategy strategy = new SilverFineStrategy();
        double result = strategy.calculateFine(100);
        assertEquals(90, result); // 10% discount
    }

    @Test
    void testGoldFineStrategy() {
        FineStrategy strategy = new GoldFineStrategy();
        double result = strategy.calculateFine(100);
        assertEquals(80, result); // 20% discount
    }



}