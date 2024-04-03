package persistence;

import model.ExpenseTracker;
import org.json.JSONObject;
import java.io.*;

// Represents a writer that writes JSON representation of expense to file
// Work Cited: Structure of constructor, open, write and close from JsonSerializationDemo
public class JsonWriterExpense {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriterExpense(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundExceptions
    // if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of expense file
    public void write(ExpenseTracker expenseTracker) {
        JSONObject json = expenseTracker.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
