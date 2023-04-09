/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.Booking;
import ict.bean.Guest;
import ict.bean.VenueTimeslot;
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
                + "approvalStatus TINYINT(1) NOT NULL,"
                + "attendanceStatus TINYINT(1) NOT NULL,"
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY (userId) REFERENCES user(id)"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean addRecord(int userId, int approvalStatus, int attendanceStatus) {
        boolean isSuccess = false;
        ArrayList<Object> params = new ArrayList<>();
        params.add(userId);
        params.add(approvalStatus);
        params.add(attendanceStatus);
        String sql = "INSERT INTO booking (userId, approvalStatus, attendanceStatus) VALUES (?,?,?)";
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public Booking queryById(int id) {
        String sql = "SELECT * FROM booking WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        Booking b = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            b = new Booking();
            b.setId((int) m.get("id"));
            b.setUserId((int) m.get("userId"));
            b.setApprovalStatus((int) m.get("approvalStatus"));
            b.setAttendanceStatus((int) m.get("attendanceStatus"));
        }
        return b;
    }

    public ArrayList<Booking> queryByVenueId(int venueId) {
        String sql = "SELECT * FROM booking WHERE venueId=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(venueId);
        Booking b = null;
        ArrayList<Booking> bs = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            b = new Booking();
            b.setId((int) m.get("id"));
            b.setUserId((int) m.get("userId"));
            b.setApprovalStatus((int) m.get("approvalStatus"));
            b.setAttendanceStatus((int) m.get("attendanceStatus"));
            bs.add(b);
        }
        return bs;
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
                + "SET userId = ?, approvalStatus = ?, attendanceStatus = ?"
                + "WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(b.getUserId());
        params.add(b.getApprovalStatus());
        params.add(b.getAttendanceStatus());
        params.add(b.getId());
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public void dropTable() {
        String sql = "DROP TABLE booking";
        dbUtil.executeByPreparedStatement(sql);
    }

    public ArrayList<Booking> queryByUserId(int userId) {
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
            b.setApprovalStatus((int) m.get("approvalStatus"));
            b.setAttendanceStatus((int) m.get("attendanceStatus"));
            bs.add(b);
        }
        return bs;
    }

}
