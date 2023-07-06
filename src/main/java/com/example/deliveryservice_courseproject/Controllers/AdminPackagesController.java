package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Models.AdminPackages;
import com.example.deliveryservice_courseproject.Models.DBConnection;
import com.example.deliveryservice_courseproject.Models.Package;
import com.example.deliveryservice_courseproject.Utils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.deliveryservice_courseproject.Other.AlertMessage;

public class AdminPackagesController {
    AdminPackages adminPackages = new AdminPackages();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Package> packageTable;

    @FXML
    private Button backBtn;

    @FXML
    private Button clearFieldsBtn;

    @FXML
    private TableColumn<Package, String> courierIdColumn;

    @FXML
    private TextField courieridField;

    @FXML
    private TableColumn<Package, String> departcenterColumn;

    @FXML
    private TextField departcenterField;

    @FXML
    private TableColumn<Package, String> endDateColumn;

    @FXML
    private TextField enddateField;

    @FXML
    private Button packageAddBtn;

    @FXML
    private Button packageDeleteBtn;

    @FXML
    private TextField packageIdField;

    @FXML
    private Button packageUpdateBtn;

    @FXML
    private TableColumn<Package, String> packageidColumn;

    @FXML
    private TextField receivecenterField;

    @FXML
    private TextField recepientidField;

    @FXML
    private TableColumn<Package, String> recievecenterColumn;

    @FXML
    private TableColumn<Package, String> recipientIdColumn;

    @FXML
    private TableColumn<Package, String> senderIdColumn;

    @FXML
    private TextField senderidField;

    @FXML
    private TableColumn<Package, String> startDateColumn;

    @FXML
    private TextField startdateField;

    @FXML
    private TableColumn<Package, String> statusColumn;

    @FXML
    private TextField statusField;

    @FXML
    private TableColumn<Package, String> typedeliveryColumn;

    @FXML
    private TextField typedeliveryField;

    @FXML
    private TableColumn<Package, String> weightColumn;

    @FXML
    private TextField weightField;
    AlertMessage am = new AlertMessage();


    @FXML
    void getPackageOnClick (){
        packageTable.setOnMouseClicked(mouseEvent -> {
            if(!packageTable.getSelectionModel().isEmpty()) {
                Package pckge = packageTable.getSelectionModel().getSelectedItem();
                packageIdField.setText(pckge.getId());
                typedeliveryField.setText(pckge.getType_of_delivery());
                weightField.setText(pckge.getWeight());
                statusField.setText(pckge.getStatus());
                startdateField.setText(pckge.getDate_start());
                enddateField.setText(pckge.getDate_end());
                courieridField.setText(pckge.getCourier_id());
                senderidField.setText(pckge.getSender_id());
                recepientidField.setText(pckge.getRecipient_id());
                departcenterField.setText(pckge.getDepartcenter_id());
                receivecenterField.setText(pckge.getReceivingcenter_id());
            }

        });
    }

    void clearFields(){
        packageIdField.clear();
        typedeliveryField.clear();
        weightField.clear();
        statusField.clear();
        startdateField.clear();
        enddateField.clear();
        courieridField.clear();
        senderidField.clear();
        recepientidField.clear();
        departcenterField.clear();
        receivecenterField.clear();
    }

    void fillTable() throws SQLException {
        packageidColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typedeliveryColumn.setCellValueFactory(new PropertyValueFactory<>("type_of_delivery"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_start"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_end"));
        courierIdColumn.setCellValueFactory(new PropertyValueFactory<>("courier_id"));
        senderIdColumn.setCellValueFactory(new PropertyValueFactory<>("sender_id"));
        recipientIdColumn.setCellValueFactory(new PropertyValueFactory<>("recipient_id"));
        departcenterColumn.setCellValueFactory(new PropertyValueFactory<>("departcenter_id"));
        recievecenterColumn.setCellValueFactory(new PropertyValueFactory<>("receivingcenter_id"));

        packageTable.setItems(adminPackages.getObservableList());
    }

    private boolean checkFields(){
        if (!packageIdField.getText().isEmpty() && !typedeliveryField.getText().isEmpty() && !weightField.getText().isEmpty()
                && !statusField.getText().isEmpty() && !startdateField.getText().isEmpty() && !enddateField.getText().isEmpty() && !courieridField.getText().isEmpty()
                && !senderidField.getText().isEmpty() && !recepientidField.getText().isEmpty() && !departcenterField.getText().isEmpty() && !receivecenterField.getText().isEmpty()){
            return true;
        }
        return false;
    }

    @FXML
    void initialize() throws SQLException {
        getPackageOnClick();
        fillTable();
        clearFieldsBtn.setOnAction(event -> clearFields());
        packageUpdateBtn.setOnAction(event -> {
            if(!packageIdField.getText().isEmpty() && !typedeliveryField.getText().isEmpty() && !weightField.getText().isEmpty() && !statusField.getText().isEmpty()){
                try {
                    am.confirmationMessage("Вы действительно хотите обновить данные этой посылки?");
                    if(am.checkconfirm()) {
                        try {
                            if(!typedeliveryField.getText().isEmpty() && !weightField.getText().isEmpty() && !statusField.getText().isEmpty()) {
                                adminPackages.updatePackage(getNewPackage());
                                clearFields();
                                initialize();
                            }
                            else{
                                am.warningMessage("Не заполнены обязательные поля");
                            }
                            am.informationMessage("Обновление данных отправления прошло успешно");
                        }
                        catch (Exception e) {
                            am.warningMessage("Невозможно обновить посылку введены неверные данные (ID курьера | ID отправителя | ID получателя | ID ЦО | ID ЦП не сущестует");
                        }
                    }
                    else{
                        ;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else am.warningMessage("Не выбрана посылка для обновления");
        });

        packageAddBtn.setOnAction(event -> {
            if(!typedeliveryField.getText().isEmpty() && !weightField.getText().isEmpty() && !statusField.getText().isEmpty()){
                try {
                    am.confirmationMessage("Вы действительно хотите добавить эту посылку?");
                    if(am.checkconfirm()) {
                        if(!typedeliveryField.getText().isEmpty() && !weightField.getText().isEmpty() && !statusField.getText().isEmpty()) {
                            adminPackages.addPackage(getNewPackage());
                            clearFields();
                            initialize();
                            am.informationMessage("Добавление прошло успешно");
                        }
                        else am.warningMessage("Не заполнены обязательные поля");
                    }
                    else{
                        ;
                    }
                } catch (Exception e) {
                    am.warningMessage("Невозможно добавить посылку введены неверные данные (ID курьера | ID отправителя | ID получателя | ID ЦО | ID ЦП не сущестует");
                }
            }
            else am.warningMessage("Заполните поля обязательные поля (Тип доставки, вес, статус)");
        });

        packageDeleteBtn.setOnAction(event -> {
            if(!packageIdField.getText().isEmpty()){
                try {
                    am.confirmationMessage("Вы действительно хотите удалить эту посылку?");
                    if(am.checkconfirm()) {
                        adminPackages.deletePackage(packageIdField.getText().trim());
                        clearFields();
                        initialize();
                        am.informationMessage("Удаление прошло успешно");
                    }
                    else{
                        ;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else am.warningMessage("Не выбрана посылка для удаления");
        });

        backBtn.setOnAction(event -> Utils.changeScene(event,"adminmain.fxml", "Главная страница (Администратор)"));
    }


    private Package getNewPackage(){
        String id = packageIdField.getText();
        String type = typedeliveryField.getText();
        String weight = weightField.getText();
        String status =  statusField.getText();
        String start_date = startdateField.getText();
        String end_date = enddateField.getText();
        String courier_id = courieridField.getText();
        String sender_id = senderidField.getText();
        String recepient_id = recepientidField.getText();
        String departcenter_id = departcenterField.getText();
        String recievingcenter_id = receivecenterField.getText();

        Package pckge = new Package(id, type, weight, status, start_date, end_date, courier_id, sender_id, recepient_id, departcenter_id, recievingcenter_id);

        return pckge;
    }
}
