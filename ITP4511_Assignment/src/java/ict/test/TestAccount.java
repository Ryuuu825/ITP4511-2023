package ict.test;


import ict.bean.Account;
import ict.db.AccountDAO;
import ict.db.UserDAO;

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
        UserDAO udb = new UserDAO("jdbc:mysql://localhost:3306/ITP4511_DB", "root", "");
        AccountDAO db = new AccountDAO("jdbc:mysql://localhost:3306/ITP4511_DB", "root", "");
        udb.dropTable();
        db.dropTable();
        db.createTable();
        db.addAccount("ben", "123", 3);
        db.addAccount("ken", "123", 3);
        db.addAccount("user", "123", 1);
        db.addAccount("staff", "123", 2);
        Account acc = db.queryById(3);
        acc.toString();
        acc.setPassword("456");
        db.editRecord(acc);
        udb.createTable();
        udb.addRecord(1, "Ben", "Poon", "210426767@stu.vtc.edu.hk", "62723020");
        udb.addRecord(2, "Ken", "Lee", "210426767@stu.vtc.edu.hk", "62723020");
        udb.addRecord(3, "User", "Lam", "210426767@stu.vtc.edu.hk", "62723020");
        db.delRecord(1);
    }
}
