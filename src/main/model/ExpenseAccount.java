package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an expense account with description, amount, date, category
public class ExpenseAccount implements Writable {
    private String description;
    private double amount;
    private String date; // YYYY-MM-DD
    private String category;

    //EFFECTS: Creates the account transaction with description, amount, date and category
    public ExpenseAccount(String description, double amount, String date, String category) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    //EFFECTS: returns the description of an entry
    public String getDescription() {
        return description;
    }

    //EFFECTS: returns the amount of an entry
    public double getAmount() {
        return amount;
    }

    //EFFECTS: returns the date of an entry
    public String getDate() {
        return date;
    }

    //EFFECTS: returns the category of an entry
    public String getCategory() {
        return category;
    }

    //MODIFIES: this
    //EFFECTS: updates the description of an entry
    public void updateDescription(String updatedDescription) {
        this.description = updatedDescription;
    }

    //MODIFIES: this
    //EFFECTS: updates the amount of an entry
    public void updateAmount(double updatedAmount) {
        this.amount = updatedAmount;
    }

    //MODIFIES: this
    //EFFECTS: updates the date of an entry
    public void updateDate(String updatedDate) {
        this.date = updatedDate;
    }

    //MODIFIES: this
    //EFFECTS: updates the category of an entry
    public void updateCategory(String updatedCategory) {
        this.category = updatedCategory;
    }

    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("description", this.description);
        json.put("amount", this.amount);
        json.put("date", this.date);
        json.put("category", this.category);
        return json;
    }

}
