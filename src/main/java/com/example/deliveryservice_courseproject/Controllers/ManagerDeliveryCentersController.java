package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Models.*;
import com.example.deliveryservice_courseproject.Utils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManagerDeliveryCentersController {

    ManagerDeliveryCenters managerDeliveryCenters = new ManagerDeliveryCenters();

    ObservableList<ManagerDeliveryCenters> deliveryCenters;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<ManagerDeliveryCenters, String> addresscenterColumn;

    @FXML
    private TextField addresscenterField;

    @FXML
    private TableView<ManagerDeliveryCenters> allDeliveryCenters;

    @FXML
    private Button backBtn;

    @FXML
    private TextField centerIdField;

    @FXML
    private TableColumn<ManagerDeliveryCenters, String> centeridColumn;

    @FXML
    private TableColumn<ManagerDeliveryCenters, String> namecenterColumn;

    @FXML
    private TextField namecenterField;

    @FXML
    void getDCOnClick (){
        allDeliveryCenters.setOnMouseClicked(mouseEvent -> {
            if(!allDeliveryCenters.getSelectionModel().isEmpty()) {
                DeliveryCenter dc = allDeliveryCenters.getSelectionModel().getSelectedItem();
                centerIdField.setText(dc.getId());
                namecenterField.setText(dc.getName());
                addresscenterField.setText(dc.getAddress());
            }

        });
    }
    @FXML
    void initialize() throws SQLException {
        getDCOnClick();

        centeridColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecenterColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addresscenterColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        allDeliveryCenters.setItems(managerDeliveryCenters.getObservableList());



        backBtn.setOnAction(event -> Utils.changeScene(event,"managermain.fxml", "Главная страница (Менеджер)"));

    }

}
