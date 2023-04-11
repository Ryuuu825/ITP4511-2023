/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean.view;

import ict.bean.User;
import ict.bean.Venue;

/**
 *
 * @author jyuba
 */
public class VenueDTO {
    private Venue venue;
    private User user;

    public VenueDTO() {
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "VenueDTO{" + "venue=" + venue + ", user=" + user + '}';
    }
    
}
