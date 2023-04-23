package ict.util;

import java.util.ArrayList;


public class JsonArray extends JsonResponse {


    private ArrayList<Object> items = new ArrayList<>();


    public JsonArray(String name) {
        this.name = name;
    }

    public void add(Object value) {
        items.add(value.toString());
    }

    public void addJsonObject(JsonObject obj) {
        items.add(obj.toValueString());
    }

    public String toValueString() {
        String str = "[";
        int i = 0;
        for (Object item : items) {
            if (i > 0) {
                str += ",";
            }
            // str += "\"" + key + "\":\"" + items.get(key) + "\"";
            // if the string has : or { or [ or , or " or \ or space
            if (item.toString().startsWith("{") || item.toString().startsWith("[")  ) {
                try {
                    str += ((JsonResponse) item).toValueString();
                } catch (Exception e) {
                    str += item.toString();
                }
            } else {
                str += "\"" + item.toString() + "\"";
            }
            i++;
        }
        str += "]";
        return str;
    }

    public String toString() {
        String str = "[";
        int i = 0;
        for (Object item : items) {
            if (i > 0) {
                str += ",";
            }
            // str += "\"" + key + "\":\"" + items.get(key) + "\"";
            // if the value is a json object
            if (item.toString().startsWith("{")) {
                str += item.toString();
            } else {
                str += "\"" + item.toString() + "\"";
            }
            i++;
        }
        str += "]";
        return str;

    }

   
}
