package tyss;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {

    LoanSerivice ls = new LoanSerivice();

    // 1.
    @Test
    void validEligibility() {
        assertTrue(ls.isEligible(30, 40000));
    }

    // 2.
    @Test
    void invalidAge() {
        assertFalse(ls.isEligible(18, 40000));
    }

    // 3.
    @Test
    void invalidSalary() {
        assertFalse(ls.isEligible(30, 20000));
    }

    // 4.
    @Test
    void validEmi() {
        assertEquals(500, ls.calculateEMI(12000, 2));
    }

    // 5.
    @Test
    void invalidLoanAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> ls.calculateEMI(0, 2));
    }

    // 6.
    @Test
    void invalidTenure() {
        assertThrows(IllegalArgumentException.class,
                () -> ls.calculateEMI(100000, 0));
    }

    // 7.
    @Test
    void premiumCategory() {
        assertEquals("Premium", ls.getLoanCategory(800));
    }

    // 8.
    @Test
    void standardCategory() {
        assertEquals("Standard", ls.getLoanCategory(650));
    }

    @Test
    void highRiskCategory() {
        assertEquals("High Risk", ls.getLoanCategory(500));
    }

    // 10.
    void groupedAssertion() {

        assertAll(
                () -> assertTrue(ls.isEligible(21, 25000)),
                () -> assertEquals("Premium", ls.getLoanCategory(750)),
                () -> assertNotNull(ls.getLoanCategory(700))
        );
    }
}
