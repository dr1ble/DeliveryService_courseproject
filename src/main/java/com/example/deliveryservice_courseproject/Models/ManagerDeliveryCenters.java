package com.example.deliveryservice_courseproject.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class ManagerDeliveryCenters extends DeliveryCenter {
    private ObservableList<ManagerDeliveryCenters> observableAllDcList;

    public ObservableList<ManagerDeliveryCenters> getObservableList() throws SQLException {
        observableAllDcList = FXCollections.observableArrayList(DBConnection.getInstance().getDCdata());
        return observableAllDcList;
    }

    public ObservableList<ManagerDeliveryCenters> getObservableAllDcList() {
        return observableAllDcList;
    }

    public void setObservableAllDcList(ObservableList<ManagerDeliveryCenters> observableAllDcList) {
        this.observableAllDcList = observableAllDcList;
    }

}
