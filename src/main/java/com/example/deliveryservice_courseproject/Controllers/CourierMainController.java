package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Models.Data;
import com.example.deliveryservice_courseproject.Other.AlertMessage;
import com.example.deliveryservice_courseproject.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CourierMainController {

    Data data = Data.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deliveryBtn;


    @FXML
    private Button mydeliveriesBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Text namefield;

    @FXML
    void initialize() {
        namefield.setText("Здравствуйте, " + data.getCourier().getName() + "!");

        deliveryBtn.setOnAction(event ->
                Utils.changeScene(event,"courierdelivery.fxml","Доставка"));

        exitBtn.setOnAction(event -> {
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.confirmationMessage("Вы действительно хотите выйти из аккаунта?");
            if (alertMessage.checkconfirm()) {
                Utils.changeScene(event, "loginpage.fxml", "DeliveryService");
                Data.getInstance().setUser(null);
            }
            else {
                ;
            }
        });

    }

}
