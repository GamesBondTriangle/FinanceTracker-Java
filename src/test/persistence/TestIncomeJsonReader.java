package persistence;


import model.IncomeTracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Work Cited: Structure of the tests from JsonSerializationDemo
public class TestIncomeJsonReader extends IncomeJsonTest {

    IncomeTracker incomeTracker;

    @Test
    void testReaderNonExistentFile() {
        JsonReaderIncome reader = new JsonReaderIncome("./data/NAfile.json");
        try {
            incomeTracker = reader.read();
            fail("An IOException should have been thrown.");
        } catch (IOException e) {

        }
    }

    @Test
    void testReaderEmptyIncome() {
        JsonReaderIncome reader = new JsonReaderIncome("./data/testIncomeReaderEmpty.json");
        try {
            incomeTracker = reader.read();
            assertEquals(0, incomeTracker.getTransactions().size());
        } catch (IOException e) {
            fail("Could not read from file.");
        }
    }

    @Test
    void testReaderDuplicateIncome() {

        JsonReaderIncome reader = new JsonReaderIncome("./data/testIncomeReaderDuplicate.json");
        try {
            incomeTracker = reader.read();
            assertEquals(2, incomeTracker.getTransactions().size());
        } catch (IOException e){
            fail("Could not read from file.");
        }
    }

    @Test
    void testReaderManyEntriesExpense() {

        JsonReaderIncome reader = new JsonReaderIncome("./data/testIncomeReaderManyEntries.json");
        try {
            incomeTracker = reader.read();
            assertEquals(3, incomeTracker.getTransactions().size());
            assertEquals("2023-02-01", incomeTracker.getTransactions().get(0).getDate());
            assertEquals(54.01, incomeTracker.getTransactions().get(0).getAmount());
            assertEquals("CIBC", incomeTracker.getTransactions().get(0).getDescription());
            assertEquals("interest", incomeTracker.getTransactions().get(0).getCategory());

            assertEquals("2023-01-15", incomeTracker.getTransactions().get(1).getDate());
            assertEquals(1345.01, incomeTracker.getTransactions().get(1).getAmount());
            assertEquals("FortisBC", incomeTracker.getTransactions().get(1).getDescription());
            assertEquals("salary", incomeTracker.getTransactions().get(1).getCategory());

            assertEquals("2021-01-01", incomeTracker.getTransactions().get(2).getDate());
            assertEquals(54.01, incomeTracker.getTransactions().get(2).getAmount());
            assertEquals("CIBC", incomeTracker.getTransactions().get(2).getDescription());
            assertEquals("interest", incomeTracker.getTransactions().get(2).getCategory());
        } catch (IOException e){
            fail("Could not read from file.");
        }
    }
}

