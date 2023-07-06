package com.example.deliveryservice_courseproject.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class AdminClients extends Client{
    private ObservableList<Client> adminObservableClientList;

    public AdminClients() {
    }

    public ObservableList<Client> getObservableList() throws SQLException {
        adminObservableClientList= FXCollections.observableArrayList(DBConnection.getInstance().getClientsData());
        return adminObservableClientList;
    }

    public void updateClient(String id, String name, String number, String address, String idneardc, String user_id) throws Exception {
        Client client = new Client(id, name, number, address, idneardc, user_id);
        DBConnection.getInstance().updateClient(client);
    }

    public void deleteClient(String id, String user_id) throws Exception {
        DBConnection.getInstance().deleteClient(id, user_id);
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

}
