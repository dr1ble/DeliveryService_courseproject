package com.example.deliveryservice_courseproject.Models;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeliveryCenter {
    private  String id, name, address;

    public DeliveryCenter(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void addDeliveryCenter(DeliveryCenter deliveryCenter) throws SQLException {

        String insertDeliveryCenter = "INSERT INTO delivery_centers(name, address) VALUES(?,?)";
        PreparedStatement prStInsertDC =  DBConnection.getInstance().getConnection().prepareStatement(insertDeliveryCenter);

        prStInsertDC.setString(1, deliveryCenter.getName());
        prStInsertDC.setString(2, deliveryCenter.getAddress());

        prStInsertDC.executeUpdate();
    }

    public void updateDeliveryCenter(DeliveryCenter deliveryCenter) throws SQLException{
        String updateDeliveryCenter = "UPDATE delivery_centers SET name =?, address =? WHERE id =?";
        PreparedStatement prstupdateDC = DBConnection.getInstance().getConnection().prepareStatement(updateDeliveryCenter);

        prstupdateDC.setString(1, deliveryCenter.getName());
        prstupdateDC.setString(2, deliveryCenter.getAddress());
        prstupdateDC.setString(3, deliveryCenter.getId());


        prstupdateDC.executeUpdate();
    }
}
