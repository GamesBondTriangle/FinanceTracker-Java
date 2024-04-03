package persistence;

import model.IncomeTracker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads Income from JSON data stored in the file
// Work Cited: Structure of constructor, read, and readFile from JsonSerializationDemo
public class JsonReaderIncome {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderIncome(String source) {
        this.source = source;
    }

    // EFFECTS: reads income from file and returns it;
    // throws IOException if an error occurs reading data from file
    public IncomeTracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseIncome(jsonObject);
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
    // EFFECTS: parses income from JSON object and returns it
    private IncomeTracker parseIncome(JSONObject jsonObject) {
        IncomeTracker incomeTracker = new IncomeTracker();
        addIncomes(incomeTracker, jsonObject);
        return incomeTracker;

    }

    // MODIFIES: incomeTracker
    // EFFECTS: parses income (multiple) from JSON object and adds them to ExpenseTracker
    private void addIncomes(IncomeTracker incomeTracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("income");
        for (Object json : jsonArray) {
            JSONObject nextIncome = (JSONObject) json;
            addIncome(incomeTracker, nextIncome);
        }
    }


    // MODIFIES: incomeTracker
    // EFFECTS: parses income from JSON object and adds it to IncomeTracker
    private void addIncome(IncomeTracker incomeTracker, JSONObject jsonObject) {
        String description = jsonObject.getString("description");
        double amount = jsonObject.getDouble("amount");
        String date = jsonObject.getString("date");
        String category = jsonObject.getString("category");
        incomeTracker.addTransaction(description, amount, date, category);
    }
}
