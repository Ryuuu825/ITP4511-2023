/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import com.mysql.jdbc.Connection;
import ict.bean.User;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author jyuba
 */
public class UserDB extends BaseDAO{

    public UserDB(String dburl, String dbUser, String dbPassword) {
        super(dburl, dbUser, dbPassword);
    }

    public void createTable() {
        Statement stmnt = null;
        Connection cnnt = null;
        try {
            cnnt = getConnection();
            stmnt = cnnt.createStatement();
            String sql
                    = "CREATE TABLE IF NOT EXISTS user ("
                    + "id INT NOT NULL AUTO_INCREMENT,"
                    + "accountId INT NOT NULL,"
                    + "firstName VARCHAR(25) NOT NULL,"
                    + "lastName VARCHAR(25) NOT NULL,"
                    + "email VARCHAR(50) NOT NULL,"
                    + "phone VARCHAR(15) NOT NULL,"
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

    public boolean addRecord(String accountId, String firstName, String lastName, String email, String phone) {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnt = getConnection();
            String preQueryStatement = "INSERT INTO user (accountId, firstName, lastName, email, phone) VALUES (?,?,?,?,?)";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(1, accountId);
            pStmnt.setString(2, firstName);
            pStmnt.setString(3, lastName);
            pStmnt.setString(4, email);
            pStmnt.setString(5, phone);
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

    public User queryUserById(String id) {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        User u = null;
        try {
            cnnt = getConnection();
            String preQueryStatement = "SELECT * FROM user WHERE id=?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                u = new User();
                u.setAccountId(rs.getInt("accountId"));
                u.setFirstName(rs.getString("firstName"));
                u.setLastName(rs.getString("lastName"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
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
        return u;
    }
    
    public User queryUserByAccountId(int accountId) {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        User u = null;
        try {
            cnnt = getConnection();
            String preQueryStatement = "SELECT * FROM user WHERE accountId=?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, accountId);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                u = new User();
                u.setAccountId(rs.getInt("accountId"));
                u.setFirstName(rs.getString("firstName"));
                u.setLastName(rs.getString("lastName"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
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
        return u;
    }

    public ArrayList<User> queryUserByName(String name) {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        ArrayList<User> us = new ArrayList<>();
        User u = null;
        try {
            cnnt = getConnection();
            String preQueryStatement = "SELECT * FROM user WHERE firstName=? OR lastName=?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(1, name);
            pStmnt.setString(2, name);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                u = new User();
                u.setAccountId(rs.getInt("accountId"));
                u.setFirstName(rs.getString("firstName"));
                u.setLastName(rs.getString("lastName"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                us.add(u);
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
        return us;
    }

    public ArrayList<User> queryUserByPhone(String tel) {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        ArrayList<User> us = new ArrayList<User>();
        User u = null;
        try {
            cnnt = getConnection();
            String preQueryStatement = "SELECT * FROM user WHERE phone=?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(1, tel);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                u = new User();
                u.setAccountId(rs.getInt("accountId"));
                u.setFirstName(rs.getString("firstName"));
                u.setLastName(rs.getString("lastName"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                us.add(u);
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
        return us;
    }

    public ArrayList<User> queryUser() {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        ArrayList<User> us = new ArrayList<>();
        User u = null;
        try {
            cnnt = getConnection();
            String preQueryStatement = "SELECT * FROM user";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                u = new User();
                u.setAccountId(rs.getInt("accountId"));
                u.setFirstName(rs.getString("firstName"));
                u.setLastName(rs.getString("lastName"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                us.add(u);
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
        return us;
    }

    public boolean delRecord(int id) {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnt = getConnection();
            String preQueryStatement = "DELETE FROM user WHERE id=?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);
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

    public boolean editRecord(User u) {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnt = getConnection();
            String preQueryStatement = "UPDATE customer "
                    + "SET firstName = ?, lastName = ?, email = ?, phone = ? "
                    + "WHERE id = ?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(1, u.getFirstName());
            pStmnt.setString(2, u.getLastName());
            pStmnt.setString(3, u.getEmail());
            pStmnt.setString(4, u.getPhone());
            pStmnt.setInt(5, u.getId());
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
            String preQueryStatement = "DROP TABLE user";
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
