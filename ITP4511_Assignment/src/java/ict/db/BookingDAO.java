/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.Booking;
import ict.bean.Guest;
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
    
    // public ArrayList<BookingDTO> queryRecordToDTO() {
    //     // inner join booking, user, venue, timeslot, guest, venue_timeslot, account
    //     String sql = "SELECT booking.id, booking.amount, booking.status, venue.name, venue.id as venueId,"
    //             + "venue.address, venue.email, venue.location, venue.description, venue.img, venue.capacity"
    //             + "booking.id as bookingId, timeslot.* exclude id, timeslot.id as timeslotId,"
    //             + "guest.* exclude id, guest.id as guestId, "
    //             + "CONCAT(user.firstName, '', user.lastName) as member"
    //             + "FROM booking"
    //             + "INNER JOIN user ON booking.userId = user.id "
    //             + "INNER JOIN venue_timeslot ON booking.id = venue_timeslot.bookingId "
    //             + "INNER JOIN venue ON venue_timeslot.venueId = venue.id "
    //             + "INNER JOIN timeslot ON venue_timeslot.timeslotId = timeslot.id "
    //             + "INNER JOIN guest ON booking.id = guest.bookingId";
    //     ArrayList<Object> params = new ArrayList<>();
    //     BookingDTO b = null;
    //     ArrayList<BookingDTO> bs = new ArrayList<>();
    //     ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
    //     for (Map<String, Object> m : ls) {
    //         // b = new BookingDTO();
    //         // b.setGuests((int) m.get("bookingId"));
    //         // b.setUserId((int) m.get("userId"));
    //         // b.setAmount((double) m.get("amount"));
    //         // b.setStatus((int) m.get("status"));
    //         System.out.println(m.toString());
    //         bs.add(b);
    //     }
    //     return bs;
    // }
}
