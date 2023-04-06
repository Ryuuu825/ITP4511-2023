/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.Timeslot;
import ict.bean.Venue;
import ict.util.DbUtil;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author jyuba
 */
public class VenueDAO {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private DbUtil dbUtil;

    public VenueDAO(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbUtil = new DbUtil(dbUrl, dbUser, dbPassword);
    }

    public void createTable() {
        String sql
                = "CREATE TABLE IF NOT EXISTS venue ("
                + "id INT(11) NOT NULL AUTO_INCREMENT,"
                + "name varchar(50) NOT NULL,"
                + "address varchar(255) NOT NULL,"
                + "capacity INT(11) NOT NULL,"
                + "type INT(2) NOT NULL,"
                + "img varchar(255) NOT NULL,"
                + "PRIMARY KEY (id)"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean addRecord(String name, String address, int capacity, int type, String img) {
        boolean isSuccess = false;
        if (queryByName(name) == null) {
            ArrayList<Object> params = new ArrayList<>();
            params.add(name);
            params.add(address);
            params.add(capacity);
            params.add(type);
            params.add(img);
            String sql = "INSERT INTO venue (name, address, capacity) VALUES (?,?,?,?,?)";
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        };
        return isSuccess;
    }

    public Venue queryById(int id) {
        String sql = "SELECT * FROM venue WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        Venue v = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            v = new Venue();
            v.setId((int) m.get("id"));
            v.setName((String) m.get("name"));
            v.setAddress((String) m.get("address"));
            v.setCapacity((int) m.get("capacity"));
            v.setType((int) m.get("type"));
            v.setImg((String) m.get("img"));
        }
        return v;
    }

    public Venue queryByName(String name) {
        String sql = "SELECT * FROM venue WHERE name=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(name);
        Venue v = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            v = new Venue();
            v.setId((int) m.get("id"));
            v.setName((String) m.get("name"));
            v.setAddress((String) m.get("address"));
            v.setCapacity((int) m.get("capacity"));
            v.setType((int) m.get("type"));
            v.setImg((String) m.get("img"));
        }
        return v;
    }

    public boolean delRecord(int id) {
        String sql = "DELETE FROM venue WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        boolean isSuccess = false;

        //delete releted timeslot records
        TimeslotDAO tsDB = new TimeslotDAO(dbUrl, dbUser, dbPassword);
        ArrayList<Timeslot> tss = tsDB.queryRecordByVenueId(id);
        for (Timeslot ts : tss) {
            isSuccess = tsDB.delRecord(ts.getId());
        }

        //delete releted user records
        if (isSuccess == true) {
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        }
        return isSuccess;
    }

    public boolean editRecord(Venue v) {
        String sql = "UPDATE venue "
                + "SET name = ?, address = ?, capacity = ?, type = ?, img = ?"
                + "WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(v.getName());
        params.add(v.getAddress());
        params.add(v.getCapacity());
        params.add(v.getType());
        params.add(v.getImg());
        params.add(v.getId());
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public void dropTable() {
        String sql = "DROP TABLE venue";
        dbUtil.executeByPreparedStatement(sql);
    }
}
