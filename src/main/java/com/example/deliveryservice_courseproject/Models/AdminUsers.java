package com.example.deliveryservice_courseproject.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class AdminUsers extends User{

    private ObservableList<User> adminObservableUsersList;

    public AdminUsers(String id, String login, String password, String accesslevel) {
        super(id, login, password, accesslevel);
    }

    public AdminUsers() {
    }

    public ObservableList<User> getObservableList() throws SQLException {
        adminObservableUsersList = FXCollections.observableArrayList(DBConnection.getInstance().getUsersData());
        return adminObservableUsersList;
    }

    public void addUser(String login, String password, String level) throws SQLException {

        User user = new User("",login, password, level);
        DBConnection.getInstance().signUpUser(user);
    }

    public void addClient(String name, String number, String address, String neardc, String login, String password) throws SQLException {

        User user = new User("",login, password, "0");
        Client client = new Client("", name, number, address, neardc, "");
        if (DBConnection.getInstance().checkDc(neardc)) {
            DBConnection.getInstance().signUpUser(user);
            DBConnection.getInstance().signUpClient(client, user);
        }
        else{
            throw new SQLException();
        }
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

    public void deleteUser(String login, String id) throws Exception {

        if(!login.equals("admin")) {
            DBConnection.getInstance().deleteUser(id);
        }
        else {
            throw new Exception("Невозможно удалить администратора");
        }
    }

    public void updateUser(String id, String login, String password, String level) throws Exception {

        User user = new User(id,login, password, level);
        DBConnection.getInstance().updateUser(user);
    }
}
