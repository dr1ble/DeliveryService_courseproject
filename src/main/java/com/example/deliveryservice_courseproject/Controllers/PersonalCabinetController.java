package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Models.Data;
import com.example.deliveryservice_courseproject.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class PersonalCabinetController {
    Data data = Data.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Button chngdataBtn;

    @FXML
    private Text loginText;

    @FXML
    private Text nameText;

    @FXML
    private Text numberText;

    @FXML
    private Text passwordText;

    @FXML
    private Text addressText;

    @FXML
    void initialize() {
        nameText.setText(data.getUser().getName());
        numberText.setText(data.getUser().getNumber());
        addressText.setText(data.getUser().getAddress());
        loginText.setText(data.getUser().getLogin());
        passwordText.setText(data.getUser().getPassword());
        backBtn.setOnAction(event -> Utils.changeScene(event, "mainpage.fxml", "Главная страница"));

        chngdataBtn.setOnAction(event -> Utils.changeScene(event, "changepersonaldata.fxml", "Изменить персональный данные"));
    }
}
