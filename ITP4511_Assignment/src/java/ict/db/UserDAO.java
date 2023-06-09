/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.User;
import ict.bean.Venue;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author jyuba
 */
public class UserDAO extends BaseDAO{

    public UserDAO(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    public void createTable() {
        String sql
                = "CREATE TABLE IF NOT EXISTS user ("
                + "id INT(11) NOT NULL AUTO_INCREMENT,"
                + "accountId INT(11) NOT NULL,"
                + "firstName VARCHAR(25) NOT NULL,"
                + "lastName VARCHAR(25) NOT NULL,"
                + "email VARCHAR(50) NOT NULL,"
                + "phone VARCHAR(15) NOT NULL,"
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY (accountId) REFERENCES account(id) ON DELETE CASCADE"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean addRecord(int accountId, String firstName, String lastName, String email, String phone) {
        boolean isSuccess = false;
        if (queryRecordByAccountId(accountId) == null) {
            ArrayList<Object> params = new ArrayList<>();
            params.add(accountId);
            params.add(firstName);
            params.add(lastName);
            params.add(email);
            params.add(phone);
            String sql = "INSERT INTO user (accountId, firstName, lastName, email, phone) VALUES (?,?,?,?,?)";
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        };
        return isSuccess;
    }

    public User queryRecordById(int id) {
        User u = null;
        String sql = "SELECT * FROM user WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        if (!ls.isEmpty()) {
            Map<String, Object> m = ls.get(0);
            u = new User();
            u.setId((int) m.get("id"));
            u.setAccountId((int) m.get("accountId"));
            u.setEmail((String) m.get("email"));
            u.setFirstName((String) m.get("firstName"));
            u.setLastName((String) m.get("lastName"));
            u.setPhone((String) m.get("phone"));
        }
        return u;
    }

    public User queryRecordByAccountId(int accountId) {
        User u = null;
        String sql = "SELECT * FROM user WHERE accountId=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(accountId);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        if (!ls.isEmpty()) {
            Map<String, Object> m = ls.get(0);
            u = new User();
            u.setId((int) m.get("id"));
            u.setAccountId((int) m.get("accountId"));
            u.setEmail((String) m.get("email"));
            u.setFirstName((String) m.get("firstName"));
            u.setLastName((String) m.get("lastName"));
            u.setPhone((String) m.get("phone"));
        }
        return u;
    }

    public ArrayList<User> queryRecordByName(String name) {
        User u = null;
        String sql = "SELECT * FROM user WHERE firstName LIKE ? OR lastName LIKE ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(("%" + name + "%"));
        params.add(("%" + name + "%"));
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<User> us = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            u = new User();
            u.setId((int) m.get("id"));
            u.setAccountId((int) m.get("accountId"));
            u.setEmail((String) m.get("email"));
            u.setFirstName((String) m.get("firstName"));
            u.setLastName((String) m.get("lastName"));
            u.setPhone((String) m.get("phone"));
            us.add(u);
        }
        return us;
    }

    public ArrayList<User> queryRecordByPhone(String phone) {
        User u = null;
        String sql = "SELECT * FROM user WHERE phone LIKE ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(("%" + phone + "%"));
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<User> us = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            u = new User();
            u.setId((int) m.get("id"));
            u.setAccountId((int) m.get("accountId"));
            u.setEmail((String) m.get("email"));
            u.setFirstName((String) m.get("firstName"));
            u.setLastName((String) m.get("lastName"));
            u.setPhone((String) m.get("phone"));
            us.add(u);
        }
        return us;
    }

    public ArrayList<User> queryRecord() {
        User u = null;
        String sql = "SELECT * FROM user";
        ArrayList<Object> params = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<User> us = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            u = new User();
            u.setId((int) m.get("id"));
            u.setAccountId((int) m.get("accountId"));
            u.setEmail((String) m.get("email"));
            u.setFirstName((String) m.get("firstName"));
            u.setLastName((String) m.get("lastName"));
            u.setPhone((String) m.get("phone"));
            us.add(u);
        }
        return us;
    }

    public ArrayList<User> queryRecord(int limit, int offset) {
        User u = null;
        String sql = "SELECT * FROM user LIMIT ? OFFSET ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(limit);
        params.add(offset);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<User> us = new ArrayList<>();

        for (Map<String, Object> m : ls) {
            u = new User();
            u.setId((int) m.get("id"));
            u.setAccountId((int) m.get("accountId"));
            u.setEmail((String) m.get("email"));
            u.setFirstName((String) m.get("firstName"));
            u.setLastName((String) m.get("lastName"));
            u.setPhone((String) m.get("phone"));
            us.add(u);
        }

        return us;
    }

    public ArrayList<User> queryRecordByKeywords(String keywords) {
        User u = null;
        String sql = "SELECT user.* FROM user inner join account on user.accountId = account.id WHERE account.username LIKE ? OR user.email LIKE ? OR user.id LIKE ? OR account.id LIKE ?";
    
        ArrayList<Object> params = new ArrayList<>();
        params.add(("%" + keywords + "%"));
        params.add(("%" + keywords + "%"));
        params.add(("%" + keywords + "%"));
        params.add(("%" + keywords + "%"));
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<User> us = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            u = new User();
            u.setId((int) m.get("id"));
            u.setAccountId((int) m.get("accountId"));
            u.setEmail((String) m.get("email"));
            u.setFirstName((String) m.get("firstName"));
            u.setLastName((String) m.get("lastName"));
            u.setPhone((String) m.get("phone"));
            us.add(u);
        }
        return us;
    }

    public ArrayList<User> queryRecordByRole(int role) {
        User u = null;
        String sql = "SELECT account.role, user.* FROM account JOIN user ON user.accountId = account.id WHERE role = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(role);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        ArrayList<User> us = new ArrayList<>();
        for (Map<String, Object> m : ls) {
            u = new User();
            u.setId((int) m.get("id"));
            u.setAccountId((int) m.get("accountId"));
            u.setEmail((String) m.get("email"));
            u.setFirstName((String) m.get("firstName"));
            u.setLastName((String) m.get("lastName"));
            u.setPhone((String) m.get("phone"));
            us.add(u);
        }
        return us;
    }

    public boolean delRecord(int id) {
        String sql = "DELETE FROM user WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        boolean isSuccess = false;
        VenueDAO vdao = new VenueDAO(dbUrl, dbUser, dbPassword);
        ArrayList<Venue> vs = vdao.queryRecordByUserId(id);
        if (vs.size() != 0) {
            for (Venue v : vs) {
                v.setUserId(0);
                isSuccess = vdao.editRecord(v);
            }
        }
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean editRecord(User u) {
        String sql = "UPDATE user "
                + "SET firstName = ?, lastName = ?, email = ?, phone = ? "
                + "WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(u.getFirstName());
        params.add(u.getLastName());
        params.add(u.getEmail());
        params.add(u.getPhone());
        params.add(u.getId());
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }



    public void dropTable() {
        String sql = "DROP TABLE user";
        dbUtil.executeByPreparedStatement(sql);
    }
}
