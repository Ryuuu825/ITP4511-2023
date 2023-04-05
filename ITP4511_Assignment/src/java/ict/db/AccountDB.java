/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import com.mysql.jdbc.Connection;
import ict.bean.Account;
import ict.bean.User;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jyuba
 */
public class AccountDB extends BaseDAO{

    public AccountDB(String dburl, String dbUser, String dbPassword) {
        super(dburl, dbUser, dbPassword);
    }

    public void createTable() {
        Statement stmnt = null;
        Connection cnnt = null;
        try {
            cnnt = getConnection();
            stmnt = cnnt.createStatement();
            String sql
                    = "CREATE TABLE IF NOT EXISTS account ("
                    + "id INT NOT NULL AUTO_INCREMENT,"
                    + "username varchar(25) NOT NULL,"
                    + "password varchar(25) NOT NULL,"
                    + "role varchar(10) NOT NULL,"
                    + "PRIMARY KEY (id)"
                    + ")";
            stmnt.execute(sql);
            stmnt.close();
            cnnt.close();
        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addAccount(String username, String pwd, String role) {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnt = getConnection();
            String preQueryStatement = "INSERT INTO account (username, password, role) VALUES (?,?,?,?)";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(2, username);
            pStmnt.setString(3, pwd);
            pStmnt.setString(4, role);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
            }
            pStmnt.close();
            cnnt.close();
        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public boolean isVaildAccount(String user, String pwd) {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        boolean isVaild = false;
        try {
            cnnt = getConnection();
            String preQueryStatement = "SELECT * FROM account WHERE username=? and password=?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(1, user);
            pStmnt.setString(2, pwd);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                isVaild = true;
            }
            pStmnt.close();
            cnnt.close();
        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isVaild;
    }
    
    public boolean delRecord(int id) {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnt = getConnection();
            String preQueryStatement = "DELETE FROM account WHERE id=?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);
            int rowCount = pStmnt.executeUpdate();
            UserDB userDB = new UserDB(dburl, dbUser, dbPassword);
            User u = userDB.queryUserByAccountId(id);
            isSuccess = userDB.delRecord(u.getId());
            if (rowCount >= 1 && isSuccess == true) {
                isSuccess = true;
            }else {
                isSuccess = false;
            }
            pStmnt.close();
            cnnt.close();
        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public boolean editRecord(Account acc) {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnt = getConnection();
            String preQueryStatement = "UPDATE account "
                    + "SET username = ?, password = ?, role = ? "
                    + "WHERE id = ?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(1, acc.getUsername());
            pStmnt.setString(2, acc.getPassword());
            pStmnt.setString(3, acc.getRole());
            pStmnt.setInt(4, acc.getId());
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
            }
            pStmnt.close();
            cnnt.close();
        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
    
    public void dropTable() {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnt = getConnection();
            String preQueryStatement = "DROP TABLE account";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.executeUpdate();
            System.out.println("Table droped");
            pStmnt.close();
            cnnt.close();
        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
