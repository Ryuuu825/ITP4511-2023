/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import java.util.ArrayList;

/**
 *
 * @author jyuba
 */
public class GuestListGuestDAO extends BaseDAO {

    public GuestListGuestDAO(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS guestlist_guest ("
                + "id INT(11) NOT NULL AUTO_INCREMENT,"
                + "guestlistId INT(11) NOT NULL,"
                + "guestId INT(11) NOT NULL,"
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY (guestlistId) REFERENCES guestlist(id) ON DELETE CASCADE,"
                + "FOREIGN KEY (guestId) REFERENCES guest(id) ON DELETE CASCADE"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean delRecord(int id) {
        String sql = "DELETE FROM guestlist_guest WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean delRecordByGuestId(int guestId) {
        String sql = "DELETE FROM guestlist_guest WHERE guestId=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(guestId);
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean delRecordByGuestId(int bookingId, int venueId, int guestId) {
        String sql = "DELETE FROM guestlist_guest WHERE bookingId = ? and venueId = ? and guestId=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(bookingId);
        params.add(venueId);
        params.add(guestId);
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean delRecordByGuestlistId(int guestlsitId) {
        String sql = "DELETE FROM guestlist_guest WHERE guestlistId=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(guestlsitId);
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }
}
