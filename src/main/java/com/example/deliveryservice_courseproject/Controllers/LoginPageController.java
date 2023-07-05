package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Models.LoginPageData;
import com.example.deliveryservice_courseproject.Other.AlertMessage;
import com.example.deliveryservice_courseproject.Models.Data;
import com.example.deliveryservice_courseproject.Models.DBConnection;
import com.example.deliveryservice_courseproject.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {
    Data data = Data.getInstance();
    LoginPageData loginPageData = new LoginPageData();

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
                    if(loginPageData.login(login, password)) {
                        if (DBConnection.getInstance().getAccessLevel(login) == 0) {
                            if (loginPageData.loginClient(login, password)) {
                                data.setUser(loginPageData.getUser());
                                data.setClient(loginPageData.getClient());
                                Utils.changeScene(event, "mainpage.fxml", "Главная страница");
                            }
                        } else if (DBConnection.getInstance().getAccessLevel(login) == 2) {
                            if (loginPageData.loginManager(login, password)) {
                                data.setUser(loginPageData.getUser());
                                Utils.changeScene(event, "managermain.fxml", "Главная страница (Менеджер)");
                            }
                        } else if (DBConnection.getInstance().getAccessLevel(login) == 1) {
                            if (loginPageData.loginCourier(login, password)) {
                                data.setUser(loginPageData.getUser());
                                data.setCourier(loginPageData.getCourier());
                                Utils.changeScene(event, "couriermain.fxml", "Главная страница (Курьер)");
                            }
                        } else if (DBConnection.getInstance().getAccessLevel(login) == 3) {
                            if(loginPageData.loginAdmin(login, password)){
                                data.setUser(loginPageData.getUser());
                                Utils.changeScene(event, "adminmain.fxml", "Главная страница (Администратор)");
                            }
                        }
                    }
                    else {
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

}
