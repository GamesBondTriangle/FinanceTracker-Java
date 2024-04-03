package persistence;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as JSON object
    // Work Cited: Structure of interface from JsonSerializationDemo
    JSONObject toJson();
}
