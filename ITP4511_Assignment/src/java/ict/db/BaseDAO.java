/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jyuba
 */
public abstract class BaseDAO {
    protected String dburl;
    protected String dbUser;
    protected String dbPassword;

    public BaseDAO(String dburl, String dbUser, String dbPassword) {
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
    
    public abstract void dropTable();
    public abstract void createTable();
    public abstract boolean delRecord(int id);
}
