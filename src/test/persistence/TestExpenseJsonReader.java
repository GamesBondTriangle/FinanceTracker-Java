package persistence;


import model.ExpenseTracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Work Cited: Structure of the tests from JsonSerializationDemo
public class TestExpenseJsonReader extends ExpenseJsonTest {

    ExpenseTracker expenseTracker;

    @Test
    void testReaderNonExistentFile() {
        JsonReaderExpense reader = new JsonReaderExpense("./data/NAfile.json");
        try {
            expenseTracker = reader.read();
            fail("An IOException should have been thrown.");
        } catch (IOException e) {

        }
    }

    @Test
    void testReaderEmptyExpense() {
        JsonReaderExpense reader = new JsonReaderExpense("./data/testExpenseReaderEmpty.json");
        try {
            expenseTracker = reader.read();
            assertEquals(0, expenseTracker.getTransactions().size());
        } catch (IOException e) {
            fail("Could not read from file.");
        }
    }

    @Test
    void testReaderDuplicateExpense() {

        JsonReaderExpense reader = new JsonReaderExpense("./data/testExpenseReaderDuplicate.json");
        try {
            expenseTracker = reader.read();
            assertEquals(2, expenseTracker.getTransactions().size());
        } catch (IOException e){
            fail("Could not read from file.");
        }
    }

    @Test
    void testReaderManyEntriesExpense() {

        JsonReaderExpense reader = new JsonReaderExpense("./data/testExpenseReaderManyEntries.json");
        try {
            expenseTracker = reader.read();
            assertEquals(3, expenseTracker.getTransactions().size());
            assertEquals("2022-01-01", expenseTracker.getTransactions().get(0).getDate());
            assertEquals(30.01, expenseTracker.getTransactions().get(0).getAmount());
            assertEquals("apple", expenseTracker.getTransactions().get(0).getDescription());
            assertEquals("grocery", expenseTracker.getTransactions().get(0).getCategory());

            assertEquals("2023-01-01", expenseTracker.getTransactions().get(1).getDate());
            assertEquals(54.01, expenseTracker.getTransactions().get(1).getAmount());
            assertEquals("banana", expenseTracker.getTransactions().get(1).getDescription());
            assertEquals("grocery", expenseTracker.getTransactions().get(1).getCategory());

            assertEquals("2023-01-12", expenseTracker.getTransactions().get(2).getDate());
            assertEquals(5.01, expenseTracker.getTransactions().get(2).getAmount());
            assertEquals("shirt", expenseTracker.getTransactions().get(2).getDescription());
            assertEquals("clothes", expenseTracker.getTransactions().get(2).getCategory());
        } catch (IOException e){
            fail("Could not read from file.");
        }
    }
}

