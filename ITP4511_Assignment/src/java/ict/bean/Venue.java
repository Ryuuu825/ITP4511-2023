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
    private String district;
    private String address;
    private int capacity;
    private int type;
    private String img;
    private String description;
    private int userId;
    private double hourlyRate;

    public static String[] typeStrings = {
        "Hotel",
        "Restaurant",
        "Art Gallery",
        "Park",
        "Club"
    };

    public Venue() {
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
        for (int i = 1; i <= typeStrings.length; i++) {
            if (typeStrings[i].equals(type)) {
                this.type = i;
            }
        }
    }
    
    public String getTypeString() {
        return typeStrings[type-1];
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
    
    @Override
    public String toString() {
        return "Venue{" + "id=" + id + ", name=" + name + ", district=" + district + ", address=" + address + ", capacity=" + capacity + ", type=" + type + ", img=" + img + ", description=" + description + ", userId=" + userId + ", hourlyRate=" + hourlyRate + '}';
    }

}
