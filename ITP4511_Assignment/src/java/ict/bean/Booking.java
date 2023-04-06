/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

import java.io.Serializable;

/**
 *
 * @author jyuba
 */
public class Booking implements Serializable{
    private int id;
    private int userId;
    private int venueId;
    private int timeslotId;
    private int approvalStatus;
    private int attendanceStatus;

    public Booking() {
    }

    public Booking(int id, int userId, int venueId, int timeslotId, int approvalStatus, int attendanceStatus) {
        this.id = id;
        this.userId = userId;
        this.venueId = venueId;
        this.timeslotId = timeslotId;
        this.approvalStatus = approvalStatus;
        this.attendanceStatus = attendanceStatus;
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

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public int getTimeslotId() {
        return timeslotId;
    }

    public void setTimeslotId(int timeslotId) {
        this.timeslotId = timeslotId;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(int attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    @Override
    public String toString() {
        return "Booking{" + "id=" + id + ", userId=" + userId + ", venueId=" + venueId + ", timeslotId=" + timeslotId + ", approvalStatus=" + approvalStatus + ", attendanceStatus=" + attendanceStatus + '}';
    }
    
}
