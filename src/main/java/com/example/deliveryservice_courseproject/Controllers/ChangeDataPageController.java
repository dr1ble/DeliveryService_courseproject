package com.example.deliveryservice_courseproject.Controllers;

import com.example.deliveryservice_courseproject.Other.HashCoder;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import com.example.deliveryservice_courseproject.Other.AlertMessage;

import com.example.deliveryservice_courseproject.Models.Data;
import com.example.deliveryservice_courseproject.Models.DBConnection;
import com.example.deliveryservice_courseproject.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ChangeDataPageController {

    Data data = Data.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Button savechangesBtn;

    @FXML
    private TextField changeAddressField;

    @FXML
    private TextField changeLoginField;

    @FXML
    private TextField changeNameField;

    @FXML
    private TextField changeNumberField;

    @FXML
    private PasswordField newPassField;

    @FXML
    private PasswordField oldPassField;


    @FXML
    void initialize() {
        backBtn.setOnAction(event -> Utils.changeScene(event, "personalcabinet.fxml", "Личный кабинет"));

        savechangesBtn.setOnAction(event -> {
            try {
                AlertMessage alertMessage = new AlertMessage();
                if (Objects.equals(HashCoder.toHash(oldPassField.getText().trim()), data.getUser().getPassword())) {
                    DBConnection.getInstance().updatePass(data.getUser().getLogin(), HashCoder.toHash(newPassField.getText()));
                    Data.getInstance().getUser().setPassword(HashCoder.toHash(newPassField.getText()));
                    System.out.println("Пароль успешно изменён!");
                }
                else {
                    ;
                }
                try {
                    if(!changeLoginField.getText().isEmpty()){
                        DBConnection.getInstance().updateLogin(data.getUser().getLogin(),changeLoginField.getText());
                        Data.getInstance().getUser().setLogin(changeLoginField.getText());
                        System.out.println("Логин изменён!");
                    }
                    else{
                        ;
                    }
                }
                catch (Exception e){
                    System.out.println("Логин уже занят! Выберите другой логин");
                    changeLoginField.clear();
                    alertMessage.warningMessage("Логин уже занят! Выберите другой логин!");
                }
                if(!changeNameField.getText().isEmpty()){
                    DBConnection.getInstance().updateName(data.getUser().getLogin(), changeNameField.getText());
                    Data.getInstance().getClient().setName(changeNameField.getText());
                    System.out.println("Имя изменено!");
                }
                else{
                    ;
                }
                if(!changeNumberField.getText().isEmpty()){
                    DBConnection.getInstance().updateNumber(data.getUser().getLogin(), changeNumberField.getText());
                    Data.getInstance().getClient().setNumber(changeNumberField.getText());
                    System.out.println("Номер изменен!");
                }
                else{
                    ;
                }
                if(!changeAddressField.getText().isEmpty()){
                    DBConnection.getInstance().updateAddress(data.getUser().getLogin(), changeAddressField.getText());
                    Data.getInstance().getClient().setAddress(changeAddressField.getText());
                    System.out.println("Адрес изменен!");
                }
                else{
                    ;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}

