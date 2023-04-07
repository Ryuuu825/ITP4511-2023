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
public class VenueTimeslot implements Serializable{
    private int id;
    private int timeslotId;
    private int venueId;
    private LocalDate date;
    private int bookingId;

    public VenueTimeslot() {
    }

    public VenueTimeslot(int id, int timeslotId, int venueId, LocalDate date, int bookingId) {
        this.id = id;
        this.timeslotId = timeslotId;
        this.venueId = venueId;
        this.date = date;
        this.bookingId = bookingId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeslotId() {
        return timeslotId;
    }

    public void setTimeslotId(int timeslotId) {
        this.timeslotId = timeslotId;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String toString() {
        return "VenueTimeslot{" + "id=" + id + ", timeslotId=" + timeslotId + ", venueId=" + venueId + ", date=" + date + ", bookingId=" + bookingId + '}';
    }

}
