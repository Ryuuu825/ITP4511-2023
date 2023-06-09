/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.Booking;
import ict.bean.GuestList;
import ict.bean.User;
import ict.bean.VenueTimeslot;
import ict.bean.Booking.BookingStatus;
import ict.bean.view.BookingDTO;
import ict.bean.view.VenueTimeslots;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
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
                + "createDate DATE NOT NULL DEFAULT current_timestamp(),"
                + "receipt VARCHAR(50),"
                + "status TINYINT(1) NOT NULL DEFAULT 1,"
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY (userId) REFERENCES user(id)"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public int addRecord(int userId, double amount, int status) {
        boolean isSuccess = false;
        LocalDate now = LocalDate.now();
        ArrayList<Object> params = new ArrayList<>();
        params.add(userId);
        params.add(amount);
        params.add(status);
        params.add(now);
        String sql = "INSERT INTO booking (userId, amount, status, createDate) VALUES (?,?,?,?);";
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        if (isSuccess) {
            return dbUtil.getLastInsertId();
        } else {
            return 0;
        }
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
            b.setCreateDate(((Date) m.get("createDate")).toLocalDate());
            b.setReceipt((String) m.get("receipt"));
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
            b.setCreateDate(((Date) m.get("createDate")).toLocalDate());
            b.setReceipt((String) m.get("receipt"));
            bs.add(b);
        }
        return bs;
    }

    public ArrayList<Booking> queryRecordByKeywords(String keywords) {
        String sql = "SELECT booking.*, user.firstName, user.lastName FROM user "
                + "JOIN booking ON booking.userId = user.id "
                + "WHERE booking.id = ? or user.firstname LIKE ? or user.lastname LIKE ? ";
        ArrayList<Object> params = new ArrayList<>();
        params.add(keywords);
        params.add("%" + keywords + "%");
        params.add("%" + keywords + "%");
        Booking b = null;
        ArrayList<Booking> bs = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            b = new Booking();
            b.setId((int) m.get("id"));
            b.setUserId((int) m.get("userId"));
            b.setAmount(((BigDecimal) m.get("amount")).doubleValue());
            b.setStatus((int) m.get("status"));
            b.setCreateDate(((Date) m.get("createDate")).toLocalDate());
            b.setReceipt((String) m.get("receipt"));
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
            b.setCreateDate(((Date) m.get("createDate")).toLocalDate());
            b.setReceipt((String) m.get("receipt"));
            bs.add(b);
        }
        return bs;
    }

    public ArrayList<Booking> queryRecordByDate(String date) {
        String sql = "SELECT * FROM booking WHERE createDate=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(date);
        Booking b = null;
        ArrayList<Booking> bs = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            b = new Booking();
            b.setId((int) m.get("id"));
            b.setUserId((int) m.get("userId"));
            b.setAmount(((BigDecimal) m.get("amount")).doubleValue());
            b.setStatus((int) m.get("status"));
            b.setCreateDate(((Date) m.get("createDate")).toLocalDate());
            b.setReceipt((String) m.get("receipt"));
            bs.add(b);
        }
        return bs;
    }

    public ArrayList<Booking> queryRecordBetweenDate(String startDate, String endDate) {
        String sql = "SELECT * FROM booking WHERE createDate >= ? and createDate <= ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(startDate);
        params.add(endDate);
        Booking b = null;
        ArrayList<Booking> bs = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            b = new Booking();
            b.setId((int) m.get("id"));
            b.setUserId((int) m.get("userId"));
            b.setAmount(((BigDecimal) m.get("amount")).doubleValue());
            b.setStatus((int) m.get("status"));
            b.setCreateDate(((Date) m.get("createDate")).toLocalDate());
            b.setReceipt((String) m.get("receipt"));
            bs.add(b);
        }
        return bs;
    }

    public ArrayList<BookingDTO> queryRecordToDTO() {
        UserDAO userDB = new UserDAO(dbUrl, dbUser, dbPassword);
        VenueTimeslotDAO venueTimeslotDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        GuestListDAO guestListDAO = new GuestListDAO(dbUrl, dbUser, dbPassword);
        ArrayList<BookingDTO> bdtos = new ArrayList<>();
        ArrayList<Booking> bookings = queryRecord();
        ArrayList<GuestList> gl = null;
        BookingDTO bdto = null;
        if (bookings.size() != 0) {
            for (Booking b : bookings) {
                bdto = new BookingDTO();
                bdto.setBooking(b);
                ArrayList<VenueTimeslots> vts1 = venueTimeslotDB.queryRocordToVenueTimeslotsList(b.getId());
                bdto.setVenueTimeslotses(vts1);
                gl = guestListDAO.queryRocordByBookingId(b.getId());
                bdto.setVenueGuestlists(gl);
                User u = userDB.queryRecordById(b.getUserId());
                bdto.setMember(u);
                bdtos.add(bdto);
            }
        }
        return bdtos;
    }

    public ArrayList<BookingDTO> queryRecordToDTOWithUserId(int userID) {
        UserDAO userDB = new UserDAO(dbUrl, dbUser, dbPassword);
        VenueTimeslotDAO venueTimeslotDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        GuestListDAO guestListDAO = new GuestListDAO(dbUrl, dbUser, dbPassword);
        ArrayList<BookingDTO> bdtos = new ArrayList<>();
        ArrayList<Booking> bookings = queryRecordByUserId(userID);
        ArrayList<GuestList> gl = null;
        BookingDTO bdto = null;
        if (bookings.size() != 0) {
            for (Booking b : bookings) {
                bdto = new BookingDTO();
                bdto.setBooking(b);
                ArrayList<VenueTimeslots> vts1 = venueTimeslotDB.queryRocordToVenueTimeslotsList(b.getId());
                bdto.setVenueTimeslotses(vts1);
                gl = guestListDAO.queryRocordByBookingId(b.getId());
                bdto.setVenueGuestlists(gl);
                User u = userDB.queryRecordById(b.getUserId());
                bdto.setMember(u);
                bdtos.add(bdto);
            }
        }
        return bdtos;
    }

    public ArrayList<BookingDTO> queryRecordToDTOByKeyword(String keyword) {
        UserDAO userDB = new UserDAO(dbUrl, dbUser, dbPassword);
        GuestListDAO guestListDAO = new GuestListDAO(dbUrl, dbUser, dbPassword);
        VenueTimeslotDAO venueTimeslotDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        ArrayList<BookingDTO> bdtos = new ArrayList<>();
        ArrayList<Booking> bookings = queryRecordByKeywords(keyword);
        ArrayList<GuestList> gl = null;
        BookingDTO bdto = null;
        if (bookings.size() != 0) {
            for (Booking b : bookings) {
                bdto = new BookingDTO();
                bdto.setBooking(b);
                ArrayList<VenueTimeslots> vts1 = venueTimeslotDB.queryRocordToVenueTimeslotsList(b.getId());
                bdto.setVenueTimeslotses(vts1);
                gl = guestListDAO.queryRocordByBookingId(b.getId());
                bdto.setVenueGuestlists(gl);
                User u = userDB.queryRecordById(b.getUserId());
                bdto.setMember(u);
                bdtos.add(bdto);
            }
        }
        return bdtos;
    }

    public ArrayList<BookingDTO> queryRecordToDTOByKeywordWithUserId( String keyword , String userId) {

        UserDAO userDB = new UserDAO(dbUrl, dbUser, dbPassword);
        GuestListDAO guestListDAO = new GuestListDAO(dbUrl, dbUser, dbPassword);
        VenueTimeslotDAO venueTimeslotDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        ArrayList<BookingDTO> bdtos = new ArrayList<>();
        ArrayList<Booking> bookings = queryRecordByKeywords(keyword);
        ArrayList<GuestList> gl = null;
        BookingDTO bdto = null;
        if (bookings.size() != 0) {
            for (Booking b : bookings) {
                User u = userDB.queryRecordById(b.getUserId());
                if (u.getId() == Integer.parseInt(userId)) {
                    bdto = new BookingDTO();
                    bdto.setBooking(b);
                    ArrayList<VenueTimeslots> vts1 = venueTimeslotDB.queryRocordToVenueTimeslotsList(b.getId());
                    bdto.setVenueTimeslotses(vts1);
                    gl = guestListDAO.queryRocordByBookingId(b.getId());
                    bdto.setVenueGuestlists(gl);
                    bdto.setMember(u);
                    bdtos.add(bdto);
                }
            }
        }
        return bdtos;

    }

    public BookingDTO queryRecordToDTOByBookingId(int bookingId) {
        UserDAO userDB = new UserDAO(dbUrl, dbUser, dbPassword);
        VenueTimeslotDAO venueTimeslotDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        GuestListDAO guestListDAO = new GuestListDAO(dbUrl, dbUser, dbPassword);
        Booking b = queryRecordById(bookingId);
        ArrayList<GuestList> gl = null;
        BookingDTO bdto = null;
        if (b != null) {
            bdto = new BookingDTO();
            bdto.setBooking(b);
            ArrayList<VenueTimeslots> vts1 = venueTimeslotDB.queryRocordToVenueTimeslotsList(b.getId());
            bdto.setVenueTimeslotses(vts1);
            gl = guestListDAO.queryRocordByBookingId(b.getId());
            bdto.setVenueGuestlists(gl);
            User u = userDB.queryRecordById(b.getUserId());
            bdto.setMember(u);
        }
        return bdto;
    }

    public ArrayList<BookingDTO> queryRecordByVenueId(int venueId) {
        ArrayList<BookingDTO> bookings = new ArrayList<>();

        String sql = "SELECT * FROM booking WHERE id IN (SELECT bookingId FROM venue_timeslot WHERE venueId = ?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add(venueId);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);

        for (Map<String, Object> m : ls) {
            bookings.add(queryRecordToDTOByBookingId((int) m.get("id")));
        }

        return bookings;
    }

    public ArrayList<BookingDTO> queryUpcomingFiveDayRecord(String memberId) {
        ArrayList<BookingDTO> bookings = new ArrayList<>();

        // get all booking id
        String status = Integer.toString(Booking.getStatusInt(BookingStatus.PENDING_CHECK_IN));
        String sql = "SELECT booking.* FROM booking INNER JOIN venue_timeslot ON booking.id = venue_timeslot.bookingId WHERE booking.userId = ? AND booking.status = ? AND venue_timeslot.date >= CURDATE() AND venue_timeslot.date <= DATE_ADD(CURDATE(), INTERVAL 5 DAY)";
        ArrayList<Object> params = new ArrayList<>();
        params.add(memberId);
        params.add(status);

        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);

        for (Map<String, Object> m : ls) {
            bookings.add(queryRecordToDTOByBookingId((int) m.get("id")));
        }

        return bookings;
    }

    public boolean updateStatus(int bookingId, int status) {
        String sql = "UPDATE booking "
                + "SET status = ? "
                + "WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(status);
        params.add(bookingId);
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }
    
    public boolean updateReceipt(int bookingId, String url) {
        String sql = "UPDATE booking "
                + "SET receipt = ? "
                + "WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(url);
        params.add(bookingId);
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }
}
