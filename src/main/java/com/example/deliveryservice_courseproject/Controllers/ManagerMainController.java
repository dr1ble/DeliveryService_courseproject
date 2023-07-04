package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Models.Data;
import com.example.deliveryservice_courseproject.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import com.example.deliveryservice_courseproject.Other.AlertMessage;

public class ManagerMainController {

    Data data = Data.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitBtn;
    @FXML
    private Button acceptPackageBtn;
    @FXML
    private Text namefield;

    @FXML
    private Button allPackagesBtn;

    @FXML
    private Button deliverycentersBtn;

    @FXML
    private Button regcourierBtn;


    @FXML
    private Button sendPackageBtn;

    @FXML
    void initialize() {
        namefield.setText("Здравствуйте, " + data.getUser().getLogin() + "!");

        regcourierBtn.setOnAction(event -> Utils.changeScene(event, "regcourier.fxml","Регистрация курьера"));


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

        allPackagesBtn.setOnAction(event -> Utils.changeScene(event, "allpackages.fxml","Все отправления"));


        acceptPackageBtn.setOnAction(event ->
                Utils.changeScene(event,"acceptpackage.fxml", "Подтверждение посылки"));

        deliverycentersBtn.setOnAction(event ->
                Utils.changeScene(event,"alldeliverycenters.fxml", "Центры доставки"));
    }

}
