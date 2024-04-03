package persistence;

import model.IncomeTracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Work Cited: Structure of the tests from JsonSerializationDemo
public class TestIncomeJsonWriter extends IncomeJsonTest {

    IncomeTracker incomeTracker;
    JsonWriterIncome writer;
    JsonReaderIncome reader;

    @Test
    void testWriterInvalidFile() {
        try {
            incomeTracker = new IncomeTracker();
            writer = new JsonWriterIncome("/data/NAfile.json");
            writer.open();
            fail("An IOException should have been thrown.");
        } catch (IOException e) {

        }
    }

    @Test
    void testWriterEmptyExpense() {
        try {
            incomeTracker = new IncomeTracker();
            writer = new JsonWriterIncome("./data/testIncomeWriterEmpty.json");
            writer.open();
            writer.write(incomeTracker);
            writer.close();

            reader = new JsonReaderIncome("./data/testIncomeWriterEmpty.json");
            incomeTracker = reader.read();
            assertEquals(0, incomeTracker.getTransactions().size());
        } catch (IOException e) {
            fail("IOException should have been thrown.");
        }
    }

    @Test
    void testWriterManyEntriesExpense() {
        try {
            incomeTracker = new IncomeTracker();
            incomeTracker.addTransaction("apple", 86.01, "2024-11-01", "grocery");
            incomeTracker.addTransaction("pills", 54.31, "2023-01-31", "pharmacy");
            incomeTracker.addTransaction("cap", 5.05, "2023-01-12", "clothes");
            writer = new JsonWriterIncome("./data/testIncomeWriterManyEntries.json");
            writer.open();
            writer.write(incomeTracker);
            writer.close();

            reader = new JsonReaderIncome("./data/testIncomeWriterManyEntries.json");
            incomeTracker = reader.read();
            assertEquals(3, incomeTracker.getTransactions().size());
            assertEquals("apple", incomeTracker.getTransactions().get(0).getDescription());
            assertEquals(86.01, incomeTracker.getTransactions().get(0).getAmount());
            assertEquals("2024-11-01", incomeTracker.getTransactions().get(0).getDate());
            assertEquals("grocery", incomeTracker.getTransactions().get(0).getCategory());

            assertEquals("pills", incomeTracker.getTransactions().get(1).getDescription());
            assertEquals(54.31, incomeTracker.getTransactions().get(1).getAmount());
            assertEquals("2023-01-31", incomeTracker.getTransactions().get(1).getDate());
            assertEquals("pharmacy", incomeTracker.getTransactions().get(1).getCategory());

            assertEquals("cap", incomeTracker.getTransactions().get(2).getDescription());
            assertEquals(5.05, incomeTracker.getTransactions().get(2).getAmount());
            assertEquals("2023-01-12", incomeTracker.getTransactions().get(2).getDate());
            assertEquals("clothes", incomeTracker.getTransactions().get(2).getCategory());
        } catch (IOException e) {
            fail("IOException should have been thrown.");
        }
    }
}
