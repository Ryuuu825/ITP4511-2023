/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean.view;

import ict.bean.Booking;
import ict.bean.Guest;
import ict.bean.GuestList;
import ict.bean.User;
import java.util.ArrayList;

/**
 *
 * @author jyuba
 */
public class BookingDTO {

    private Booking booking;
    private ArrayList<VenueTimeslots> venueTimeslotses;
    private ArrayList<GuestList> venueGuestlists;
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

    public ArrayList<GuestList> getVenueGuestlists() {
        return venueGuestlists;
    }

    public void setVenueGuestlists(ArrayList<GuestList> venueGuestlists) {
        this.venueGuestlists = venueGuestlists;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "BookingDTO{" + "booking=" + booking + ", venueTimeslotses=" + venueTimeslotses + ", venueGuestlists=" + venueGuestlists + ", member=" + member + '}';
    }

}
