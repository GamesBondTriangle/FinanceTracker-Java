package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents expense tracker as a collection of expenses
public class ExpenseTracker implements Writable {

    private List<ExpenseAccount> expenseTransactions;
    private EventLog eventLog;

    //EFFECTS: Creates ExpenseTracker that manages all the expense included in the application
    public ExpenseTracker() {
        expenseTransactions = new ArrayList<>();
        eventLog = EventLog.getInstance();
    }

    //REQUIRES: Amount >= 0, date is in correct format
    //MODIFIES: this
    //EFFECTS: Adds a new transaction entry to the list and saves transaction to the vent log
    public void addTransaction(String description, double amount, String date, String category) {
        ExpenseAccount newTransaction = new ExpenseAccount(description, amount, date, category);
        expenseTransactions.add(newTransaction);
        eventLog.logEvent(new Event("Expense added: " + description
                + " $" + amount + " " + date + " " + category));
    }

    //REQUIRES: startDate < endDate
    //EFFECTS: returns all the transactions within the specified timeframe
    public List<ExpenseAccount> viewTransaction(String startDate, String endDate) {
        List<ExpenseAccount> byDateTransaction = new ArrayList<>();
        for (ExpenseAccount transaction : expenseTransactions) {
            if (transaction.getDate().compareTo(startDate) >= 0 && transaction.getDate().compareTo(endDate) <= 0) {
                byDateTransaction.add(transaction);
            }
        }
        return byDateTransaction;
    }


    //REQUIRES: index < list.size()
    //MODIFIES: this
    //EFFECTS: removes the entry from the transaction list given the index and saves the action to the eventlog
    public void deleteTransaction(int index) {
        eventLog.logEvent(new Event("Expense deleted: " + getAccountTransaction(index).getDescription()
                + " $" + getAccountTransaction(index).getAmount() + " " + getAccountTransaction(index).getDate()
                + " " + getAccountTransaction(index).getCategory()));
        expenseTransactions.remove(index);
    }

    //REQUIRES: index < list.size()
    //MODIFIES: this
    //EFFECTS: changes the category of the transaction provided the index
    public void recategorizeTransaction(int index, String category) {
        expenseTransactions.get(index).updateCategory(category);
    }

    //REQUIRES: index < list.size()
    //MODIFIES: this
    //EFFECTS: changes the amount of the transaction provided the index
    public void updateAmountTransaction(int index, double amount) {
        expenseTransactions.get(index).updateAmount(amount);
    }

    //REQUIRES: index < list.size()
    //MODIFIES: this
    //EFFECTS: changes the description of the transaction provided the index
    public void updatedDescriptionTransaction(int index, String description) {
        expenseTransactions.get(index).updateDescription(description);
    }

    //REQUIRES: index < list.size()
    //MODIFIES: this
    //EFFECTS: changes the date of the transaction provided the index
    public void updatedDateTransaction(int index, String date) {
        expenseTransactions.get(index).updateDate(date);
    }

    //EFFECTS: returns the number of transactions in the transactions
    public int getNumberTransactions() {
        return expenseTransactions.size();
    }

    //EFFECTS: returns true if the transactions is empty, false otherwise
    public boolean isEmpty() {
        return expenseTransactions.isEmpty();
    }

    //REQUIRES: index < list.size()
    //EFFECTS: returns the transaction at a given index
    public ExpenseAccount getAccountTransaction(int index) {
        return expenseTransactions.get(index);
    }

    //EFFECTS: returns all the expense transactions
    public List<ExpenseAccount> getTransactions() {
        return expenseTransactions;
    }

    //MODIFIES: this
    //EFFECTS: adds list of transactions from files to the expense transactions
    public void addTransactionsFromFile(List<ExpenseAccount> fileTransactions) {
        expenseTransactions.addAll(fileTransactions);
    }


    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("expenses", expensesToJson());
        return json;
    }

    // EFFECTS: returns expense in this ExpenseTracker as a JSON array
    private JSONArray expensesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (ExpenseAccount expenseAccount : getTransactions()) {
            jsonArray.put(expenseAccount.toJson());
        }
        return jsonArray;
    }
}
