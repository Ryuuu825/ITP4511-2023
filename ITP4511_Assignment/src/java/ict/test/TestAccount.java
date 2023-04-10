package ict.test;

import ict.bean.Account;
import ict.db.AccountDAO;
import ict.db.BaseDAO;
import ict.db.BookingDAO;
import ict.db.GuestDAO;
import ict.db.TimeslotDAO;
import ict.db.UserDAO;
import ict.db.VenueDAO;
import ict.db.VenueTimeslotDAO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author jyuba
 */
public class TestAccount {

    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_ASM_DB?tinyInt1isBit=false";
        String dbUser = "root";
        String dbPassword = "";
        UserDAO udb = new UserDAO(dbUrl, dbUser, dbPassword);
        AccountDAO adb = new AccountDAO(dbUrl, dbUser, dbPassword);
        GuestDAO gdb = new GuestDAO(dbUrl, dbUser, dbPassword);
        TimeslotDAO tdb = new TimeslotDAO(dbUrl, dbUser, dbPassword);
        BookingDAO bdb = new BookingDAO(dbUrl, dbUser, dbPassword);
        VenueDAO vdb = new VenueDAO(dbUrl, dbUser, dbPassword);
        VenueTimeslotDAO vtdb = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        gdb.createTable();
//        Account acc = adb.queryRecordById(12);
//        acc.setPassword("123");
//        adb.editRecord(acc);
//        bdb.queryRecordToDTO();
//        udb.dropTable();
//        db.dropTable();
//        adb.createTable();
//        udb.createTable();
//        vdb.createTable();
//        tdb.createTable();
//        vtdb.createTable();
//        bdb.createTable();
//        gdb.createTable();
//        adb.addRecord("ben", "123", 3);
//        adb.addRecord("ken", "123", 3);
//        adb.addRecord("user", "123", 1);
//        adb.addRecord("staff", "123", 2);
//        udb.addRecord(1, "Ben", "Poon", "210426767@stu.vtc.edu.hk", "62723020");
//        udb.addRecord(2, "Ken", "Lee", "210426767@stu.vtc.edu.hk", "62723020");
//        udb.addRecord(3, "User", "Lam", "210426767@stu.vtc.edu.hk", "62723020");
//            Account acc = adb.queryRecordByUsername("ben");
//            System.out.println(acc.getId());
//        acc.toString();
//        acc.setPassword("456");
//        adb.editRecord(acc);
//        udb.createTable();
//        
//        adb.delRecord(1);
    }
}
