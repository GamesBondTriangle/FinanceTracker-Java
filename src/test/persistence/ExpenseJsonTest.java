package persistence;

import model.ExpenseAccount;
import model.ExpenseTracker;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseJsonTest {
    protected void checkExpenses(List<ExpenseAccount> expenseTransactions, ExpenseTracker expenseTracker) {
        assertEquals(expenseTransactions, expenseTracker.getTransactions());
    }
}
