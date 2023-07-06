package com.example.deliveryservice_courseproject.Models;

public class Courier {
    private  String id, name, number,  delivery_center_id, user_id;

    public Courier(String id, String name, String number, String delivery_center_id, String user_id) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.delivery_center_id = delivery_center_id;
        this.user_id = user_id;
    }

    public Courier() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDelivery_center_id() {
        return delivery_center_id;
    }

    public void setDelivery_center_id(String delivery_center_id) {
        this.delivery_center_id = delivery_center_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
