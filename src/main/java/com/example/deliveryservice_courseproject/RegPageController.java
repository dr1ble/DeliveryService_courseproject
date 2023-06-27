package com.example.deliveryservice_courseproject;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressField;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button regBtn;

    @FXML
    void initialize() throws SQLException {

        regBtn.setOnAction(event -> {
            try {
                if (!nameField.getText().isEmpty() && !numberField.getText().isEmpty() && !addressField.getText().isEmpty()
                        && !loginField.getText().isEmpty() && !passField.getText().isEmpty()) {
                    if (!DBConnection.getInstance().checkLogin(loginField.getText())) {
                        DBConnection.getInstance().signUpUser(nameField.getText(),
                                numberField.getText(),
                                addressField.getText(),
                                loginField.getText(),
                                passField.getText());
                        System.out.println("Успешная регистрация!");
                        Utils.changeScene(event, "homepage.fxml", "DeliveryService");
                    }
                    else{
                        System.out.println("Логин уже занят");
                    }
                }
                else{
                    System.out.println("Регистрационные поля не заполнены!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        cancelBtn.setOnAction(event -> Utils.changeScene(event, "homepage.fxml", "DeliveryService"));
    }

}
