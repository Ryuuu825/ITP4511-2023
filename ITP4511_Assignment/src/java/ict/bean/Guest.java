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
public class Guest implements Serializable{
    private int id;
    private int userId;
    private int venueId;
    private int bookingId;
    private String name;
    private String email;

    public Guest(int id, int userId, int venueId, int bookingId, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.venueId = venueId;
        this.bookingId = bookingId;
        this.name = name;
        this.email = email;
    }

    public Guest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Guest{" + "id=" + id + ", userId=" + userId + ", venueId=" + venueId + ", bookingId=" + bookingId + ", name=" + name + ", email=" + email + '}';
    }
}
