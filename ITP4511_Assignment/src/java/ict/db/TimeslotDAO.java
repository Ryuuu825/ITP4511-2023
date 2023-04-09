/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.Timeslot;
import ict.bean.VenueTimeslot;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author jyuba
 */
public class TimeslotDAO extends BaseDAO {

    public TimeslotDAO(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    public void createTable() {
        String sql
                = "CREATE TABLE IF NOT EXISTS timeslot ("
                + "id INT(11) NOT NULL AUTO_INCREMENT,"
                + "startTime TIME NOT NULL,"
                + "endTime TIME NOT NULL,"
                + "PRIMARY KEY (id)"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean addRecord(String startTime, String endTime) {
        boolean isSuccess = false;
        LocalTime sTime = LocalTime.parse(startTime);
        LocalTime eTime = LocalTime.parse(endTime);
        ArrayList<Object> params = new ArrayList<>();
        params.add(sTime);
        params.add(eTime);
        String sql = "INSERT INTO timeslot (startTime, endTime) VALUES (?,?)";
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
            ts.setStartTime((LocalTime) m.get("startTime"));
            ts.setEndTime((LocalTime) m.get("endTime"));
        }
        return ts;
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
            ts.setStartTime((LocalTime) m.get("startTime"));
            ts.setEndTime((LocalTime) m.get("endTime"));
            tss.add(ts);
        }
        return tss;
    }

    public ArrayList<Timeslot> queryRecordByEndTime(String endTime) {
        Timeslot ts = null;
        LocalTime time = LocalTime.parse(endTime);
        String sql = "SELECT * FROM timeslot WHERE endTime <= ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(time);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<Timeslot> tss = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            ts = new Timeslot();
            ts.setId((int) m.get("id"));
            ts.setStartTime((LocalTime) m.get("startTime"));
            ts.setEndTime((LocalTime) m.get("endTime"));
            tss.add(ts);
        }
        return tss;
    }

    public ArrayList<Timeslot> queryRecordBetweenTime(String startTime, String endTime) {
        Timeslot ts = null;
        LocalTime stime = LocalTime.parse(startTime);
        LocalTime etime = LocalTime.parse(endTime);
        String sql = "SELECT * FROM timeslot WHERE startTime >= ? and endTime <= ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(stime);
        params.add(etime);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<Timeslot> tss = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            ts = new Timeslot();
            ts.setId((int) m.get("id"));
            ts.setStartTime((LocalTime) m.get("startTime"));
            ts.setEndTime((LocalTime) m.get("endTime"));
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
            ts.setStartTime((LocalTime) m.get("startTime"));
            ts.setEndTime((LocalTime) m.get("endTime"));
            tss.add(ts);
        }
        return tss;
    }

    public boolean delRecord(int id) {
        String sql = "DELETE FROM timeslot WHERE id=?";
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

        if (isSuccess) {
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        }
        return isSuccess;
    }

    public boolean editRecord(Timeslot ts) {
        String sql = "UPDATE timeslot "
                + "SET startTime = ?, endTime = ?"
                + "WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(ts.getStartTime());
        params.add(ts.getEndTime());
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
