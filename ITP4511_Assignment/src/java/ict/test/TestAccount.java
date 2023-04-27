package ict.test;

import ict.bean.Account;
import ict.db.AccountDAO;
import ict.db.BaseDAO;
import ict.db.BookingDAO;
import ict.db.GuestDAO;
import ict.db.GuestListDAO;
import ict.db.GuestListGuestDAO;
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
        GuestListGuestDAO glsdb = new GuestListGuestDAO(dbUrl, dbUser, dbPassword);
        GuestListDAO gldb = new GuestListDAO(dbUrl, dbUser, dbPassword);
    }
}
