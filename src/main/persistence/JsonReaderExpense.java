package persistence;

import model.ExpenseTracker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Represents a reader that reads Expenses from JSON data stored in the file
// Work Cited: Structure of constructor, read, and readFile from JsonSerializationDemo
public class JsonReaderExpense {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderExpense(String source) {
        this.source = source;
    }

    // EFFECTS: reads expenses from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ExpenseTracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseExpense(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: this
    // EFFECTS: parses expenses from JSON object and returns it
    private ExpenseTracker parseExpense(JSONObject jsonObject) {
        ExpenseTracker expenseTracker = new ExpenseTracker();
        addExpenses(expenseTracker, jsonObject);
        return expenseTracker;

    }

    // MODIFIES: expenseTracker
    // EFFECTS: parses expenses from JSON object and adds them to ExpenseTracker
    private void addExpenses(ExpenseTracker expenseTracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("expenses");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(expenseTracker, nextExpense);
        }
    }


    // MODIFIES: expenseTracker
    // EFFECTS: parses expense from JSON object and adds it to ExpenseTracker
    private void addExpense(ExpenseTracker expenseTracker, JSONObject jsonObject) {
        String description = jsonObject.getString("description");
        double amount = jsonObject.getDouble("amount");
        String date = jsonObject.getString("date");
        String category = jsonObject.getString("category");
        expenseTracker.addTransaction(description, amount, date, category);
    }
}
