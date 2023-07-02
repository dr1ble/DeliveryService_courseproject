package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Models.Data;
import Utils.AlertMessage;
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
    private Button acceptPackageBtn;

    @FXML
    private Button allPackagesBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Text namefield;

    @FXML
    void initialize() {
        namefield.setText("Здравствуйте, " + data.getCourier().getName() + "!");

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
