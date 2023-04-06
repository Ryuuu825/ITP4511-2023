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
public class Venue implements Serializable {

    private int id;
    private String name;
    private String address;
    private int capacity;
    private int type;
    private String img;

    private enum typeEnum {
        SportsClub,
        Hotel,
        Restaurant,
        ArtGallerie,
        Park,
        Club
    }

    public Venue() {
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTypeString() {
        return typeEnum.values()[type].toString();
    }

    @Override
    public String toString() {
        return "Venue{" + "id=" + id + ", name=" + name + ", address=" + address + ", capacity=" + capacity + ", type=" + type + ", img=" + img + '}';
    }

}
