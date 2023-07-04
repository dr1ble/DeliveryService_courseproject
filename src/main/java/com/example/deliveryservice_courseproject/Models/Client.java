package com.example.deliveryservice_courseproject.Models;

public class Client {
    private String id, user_id, nearest_dc_id;
    private String name, number, address;

    public Client(String id, String name, String number, String address, String nearest_dc_id, String user_id) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.address = address;
        this.nearest_dc_id = nearest_dc_id;
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNearest_dc_id() {
        return nearest_dc_id;
    }

    public void setNearest_dc_id(String nearest_dc_id) {
        this.nearest_dc_id = nearest_dc_id;
    }
}
