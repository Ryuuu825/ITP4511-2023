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
public class GuestListGuestDAO extends BaseDAO{

    public GuestListGuestDAO(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }
    
    public boolean delRecord(int id) {
        String sql = "DELETE FROM guestlist_guest WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }
    
    public boolean delRecordByGuestId(int id) {
        String sql = "DELETE FROM guestlist_guest WHERE guestId=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }
}
