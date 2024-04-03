package model;


import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExpenseTracker {

    private ExpenseTracker te1;

    @BeforeEach
    public void runBefore() {
        te1 = new ExpenseTracker();
    }

    @Test
    public void testConstructor() {
        assertEquals(te1.getNumberTransactions(), 0);
        assertTrue(te1.isEmpty());

    }

    @Test
    public void testAddTransaction() {

        te1.addTransaction("T&T", 178.00, "2023-11-15", "groceries");
        assertEquals(te1.getNumberTransactions(), 1);
        assertFalse(te1.isEmpty());
        te1.addTransaction("Aritzia", 86.50, "2024-01-04", "clothes");
        assertEquals(te1.getNumberTransactions(), 2);
        assertFalse(te1.isEmpty());
        assertEquals(te1.getAccountTransaction(0).getDescription(), "T&T");
        assertEquals(te1.getAccountTransaction(1).getDescription(), "Aritzia");
        te1.addTransaction("T&T", 178.00, "2023-11-15", "groceries");
        assertEquals(te1.getNumberTransactions(), 3);
        assertFalse(te1.isEmpty());
        assertEquals(te1.getAccountTransaction(0).getAmount(), 178.00);
        assertEquals(te1.getAccountTransaction(1).getAmount(), 86.50);
        assertEquals(te1.getAccountTransaction(2).getAmount(), 178.00);
        assertEquals(te1.getTransactions().size(), 3);
    }

    @Test
    public void testViewTransaction() {
        te1.addTransaction("T&T", 178.00, "2023-11-17", "groceries");
        te1.addTransaction("Aritzia", 86.50, "2024-01-04", "clothes");
        te1.addTransaction("T&T", 178.00, "2023-11-15", "groceries");
        te1.addTransaction("Aritzia", 86.50, "2024-01-03", "clothes");

        List<ExpenseAccount> tRange1 = te1.viewTransaction("2023-11-14", "2024-01-05");
        assertEquals(tRange1.size(), 4);

        List<ExpenseAccount> tRange2 = te1.viewTransaction("2023-11-15", "2024-01-04");
        assertEquals(tRange2.size(), 4);

        List<ExpenseAccount> tRange3 = te1.viewTransaction("2023-11-16", "2024-01-04");
        assertEquals(tRange3.size(), 3);

        List<ExpenseAccount> tRange4 = te1.viewTransaction("2023-11-15", "2024-01-03");
        assertEquals(tRange4.size(), 3);

        List<ExpenseAccount> tRange5 = te1.viewTransaction("2023-11-16", "2024-01-03");
        assertEquals(tRange5.size(), 2);

        List<ExpenseAccount> tRange6 = te1.viewTransaction("2023-11-16", "2024-01-02");
        assertEquals(tRange6.size(), 1);

    }


    @Test
    public void testDeleteTransaction() {

        te1.addTransaction("T&T", 178.00, "2023-11-15", "groceries");
        te1.deleteTransaction(0);
        assertTrue(te1.isEmpty());

        te1.addTransaction("T&T", 178.00, "2023-11-15", "groceries");
        te1.addTransaction("Aritzia", 86.50, "2024-01-04", "clothes");
        te1.deleteTransaction(0);
        assertEquals(te1.getAccountTransaction(0).getAmount(), 86.50);
        assertEquals(te1.getAccountTransaction(0).getDescription(), "Aritzia");
        assertEquals(te1.getNumberTransactions() , 1);

        te1.addTransaction("T&T", 178.00, "2023-11-15", "groceries");
        te1.addTransaction("T&T", 178.00, "2023-11-15", "groceries");
        assertEquals(te1.getNumberTransactions() , 3);
        te1.deleteTransaction(2);
        assertEquals(te1.getNumberTransactions() , 2);
        te1.deleteTransaction(0);
        assertEquals(te1.getNumberTransactions() , 1);
        assertEquals(te1.getAccountTransaction(0).getAmount(), 178.00);
        assertEquals(te1.getAccountTransaction(0).getDate(), "2023-11-15");

    }

    @Test
    public void testRecategorizeTransaction() {
        te1.addTransaction("T&T", 178.00, "2023-11-15", "groceries");
        te1.recategorizeTransaction(0, "food");
        assertEquals(te1.getAccountTransaction(0).getCategory(), "food");
        te1.recategorizeTransaction(0, "milk");
        assertEquals(te1.getAccountTransaction(0).getCategory(), "milk");


        te1.addTransaction("Aritzia", 86.50, "2024-01-04", "clothes");
        assertEquals(te1.getAccountTransaction(1).getCategory(), "clothes");
        te1.recategorizeTransaction(1, "top");
        assertEquals(te1.getAccountTransaction(1).getCategory(), "top");
    }

    @Test
    public void testUpdateAmountTransaction() {
        te1.addTransaction("T&T", 178.00, "2023-11-15", "groceries");
        te1.updateAmountTransaction(0, 1200.00);
        assertEquals(te1.getAccountTransaction(0).getAmount(), 1200.00);
        te1.updateAmountTransaction(0, 0.01);
        assertEquals(te1.getAccountTransaction(0).getAmount(), 0.01);


        te1.addTransaction("Aritzia", 86.50, "2024-01-04", "clothes");
        assertEquals(te1.getAccountTransaction(1).getAmount(), 86.50);
        te1.updateAmountTransaction(1, 11.54);
        assertEquals(te1.getAccountTransaction(1).getAmount(), 11.54);
    }

    @Test
    public void testUpdatedDescriptionTransaction() {
        te1.addTransaction("T&T", 178.00, "2023-11-15", "groceries");
        te1.updatedDescriptionTransaction(0, "Safeway");
        assertEquals(te1.getAccountTransaction(0).getDescription(), "Safeway");
        te1.updatedDescriptionTransaction(0, "store");
        assertEquals(te1.getAccountTransaction(0).getDescription(), "store");


        te1.addTransaction("Aritzia", 86.50, "2024-01-04", "clothes");
        assertEquals(te1.getAccountTransaction(1).getDescription(), "Aritzia");
        te1.updatedDescriptionTransaction(1, "Lulu");
        assertEquals(te1.getAccountTransaction(1).getDescription(), "Lulu");
    }

    @Test
    public void testUpdatedDateTransaction() {
        te1.addTransaction("T&T", 178.00, "2023-11-15", "groceries");
        te1.updatedDateTransaction(0, "2023-11-30");
        assertEquals(te1.getAccountTransaction(0).getDate(), "2023-11-30");
        te1.updatedDateTransaction(0, "2023-11-29");
        assertEquals(te1.getAccountTransaction(0).getDate(), "2023-11-29");


        te1.addTransaction("Aritzia", 86.50, "2024-01-04", "clothes");
        assertEquals(te1.getAccountTransaction(1).getDate(), "2024-01-04");
        te1.updatedDateTransaction(1, "2024-02-01");
        assertEquals(te1.getAccountTransaction(1).getDate(), "2024-02-01");
    }

    @Test
    public void testToJsonIncomeTrackerEmpty() {
        JSONObject json = te1.toJson();

        assertTrue(json.has("expenses"));
        JSONArray incomeArray = json.getJSONArray("expenses");
        assertTrue(incomeArray.isEmpty());
    }

    @Test
    public void testToJsonIncomeTracker() {

        te1.addTransaction("CIBC", 9.98, "2020-01-01", "interest");
        te1.addTransaction("FortisBC", 1290.65, "2024-02-01", "salary");

        JSONObject json = te1.toJson();
        assertTrue(json.has("expenses"));
        JSONArray incomeArray = json.getJSONArray("expenses");

        assertEquals(2, incomeArray.length());

        assertEquals("CIBC", incomeArray.getJSONObject(0).getString("description"));
        assertEquals(9.98, incomeArray.getJSONObject(0).getDouble("amount"));
        assertEquals("2020-01-01", incomeArray.getJSONObject(0).getString("date"));
        assertEquals("interest", incomeArray.getJSONObject(0).getString("category"));

        assertEquals("FortisBC", incomeArray.getJSONObject(1).getString("description"));
        assertEquals(1290.65, incomeArray.getJSONObject(1).getDouble("amount"));
        assertEquals("2024-02-01", incomeArray.getJSONObject(1).getString("date"));
        assertEquals("salary", incomeArray.getJSONObject(1).getString("category"));
    }

    @Test
    public void testAddTransactionsFromFile() {
        te1.addTransaction("CIBC", 9.98, "2020-01-01", "interest");
        te1.addTransaction("FortisBC", 1290.65, "2024-02-01", "salary");

        List <ExpenseAccount> lea = new ArrayList<>();
        ExpenseAccount ea1 = new ExpenseAccount("Aritzia", 86.50,
                "2024-01-04", "clothes");
        ExpenseAccount ea2 = new ExpenseAccount("T&T", 178.00,
                "2023-11-15", "groceries");
        lea.add(ea1);
        lea.add(ea2);
        assertEquals(te1.getTransactions().size(), 2);
        te1.addTransactionsFromFile(lea);
        assertEquals(te1.getTransactions().size(), 4);




    }



}
