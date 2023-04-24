package ict.util;

import java.util.HashMap;

public class JsonObject extends JsonResponse {


    private HashMap<String, Object> items = new HashMap<>();
    private boolean onlyOneObj = false;
    private boolean rValueOnly = false;


    public JsonObject(String name) {
        this.name = name;
    }

    public JsonObject(String name  , boolean rValueOnly) {
        this.name = name;
        this.rValueOnly = rValueOnly;
    }


    public JsonObject(String name , String value , boolean onlyOneObj) {
        this.name = name;
        items.put(name, value);
        this.onlyOneObj = onlyOneObj;
    }


    public void add(String name, Object value) {
        items.put(name, value.toString());
    }

    public void add(JsonArray array) {
        items.put(array.name, array.toValueString());
    }


    public void add(JsonObject obj) {
        items.put(obj.name, obj.toValueString());
    }

    public Object get(String name) {
        return items.get(name);
    }

    public String toString() {
        if (onlyOneObj) {
            String str = "\"" + name + "\":" + items.get(name).toString();
            return str;
        }


        String str = "\"" + name + "\":{";

        if (rValueOnly) {
            str = "{";
        }
        int i = 0;
        for (String key : items.keySet()) {
            if (i > 0) {
                str += ",";
            }

            // str += "\"" + key + "\":\"" + items.get(key) + "\"";
            // if the value is a json object
            if (items.get(key).toString().startsWith("{") || items.get(key).toString().startsWith("[")) {
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
            if (items.get(key).toString().startsWith("{") || items.get(key).toString().startsWith("[")) {
                str += "\"" + key + "\":" + items.get(key);
            } else {
                str += "\"" + key + "\":\"" + items.get(key) + "\"";
            }
            i++;
        }
        str += "}";
        return str;
    }
}
