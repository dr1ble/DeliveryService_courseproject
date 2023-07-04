package com.example.deliveryservice_courseproject.Models;

public class Package {
    private String id,type_of_delivery,weight,status,date_start,date_end,courier_id,sender_id,recipient_id,departcenter_id,receivingcenter_id;

    public Package(String id, String type_of_delivery, String weight, String status, String date_start, String date_end, String courier_id, String sender_id, String recipient_id, String departcenter_id, String receivingcenter_id) {
        this.id = id;
        this.type_of_delivery = type_of_delivery;
        this.weight = weight;
        this.status = status;
        this.date_start = date_start;
        this.date_end = date_end;
        this.courier_id = courier_id;
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        this.departcenter_id = departcenter_id;
        this.receivingcenter_id = receivingcenter_id;
    }

    public String getType_of_delivery() {
        return type_of_delivery;
    }

    public void setType_of_delivery(String type_of_delivery) {
        this.type_of_delivery = type_of_delivery;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(String courier_id) {
        this.courier_id = courier_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(String recipient_id) {
        this.recipient_id = recipient_id;
    }

    public String getDepartcenter_id() {
        return departcenter_id;
    }

    public void setDepartcenter_id(String departcenter_id) {
        this.departcenter_id = departcenter_id;
    }

    public String getReceivingcenter_id() {
        return receivingcenter_id;
    }

    public void setReceivingcenter_id(String receivingcenter_id) {
        this.receivingcenter_id = receivingcenter_id;
    }
}
