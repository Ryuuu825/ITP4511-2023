/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean.view;

import ict.bean.Timeslot;
import java.time.LocalDate;

/**
 *
 * @author jyuba
 */
public class CalendarTimeslot {
    private int venueId;
    private Timeslot timeslot;
    private LocalDate date;
    private boolean booked;
    private int venuetimeslotId;

    public CalendarTimeslot() {
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public int getVenuetimeslotId() {
        return venuetimeslotId;
    }

    public void setVenuetimeslotId(int venuetimeslotId) {
        this.venuetimeslotId = venuetimeslotId;
    }

    @Override
    public String toString() {
        return "CalendarTimeslot{" + "venueId=" + venueId + ", timeslot=" + timeslot + ", date=" + date + ", booked=" + booked + ", venuetimeslotId=" + venuetimeslotId + '}';
    }
}
