package com.example.deliveryservice_courseproject.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class AdminDeliveryCenters extends DeliveryCenter {

    private ObservableList<DeliveryCenter> adminObesrvableDcList;

    public AdminDeliveryCenters(String id, String name, String address){
        super(id, name, address);
    }

    public AdminDeliveryCenters(){
    }
    public ObservableList<DeliveryCenter> getObservableList() throws SQLException {
        adminObesrvableDcList = FXCollections.observableArrayList(DBConnection.getInstance().getDCdata());
        return adminObesrvableDcList;
    }
    public void addDeliveryCenter(DeliveryCenter deliveryCenter) throws SQLException {
        DBConnection.getInstance().addDeliveryCenter(deliveryCenter);
    }

    public void updateDC(DeliveryCenter deliveryCenter) throws SQLException{
        DBConnection.getInstance().updateDeliveryCenter(deliveryCenter);
    }
    public void deleteDC(String id) throws SQLException{
        DBConnection.getInstance().deleteDeliveryCenter(id);
    }

    public ObservableList<DeliveryCenter> getAdminObesrvableDcList() {
        return adminObesrvableDcList;
    }

    public void setAdminObesrvableDcList(ObservableList<DeliveryCenter> adminObesrvableDcList) {
        this.adminObesrvableDcList = adminObesrvableDcList;
    }
}
