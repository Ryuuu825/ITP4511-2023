/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.Booking;
import ict.bean.Guest;
import ict.bean.Timeslot;
import ict.bean.User;
import ict.bean.Venue;
import ict.bean.VenueTimeslot;
import ict.bean.view.BookingDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author jyuba
 */
public class BookingDAO extends BaseDAO {

    public BookingDAO(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    public void createTable() {
        String sql
                = "CREATE TABLE IF NOT EXISTS booking ("
                + "id INT(11) NOT NULL AUTO_INCREMENT,"
                + "userId INT(11) NOT NULL,"
                + "amount DECIMAL(10,2) NOT NULL,"
                + "status TINYINT(1) NOT NULL,"
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY (userId) REFERENCES user(id)"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean addRecordRecord(int userId, double amount, int status) {
        boolean isSuccess = false;
        ArrayList<Object> params = new ArrayList<>();
        params.add(userId);
        params.add(amount);
        params.add(status);
        String sql = "INSERT INTO booking (userId, amount, status) VALUES (?,?,?)";
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public Booking queryRecordById(int id) {
        String sql = "SELECT * FROM booking WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        Booking b = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            b = new Booking();
            b.setId((int) m.get("id"));
            b.setUserId((int) m.get("userId"));
            b.setAmount(((BigDecimal) m.get("amount")).doubleValue());
            b.setStatus((int) m.get("status"));
        }
        return b;
    }

    public boolean delRecord(int id) {
        String sql = "DELETE FROM booking WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        boolean isSuccess = false;

        //delete releted guest records
        VenueTimeslotDAO vtsDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        ArrayList<VenueTimeslot> vts = vtsDB.queryRocordByBookingId(id);
        if (vts.size() != 0) {
            for (VenueTimeslot vt : vts) {
                vt.setBookingId(0);
                isSuccess = vtsDB.editRecord(vt);
            }
        }

        //delete releted guest records
        GuestDAO gDB = new GuestDAO(dbUrl, dbUser, dbPassword);
        ArrayList<Guest> gs = gDB.queryRecordByBookingId(id);
        if (gs.size() != 0) {
            for (Guest ts : gs) {
                isSuccess = gDB.delRecord(ts.getId());
            }
        }

        if (isSuccess) {
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        }
        return isSuccess;
    }

    public boolean editRecord(Booking b) {
        String sql = "UPDATE booking "
                + "SET userId = ?, amount = ?, status = ?"
                + "WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(b.getUserId());
        params.add(b.getAmount());
        params.add(b.getStatus());
        params.add(b.getId());
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public void dropTable() {
        String sql = "DROP TABLE booking";
        dbUtil.executeByPreparedStatement(sql);
    }

    public ArrayList<Booking> queryRecordByUserId(int userId) {
        String sql = "SELECT * FROM booking WHERE userId=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(userId);
        Booking b = null;
        ArrayList<Booking> bs = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            b = new Booking();
            b.setId((int) m.get("id"));
            b.setUserId((int) m.get("userId"));
            b.setAmount(((BigDecimal) m.get("amount")).doubleValue());
            b.setStatus((int) m.get("status"));
            bs.add(b);
        }
        return bs;
    }

    public ArrayList<Booking> queryRecord() {
        String sql = "SELECT * FROM booking";
        ArrayList<Object> params = new ArrayList<>();
        Booking b = null;
        ArrayList<Booking> bs = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            b = new Booking();
            b.setId((int) m.get("id"));
            b.setUserId((int) m.get("userId"));
            b.setAmount(((BigDecimal) m.get("amount")).doubleValue());
            b.setStatus((int) m.get("status"));
            bs.add(b);
        }
        return bs;
    }

    public ArrayList<BookingDTO> queryRecordToDTO() {
        UserDAO userDB = new UserDAO(dbUrl, dbUser, dbPassword);
        VenueTimeslotDAO venueTimeslotDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        GuestDAO guestDB = new GuestDAO(dbUrl, dbUser, dbPassword);
        VenueDAO venueDB = new VenueDAO(dbUrl, dbUser, dbPassword);
        TimeslotDAO timeslotDB = new TimeslotDAO(dbUrl, dbUser, dbPassword);
        ArrayList<BookingDTO> bdtos = new ArrayList<>();
        ArrayList<Booking> bookings = queryRecord();
        ArrayList<VenueTimeslot> vts = null;
        ArrayList<ArrayList<Guest>> gss = null;
        ArrayList<Venue> vs = null;
        ArrayList<Timeslot> tss = null;
        BookingDTO bdto = null;
        if (bookings.size() != 0) {
            for (Booking b : bookings) {
                bdto = new BookingDTO();
                bdto.setBooking(b);
                vts = venueTimeslotDB.queryRocordByBookingId(b.getId());

                if (vts.size() != 0) {
                    vs = new ArrayList<>();
                    tss = new ArrayList<>();
                    gss = new ArrayList<>();
                    for (VenueTimeslot vt : vts) {
                        Venue v = venueDB.queryRecordById(vt.getVenueId());
                        vs.add(v);
                        Timeslot ts = timeslotDB.queryRecordById(vt.getTimeslotId());
                        tss.add(ts);
                        ArrayList<Guest> gs = guestDB.queryRecordByBooking(b.getId(), vt.getVenueId());
                        gss.add(gs);
                    }
                }

                bdto.setVenues(vs);
                bdto.setGuestLists(gss);
                bdto.setTimeslots(tss);
                User u = userDB.queryRecordById(b.getUserId());
                bdto.setMember(u.getFirstName() + " " + u.getLastName());
                bdtos.add(bdto);
            }
        }
        return bdtos;
    }

    public BookingDTO queryRecordToDTOByBookingId(int bookingId) {
        UserDAO userDB = new UserDAO(dbUrl, dbUser, dbPassword);
        VenueTimeslotDAO venueTimeslotDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        GuestDAO guestDB = new GuestDAO(dbUrl, dbUser, dbPassword);
        VenueDAO venueDB = new VenueDAO(dbUrl, dbUser, dbPassword);
        TimeslotDAO timeslotDB = new TimeslotDAO(dbUrl, dbUser, dbPassword);
        Booking b = queryRecordById(bookingId);
        ArrayList<VenueTimeslot> vts = null;
        ArrayList<ArrayList<Guest>> gss = null;
        ArrayList<Venue> vs = null;
        ArrayList<Timeslot> tss = null;
        BookingDTO bdto = null;
        if (b != null) {
            bdto = new BookingDTO();
            bdto.setBooking(b);
            vts = venueTimeslotDB.queryRocordByBookingId(b.getId());

            if (vts.size() != 0) {
                vs = new ArrayList<>();
                tss = new ArrayList<>();
                gss = new ArrayList<>();
                for (VenueTimeslot vt : vts) {
                    Venue v = venueDB.queryRecordById(vt.getVenueId());
                    vs.add(v);
                    Timeslot ts = timeslotDB.queryRecordById(vt.getTimeslotId());
                    tss.add(ts);
                    ArrayList<Guest> gs = guestDB.queryRecordByBooking(b.getId(), vt.getVenueId());
                    gss.add(gs);
                }
            }

            bdto.setVenues(vs);
            bdto.setGuestLists(gss);
            bdto.setTimeslots(tss);
            User u = userDB.queryRecordById(b.getUserId());
            bdto.setMember(u.getFirstName() + " " + u.getLastName());
        }
        return bdto;
    }
}
