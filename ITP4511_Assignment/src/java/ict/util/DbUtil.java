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

    private static ArrayList<String> errorMsgs = new ArrayList<>();
    private static boolean hasError = false;

    public DbUtil(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws IOException, SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            hasError = false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (Connection) DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public boolean testConnectionWithDB() {

        Connection cnnt = null;
        try {
            cnnt = getConnection();
            cnnt.close();
            return true;
        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                hasError = true;
                String errorMsg = e.getMessage();
                if (errorMsg.contains("(")) {
                    errorMsg = errorMsg.substring(0, e.getLocalizedMessage().indexOf("(")) + ". SQL State Code:" + e.getSQLState();
                }
                errorMsgs.add(errorMsg);
                e = e.getNextException();
            }
        } catch (IOException e) {
            String errorMsg = e.getMessage();
            errorMsgs.add(errorMsg);
            e.printStackTrace();
            hasError = true;
        }
        return false;

    }

    public int getLastInsertId() {
        // get last insert id
        int lastInsertId = 0;

        try {
            Connection cnnt = getConnection();
            Statement stmnt = cnnt.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT LAST_INSERT_ID()");

            if (rs.next()) {
                lastInsertId = rs.getInt(1);
            }
            cnnt.close();
        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                hasError = true;
                String errorMsg = e.getMessage();
                if (errorMsg.contains("(")) {
                    errorMsg = errorMsg.substring(0, e.getLocalizedMessage().indexOf("(")) + ". SQL State Code:" + e.getSQLState();
                }
                errorMsgs.add(errorMsg);
                e = e.getNextException();
            }
        } catch (IOException e) {
            String errorMsg = e.getMessage();
            errorMsgs.add(errorMsg);
            e.printStackTrace();
            hasError = true;
        }
        return lastInsertId;
    }

    /**
     * excute query statement
     *
     * @param sql
     * @param params
     * @return
     */
    public ArrayList<Map<String, Object>> findRecord(String sql, ArrayList<?> params) {
        java.sql.Connection cnnt = null;
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
                    map.put(metaData.getColumnLabel(i + 1), rs.getObject(i + 1));
                }
                ls.add(map);
            }

            pStmnt.close();
            cnnt.close();
        } catch (SQLException e) {
            while (e != null) {

                e.printStackTrace();
                hasError = true;

                // Error:com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Cannot delete or update a parent row: a foreign key constraint fails (`itp4511_asm_db`.`venue`, CONSTRAINT `venue_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`))|#]
                // remove all the content after the (), where it show the database structure
                // errorMsg = errorMsg.substring(0, e.getLocalizedMessage().indexOf("(")) + "<br/>" + e.getSQLState();
                String errorMsg = e.getMessage();
                if (errorMsg.contains("(")) {
                    errorMsg = errorMsg.substring(0, e.getLocalizedMessage().indexOf("(")) + ". SQL State Code:" + e.getSQLState();
                }
                errorMsgs.add(errorMsg);

                e = e.getNextException();
            }
        } catch (IOException e) {
            String errorMsg = e.getMessage();
            errorMsgs.add(errorMsg);
            e.printStackTrace();
            hasError = true;
        } catch (Exception e) {
            e.printStackTrace();
            hasError = true;
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
            cnnt.setAutoCommit(false);
            pStmnt = cnnt.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                pStmnt.setObject(i + 1, params.get(i));
            }
            int rowCount = pStmnt.executeUpdate();
            cnnt.commit();
            if (rowCount >= 1) {
                isSuccess = true;
            }
            pStmnt.close();
            cnnt.close();
        } catch (SQLException e) {
            while (e != null) {
                if (cnnt != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        cnnt.rollback();
                    } catch (SQLException excep) {
                        excep.printStackTrace();
                    }
                }
                e.printStackTrace();
                hasError = true;
                // Error:com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Cannot delete or update a parent row: a foreign key constraint fails (`itp4511_asm_db`.`venue`, CONSTRAINT `venue_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`))|#]
                // remove all the content after the (), where it show the database structure
                // errorMsg = errorMsg.substring(0, e.getLocalizedMessage().indexOf("(")) + "<br/>" + e.getSQLState();
                String errorMsg = e.getMessage();
                if (errorMsg.contains("(")) {
                    errorMsg = errorMsg.substring(0, e.getLocalizedMessage().indexOf("(")) + ". SQL State Code:" + e.getSQLState();
                }
                errorMsgs.add(errorMsg);

                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            hasError = true;
        } catch (Exception e) {
            e.printStackTrace();
            hasError = true;
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
            cnnt.setAutoCommit(false);
            stmnt = cnnt.createStatement();
            stmnt.execute(sql);
            cnnt.commit();
            stmnt.close();
            cnnt.close();
        } catch (SQLException e) {
            while (e != null) {
                if (cnnt != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        cnnt.rollback();
                    } catch (SQLException excep) {
                        excep.printStackTrace();
                    }
                }
                e.printStackTrace();

                String errorMsg = e.getMessage();
                if (errorMsg.contains("(")) {
                    errorMsg = errorMsg.substring(0, e.getLocalizedMessage().indexOf("(")) + ". SQL State Code:" + e.getSQLState();
                }
                errorMsgs.add(errorMsg);

                e = e.getNextException();
                hasError = true;
            }
        } catch (IOException e) {

            e.printStackTrace();
            hasError = true;
        } catch (Exception e) {
            e.printStackTrace();
            hasError = true;
        }
    }

    public String getErrorMsg() {
        if (errorMsgs.size() > 0) {
            return errorMsgs.get(errorMsgs.size() - 1);
        }
        return "";
    }

    public boolean hasErrorAtLastOperation() {
        return hasError;
    }

    public int getTotalRecords(String table) {
        int totalRecords = 0;
        String sql = "SELECT COUNT(*) FROM " + table;
        ArrayList<Map<String, Object>> ls = findRecord(sql, new ArrayList<>());
        if (ls.size() > 0) {
            totalRecords = Integer.parseInt(ls.get(0).get("COUNT(*)").toString());
        }
        return totalRecords;
    }
}
