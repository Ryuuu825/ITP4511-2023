package ict.db;

import ict.bean.Guest;
import ict.bean.GuestList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

import ict.bean.Timeslot;
import ict.bean.Venue;
import ict.bean.VenueTimeslot;
import ict.bean.view.DateTimeslots;
import ict.bean.view.VenueTimeslots;
import java.sql.Date;
import java.sql.Time;

public class GuestListDAO extends BaseDAO {

    public GuestListDAO(String dbUrl, String dbUser, String dbPassword) {
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

    public boolean editRecord(GuestList gl) {
        boolean isSuccess = false;
        ArrayList<Object> params = new ArrayList<>();
        String sql;
        if (gl.getBookingId() != 0) {
            sql = "UPDATE guestlist SET bookingId = ? WHERE id = ?";
            params.add(gl.getBookingId());
        } else {
            sql = "UPDATE guestlist SET bookingId = null WHERE id = ?";
        }
        params.add(gl.getId());
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
            vt.setDate(((Date) m.get("date")).toLocalDate());
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
            vt.setDate(((Date) m.get("date")).toLocalDate());
            vts.add(vt);
        }
        return vts;
    }

    public GuestList queryRocordByBookingId(int bookingId) {
        String sql = "SELECT * FROM guestlist WHERE bookingId = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        GuestList gl = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        if (ls.size() != 0) {
            Map<String, Object> m = ls.get(0);
            gl = new GuestList();
            gl.setCreateDate(((Date) m.get("createDate")).toLocalDate());
            gl.setId((int) m.get("id"));
            gl.setBookingId((int) m.get("bookingId"));
            gl.setGuests(queryGuestsByGuestListId(gl.getId()));
        }
        return gl;
    }
    
    public ArrayList<Guest> queryGuestsByGuestListId(int guestlistId) {
        String sql = "SELECT * FROM guestlist_guest WHERE guestlistId = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(guestlistId);
        ArrayList<Guest> guests = new ArrayList<>();
        GuestDAO gdb = new GuestDAO(dbUrl, dbUser, dbPassword);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            int gid = (int) m.get("guestId");
            guests.add(gdb.queryRecordById(gid));
        }
        return guests;
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
            vt.setDate(((Date) m.get("date")).toLocalDate());
            vts.add(vt);
        }
        return vts;
    }

    public LocalDate queryMaxDate() {
        String sql = "SELECT max(date) as max FROM venue_timeslot";
        ArrayList<Object> params = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        return ((Date) ls.get(0).get("max")).toLocalDate();
    }

    public ArrayList<VenueTimeslots> queryRocordToVenueTimeslotsList(int bookingId) {
        String sql = "SELECT v.id "
                + "FROM venue v "
                + "JOIN venue_timeslot vt ON v.id = vt.venueId "
                + "JOIN timeslot t ON vt.timeslotId = t.id "
                + "WHERE vt.bookingId = ? GROUP BY v.id";
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        ArrayList<VenueTimeslots> vtss = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            int venueId = (int) m.get("id");
            VenueTimeslots vts = queryRocordToVenueTimeslots(bookingId, venueId);
            vtss.add(vts);
        }
        return vtss;
    }

    public DateTimeslots queryRocordToDateTimeslots(int bookingId, LocalDate date, int venueId) {
        VenueTimeslot vt = new VenueTimeslot();
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        params.add(venueId);
        params.add(date);
        DateTimeslots dts = new DateTimeslots();
        ArrayList<Timeslot> tss = new ArrayList<>();;
        String sql = "SELECT v.id, vt.date, t.* FROM "
                + "venue v JOIN venue_timeslot vt ON v.id = vt.venueId "
                + "JOIN timeslot t ON vt.timeslotId = t.id "
                + "WHERE vt.bookingId = ? and vt.venueId = ? and date = ? ";
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        dts.setDate(date);
        for (Map<String, Object> m : ls) {
            Timeslot ts = new Timeslot();
            ts.setId((int) m.get("id"));
            ts.setStartTime(((Time) m.get("startTime")).toLocalTime());
            ts.setEndTime(((Time) m.get("endTime")).toLocalTime());
            tss.add(ts);
        }
        dts.setTimeslots(tss);
        return dts;
    }

    public VenueTimeslots queryRocordToVenueTimeslots(int bookingId, int venueId) {
        VenueTimeslots vts = new VenueTimeslots();
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        params.add(venueId);
        ArrayList<DateTimeslots> dtss = new ArrayList<>();
        DateTimeslots dts = null;
        String sql = "SELECT v.*, vt.date FROM "
                + "venue v JOIN venue_timeslot vt ON v.id = vt.venueId "
                + "JOIN timeslot t ON vt.timeslotId = t.id "
                + "WHERE vt.bookingId = ? and vt.venueId = ? "
                + "GROUP BY vt.date";
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        Venue v = null;
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
            LocalDate date = ((Date) m.get("date")).toLocalDate();
            dts = queryRocordToDateTimeslots(bookingId, date, venueId);
            dtss.add(dts);
        }
        vts.setVenue(v);
        vts.setDateTimeslots(dtss);
        return vts;
    }
}
