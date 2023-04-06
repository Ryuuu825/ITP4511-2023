/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.util;

import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jyuba
 */
public class DbUtil {

    private String dburl;
    private String dbUser;
    private String dbPassword;

    public DbUtil(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws IOException, SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (Connection) DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    /**
     * excute query statement
     *
     * @param sql
     * @param params
     * @return
     */
    public ArrayList<Map<String, Object>> findRecord(String sql, ArrayList<?> params) {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        ArrayList<Map<String, Object>> ls = new ArrayList<>();
        try {
            cnnt = getConnection();
            pStmnt = cnnt.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                pStmnt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                ResultSetMetaData metaData = rs.getMetaData();
                int rowCount = metaData.getColumnCount();
                for (int i = 0; i < rowCount; i++) {
                    map.put(metaData.getColumnName(i + 1), rs.getObject(i + 1));
                }
                ls.add(map);
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
        return ls;
    }

    /**
     * excute insertion, updation, deletion statement
     *
     * @param sql
     * @param params
     * @return
     */
    public boolean updateByPreparedStatement(String sql, ArrayList<?> params) {
        Connection cnnt = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnt = getConnection();
            pStmnt = cnnt.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                pStmnt.setObject(i + 1, params.get(i));
            }
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

    /**
     * excute creation statement
     *
     * @param sql
     */
    public void executeByPreparedStatement(String sql) {
        Statement stmnt = null;
        Connection cnnt = null;
        try {
            cnnt = getConnection();
            stmnt = cnnt.createStatement();
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
}
