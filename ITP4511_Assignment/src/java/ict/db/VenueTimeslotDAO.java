package ict.db;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

import ict.bean.Timeslot;
import ict.bean.Venue;
import ict.bean.VenueTimeslot;
import java.sql.Date;

public class VenueTimeslotDAO extends BaseDAO {

    public VenueTimeslotDAO(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS venue_timeslot ("
                + "id INT(11) NOT NULL AUTO_INCREMENT,"
                + "venueId INT(11) NOT NULL,"
                + "timeslotId INT(11) NOT NULL,"
                + "bookingId INT(11) DEFAULT NULL,"
                + "date DATE NOT NULL,"
                + "PRIMARY KEY (id, venueId, timeslotId),"
                + "FOREIGN KEY (venueId) REFERENCES venue(id),"
                + "FOREIGN KEY (bookingId) REFERENCES booking(id),"
                + "FOREIGN KEY (timeslotId) REFERENCES timeslot(id)"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean addRecord(int venueId, int timeslotId, int bookingId, String date) {
        boolean isSuccess = false;
        LocalDate day = LocalDate.parse(date);
        String sql = "INSERT INTO venue_timeslot (venueId, timeslotId, date) VALUES (?,?,?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add(venueId);
        params.add(timeslotId);
        if (bookingId != 0) {
            sql = "INSERT INTO venue_timeslot (venueId, timeslotId, bookingId, date) VALUES (?,?,?,?)";
            params.add(bookingId);
        }
        params.add(day);
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean delRecordByVenueId(int venueId) {
        boolean isSuccess = false;
        ArrayList<Object> params = new ArrayList<>();
        params.add(venueId);
        String sql = "DELETE FROM venue_timeslot WHERE venueId=?";
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean delRecordByTimeslotId(int timeslotId) {
        boolean isSuccess = false;
        ArrayList<Object> params = new ArrayList<>();
        params.add(timeslotId);
        String sql = "DELETE FROM venue_timeslot WHERE timeslotId=?";
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean delRecord(int id) {
        boolean isSuccess = false;
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        String sql = "DELETE FROM venue_timeslot WHERE id=?";
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean editRecord(VenueTimeslot vt) {
        boolean isSuccess = false;
        ArrayList<Object> params = new ArrayList<>();
        String sql;
        if (vt.getBookingId() != 0) {
            sql = "UPDATE venue_timeslot SET bookingId = ?, date = ? WHERE venueId = ? and timeslotId = ?";
            params.add(vt.getBookingId());
        } else {
            sql = "UPDATE venue_timeslot SET bookingId = null date = ? WHERE venueId = ? and timeslotId = ?";
        }
        params.add(vt.getDate());
        params.add(vt.getVenueId());
        params.add(vt.getTimeslotId());
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public ArrayList<Timeslot> queryTimeslotByVenueId(int venueId) {
        String sql = "SELECT venue.name, venue_timeslot.date, timeslot.startTime, timeslot.endTime"
                + "FROM venue"
                + "JOIN venue_timeslot ON venue.id = venue_timeslot.venueId"
                + "JOIN timeslot ON venue_timeslot.timeslotId = timeslot.id"
                + "WHERE venue.id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(venueId);
        ArrayList<Timeslot> vts = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            Timeslot ts = new Timeslot();
            ts.setId((int) m.get("id"));
            ts.setStartTime((LocalTime) m.get("startTime"));
            ts.setEndTime((LocalTime) m.get("endTime"));
            vts.add(ts);
        }
        return vts;
    }

    public ArrayList<Venue> queryVenueByTimeslotId(int timeslot) {
        String sql = "SELECT venue.name, venue_timeslot.date, timeslot.startTime, timeslot.endTime"
                + "FROM venue"
                + "JOIN venue_timeslot ON venue.id = venue_timeslot.venueId"
                + "JOIN timeslot ON venue_timeslot.timeslotId = timeslot.id"
                + "WHERE timeslot.id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(timeslot);
        ArrayList<Venue> vts = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            Venue v = new Venue();
            v.setId((int) m.get("id"));
            v.setName((String) m.get("name"));
            v.setCapacity((int) m.get("capacity"));
            v.setAddress((String) m.get("address"));
            v.setDescription((String) m.get("description"));
            v.setHourlyRate((double) m.get("hourlyRate"));
            v.setImg((String) m.get("img"));
            v.setType((int) m.get("type"));
            v.setUserId((int) m.get("userId"));
            v.setLocation((String) m.get("location"));
            vts.add(v);
        }
        return vts;
    }

    public ArrayList<VenueTimeslot> queryRocordByTimeslotId(int timeslot) {
        String sql = "SELECT * FROM venue_timeslot WHERE timeslotId = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(timeslot);
        ArrayList<VenueTimeslot> vts = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            VenueTimeslot vt = new VenueTimeslot();
            vt.setBookingId((int) m.get("bookingId"));
            vt.setVenueId((int) m.get("venueId"));
            vt.setTimeslotId((int) m.get("timeslotId"));
            vt.setDate((LocalDate) m.get("date"));
            vts.add(vt);
        }
        return vts;
    }

    public ArrayList<VenueTimeslot> queryRocordByVenueId(int venueId) {
        String sql = "SELECT * FROM venue_timeslot WHERE venueId = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(venueId);
        ArrayList<VenueTimeslot> vts = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            VenueTimeslot vt = new VenueTimeslot();
            vt.setBookingId((int) m.get("bookingId"));
            vt.setVenueId((int) m.get("venueId"));
            vt.setTimeslotId((int) m.get("timeslotId"));
            vt.setDate((LocalDate) m.get("date"));
            vts.add(vt);
        }
        return vts;
    }

    public ArrayList<VenueTimeslot> queryRocordByBookingId(int bookingId) {
        String sql = "SELECT * FROM venue_timeslot WHERE bookingId = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        ArrayList<VenueTimeslot> vts = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            VenueTimeslot vt = new VenueTimeslot();
            vt.setBookingId((int) m.get("bookingId"));
            vt.setVenueId((int) m.get("venueId"));
            vt.setTimeslotId((int) m.get("timeslotId"));
            vt.setDate((LocalDate) m.get("date"));
            vts.add(vt);
        }
        return vts;
    }

    public ArrayList<VenueTimeslot> queryRocord() {
        String sql = "SELECT * FROM venue_timeslot";
        ArrayList<Object> params = new ArrayList<>();
        ArrayList<VenueTimeslot> vts = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            VenueTimeslot vt = new VenueTimeslot();
            vt.setBookingId((int) m.get("bookingId"));
            vt.setVenueId((int) m.get("venueId"));
            vt.setTimeslotId((int) m.get("timeslotId"));
            vt.setDate((LocalDate) m.get("date"));
            vts.add(vt);
        }
        return vts;
    }

    public LocalDate queryMaxDate() {
        String sql = "SELECT max(date) as max FROM venue_timeslot";
        ArrayList<Object> params = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        return ((Date)ls.get(0).get("max")).toLocalDate();
    }
}
