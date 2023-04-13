/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author jyuba
 */
public class Venue implements Serializable {

    private int id;
    private String name;
    private String location;
    private String address;
    private int capacity;
    private int type;
    private String img;
    private String description;
    private int userId;
    private double hourlyRate;

    private ArrayList<String> typeStrings = new ArrayList<>();

    public Venue() {
        typeStrings = new ArrayList<>();
        typeStrings.add("Hotel");
        typeStrings.add("Restaurant");
        typeStrings.add("Art Gallery");
        typeStrings.add("Park");
        typeStrings.add("Club");
    }

    public Venue(int id, String name, String address, int capacity, int type, String img) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.type = type;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setType(String type) {
        int typeIdx = typeStrings.indexOf(type);
        if (typeIdx != -1) {
            this.type = typeIdx + 1;
        } else {
            typeStrings.add(type);
            this.type = typeStrings.size() - 1;
        }
    }

    public void addType(String type) {
        typeStrings.add(type);
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTypeString() {
        return typeStrings.get(type-1);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Venue{" + "id=" + id + ", name=" + name + ", location=" + location + ", address=" + address + ", capacity=" + capacity + ", type=" + type + ", img=" + img + ", description=" + description + ", userId=" + userId + ", hourlyRate=" + hourlyRate + '}';
    }

}
