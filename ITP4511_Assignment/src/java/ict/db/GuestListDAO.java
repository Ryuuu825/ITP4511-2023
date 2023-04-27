package ict.db;

import ict.bean.Guest;
import ict.bean.GuestList;
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
                + "bookingId INT(11),"
                + "venueId INT(11),"
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY (bookingId) REFERENCES booking(id) ON DELETE CASCADE,"
                + "FOREIGN KEY (venueId) REFERENCES venue(id) ON DELETE CASCADE"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public int addRecord(int bookingId, int venueId) {
        String sql = "INSERT INTO guestlist (bookingId, venueId) VALUES (?, ?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        params.add(venueId);
        dbUtil.updateByPreparedStatement(sql, params);
        return dbUtil.getLastInsertId();
    }

    public boolean delRecord(int id) {
        boolean isSuccess = false;
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        String sql = "DELETE FROM guestlist WHERE id=?";
        GuestListGuestDAO glgdao = new GuestListGuestDAO(dbUrl, dbUser, dbPassword);
        isSuccess = glgdao.delRecordByGuestlistId(id);
        if (isSuccess) {
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        }
        return isSuccess;
    }

    public boolean delRecordByBookingId(int bookingId) {
        boolean isSuccess = false;
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        String sql = "DELETE FROM guestlist WHERE bookingId=?";
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean delRecordByVenueId(int venueId) {
        boolean isSuccess = false;
        ArrayList<Object> params = new ArrayList<>();
        params.add(venueId);
        String sql = "DELETE FROM guestlist WHERE venueId=?";
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean delRecordByBookingId(int bookingId, int venueId) {
        boolean isSuccess = false;
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        params.add(venueId);
        String sql = "DELETE FROM guestlist WHERE bookingId=? and venueId=?";
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean editRecord(GuestList gl) {
        boolean isSuccess = false;
        ArrayList<Object> params = new ArrayList<>();
        String sql;
        sql = "UPDATE guestlist SET bookingId = ?, venueId = ? WHERE id = ?";
        params.add(gl.getBookingId());
        params.add(gl.getVenueId());
        params.add(gl.getId());
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public GuestList queryRecordById(int id) {
        String sql = "SELECT * FROM guestlist WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        GuestList gl = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        if (ls.size() != 0) {
            Map<String, Object> m = ls.get(0);
            gl = new GuestList();
            gl.setCreateDate(((Date) m.get("createDate")).toLocalDate());
            gl.setId((int) m.get("id"));
            gl.setBookingId(m.get("bookingId") != null ? (int) m.get("bookingId") : 0);
            gl.setGuests(queryGuestsByGuestListId(gl.getId()));
            gl.setVenueId(m.get("venueId") != null ? (int) m.get("venueId") : 0);
        }
        return gl;
    }

    public ArrayList<GuestList> queryRocordByVenueId(int venueId) {
        String sql = "SELECT * FROM guestlist WHERE venueId = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(venueId);
        GuestList gl = null;
        ArrayList<GuestList> gls = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            gls.add(queryRecordById((int) m.get("id")));
        }
        return gls;
    }

    public ArrayList<GuestList> queryRocordByBookingId(int bookingId) {
        String sql = "SELECT * FROM guestlist WHERE bookingId = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        GuestList gl = null;
        ArrayList<GuestList> gls = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            gls.add(queryRecordById((int) m.get("id")));
        }
        return gls;
    }

    public GuestList queryRocordByBooking(int bookingId, int venueId) {
        String sql = "SELECT * FROM guestlist WHERE bookingId = ? and venueId = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        params.add(venueId);
        GuestList gl = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        if (ls.size() != 0) {
            Map<String, Object> m = ls.get(0);
            gl = new GuestList();
            gl.setCreateDate(((Date) m.get("createDate")).toLocalDate());
            gl.setId((int) m.get("id"));
            gl.setBookingId(m.get("bookingId") != null ? (int) m.get("bookingId") : 0);
            gl.setGuests(queryGuestsByGuestListId(gl.getId()));
            gl.setVenueId(m.get("venueId") != null ? (int) m.get("venueId") : 0);
        }
        return gl;
    }

    public GuestList queryRocordByKeyword(int bookingId, int venueId, String keyword) {
        String sql = "SELECT * FROM guestlist WHERE bookingId = ? and venueId = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        params.add(venueId);
        GuestList gl = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        if (ls.size() != 0) {
            Map<String, Object> m = ls.get(0);
            gl = new GuestList();
            gl.setCreateDate(((Date) m.get("createDate")).toLocalDate());
            gl.setId((int) m.get("id"));
            gl.setBookingId(m.get("bookingId") != null ? (int) m.get("bookingId") : 0);
            gl.setGuests(queryGuestsByKeyword(gl.getId(), keyword));
            gl.setVenueId(m.get("venueId") != null ? (int) m.get("venueId") : 0);
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
