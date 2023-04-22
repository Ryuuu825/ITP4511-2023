package ict.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;


public class CsvUtil <T> {

    private ArrayList<T> list;
    private Class<T> c;


    public CsvUtil(Class<T> c) {
        list = new ArrayList<>();
        this.c = c;
    }

    public void add (T t) {
        list.add(t);
    }

    public void add(T[] arr) {
        for (T t : arr) {
            list.add(t);
        }
    }

    public void add(ArrayList<T> list) {
        this.list.addAll(list);
    }

    public String getHeader() {
        StringBuilder sb = new StringBuilder();

        for (Field f : c.getDeclaredFields()) {
            f.setAccessible(true);
            // if it is the last field, don't append a comma
            if (f.equals(c.getDeclaredFields()[c.getDeclaredFields().length - 1])) {
                sb.append(f.getName());
            } else {
                sb.append(f.getName()).append(",");
            }
        }

        return sb.toString();
        
    }

    public String getBody() {
        StringBuilder sb = new StringBuilder();

        for (T t : list) {
            for (Field f : t.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                try {
                    // if it is the last field, don't append a comma
                    if (f.equals(t.getClass().getDeclaredFields()[t.getClass().getDeclaredFields().length - 1])) {
                        sb.append( removeBadChars( f.get(t) ) );
                    } else {
                        sb.append( removeBadChars(f.get(t)) ).append(",");
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
            sb.append("\r\n");
        }

        return sb.toString();

    }

    public String getCSV() {
        return getHeader() + "\r\n" + getBody();
    }

    public String removeBadChars(String s) {
        s =  s.replaceAll("[^\\x00-\\x7F]", "");
        // remove , and " from string
        s = s.replaceAll(",", "");
        s = s.replaceAll("\"", "");
        return s;
    }

    public String removeBadChars(Object s) {
        return removeBadChars(s.toString());
    }


    public void setHeader(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    public void setDownloadable(HttpServletResponse resp, String filename) {
        resp.setHeader("Content-Disposition", "attachment; filename=" + filename);
    }
}