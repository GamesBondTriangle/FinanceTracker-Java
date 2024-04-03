package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents income tracker as a collection of income
public class IncomeTracker implements Writable {

    private List<IncomeAccount> incomeTransactions;
    private EventLog eventLog;

    //EFFECTS: Creates IncomeTracker that manages all the income included in the application
    public IncomeTracker() {
        incomeTransactions = new ArrayList<>();
        eventLog = EventLog.getInstance();
    }

    //REQUIRES: Amount >= 0, date is in correct format
    //MODIFIES: this
    //EFFECTS: Adds a new transaction entry to the list and saves the action to the eventLog
    public void addTransaction(String description, double amount, String date, String category) {
        IncomeAccount newTransaction = new IncomeAccount(description, amount, date, category);
        incomeTransactions.add(newTransaction);
        eventLog.logEvent(new Event("Income added: " + description
                + " $" + amount + " " + date + " " + category));
    }

    //REQUIRES: startDate < endDate
    //EFFECTS: returns all the transactions within the specified timeframe
    public List<IncomeAccount> viewTransaction(String startDate, String endDate) {
        List<IncomeAccount> byDateTransaction = new ArrayList<>();
        for (IncomeAccount transaction : incomeTransactions) {
            if (transaction.getDate().compareTo(startDate) >= 0 && transaction.getDate().compareTo(endDate) <= 0) {
                byDateTransaction.add(transaction);
            }
        }
        return byDateTransaction;
    }


    //REQUIRES: index < list.size()
    //MODIFIES: this
    //EFFECTS: removes the entry from the transaction list given the index and saves the action to the event log
    public void deleteTransaction(int index) {
        eventLog.logEvent(new Event("Income deleted: " + getAccountTransaction(index).getDescription()
                + " $" + getAccountTransaction(index).getAmount() + " " + getAccountTransaction(index).getDate()
                + " " + getAccountTransaction(index).getCategory()));
        incomeTransactions.remove(index);
    }

    //REQUIRES: index < list.size()
    //MODIFIES: this
    //EFFECTS: changes the category of the transaction provided the index
    public void recategorizeTransaction(int index, String category) {
        incomeTransactions.get(index).updateCategory(category);
    }

    //REQUIRES: index < list.size()
    //MODIFIES: this
    //EFFECTS: changes the amount of the transaction provided the index
    public void updateAmountTransaction(int index, double amount) {
        incomeTransactions.get(index).updateAmount(amount);
    }

    //REQUIRES: index < list.size()
    //MODIFIES: this
    //EFFECTS: changes the description of the transaction provided the index
    public void updatedDescriptionTransaction(int index, String description) {
        incomeTransactions.get(index).updateDescription(description);
    }

    //REQUIRES: index < list.size()
    //MODIFIES: this
    //EFFECTS: changes the date of the transaction provided the index
    public void updatedDateTransaction(int index, String date) {
        incomeTransactions.get(index).updateDate(date);
    }

    //EFFECTS: returns the number of transactions in the transactions
    public int getNumberTransactions() {
        return incomeTransactions.size();
    }

    //EFFECTS: returns true if the transactions is empty, false otherwise
    public boolean isEmpty() {
        return incomeTransactions.isEmpty();
    }

    //REQUIRES: index < list.size()
    //EFFECTS: returns the transaction at a given index
    public IncomeAccount getAccountTransaction(int index) {
        return incomeTransactions.get(index);
    }

    //EFFECTS: returns all the income transactions
    public List<IncomeAccount> getTransactions() {
        return incomeTransactions;
    }

    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("income", incomeToJson());
        return json;
    }

    // EFFECTS: returns income in this IncomeTracker as a JSON array
    private JSONArray incomeToJson() {
        JSONArray jsonArray = new JSONArray();
        for (IncomeAccount incomeAccount : getTransactions()) {
            jsonArray.put(incomeAccount.toJson());
        }
        return jsonArray;
    }


}
