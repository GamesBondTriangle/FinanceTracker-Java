package persistence;

import model.IncomeAccount;
import model.IncomeTracker;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncomeJsonTest {
    protected void checkIncome(List<IncomeAccount> incomeTransactions, IncomeTracker incomeTracker) {
        assertEquals(incomeTransactions, incomeTracker.getTransactions());
    }
}
