/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.Guest;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author jyuba
 */
public class GuestDAO extends BaseDAO{

    public GuestDAO(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    public void createTable() {
        String sql
                = "CREATE TABLE IF NOT EXISTS guest ("
                + "id INT(11) NOT NULL AUTO_INCREMENT,"
                + "bookingId INT(11) NOT NULL,"
                + "name varchar(50) NOT NULL,"
                + "email varchar(50) NOT NULL,"
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY (bookingId) REFERENCES booking(id)"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean addRecord(int bookingId, String name, String email) {
        boolean isSuccess = false;
        if (!isExistedGuest(bookingId, name, email)) {
            ArrayList<Object> params = new ArrayList<>();
            params.add(bookingId);
            params.add(name);
            params.add(email);
            String sql = "INSERT INTO guest (bookingId, name, email) VALUES (?,?,?)";
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        };
        return isSuccess;
    }

    public boolean isExistedGuest(int bookingId, String name, String email) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        params.add(name);
        params.add(email);
        String sql = "SELECT * FROM guest WHERE bookingId=? and name=? and email=?";
        boolean isExisted = false;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        if (!ls.isEmpty()) {
            isExisted = true;
        }
        return isExisted;
    }

    public Guest queryRecordById(int id) {
        String sql = "SELECT * FROM guest WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        Guest g = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            g = new Guest();
            g.setId((int) m.get("id"));
            g.setBookingId((int) m.get("bookingId"));
            g.setName((String) m.get("name"));
            g.setEmail((String) m.get("email"));
        }
        return g;
    }

    public ArrayList<Guest> queryRecordByBookingId(int bookingId) {
        Guest g = null;
        String sql = "SELECT * FROM guest WHERE bookingId=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<Guest> gs = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            g = new Guest();
            g.setId((int) m.get("id"));
            g.setBookingId((int) m.get("bookingId"));
            g.setName((String) m.get("name"));
            g.setEmail((String) m.get("email"));
            gs.add(g);
        }
        return gs;
    }

    public boolean delRecord(int id) {
        String sql = "DELETE FROM guest WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public ArrayList<Guest> queryRecord() {
        Guest g = null;
        String sql = "SELECT * FROM guest";
        ArrayList<Object> params = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<Guest> gs = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            g = new Guest();
            g.setId((int) m.get("id"));
            g.setBookingId((int) m.get("bookingId"));
            g.setName((String) m.get("name"));
            g.setEmail((String) m.get("email"));
            gs.add(g);
        }
        return gs;
    }

    public boolean editRecord(Guest g) {
        String sql = "UPDATE guest "
                + "SET bookingId = ?, name = ?, email = ? "
                + "WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(g.getBookingId());
        params.add(g.getName());
        params.add(g.getEmail());
        params.add(g.getId());
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public void dropTable() {
        String sql = "DROP TABLE guest";
        dbUtil.executeByPreparedStatement(sql);
    }
}
