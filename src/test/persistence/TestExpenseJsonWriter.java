package persistence;

import model.ExpenseTracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Work Cited: Structure of the tests from JsonSerializationDemo
public class TestExpenseJsonWriter extends ExpenseJsonTest {

    ExpenseTracker expenseTracker;
    JsonWriterExpense writer;
    JsonReaderExpense reader;

    @Test
    void testWriterInvalidFile() {
        try {
            expenseTracker = new ExpenseTracker();
            writer = new JsonWriterExpense("/data/NAfile.json");
            writer.open();
            fail("An IOException should have been thrown.");
        } catch (IOException e) {

        }
    }

    @Test
    void testWriterEmptyExpense() {
        try {
            expenseTracker = new ExpenseTracker();
            writer = new JsonWriterExpense("./data/testExpenseWriterEmpty.json");
            writer.open();
            writer.write(expenseTracker);
            writer.close();

            reader = new JsonReaderExpense("./data/testExpenseWriterEmpty.json");
            expenseTracker = reader.read();
            assertEquals(0, expenseTracker.getTransactions().size());
        } catch (IOException e) {
            fail("IOException should have been thrown.");
        }
    }

    @Test
    void testWriterManyEntriesExpense() {
        try {
            expenseTracker = new ExpenseTracker();
            expenseTracker.addTransaction("apple", 86.01, "2024-11-01", "grocery");
            expenseTracker.addTransaction("pills", 54.31, "2023-01-31", "pharmacy");
            expenseTracker.addTransaction("cap", 5.05, "2023-01-12", "clothes");
            writer = new JsonWriterExpense("./data/testExpenseWriterManyEntries.json");
            writer.open();
            writer.write(expenseTracker);
            writer.close();

            reader = new JsonReaderExpense("./data/testExpenseWriterManyEntries.json");
            expenseTracker = reader.read();
            assertEquals(3, expenseTracker.getTransactions().size());
            assertEquals("apple", expenseTracker.getTransactions().get(0).getDescription());
            assertEquals(86.01, expenseTracker.getTransactions().get(0).getAmount());
            assertEquals("2024-11-01", expenseTracker.getTransactions().get(0).getDate());
            assertEquals("grocery", expenseTracker.getTransactions().get(0).getCategory());

            assertEquals("pills", expenseTracker.getTransactions().get(1).getDescription());
            assertEquals(54.31, expenseTracker.getTransactions().get(1).getAmount());
            assertEquals("2023-01-31", expenseTracker.getTransactions().get(1).getDate());
            assertEquals("pharmacy", expenseTracker.getTransactions().get(1).getCategory());

            assertEquals("cap", expenseTracker.getTransactions().get(2).getDescription());
            assertEquals(5.05, expenseTracker.getTransactions().get(2).getAmount());
            assertEquals("2023-01-12", expenseTracker.getTransactions().get(2).getDate());
            assertEquals("clothes", expenseTracker.getTransactions().get(2).getCategory());
        } catch (IOException e) {
            fail("IOException should have been thrown.");
        }
    }

    //{"expenses": [
    //    {
    //        "date": "2024-11-01",
    //            "amount": 86.01,
    //            "description": "apple",
    //            "category": "grocery"
    //    },
    //    {
    //        "date": "2023-01-31",
    //            "amount": 54.31,
    //            "description": "pills",
    //            "category": "pharmacy"
    //    },
    //    {
    //        "date": "2023-01-12",
    //            "amount": 5.05,
    //            "description": "cap",
    //            "category": "clothes"
    //    }
}
