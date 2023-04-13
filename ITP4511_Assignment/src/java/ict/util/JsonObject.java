package ict.util;

import java.util.HashMap;

public class JsonObject {


    private String name;
    private HashMap<String, Object> items = new HashMap<>();


    public JsonObject(String name) {
        this.name = name;
    }

    public void add(String name, Object value) {
        items.put(name, value.toString());
    }

    public void add(JsonObject obj) {
        items.put(obj.name, obj.toValueString());
    }

    public String toString() {
        String str = "\"" + name + "\":{";
        int i = 0;
        for (String key : items.keySet()) {
            if (i > 0) {
                str += ",";
            }
            // str += "\"" + key + "\":\"" + items.get(key) + "\"";
            // if the value is a json object
            if (items.get(key).toString().startsWith("{")) {
                str += "\"" + key + "\":" + items.get(key);
            } else {
                str += "\"" + key + "\":\"" + items.get(key) + "\"";
            }
            i++;
        }
        str += "}";
        return str;
    }

    public String toValueString() {
        String str = "{";
        int i = 0;
        for (String key : items.keySet()) {
            if (i > 0) {
                str += ",";
            }
            str += "\"" + key + "\":\"" + items.get(key) + "\"";
            i++;
        }
        str += "}";
        return str;
    }
}
