package com.example.deliveryservice_courseproject.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class AdminCouriers extends Courier {
    private ObservableList<Courier> adminObservableCourierList;
    public AdminCouriers(){
    }

    public ObservableList<Courier> getObservableList() throws SQLException {
        adminObservableCourierList = FXCollections.observableArrayList(DBConnection.getInstance().getCouriersData());
        return adminObservableCourierList;
    }

    public void addCourier(String name, String number, String address, String login, String password) throws SQLException {

        User user = new User("",login, password, "1");
        Courier courier = new Courier("", name, number, address, "");
        if(DBConnection.getInstance().checkDc(address)) {
            DBConnection.getInstance().signUpUser(user);
            DBConnection.getInstance().signUpCourier(courier, user);
        }
        else{
            throw new SQLException();
        }
    }
    public void updateCourier(String id, String name, String number, String dcid, String user_id) throws Exception {


        Courier courier = new Courier(id, name, number, dcid, user_id);
        if(DBConnection.getInstance().checkDc(dcid)) {
            DBConnection.getInstance().updateCourier(courier);
        }
        else{
            throw new SQLException();
        }
    }
    public void deleteCourier(String id, String user_id) throws Exception {
        DBConnection.getInstance().deleteCourier(id, user_id);
    }
}

