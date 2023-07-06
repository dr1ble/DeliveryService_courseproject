package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Models.*;
import com.example.deliveryservice_courseproject.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.deliveryservice_courseproject.Other.AlertMessage;
import javafx.scene.layout.Pane;

public class AdminUsersController {

    AdminUsers adminUsers = new AdminUsers();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Button userAddBtn;

    @FXML
    private Button userDeleteBtn;

    @FXML
    private TextField userIdField;

    @FXML
    private Button userUpdateBtn;

    @FXML
    private TableColumn<User, String> useridColumn;

    @FXML
    private TableColumn<User, String> userlevelColumn;

    @FXML
    private TextField userlevelField;

    @FXML
    private TableColumn<User, String> userloginColumn;

    @FXML
    private Button clearFieldsBtn;

    @FXML
    private TextField userloginField;

    @FXML
    private TextField userpassField;

    @FXML
    private TableColumn<User, String> userpasswordColumn;

    @FXML
    private TableView<User> usersTable;


    @FXML
    private TextField clientAddressField;

    @FXML
    private TextField clientCenterDeliveryField;

    @FXML
    private TextField clientNameField;

    @FXML
    private TextField clientNumberField;

    @FXML
    private Button clientregBtn;

    @FXML
    private Pane clientregPane;

    @FXML
    private Button canceladdclientBtn;
    @FXML
    private Button canceladdcourierBtn;

    @FXML
    private TextField courierCenterDeliveryField;

    @FXML
    private TextField courierNameField;

    @FXML
    private TextField courierNumberField;

    @FXML
    private Button courierregBtn;

    @FXML
    private Pane courierregPane;

    @FXML
    private TabPane usersPane;


    ObservableList<User> usersData;


    DBConnection db;

    {
        try {
            db = DBConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    AlertMessage am = new AlertMessage();



    @FXML
    void getUserOnClick (){
        usersTable.setOnMouseClicked(mouseEvent -> {
            if(!usersTable.getSelectionModel().isEmpty()) {
                User user = usersTable.getSelectionModel().getSelectedItem();
                userIdField.setText(user.getId());
                userloginField.setText(user.getLogin());
                userpassField.setText(user.getPassword());
                userlevelField.setText(user.getAccesslevel());
            }
        });
    }


    void fillTable() throws SQLException {
        useridColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userloginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        userpasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        userlevelColumn.setCellValueFactory(new PropertyValueFactory<>("accesslevel"));

        usersTable.setItems(adminUsers.getObservableList());
    }
    void clearFields(){
        userIdField.clear();
        userloginField.clear();
        userpassField.clear();
        userlevelField.clear();
        courierNameField.clear();
        courierNumberField.clear();
        courierCenterDeliveryField.clear();
        clientNameField.clear();
        clientNumberField.clear();
        clientAddressField.clear();
        clientCenterDeliveryField.clear();
    }



    @FXML
    void getClientRegPane(){
        usersPane.setDisable(true);
        clientregPane.setVisible(true);
    }

    @FXML
    void getCourierRegPane(){
        usersPane.setDisable(true);
        courierregPane.setVisible(true);
    }

    @FXML
    void getMainPane(){
        usersPane.setDisable(false);
        clientregPane.setVisible(false);
        courierregPane.setVisible(false);
    }


    @FXML
    void initialize() throws SQLException {
        getUserOnClick();
        fillTable();



        userAddBtn.setOnAction(event -> {
            if(!userloginField.getText().isEmpty() && !userpassField.getText().isEmpty() && !userlevelField.getText().isEmpty()){
                try {
                    if(!db.checkLogin(userloginField.getText())){
                        if(userlevelField.getText().equals("3") || userlevelField.getText().equals("2")) {
                            adminUsers.addUser(userloginField.getText(), userpassField.getText(), userlevelField.getText());
                            clearFields();
                            initialize();
                            am.informationMessage("Успешное добавление пользователя!");
                            System.out.println("Успешное добавление пользователя!");
                        } else if (userlevelField.getText().equals("0")) {
                            getClientRegPane();
                            canceladdclientBtn.setOnAction(event1 -> getMainPane());
                            clientregBtn.setOnAction(event1 -> {
                                if(!clientNameField.getText().isEmpty() && !clientNumberField.getText().isEmpty()
                                    && !clientAddressField.getText().isEmpty() && !clientCenterDeliveryField.getText().isEmpty()){
                                    try {
                                        adminUsers.addClient(clientNameField.getText(), clientNumberField.getText(),
                                                clientAddressField.getText(), clientCenterDeliveryField.getText(), userloginField.getText(), userpassField.getText());
                                        am.informationMessage("Пользователь с уровнем доступа 0 (пользователь) успешно добавлен");
                                        getMainPane();
                                        clearFields();
                                        initialize();
                                    } catch (SQLException e) {
                                        am.warningMessage("По данному ID нет центра доставки. Повторите попытку заново");
                                        getMainPane();
                                    }
                                }
                            });
                        }
                        else if (userlevelField.getText().equals("1")) {
                            getCourierRegPane();
                            canceladdcourierBtn.setOnAction(event1 -> getMainPane());
                            courierregBtn.setOnAction(event1 -> {
                                if(!courierNameField.getText().isEmpty() && !courierNumberField.getText().isEmpty() && !courierCenterDeliveryField.getText().isEmpty()){
                                    try {
                                        adminUsers.addCourier(courierNameField.getText(), courierNumberField.getText(), courierCenterDeliveryField.getText(), userloginField.getText(), userpassField.getText());
                                        am.informationMessage("Пользователь с уровнем доступа 1 (курьер) успешно добавлен");
                                        getMainPane();
                                        clearFields();
                                        initialize();
                                    } catch (SQLException e) {
                                        am.warningMessage("По данному ID нет центра доставки. Повторите попытку заново");
                                        getMainPane();
                                    }
                                }
                            });
                        }
                    }
                    else{
                        am.warningMessage("Логин занят");
                        System.out.println("Логин занят");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                am.warningMessage("Заполните поля перед выполнением команд");
                System.out.println("Не все поля заполнены");
            }
        });

        userDeleteBtn.setOnAction(event -> {
            if (!userloginField.getText().isEmpty() && !userpassField.getText().isEmpty() && !userlevelField.getText().isEmpty()) {
                try {
                    am.confirmationMessage("Вы действительно хотите удалить этого пользователя?");
                    if(am.checkconfirm()) {
                        try {
                            adminUsers.deleteUser(userloginField.getText(), userIdField.getText());
                            clearFields();
                            initialize();
                            am.informationMessage("Удаление прошло успешно");
                        }
                        catch (Exception e){
                            am.warningMessage("Невозможно удалить администратора");
                        }
                    }
                    else{
                        ;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                am.warningMessage("Заполните поля перед выполнением команд");
                System.out.println("Не все поля заполнены");
            }
        }
        );

        userUpdateBtn.setOnAction(event -> {
            if (!userloginField.getText().isEmpty() && !userpassField.getText().isEmpty() && !userlevelField.getText().isEmpty()) {
                try {
                    am.confirmationMessage("Вы действительно хотите обновить данные этого пользователя?");
                    if(am.checkconfirm()) {
                        try {
                            if(!db.checkLogin(userloginField.getText())) {
                                adminUsers.updateUser(userIdField.getText(), userloginField.getText(), userpassField.getText(), userlevelField.getText());
                                clearFields();
                                initialize();
                                am.informationMessage("Обновление данных прошло успешно");
                            }
                            else{
                                am.warningMessage("Данный логин занят");
                            }
                        }
                        catch (Exception e){
                            am.warningMessage("Невозможно удалить администратора");
                        }
                    }
                    else{
                        ;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                am.warningMessage("Заполните поля перед выполнением команд");
                System.out.println("Не все поля заполнены");
            }
        });


        clearFieldsBtn.setOnAction(event -> clearFields());


        backBtn.setOnAction(event -> Utils.changeScene(event,"adminmain.fxml", "Главная страница (Администратор)"));

    }
}
