/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.Venue;
import ict.bean.VenueTimeslot;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author jyuba
 */
public class VenueDAO extends BaseDAO {

    public VenueDAO(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    public void createTable() {
        String sql
                = "CREATE TABLE IF NOT EXISTS venue ("
                + "id INT(11) NOT NULL AUTO_INCREMENT,"
                + "name varchar(50) NOT NULL,"
                + "location varchar(255) NOT NULL,"
                + "address varchar(255) NOT NULL,"
                + "capacity INT(11) NOT NULL,"
                + "type tinyint(2) NOT NULL,"
                + "img varchar(255) NOT NULL,"
                + "description varchar(255) NULL,"
                + "userId INT(11) NOT NULL,"
                + "hourlyRate DOUBLE NOT NULL,"
                + "FOREIGN KEY (userId) REFERENCES user(id),"
                + "PRIMARY KEY (id)"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean addRecord(String name, String location, String address, int capacity, int type, String img, String description, int userId, double hourlyRate) {
        boolean isSuccess = false;
        if (queryRecordByName(name) == null) {
            ArrayList<Object> params = new ArrayList<>();
            params.add(name);
            params.add(location);
            params.add(address);
            params.add(capacity);
            params.add(type);
            params.add(img);
            params.add(description);
            params.add(userId);
            params.add(hourlyRate);
            String sql = "INSERT INTO venue (name, location, address, capacity, type, img, description, userId, hourlyRate) VALUES (?,?,?,?,?,?,?,?,?)";
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        };
        return isSuccess;
    }

    public Venue queryRecordById(int id) {
        String sql = "SELECT * FROM venue WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        Venue v = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            v = new Venue();
            v.setId((int) m.get("id"));
            v.setName((String) m.get("name"));
            v.setLocation((String) m.get("location"));
            v.setAddress((String) m.get("address"));
            v.setCapacity((int) m.get("capacity"));
            v.setType((int) m.get("type"));
            v.setImg((String) m.get("img"));
            v.setDescription((String) m.get("description"));
            v.setUserId((int) m.get("userId"));
            v.setHourlyRate((double) m.get("hourlyRate"));
        }
        return v;
    }

    public Venue queryRecordByName(String name) {
        String sql = "SELECT * FROM venue WHERE name LIKE ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add("%" + name + "%");
        Venue v = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            v = new Venue();
            v.setId((int) m.get("id"));
            v.setName((String) m.get("name"));
            v.setLocation((String) m.get("location"));
            v.setAddress((String) m.get("address"));
            v.setCapacity((int) m.get("capacity"));
            v.setType((int) m.get("type"));
            v.setImg((String) m.get("img"));
            v.setDescription((String) m.get("description"));
            v.setUserId((int) m.get("userId"));
            v.setHourlyRate((double) m.get("hourlyRate"));
        }
        return v;
    }

    public ArrayList<Venue> queryRecordByUserId(int userId) {
        String sql = "SELECT * FROM venue WHERE userId = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(userId);
        Venue v = null;
        ArrayList<Venue> vs = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            v = new Venue();
            v.setId((int) m.get("id"));
            v.setName((String) m.get("name"));
            v.setLocation((String) m.get("location"));
            v.setAddress((String) m.get("address"));
            v.setCapacity((int) m.get("capacity"));
            v.setType((int) m.get("type"));
            v.setImg((String) m.get("img"));
            v.setDescription((String) m.get("description"));
            v.setUserId((int) m.get("userId"));
            v.setHourlyRate((double) m.get("hourlyRate"));
            vs.add(v);
        }
        return vs;
    }
    
    public boolean delRecord(int id) {
        String sql = "DELETE FROM venue WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        boolean isSuccess = false;

        //delete releted venue_timeslot records
        VenueTimeslotDAO vtsDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        ArrayList<VenueTimeslot> vts = vtsDB.queryRocordByVenueId(id);
        if (vts.size() != 0) {
            for (VenueTimeslot vt : vts) {
                isSuccess = vtsDB.delRecord(vt.getId());
            }
        }

        //delete releted user records
        if (isSuccess) {
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        }
        return isSuccess;
    }

    public boolean editRecord(Venue v) {
        String sql = "UPDATE venue "
                + "SET name = ?, location = ?, address = ?, capacity = ?, type = ?, img = ?, description = ?, userId = null, hourlyRate = ? "
                + "WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(v.getName());
        params.add(v.getLocation());
        params.add(v.getAddress());
        params.add(v.getCapacity());
        params.add(v.getType());
        params.add(v.getImg());
        params.add(v.getDescription());
        if (v.getUserId() != 0) {
            sql = "UPDATE venue "
                    + "SET name = ?, location = ?, address = ?, capacity = ?, type = ?, img = ?, description = ?, userId = ?, hourlyRate = ? "
                    + "WHERE id = ?";
            params.add(v.getUserId());
        }
        params.add(v.getHourlyRate());
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
