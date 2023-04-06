/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.Timeslot;
import ict.util.DbUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author jyuba
 */
public class TimeslotDAO {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private DbUtil dbUtil;

    public TimeslotDAO(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbUtil = new DbUtil(dbUrl, dbUser, dbPassword);
    }

    public void createTable() {
        String sql
                = "CREATE TABLE IF NOT EXISTS timeslot ("
                + "id INT(11) NOT NULL AUTO_INCREMENT,"
                + "venueId INT(11) NOT NULL,"
                + "startTime TIME NOT NULL,"
                + "endTime TIME NOT NULL,"
                + "date DATE NOT NULL,"
                + "hourlyRate DOUBLE NOT NULL,"
                + "available BOOL NOT NULL,"
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY (venueId) REFERENCES venue(id)"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean addRecord(int venueId, String startTime, String endTime, String date, double hourlyRate) {
        boolean isSuccess = false;
        boolean available = true;
        LocalTime sTime = LocalTime.parse(startTime);
        LocalTime eTime = LocalTime.parse(endTime);
        LocalDate day = LocalDate.parse(date);
        ArrayList<Object> params = new ArrayList<>();
        params.add(venueId);
        params.add(sTime);
        params.add(eTime);
        params.add(day);
        params.add(hourlyRate);
        params.add(available);
        String sql = "INSERT INTO timeslot (venueId, startTime, endTime, date, hourlyRate, available) VALUES (?,?,?,?,?,?)";
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public Timeslot queryRecordById(int id) {
        Timeslot ts = null;
        String sql = "SELECT * FROM timeslot WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        if (!ls.isEmpty()) {
            Map<String, Object> m = ls.get(0);
            ts = new Timeslot();
            ts.setId((int) m.get("id"));
            ts.setVenueId((int) m.get("venueId"));
            ts.setDate((LocalDate) m.get("date"));
            ts.setStartTime((LocalTime) m.get("startTime"));
            ts.setEndTime((LocalTime) m.get("endTime"));
            ts.setHourlyRate((double) m.get("hourlyRate"));
            ts.setAvailable((boolean) m.get("available"));
        }
        return ts;
    }

    public ArrayList<Timeslot> queryRecordByVenueId(int venueId) {
        Timeslot ts = null;
        String sql = "SELECT * FROM timeslot WHERE venueId = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(venueId);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<Timeslot> tss = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            ts = new Timeslot();
            ts.setId((int) m.get("id"));
            ts.setVenueId((int) m.get("venueId"));
            ts.setDate((LocalDate) m.get("date"));
            ts.setStartTime((LocalTime) m.get("startTime"));
            ts.setEndTime((LocalTime) m.get("endTime"));
            ts.setHourlyRate((double) m.get("hourlyRate"));
            ts.setAvailable((boolean) m.get("available"));
            tss.add(ts);
        }
        return tss;
    }

    public ArrayList<Timeslot> queryRecordByDate(String date) {
        Timeslot ts = null;
        LocalDate day = LocalDate.parse(date);
        String sql = "SELECT * FROM timeslot WHERE date = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(day);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<Timeslot> tss = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            ts = new Timeslot();
            ts.setId((int) m.get("id"));
            ts.setVenueId((int) m.get("venueId"));
            ts.setDate((LocalDate) m.get("date"));
            ts.setStartTime((LocalTime) m.get("startTime"));
            ts.setEndTime((LocalTime) m.get("endTime"));
            ts.setHourlyRate((double) m.get("hourlyRate"));
            ts.setAvailable((boolean) m.get("available"));
            tss.add(ts);
        }
        return tss;
    }

    public ArrayList<Timeslot> queryRecordByStartTime(String starTime) {
        Timeslot ts = null;
        LocalTime time = LocalTime.parse(starTime);
        String sql = "SELECT * FROM timeslot WHERE startTime >= ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(time);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<Timeslot> tss = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            ts = new Timeslot();
            ts.setId((int) m.get("id"));
            ts.setVenueId((int) m.get("venueId"));
            ts.setDate((LocalDate) m.get("date"));
            ts.setStartTime((LocalTime) m.get("startTime"));
            ts.setEndTime((LocalTime) m.get("endTime"));
            ts.setHourlyRate((double) m.get("hourlyRate"));
            ts.setAvailable((boolean) m.get("available"));
            tss.add(ts);
        }
        return tss;
    }

    public ArrayList<Timeslot> queryRecord() {
        Timeslot ts = null;
        String sql = "SELECT * FROM timeslot";
        ArrayList<Object> params = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<Timeslot> tss = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            ts = new Timeslot();
            ts.setId((int) m.get("id"));
            ts.setVenueId((int) m.get("venueId"));
            ts.setDate((LocalDate) m.get("date"));
            ts.setStartTime((LocalTime) m.get("startTime"));
            ts.setEndTime((LocalTime) m.get("endTime"));
            ts.setHourlyRate((double) m.get("hourlyRate"));
            ts.setAvailable((boolean) m.get("available"));
            tss.add(ts);
        }
        return tss;
    }

    public boolean delRecord(int id) {
        String sql = "DELETE FROM timeslot WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean editRecord(Timeslot ts) {
        String sql = "UPDATE timeslot "
                + "SET startTime = ?, endTime = ?, date = ?, hourlyRate = ?, available = ?"
                + "WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(ts.getStartTime());
        params.add(ts.getEndTime());
        params.add(ts.getDate());
        params.add(ts.getHourlyRate());
        params.add(ts.getAvailable());
        params.add(ts.getId());
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public void dropTable() {
        String sql = "DROP TABLE timeslot";
        dbUtil.executeByPreparedStatement(sql);
    }
}
