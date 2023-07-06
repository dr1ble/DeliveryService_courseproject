package com.example.deliveryservice_courseproject.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class AdminPackages extends Package{
    private ObservableList<Package> adminObservablePackagesList;

    public AdminPackages(){};

    public AdminPackages(String id, String type_of_delivery, String weight, String status, String date_start, String date_end, String courier_id, String sender_id, String recipient_id, String departcenter_id, String receivingcenter_id) {
        super(id, type_of_delivery, weight, status, date_start, date_end, courier_id, sender_id, recipient_id, departcenter_id, receivingcenter_id);
    }

    public ObservableList<Package> getObservableList() throws SQLException {
        adminObservablePackagesList = FXCollections.observableArrayList(DBConnection.getInstance().getDataPackages());
        return adminObservablePackagesList;
    }

    public void updatePackage(Package pckge) throws Exception {
        Package updatepckge = pckge;
        DBConnection.getInstance().updatePackage(updatepckge);
    }

    public void addPackage(Package pckge) throws Exception {
        Package addpckge = pckge;
        DBConnection.getInstance().addPackage(addpckge);
    }

    public void deletePackage(String id) throws SQLException {
        DBConnection.getInstance().deletePackage(id);
    }

}
