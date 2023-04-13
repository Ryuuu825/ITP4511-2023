package ict.util;

import java.util.ArrayList;

public class JsonUtil {

    private String jsonString;
    int itemsCount;

    public JsonUtil() {
        jsonString = "{";
        itemsCount = 0;
    }

    public JsonUtil addJsonObject(Object obj) {
    String name = obj.getClass().getName();
        String value = obj.toString();

        if (itemsCount > 0) {
            jsonString += ",";
        } 
        jsonString += "\"" + name + "\":\"" + value + "\"";

        itemsCount++;

        return this;
    }

    public JsonUtil addJsonObject(String name, Object obj) {
    String value = obj.toString();

        if (itemsCount > 0) {
            jsonString += ",";
        } 
        jsonString += "\"" + name + "\":\"" + value + "\"";

        itemsCount++;

        return this;
    }

    public JsonUtil addJsonArray(ArrayList<?> list) {
    String name = list.getClass().getName();

        if (itemsCount > 0) {
            jsonString += ",";
        }
        jsonString += "\"" + name + "\":[";
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                jsonString += ",";
            }
            jsonString += "\"" + list.get(i) + "\"";
        }
        jsonString += "]";
        itemsCount++;

        return this;
    }

    public JsonUtil addJsonArray(String name , ArrayList<?> list) {
    
        if (itemsCount > 0) {
            jsonString += ",";
        }
        jsonString += "\"" + name + "\":[";
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                jsonString += ",";
            }
            jsonString += "\"" + list.get(i) + "\"";
        }
        jsonString += "]";
        itemsCount++;

        return this;
    }


    public JsonUtil addJsonObject(JsonObject obj) {
    if (itemsCount > 0) {
            jsonString += ",";
        }
        jsonString += obj.toString();
        itemsCount++;

        return this;
    }


    public void clear() {
        jsonString = "{";
        itemsCount = 0;
    }

    public JsonUtil clone() {
        JsonUtil json = new JsonUtil();
        json.jsonString = jsonString;
        json.itemsCount = itemsCount;
        return json;
    }

    public String getJsonString() {
        return jsonString + "}";
    }
    
}
