package com.example.deliveryservice_courseproject.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class RegCourier extends Courier{
    ObservableList<DeliveryCenter> dcList;

    public RegCourier() {
        super();
    }

    public RegCourier(String id, String name, String number, String delivery_center_id, String user_id) {
        super(id, name, number, delivery_center_id, user_id);
    }

    public ObservableList<DeliveryCenter> getObservableDcList() throws SQLException {
        dcList = FXCollections.observableArrayList(DBConnection.getInstance().getDCdata());
        return dcList;
    }

    public void signUp(String name, String number, String dcid, String login, String password) throws SQLException {

        User user = new User("",login, password, "1");
        Courier courier = new Courier("",name,number,dcid,"");
        DBConnection.getInstance().signUpUser(user);
        DBConnection.getInstance().signUpCourier(courier, user);
    }
}
