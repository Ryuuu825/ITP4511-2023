/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import com.mysql.jdbc.Connection;
import ict.bean.Account;
import ict.bean.User;
import ict.util.DbUtil;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author jyuba
 */
public class AccountDAO{

    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private DbUtil dbUtil;

    public AccountDAO(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbUtil = new DbUtil(dbUrl, dbUser, dbPassword);
    }

    public void createTable() {
        String sql
                = "CREATE TABLE IF NOT EXISTS account ("
                + "id INT(11) NOT NULL AUTO_INCREMENT,"
                + "username varchar(25) NOT NULL,"
                + "password varchar(25) NOT NULL,"
                + "role INT(1) NOT NULL,"
                + "PRIMARY KEY (id, username)"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean addAccount(String username, String pwd, int role) {
        boolean isSuccess = false;
        if (queryByUsername(username) == null) {
            ArrayList<Object> params = new ArrayList();
            params.add(username);
            params.add(pwd);
            params.add(role);
            String sql = "INSERT INTO account (username, password, role) VALUES (?,?,?)";
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        };
        return isSuccess;
    }

    public boolean isVaildAccount(String username, String pwd) {
        ArrayList<Object> params = new ArrayList();
        params.add(username);
        params.add(pwd);
        String sql = "SELECT * FROM account WHERE username=? and password=?";
        boolean isVaild = false;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        if (!ls.isEmpty()) {
            isVaild = true;
        }
        return isVaild;
    }

    public Account queryById(int id) {
        String sql = "SELECT * FROM account WHERE id=?";
        ArrayList<Object> params = new ArrayList();
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

    public Account queryByUsername(String username) {
        String sql = "SELECT * FROM account WHERE username=?";
        ArrayList<Object> params = new ArrayList();
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
        ArrayList<Object> params = new ArrayList();
        params.add(id);
        boolean isSuccess = false;
        UserDAO userDB = new UserDAO(dbUrl, dbUser, dbPassword);
        User u = userDB.queryUserByAccountId(id);
        isSuccess = userDB.delRecord(u.getId());
        if (isSuccess == true) {
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        }
        return isSuccess;
    }

    public boolean editRecord(Account acc) {
        String sql = "UPDATE account "
                + "SET username = ?, password = ?, role = ? "
                + "WHERE id = ?";
        ArrayList<Object> params = new ArrayList();
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
