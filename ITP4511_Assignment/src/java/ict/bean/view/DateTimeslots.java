/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean.view;

import ict.bean.Timeslot;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author jyuba
 */
public class DateTimeslots {
    private LocalDate date;
    private ArrayList<Timeslot> timeslots;

    public DateTimeslots() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(ArrayList<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }
}
