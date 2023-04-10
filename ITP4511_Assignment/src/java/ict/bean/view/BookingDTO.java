/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean.view;

import ict.bean.Booking;
import ict.bean.Guest;
import ict.bean.Timeslot;
import ict.bean.User;
import ict.bean.Venue;
import java.util.ArrayList;

/**
 *
 * @author jyuba
 */
public class BookingDTO {

    private Booking booking;
    private ArrayList<VenueTimeslots> venueTimeslotses;
    private ArrayList<ArrayList<Guest>> guestLists;
    private User member;

    public BookingDTO() {
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public ArrayList<VenueTimeslots> getVenueTimeslotses() {
        return venueTimeslotses;
    }

    public void setVenueTimeslotses(ArrayList<VenueTimeslots> venueTimeslotses) {
        this.venueTimeslotses = venueTimeslotses;
    }
    
    public ArrayList<ArrayList<Guest>> getGuestLists() {
        return guestLists;
    }

    public void setGuestLists(ArrayList<ArrayList<Guest>> guestLists) {
        this.guestLists = guestLists;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }
}
