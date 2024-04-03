package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTimeframeNetGain {

    private TimeframeNetGain t1;
    private IncomeTracker it1;
    private ExpenseTracker et1;


    @BeforeEach
    public void runBefore() {
        t1 = new TimeframeNetGain();
        it1 = new IncomeTracker();
        et1 = new ExpenseTracker();
        et1.addTransaction("TnT", 135.43,"2023-01-05", "groceries");
        et1.addTransaction("Tna", 99.01,"2023-01-04", "clothes");
        et1.addTransaction("Apt 123", 1300.00,"2023-01-01", "housing");
        et1.addTransaction("TnT", 4.56,"2023-01-09", "groceries");
        it1.addTransaction("Telsa", 1500.09,"2023-01-15", "salary");
        it1.addTransaction("CIBC", 1.09,"2023-01-01", "savings");
        it1.addTransaction("CIBC", 15.09,"2023-01-01", "GIC");
        it1.addTransaction("CIBC", 100.78,"2023-01-01", "dividend");
    }


    @Test
    public void testCalculateNetGain() {
        assertEquals(t1.calculateNetGain(it1, et1, "2023-01-01", "2023-01-15"),
                78.05);
        assertEquals(t1.calculateNetGain(it1, et1, "2023-01-02", "2023-01-04"),
                -99.01);
        assertEquals(t1.calculateNetGain(it1, et1, "2023-01-01", "2023-01-01"),
                -1183.04);
        assertEquals(t1.calculateNetGain(it1, et1, "2023-01-01", "2023-01-02"),
                -1183.04);
        assertEquals(t1.calculateNetGain(it1, et1, "2022-12-31", "2023-01-01"),
                -1183.04);
        assertEquals(t1.calculateNetGain(it1, et1, "2023-01-04", "2023-01-15"),
                1261.09);
        assertEquals(t1.calculateNetGain(it1, et1, "2023-01-05", "2023-01-15"),
                1360.10);
        assertEquals(t1.calculateNetGain(it1, et1, "2023-01-17", "2024-01-15"),
                0.00);
    }
}
