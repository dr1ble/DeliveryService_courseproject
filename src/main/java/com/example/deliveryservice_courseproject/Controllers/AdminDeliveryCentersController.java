package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Models.AdminDeliveryCenters;
import com.example.deliveryservice_courseproject.Models.DBConnection;
import com.example.deliveryservice_courseproject.Models.DeliveryCenter;
import com.example.deliveryservice_courseproject.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import com.example.deliveryservice_courseproject.Other.AlertMessage;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminDeliveryCentersController {

    AdminDeliveryCenters adminDeliveryCenters = new AdminDeliveryCenters();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<DeliveryCenter, String> addresscenterColumn;

    @FXML
    private TextField addresscenterField;

    @FXML
    private TableView<DeliveryCenter> DeliveryCenters;

    @FXML
    private Button backBtn;

    @FXML
    private TextField centerIdField;

    @FXML
    private TableColumn<DeliveryCenter, String> centeridColumn;

    @FXML
    private TableColumn<DeliveryCenter, String> namecenterColumn;

    @FXML
    private TextField namecenterField;

    @FXML
    private Button clearFieldsBtn;

    @FXML
    private Button dcAddBtn;

    @FXML
    private Button dcDeleteBtn;

    @FXML
    private Button dcUpdateBtn;

    AlertMessage am = new AlertMessage();


    void clearFields(){
        addresscenterField.clear();
        namecenterField.clear();
        centerIdField.clear();
    }

    @FXML
    void getDCOnClick (){
        DeliveryCenters.setOnMouseClicked(mouseEvent -> {
            if(!DeliveryCenters.getSelectionModel().isEmpty()) {
                DeliveryCenter dc = DeliveryCenters.getSelectionModel().getSelectedItem();
                centerIdField.setText(dc.getId());
                namecenterField.setText(dc.getName());
                addresscenterField.setText(dc.getAddress());
            }
        });
    }

    void fillTable() throws SQLException {
        centeridColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecenterColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addresscenterColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        DeliveryCenters.setItems(adminDeliveryCenters.getObservableList());

    }


    @FXML
    void initialize() throws SQLException {
        getDCOnClick();
        fillTable();


        clearFieldsBtn.setOnAction(event -> clearFields());

        dcAddBtn.setOnAction(event -> {
            if(!namecenterField.getText().isEmpty() && !addresscenterField.getText().isEmpty()){
                try {
                    am.confirmationMessage("Вы действительно хотите добавить этот центр?");
                    if(am.checkconfirm()) {
                        adminDeliveryCenters.addDeliveryCenter(new AdminDeliveryCenters(getDataIdField(), getDataNameField(), getDataAddressField()));
                        clearFields();
                        initialize();
                        am.informationMessage("Центр успешно добавлен");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        dcDeleteBtn.setOnAction(event -> {
            if(!centerIdField.getText().isEmpty()){
                try {
                    am.confirmationMessage("Вы действительно хотите удалить этот центр?");
                    if(am.checkconfirm()) {
                        adminDeliveryCenters.deleteDC(getDataIdField());
                        clearFields();
                        initialize();
                        am.informationMessage("Центр успешно удалён");
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        dcUpdateBtn.setOnAction(event -> {
            if(!namecenterField.getText().isEmpty() && !addresscenterField.getText().isEmpty()){
                try {
                    am.confirmationMessage("Вы действительно хотите обновить данные этого центра?");
                    if(am.checkconfirm()) {
                        adminDeliveryCenters.updateDC(new AdminDeliveryCenters(getDataIdField(), getDataNameField(), getDataAddressField()));
                        clearFields();
                        initialize();
                        am.informationMessage("Данные успешно обновлены");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        backBtn.setOnAction(event -> Utils.changeScene(event,"adminmain.fxml", "Главная страница (Администратор)"));
    }
    private String getDataIdField(){
        return centerIdField.getText().trim();
    }
    private String getDataNameField(){
        return namecenterField.getText().trim();
    }
    private String getDataAddressField(){
        return addresscenterField.getText().trim();
    }


}
