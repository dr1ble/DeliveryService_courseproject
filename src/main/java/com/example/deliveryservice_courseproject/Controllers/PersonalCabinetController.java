package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PersonalCabinetController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Button chngdataBtn;

    @FXML
    void initialize() {
        backBtn.setOnAction(event -> Utils.changeScene(event, "mainpage.fxml", "Главная страница"));
    }
}
