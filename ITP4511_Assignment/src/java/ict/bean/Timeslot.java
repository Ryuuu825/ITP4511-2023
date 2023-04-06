/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author jyuba
 */
public class Timeslot implements Serializable {

    private int id;
    private int venueId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private double hourlyRate;
    private boolean available;

    public Timeslot() {
    }

    public Timeslot(int id, int venueId, LocalTime startTime, LocalTime endTime, LocalDate date, double hourlyRate, boolean available) {
        this.id = id;
        this.venueId = venueId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.hourlyRate = hourlyRate;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Timeslot{" + "id=" + id + ", venueId=" + venueId + ", startTime=" + startTime + ", endTime=" + endTime + ", date=" + date + ", hourlyRate=" + hourlyRate + ", available=" + available + '}';
    }
    
}
