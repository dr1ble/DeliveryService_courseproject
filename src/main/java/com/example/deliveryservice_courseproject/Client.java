package com.example.deliveryservice_courseproject;

public class Client {
    private String id, nearstdc_id, user_id;
    private String name, number, address;

    public Client(String id, String name, String number, String address, String nearstdc_id, String user_id) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.address = address;
        this.nearstdc_id = nearstdc_id;
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNearstdc_id() {
        return nearstdc_id;
    }

    public void setNearstdc_id(String nearstdc_id) {
        this.nearstdc_id = nearstdc_id;
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
}
