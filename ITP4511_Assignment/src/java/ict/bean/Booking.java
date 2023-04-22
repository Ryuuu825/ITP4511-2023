/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author jyuba
 */
public class Booking implements Serializable{
    private int id;
    private int userId;
    private int status;
    private double amount;
    private LocalDate createDate;
    
    public static String[] statuString = {"Pending Approval", "Rejected", "Pending Check in", "Check in", "Check out", "Cancel", "Complete"};
    
    public Booking() {
    }

    public Booking(int id, int userId, int status, double amount, LocalDate createDate) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.amount = amount;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
    
    public String getStatusString(){
        return statuString[status-1];
    }
    
    @Override
    public String toString() {
        return "Booking{" + "id=" + id + ", userId=" + userId + ", status=" + status + ", amount=" + amount + ", createDate=" + createDate + '}';
    }

}
