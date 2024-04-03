package model;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExpenseAccount {

    ExpenseAccount ea1;
    ExpenseAccount ea2;

    @BeforeEach
    public void runBefore() {
        ea1 = new ExpenseAccount("T&T", 178.00, "2023-11-15", "groceries");
        ea2 = new ExpenseAccount("Aritzia", 86.50, "2024-01-04", "clothes");

    }


    @Test
    public void testConstructor() {
        assertEquals(ea1.getDescription(), "T&T");
        assertEquals(ea1.getAmount(), 178.00);
        assertEquals(ea1.getDate(), "2023-11-15");
        assertEquals(ea1.getCategory(), "groceries");

    }

    @Test
    public void testUpdateDescription() {
        assertEquals(ea1.getDescription(), "T&T");
        ea1.updateDescription("Safeway");
        assertEquals(ea1.getDescription(), "Safeway");
        ea1.updateDescription("Superstore");
        assertEquals(ea1.getDescription(), "Superstore");

        assertEquals(ea2.getDescription(), "Aritzia");
        ea2.updateDescription("TNA");
        assertEquals(ea2.getDescription(), "TNA");
        ea2.updateDescription("Lulu");
        assertEquals(ea2.getDescription(), "Lulu");
    }

    @Test
    public void testUpdateAmount() {
        assertEquals(ea1.getAmount(), 178.00);
        ea1.updateAmount(123.98);
        assertEquals(ea1.getAmount(), 123.98);
        ea1.updateAmount(78.43);
        assertEquals(ea1.getAmount(), 78.43);

        assertEquals(ea2.getAmount(), 86.50);
        ea2.updateAmount(76.43);
        assertEquals(ea2.getAmount(), 76.43);
        ea2.updateAmount(0.00);
        assertEquals(ea2.getAmount(), 0.00);
    }

    @Test
    public void testUpdateDate() {
        assertEquals(ea1.getDate(), "2023-11-15");
        ea1.updateDate("2017-12-15");
        assertEquals(ea1.getDate(), "2017-12-15");
        ea1.updateDate("2017-12-25");
        assertEquals(ea1.getDate(), "2017-12-25");

        assertEquals(ea2.getDate(), "2024-01-04");
        ea2.updateDate("2024-01-03");
        assertEquals(ea2.getDate(), "2024-01-03");
        ea2.updateDate("2024-01-02");
        assertEquals(ea2.getDate(), "2024-01-02");
    }

    @Test
    public void testUpdateCategory() {
        assertEquals(ea1.getCategory(), "groceries");
        ea1.updateCategory("food");
        assertEquals(ea1.getCategory(), "food");
        ea1.updateCategory("beans");
        assertEquals(ea1.getCategory(), "beans");

        assertEquals(ea2.getCategory(), "clothes");
        ea2.updateCategory("t-shirt");
        assertEquals(ea2.getCategory(), "t-shirt");
        ea2.updateCategory("sleeveless t-shirt");
        assertEquals(ea2.getCategory(), "sleeveless t-shirt");

    }

    @Test
    public void testToJson() {
        JSONObject json = ea1.toJson();

        assertEquals("T&T", json.getString("description"));
        assertEquals(178.00, json.getDouble("amount"));
        assertEquals("2023-11-15", json.getString("date"));
        assertEquals("groceries", json.getString("category"));
    }

}
