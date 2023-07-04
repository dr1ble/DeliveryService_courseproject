package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Models.Data;
import com.example.deliveryservice_courseproject.Other.AlertMessage;
import com.example.deliveryservice_courseproject.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class AdminMainController {

    Data data = Data.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button clientsBtn;

    @FXML
    private Button couriersBtn;

    @FXML
    private Button deliverycentersBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Text namefield;

    @FXML
    private Button packagesBtn;

    @FXML
    private Button usersBtn;

    @FXML
    void initialize() {
        namefield.setText("Здравствуйте, " + data.getUser().getLogin() + "!");

        usersBtn.setOnAction(event -> Utils.changeScene(event, "adminusers.fxml", "Пользователи"));

        clientsBtn.setOnAction(event -> Utils.changeScene(event, "adminclients.fxml", "Клиенты"));

        couriersBtn.setOnAction(event -> Utils.changeScene(event, "admincouriers.fxml", "Курьеры"));

        packagesBtn.setOnAction(event -> Utils.changeScene(event, "adminpackages.fxml", "Отправления"));

        deliverycentersBtn.setOnAction(event -> Utils.changeScene(event, "admindeliverycenters.fxml", "Центры доставки"));



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