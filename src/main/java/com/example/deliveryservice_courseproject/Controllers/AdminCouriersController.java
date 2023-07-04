package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Models.Courier;
import com.example.deliveryservice_courseproject.Models.DBConnection;
import com.example.deliveryservice_courseproject.Models.User;
import com.example.deliveryservice_courseproject.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import com.example.deliveryservice_courseproject.Other.AlertMessage;

public class AdminCouriersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Button clearFieldsBtn;

    @FXML
    private Button courierAddBtn;

    @FXML
    private Button courierDeleteBtn;

    @FXML
    private TextField courierIdField;

    @FXML
    private TextField courierIduserField;

    @FXML
    private Button courierUpdateBtn;

    @FXML
    private TextField courierdcidField;

    @FXML
    private TextField couriernameField;

    @FXML
    private TextField couriernumberField;

    @FXML
    private TableView<Courier> couriersTable;

    @FXML
    private TableColumn<Courier, String> dcIdColumn;

    @FXML
    private TableColumn<Courier, String> idColumn;

    @FXML
    private TableColumn<Courier, String> nameColumn;

    @FXML
    private TableColumn<Courier, String> numberColumn;

    @FXML
    private TableColumn<Courier, String> userIdColumn;

    @FXML
    private Button usercancelBtn;

    @FXML
    private TextField userloginField;

    @FXML
    private PasswordField userpassField;

    @FXML
    private Button userregBtn;

    @FXML
    private Pane userregPane;

    @FXML
    private TabPane couriersPane;

    ObservableList<Courier> couriersData;

    AlertMessage am = new AlertMessage();

    DBConnection db;

    {
        try {
            db = DBConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void getUserRegPane(){
        couriersPane.setDisable(true);
        userregPane.setVisible(true);
    }

    @FXML
    void getMainPane(){
        couriersPane.setDisable(false);
        userregPane.setVisible(false);
    }

    @FXML
    void getUserOnClick (){
        couriersTable.setOnMouseClicked(mouseEvent -> {
            if(!couriersTable.getSelectionModel().isEmpty()) {
                Courier courier = couriersTable.getSelectionModel().getSelectedItem();
                courierIdField.setText(courier.getId());
                courierIduserField.setText(courier.getUser_id());
                couriernameField.setText(courier.getName());
                couriernumberField.setText(courier.getNumber());
                courierdcidField.setText(courier.getDelivery_center_id());
            }
        });
    }


    void fillTable() throws SQLException {
        couriersData = db.getCouriersData();
        couriersTable.setItems(couriersData);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        dcIdColumn.setCellValueFactory(new PropertyValueFactory<>("delivery_center_id"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
    }
    void clearFields(){
        courierIdField.clear();
        courierIduserField.clear();
        couriernameField.clear();
        couriernumberField.clear();
        courierdcidField.clear();
        userloginField.clear();
        userpassField.clear();
    }

    private boolean checkFields(){
        if (!courierIdField.getText().isEmpty() && !courierIduserField.getText().isEmpty() && !couriernameField.getText().isEmpty()
                && !couriernumberField.getText().isEmpty() && !courierdcidField.getText().isEmpty()){
            return true;
        }
        return false;
    }


    @FXML
    void initialize() throws SQLException {
        getUserOnClick();
        fillTable();

        courierUpdateBtn.setOnAction(event -> {
            if(checkFields()){
                am.confirmationMessage("Вы действительно хотите обновить данные данного курьера?");
                if(am.checkconfirm()) {
                    try {
                        updateCourier();
                        clearFields();
                        initialize();
                        am.informationMessage("Обновление данных прошло успешно");
                    } catch (Exception e) {
                        am.errorMessage("По данному ID нет центра доставки. Выберите другой ID");
                        getMainPane();
                    }
                }
            }
            else{
                am.warningMessage("Заполните поля перед выполнением команды");
            }
        });

        courierAddBtn.setOnAction(event -> {
            if(checkFields()){
                try {
                    am.confirmationMessage("Вы действительно хотите добавить данного клиента?");
                    if (am.checkconfirm()) {
                        getUserRegPane();
                        usercancelBtn.setOnAction(event1 -> getMainPane());
                        userregBtn.setOnAction(event1 -> {
                            if (!userloginField.getText().isEmpty() && !userpassField.getText().isEmpty()) {
                                try {
                                    if (!db.checkLogin(userloginField.getText())) {
                                        addCourier();
                                        getMainPane();
                                        clearFields();
                                        initialize();
                                        am.informationMessage("Добавление клиента прошло успешно");
                                    } else {
                                        am.warningMessage("Логин уже занят. Выберите другой логин");
                                    }
                                } catch (SQLException e) {
                                    am.errorMessage("По данному ID нет центра доставки. Выберите другой ID");
                                    getMainPane();
                                }
                            } else {
                                am.informationMessage("Заполните все поля для регистрации пользвователя");
                            }
                        });
                    }
                } catch (RuntimeException e) {
                    am.errorMessage("По данному ID нет центра доставки. Выберите другой ID");
                }
            }
            else am.warningMessage("Заполните поля перед выполнением команды");
        });

        courierDeleteBtn.setOnAction(event -> {
            if(checkFields()){
                try {
                    am.confirmationMessage("Вы действительно хотите удалить этого курьера?");
                    if(am.checkconfirm()) {
                        deleteCourier();
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
            else am.warningMessage("Заполните поля перед выполнением команды");
        });

        backBtn.setOnAction(event -> Utils.changeScene(event,"adminmain.fxml", "Главная страница (Администратор)"));
    }

    private void updateCourier() throws Exception {

        String id = courierIdField.getText().trim();
        String name = couriernameField.getText().trim();
        String number = couriernumberField.getText().trim();
        String dcid = courierdcidField.getText().trim();
        String user_id = courierIduserField.getText().trim();

        Courier courier = new Courier(id, name, number, dcid, user_id);
        if(db.checkDc(dcid)) {
            db.updateCourier(courier);
        }
        else{
            throw new SQLException();
        }
    }

    private void deleteCourier() throws Exception {

        String id = courierIdField.getText().trim();
        String user_id = courierIduserField.getText().trim();

        db.deleteCourier(id, user_id);
    }

    private void addCourier() throws SQLException {
        String name = couriernameField.getText().trim();
        String number = couriernumberField.getText().trim();
        String address = courierdcidField.getText().trim();
        String login = userloginField.getText();
        String password = userpassField.getText();

        User user = new User("",login, password, "1");
        Courier courier = new Courier("", name, number, address, "");
        if(db.checkDc(address)) {
            db.signUpUser(user);
            db.signUpCourier(courier, user);
        }
        else{
            throw new SQLException();
        }
    }
}
