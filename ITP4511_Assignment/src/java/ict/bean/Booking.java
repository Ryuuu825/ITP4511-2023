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
    private String receipt;
    
    public static String[] statuString = {"Pending Approval", "Rejected", "Pending Check in", "Check in", "Completed", "Cancelled", "Completed"};

    public enum BookingStatus {
        PENDING_APPROVAL, REJECTED, PENDING_CHECK_IN, CHECK_IN, CHECK_OUT, CANCEL, COMPLETE
    }
    
    public Booking() {
    }

    public Booking(int id, int userId, int status, double amount, LocalDate createDate, String receipt) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.amount = amount;
        this.createDate = createDate;
        this.receipt = receipt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
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

    public static BookingStatus getStatusEnum(int status) {
        return BookingStatus.values()[status-1];
    }

    public static int getStatusInt(BookingStatus status) {
        return status.ordinal()+1;
    }

    @Override
    public String toString() {
        return "Booking{" + "id=" + id + ", userId=" + userId + ", status=" + status + ", amount=" + amount + ", createDate=" + createDate + ", receipt=" + receipt + '}';
    }
    
}
