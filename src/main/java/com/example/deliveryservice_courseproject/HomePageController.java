package com.example.deliveryservice_courseproject;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HomePageController {

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
            String login = logField.getText().trim();
            String password = passField.getText().trim();
            if(!login.equals("") && !password.equals("")){
                try {
                    if(loginUser(login, password)) {
                        Utils.changeScene(event, "mainpage.fxml", "Главная страница");
                    }
                    else{
                        System.out.println("Такого пользователя не существует!");
                        passField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                        passField.clear();
                        logField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                        logField.clear();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println("Логин или пароль не введены");
                passField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                passField.clear();
                logField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                logField.clear();
            }
        });
    }

    private boolean loginUser(String login, String password) throws SQLException {

        DBConnection.getInstance().getUser(login, password);

        ResultSet resultSet = DBConnection.getInstance().getUser(login, password);
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
