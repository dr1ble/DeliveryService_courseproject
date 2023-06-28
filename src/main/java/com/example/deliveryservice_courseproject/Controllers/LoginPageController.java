package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.AlertMessage;
import com.example.deliveryservice_courseproject.DBConnection;
import com.example.deliveryservice_courseproject.HashCoder;
import com.example.deliveryservice_courseproject.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logBtn;

    @FXML
    private TextField logField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button regBtn;

    @FXML
    void initialize() {
        regBtn.setOnAction(event -> Utils.changeScene(event, "regpage.fxml", "Регистрация"));

//        logBtn.setOnAction(event -> Utils.changeScene(event, "mainpage.fxml", "Главная страница"));
        logBtn.setOnAction(event -> {
            AlertMessage alertMessage = new AlertMessage();
            String login = logField.getText().trim();
            String password = passField.getText().trim();
            if(!login.equals("") && !password.equals("")){
                try {
                    if(loginUser(login, password)) {
                        Utils.changeScene(event, "mainpage.fxml", "Главная страница");
                    }
                    else{
                        alertMessage.errorMessage("Такого пользователя не существует!");
                        System.out.println("Такого пользователя не существует!");
                        passField.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
                        passField.clear();
                        logField.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
                        logField.clear();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                alertMessage.warningMessage("Логин или пароль не введены!");
                System.out.println("Логин или пароль не введены");
                passField.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
                passField.clear();
                logField.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
                logField.clear();
            }
        });
    }

    private boolean loginUser(String login, String password) throws SQLException {

        DBConnection.getInstance().getUser(login, HashCoder.toHash(password));

        ResultSet resultSet = DBConnection.getInstance().getUser(login, HashCoder.toHash(password));
        int count = 0;
        while (resultSet.next()){
            count++;
        }
        if(count>=1){
            System.out.println("Success authorization! Hello " + login + "!");
            return true;
        }
        return false;
    }
}
