package ict.db;

import ict.bean.Guest;
import ict.bean.GuestList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

import java.sql.Date;

public class GuestListDAO extends BaseDAO {

    public GuestListDAO(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS guestlist ("
                + "id INT(11) NOT NULL AUTO_INCREMENT,"
                + "createDate DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "bookingId INT(11) DEFAULT NULL,"
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY (bookingId) REFERENCES booking(id)"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean addRecord(int bookingId) {
        boolean isSuccess = false;
        String sql = "INSERT INTO guestlist (bookingId) VALUES null";
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        if (bookingId != 0) {
            sql = "INSERT INTO guestlist (bookingId) VALUES (?)";
            params.add(bookingId);
        }
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean delRecord(int id) {
        boolean isSuccess = false;
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        String sql = "DELETE FROM guestlist WHERE id=?";
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

    public GuestList queryRocordByKeyword(int bookingId, String keyword) {
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
            gl.setGuests(queryGuestsByKeyword(gl.getId(), keyword));
        }
        return gl;
    }

    public ArrayList<Guest> queryGuestsByKeyword(int guestlistId, String keyword) {
        String sql = "SELECT g.* FROM guest g JOIN guestlist_guest gg "
                + "ON g.id = gg.guestId JOIN guestlist gl "
                + "ON gg.guestlistId = gl.id "
                + "WHERE (g.name LIKE ? or g.email LIKE ?) "
                + "AND gg.guestlistId = ?;";
        ArrayList<Object> params = new ArrayList<>();
        params.add("%" + keyword + "%");
        params.add("%" + keyword + "%");
        params.add(guestlistId);
        ArrayList<Guest> guests = new ArrayList<>();
        GuestDAO gdb = new GuestDAO(dbUrl, dbUser, dbPassword);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        if (ls.size() != 0) {
            for (Map<String, Object> m : ls) {
                int gid = (int) m.get("id");
                guests.add(gdb.queryRecordById(gid));
            }
        }
        return guests;
    }

    public ArrayList<Guest> queryGuestsByGuestListId(int guestlistId) {
        String sql = "SELECT * FROM guestlist_guest WHERE guestlistId = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(guestlistId);
        ArrayList<Guest> guests = new ArrayList<>();
        GuestDAO gdb = new GuestDAO(dbUrl, dbUser, dbPassword);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        if (ls.size() != 0) {
            for (Map<String, Object> m : ls) {
                int gid = (int) m.get("guestId");
                guests.add(gdb.queryRecordById(gid));
            }
        }
        return guests;
    }

}
