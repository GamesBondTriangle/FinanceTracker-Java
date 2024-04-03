package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIncomeAccount {

    IncomeAccount ia3;
    IncomeAccount ia4;

    @BeforeEach
    public void runBefore() {

        ia3 = new IncomeAccount("FortisBC", 1203.02, "2023-12-31", "salary");
        ia4 = new IncomeAccount("CIBC", 0.03, "2024-01-01", "interest");
    }


    @Test
    public void testConstructor() {

        assertEquals(ia3.getDescription(), "FortisBC");
        assertEquals(ia3.getAmount(), 1203.02);
        assertEquals(ia3.getDate(), "2023-12-31");
        assertEquals(ia3.getCategory(), "salary");
    }

    @Test
    public void testUpdateDescription() {

        assertEquals(ia3.getDescription(), "FortisBC");
        ia3.updateDescription("Tesla");
        assertEquals(ia3.getDescription(), "Tesla");
        ia3.updateDescription("PwC");
        assertEquals(ia3.getDescription(), "PwC");

        assertEquals(ia4.getDescription(), "CIBC");
        ia4.updateDescription("Scotia");
        assertEquals(ia4.getDescription(), "Scotia");
        ia4.updateDescription("RBC");
        assertEquals(ia4.getDescription(), "RBC");
    }

    @Test
    public void testUpdateAmount() {

        assertEquals(ia3.getAmount(), 1203.02);
        ia3.updateAmount(1000.00);
        assertEquals(ia3.getAmount(), 1000.00);
        ia3.updateAmount(1345.90);
        assertEquals(ia3.getAmount(), 1345.90);

        assertEquals(ia4.getAmount(), 0.03);
        ia4.updateAmount(1.03);
        assertEquals(ia4.getAmount(), 1.03);
        ia4.updateAmount(10.01);
        assertEquals(ia4.getAmount(), 10.01);
    }

    @Test
    public void testUpdateDate() {

        assertEquals(ia3.getDate(), "2023-12-31");
        ia3.updateDate("2023-11-30");
        assertEquals(ia3.getDate(), "2023-11-30");
        ia3.updateDate("2024-01-31");
        assertEquals(ia3.getDate(), "2024-01-31");

        assertEquals(ia4.getDate(), "2024-01-01");
        ia4.updateDate("2023-01-01");
        assertEquals(ia4.getDate(), "2023-01-01");
        ia4.updateDate("2023-11-01");
        assertEquals(ia4.getDate(), "2023-11-01");
    }

    @Test
    public void testUpdateCategory() {

        assertEquals(ia3.getCategory(), "salary");
        ia3.updateCategory("paycheque");
        assertEquals(ia3.getCategory(), "paycheque");
        ia3.updateCategory("work income");
        assertEquals(ia3.getCategory(), "work income");

        assertEquals(ia4.getCategory(), "interest");
        ia4.updateCategory("GIC");
        assertEquals(ia4.getCategory(), "GIC");
        ia4.updateCategory("bank");
        assertEquals(ia4.getCategory(), "bank");
    }

    @Test
    public void testToJson() {
        JSONObject json = ia3.toJson();

        assertEquals("FortisBC", json.getString("description"));
        assertEquals(1203.02, json.getDouble("amount"));
        assertEquals("2023-12-31", json.getString("date"));
        assertEquals("salary", json.getString("category"));
    }

}
