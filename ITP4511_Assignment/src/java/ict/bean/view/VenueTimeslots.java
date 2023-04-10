/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean.view;

import ict.bean.Venue;
import java.util.ArrayList;

/**
 *
 * @author jyuba
 */
public class VenueTimeslots {
    private Venue venue;
    private ArrayList<DateTimeslots> dateTimeslots;
    private int bookingid;

    public VenueTimeslots() {
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public ArrayList<DateTimeslots> getDateTimeslots() {
        return dateTimeslots;
    }

    public void setDateTimeslots(ArrayList<DateTimeslots> dateTimeslots) {
        this.dateTimeslots = dateTimeslots;
    }

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

}
