/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.util.DbUtil;

/**
 *
 * @author jyuba
 */
public class BaseDAO {
    protected String dbUrl="jdbc:mysql://localhost:3306/ITP4511_DB";
    protected String dbUser="root";
    protected String dbPassword="";
    protected DbUtil dbUtil;

    public BaseDAO(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbUtil = new DbUtil(dbUrl, dbUser, dbPassword);
    }

    public BaseDAO() {
        this.dbUtil = new DbUtil(dbUrl, dbUser, dbPassword);
    }
    
}