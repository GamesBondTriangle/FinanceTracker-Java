package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIncomeTracker {

    private IncomeTracker ti1;

    @BeforeEach
    public void runBefore() {
        ti1 = new IncomeTracker();
    }

    @Test
    public void testConstructor() {
        assertEquals(ti1.getNumberTransactions(), 0);
        assertTrue(ti1.isEmpty());

    }

    @Test
    public void testAddTransaction() {

        ti1.addTransaction("FortisBC", 1203.02, "2023-12-31", "salary");
        assertEquals(ti1.getNumberTransactions(), 1);
        assertFalse(ti1.isEmpty());
        ti1.addTransaction("CIBC", 0.03, "2024-01-01", "interest");
        assertEquals(ti1.getNumberTransactions(), 2);
        assertFalse(ti1.isEmpty());
        assertEquals(ti1.getAccountTransaction(0).getDescription(), "FortisBC");
        assertEquals(ti1.getAccountTransaction(1).getDescription(), "CIBC");
        ti1.addTransaction("FortisBC", 1203.02, "2023-12-31", "salary");
        assertEquals(ti1.getNumberTransactions(), 3);
        assertFalse(ti1.isEmpty());
        assertEquals(ti1.getAccountTransaction(0).getAmount(), 1203.02);
        assertEquals(ti1.getAccountTransaction(1).getAmount(), 0.03);
        assertEquals(ti1.getAccountTransaction(2).getAmount(), 1203.02);
        assertEquals(ti1.getTransactions().size(), 3);

    }

    @Test
    public void testViewTransaction() {
        ti1.addTransaction("FortisBC", 1203.02, "2023-12-31", "salary");
        ti1.addTransaction("CIBC", 0.03, "2024-01-01", "interest");
        ti1.addTransaction("FortisBC", 1203.02, "2023-11-30", "salary");
        ti1.addTransaction("Scotia", 0.03, "2024-02-01", "interest");

        List<IncomeAccount> tRange1 = ti1.viewTransaction("2023-11-29", "2024-02-02");
        assertEquals(tRange1.size(), 4);

        List<IncomeAccount> tRange2 = ti1.viewTransaction("2023-11-30", "2024-02-01");
        assertEquals(tRange2.size(), 4);

        List<IncomeAccount> tRange3 = ti1.viewTransaction("2023-12-01", "2024-02-01");
        assertEquals(tRange3.size(), 3);

        List<IncomeAccount> tRange4 = ti1.viewTransaction("2023-11-30", "2024-01-31");
        assertEquals(tRange4.size(), 3);

        List<IncomeAccount> tRange5 = ti1.viewTransaction("2023-12-01", "2024-01-31");
        assertEquals(tRange5.size(), 2);

    }

    @Test
    public void testDeleteTransaction() {

        ti1.addTransaction("FortisBC", 1203.02, "2023-12-31", "salary");
        ti1.deleteTransaction(0);
        assertTrue(ti1.isEmpty());

        ti1.addTransaction("FortisBC", 1203.02, "2023-12-31", "salary");
        ti1.addTransaction("CIBC", 0.03, "2024-01-01", "interest");
        ti1.deleteTransaction(0);
        assertEquals(ti1.getAccountTransaction(0).getAmount(), 0.03);
        assertEquals(ti1.getAccountTransaction(0).getDescription(), "CIBC");
        assertEquals(ti1.getNumberTransactions() , 1);

        ti1.addTransaction("FortisBC", 1203.02, "2023-12-31", "salary");
        ti1.addTransaction("FortisBC", 1203.02, "2023-11-31", "salary");
        assertEquals(ti1.getNumberTransactions() , 3);
        ti1.deleteTransaction(2);
        assertEquals(ti1.getNumberTransactions() , 2);
        ti1.deleteTransaction(0);
        assertEquals(ti1.getNumberTransactions() , 1);
        assertEquals(ti1.getAccountTransaction(0).getAmount(), 1203.02);
        assertEquals(ti1.getAccountTransaction(0).getDate(), "2023-12-31");

    }

    @Test
    public void testRecategorizeTransaction() {
        ti1.addTransaction("FortisBC", 1203.02, "2023-12-31", "salary");
        ti1.recategorizeTransaction(0, "cash");
        assertEquals(ti1.getAccountTransaction(0).getCategory(), "cash");
        ti1.recategorizeTransaction(0, "work");
        assertEquals(ti1.getAccountTransaction(0).getCategory(), "work");


        ti1.addTransaction("CIBC", 0.03, "2024-01-01", "interest");
        assertEquals(ti1.getAccountTransaction(1).getCategory(), "interest");
        ti1.recategorizeTransaction(1, "bank");
        assertEquals(ti1.getAccountTransaction(1).getCategory(), "bank");
    }

    @Test
    public void testUpdateAmountTransaction() {
        ti1.addTransaction("FortisBC", 1203.02, "2023-12-31", "salary");
        ti1.updateAmountTransaction(0, 1200.00);
        assertEquals(ti1.getAccountTransaction(0).getAmount(), 1200.00);
        ti1.updateAmountTransaction(0, 1400.01);
        assertEquals(ti1.getAccountTransaction(0).getAmount(), 1400.01);


        ti1.addTransaction("CIBC", 0.03, "2024-01-01", "interest");
        assertEquals(ti1.getAccountTransaction(1).getAmount(), 0.03);
        ti1.updateAmountTransaction(1, 10.54);
        assertEquals(ti1.getAccountTransaction(1).getAmount(), 10.54);
    }

    @Test
    public void testUpdatedDescriptionTransaction() {
        ti1.addTransaction("FortisBC", 1203.02, "2023-12-31", "salary");
        ti1.updatedDescriptionTransaction(0, "AWS");
        assertEquals(ti1.getAccountTransaction(0).getDescription(), "AWS");
        ti1.updatedDescriptionTransaction(0, "work");
        assertEquals(ti1.getAccountTransaction(0).getDescription(), "work");


        ti1.addTransaction("CIBC", 0.03, "2024-01-01", "interest");
        assertEquals(ti1.getAccountTransaction(1).getDescription(), "CIBC");
        ti1.updatedDescriptionTransaction(1, "Scotia");
        assertEquals(ti1.getAccountTransaction(1).getDescription(), "Scotia");
    }

    @Test
    public void testUpdatedDateTransaction() {
        ti1.addTransaction("FortisBC", 1203.02, "2023-12-31", "salary");
        ti1.updatedDateTransaction(0, "2023-11-30");
        assertEquals(ti1.getAccountTransaction(0).getDate(), "2023-11-30");
        ti1.updatedDateTransaction(0, "2023-11-29");
        assertEquals(ti1.getAccountTransaction(0).getDate(), "2023-11-29");


        ti1.addTransaction("CIBC", 0.03, "2024-01-01", "interest");
        assertEquals(ti1.getAccountTransaction(1).getDate(), "2024-01-01");
        ti1.updatedDateTransaction(1, "2024-02-01");
        assertEquals(ti1.getAccountTransaction(1).getDate(), "2024-02-01");
    }

    @Test
    public void testToJsonIncomeTrackerEmpty() {
        JSONObject json = ti1.toJson();

        assertTrue(json.has("income"));
        JSONArray incomeArray = json.getJSONArray("income");
        assertTrue(incomeArray.isEmpty());
    }

    @Test
    public void testToJsonIncomeTracker() {

        ti1.addTransaction("jeans", 672.09, "2023-01-01", "clothes");
        ti1.addTransaction("banana", 90.65, "2023-02-01", "groceries");

        JSONObject json = ti1.toJson();
        assertTrue(json.has("income"));
        JSONArray incomeArray = json.getJSONArray("income");

        assertEquals(2, incomeArray.length());

        assertEquals("jeans", incomeArray.getJSONObject(0).getString("description"));
        assertEquals(672.09, incomeArray.getJSONObject(0).getDouble("amount"));
        assertEquals("2023-01-01", incomeArray.getJSONObject(0).getString("date"));
        assertEquals("clothes", incomeArray.getJSONObject(0).getString("category"));

        assertEquals("banana", incomeArray.getJSONObject(1).getString("description"));
        assertEquals(90.65, incomeArray.getJSONObject(1).getDouble("amount"));
        assertEquals("2023-02-01", incomeArray.getJSONObject(1).getString("date"));
        assertEquals("groceries", incomeArray.getJSONObject(1).getString("category"));
    }

}
