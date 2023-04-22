/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean.view;

import ict.bean.Booking;
import ict.bean.Guest;
import ict.bean.GuestList;
import ict.bean.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author jyuba
 */
public class BookingReportDTO {

    private int ID;
    private String member;
    private int NoOfBookedVenues;
    private Double totalAmount;
    private LocalDate createDate;
    private String status;


    public BookingReportDTO() {
    }

    public BookingReportDTO(int iD, String member, int noOfBookedVenues, Double totalAmount, LocalDate createDate,
            String status) {
        ID = iD;
        this.member = member;
        NoOfBookedVenues = noOfBookedVenues;
        this.totalAmount = totalAmount;
        this.createDate = createDate;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }


    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }



    public int getNoOfBookedVenues() {
        return NoOfBookedVenues;
    }



    public void setNoOfBookedVenues(int noOfBookedVenues) {
        NoOfBookedVenues = noOfBookedVenues;
    }



    public Double getTotalAmount() {
        return totalAmount;
    }



    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }



    public LocalDate getCreateDate() {
        return createDate;
    }



    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }



    public String getStatus() {
        return status;
    }



    public void setStatus(String status) {
        this.status = status;
    }

}
