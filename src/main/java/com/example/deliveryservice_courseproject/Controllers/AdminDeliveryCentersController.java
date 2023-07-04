package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Utils.AlertMessage;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminDeliveryCentersController {

    ObservableList<DeliveryCenter> deliveryCenters;

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


    DBConnection db;

    {
        try {
            db = DBConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


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

        deliveryCenters = db.getDCdata();
        DeliveryCenters.setItems(deliveryCenters);

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
                        addCenter();
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
                        deleteCenter();
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
                        updateCenter();
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

    private void updateCenter() throws Exception {

        String id = centerIdField.getText().trim();
        String name = namecenterField.getText().trim();
        String address = addresscenterField.getText().trim();

        DeliveryCenter deliveryCenter = new DeliveryCenter(id,name,address);
        db.updateDeliveryCenter(deliveryCenter);
    }

    private void addCenter() throws SQLException {
        String id = centerIdField.getText().trim();
        String name = namecenterField.getText().trim();
        String address = addresscenterField.getText().trim();

        DeliveryCenter deliveryCenter = new DeliveryCenter(id,name,address);
        db.addDeliveryCenter(deliveryCenter);
    }

    private void deleteCenter() throws Exception {

        String id = centerIdField.getText().trim();

        db.deleteDeliveryCenter(id);
    }

}
