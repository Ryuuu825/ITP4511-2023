/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.Account;
import ict.bean.Booking;
import ict.bean.Guest;
import ict.bean.User;
import ict.bean.Venue;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author jyuba
 */
public class AccountDAO extends BaseDAO {

    public AccountDAO(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    public void createTable() {
        String sql
                = "CREATE TABLE IF NOT EXISTS account ("
                + "id INT(11) NOT NULL AUTO_INCREMENT,"
                + "username varchar(25) NOT NULL,"
                + "password varchar(25) NOT NULL,"
                + "role TINYINT(1) NOT NULL,"
                + "PRIMARY KEY (id)"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean addRecord(String username, String pwd, int role) {
        boolean isSuccess = false;
        if (queryRecordByUsername(username) == null) {
            ArrayList<Object> params = new ArrayList<>();
            params.add(username);
            params.add(pwd);
            params.add(role);
            String sql = "INSERT INTO account (username, password, role) VALUES (?,?,?)";
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        };
        return isSuccess;
    }

    public String isVaildAccount(String username, String pwd) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(username);
        params.add(pwd);
        String sql = "SELECT * FROM account WHERE username=? and password=?";
        String role = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        Account acc = null;
        if (!ls.isEmpty()) {
            acc = new Account();
            acc.setRole((int) ls.get(0).get("role"));
            role = acc.getRoleString();
        }
        return role;
    }

    public Account queryRecordById(int id) {
        String sql = "SELECT * FROM account WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        Account acc = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            acc = new Account();
            acc.setId((int) m.get("id"));
            acc.setPassword((String) m.get("password"));
            acc.setRole((int) m.get("role"));
            acc.setUsername((String) m.get("username"));
        }
        return acc;
    }

    public Account queryRecordByUsername(String username) {
        String sql = "SELECT * FROM account WHERE username=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(username);
        Account acc = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            acc = new Account();
            acc.setId((int) m.get("id"));
            acc.setPassword((String) m.get("password"));
            acc.setRole((int) m.get("role"));
            acc.setUsername((String) m.get("username"));
        }
        return acc;
    }

    public boolean delRecord(int id) {
        String sql = "DELETE FROM account WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        boolean isSuccess = false;

        //delete releted user records
        UserDAO userDB = new UserDAO(dbUrl, dbUser, dbPassword);
        User u = userDB.queryRecordByAccountId(id);
        //delete releted user records
        BookingDAO bookingDB = new BookingDAO(dbUrl, dbUser, dbPassword);
        VenueDAO venueDB = new VenueDAO(dbUrl, dbUser, dbPassword);
        GuestDAO guestDB = new GuestDAO(dbUrl, dbUser, dbPassword);
        ArrayList<Booking> bs = bookingDB.queryRecordByUserId(id);
        ArrayList<Venue> vs = venueDB.queryRecordByUserId(id);
        ArrayList<Guest> gs = guestDB.queryRecordByUserId(id);
        if (gs.size() != 0) {
            for (Guest g : gs) {
                isSuccess = guestDB.delRecord(g.getId());
            }
        }
        
        if (bs.size() != 0) {
            for (Booking b : bs) {
                isSuccess = bookingDB.delRecord(b.getId());
            }
        }

        if (vs.size() != 0) {
            for (Venue v : vs) {
                v.setUserId(0);
                isSuccess = venueDB.editRecord(v);
            }
        }

        isSuccess = userDB.delRecord(u.getId());

        if (isSuccess == true) {
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        }
        return isSuccess;
    }

    public ArrayList<Account> queryRecord() {
        Account acc = null;
        String sql = "SELECT * FROM user";
        ArrayList<Object> params = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<Account> accs = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            acc = new Account();
            acc.setId((int) m.get("id"));
            acc.setPassword((String) m.get("password"));
            acc.setRole((int) m.get("role"));
            acc.setUsername((String) m.get("username"));
            accs.add(acc);
        }
        return accs;
    }

    public boolean editRecord(Account acc) {
        String sql = "UPDATE account "
                + "SET username = ?, password = ?, role = ? "
                + "WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(acc.getUsername());
        params.add(acc.getPassword());
        params.add(acc.getRole());
        params.add(acc.getId());
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public void dropTable() {
        String sql = "DROP TABLE account";
        dbUtil.executeByPreparedStatement(sql);
    }
}
