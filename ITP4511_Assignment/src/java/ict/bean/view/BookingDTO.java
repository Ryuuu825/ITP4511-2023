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
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author jyuba
 */
public class BookingDTO {

    private Booking booking;
    private ArrayList<Timeslot> timeslots;
    private ArrayList<Venue> venues;
    private ArrayList<ArrayList<Guest>> guestLists;
    private String member;

    public BookingDTO() {
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    
    public ArrayList<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(ArrayList<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }

    public ArrayList<Venue> getVenues() {
        return venues;
    }

    public void setVenues(ArrayList<Venue> venues) {
        this.venues = venues;
    }

    public ArrayList<ArrayList<Guest>> getGuestLists() {
        return guestLists;
    }

    public void setGuestLists(ArrayList<ArrayList<Guest>> guestLists) {
        this.guestLists = guestLists;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "BookingDTO{" + "booking=" + booking + ", timeslots=" + timeslots + ", venues=" + venues + ", guestLists=" + guestLists + ", member=" + member + '}';
    }

}
