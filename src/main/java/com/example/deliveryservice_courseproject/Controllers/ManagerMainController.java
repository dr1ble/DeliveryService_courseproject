package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Models.Data;
import com.example.deliveryservice_courseproject.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import Utils.AlertMessage;
import org.w3c.dom.events.MouseEvent;

public class ManagerMainController {

    Data data = Data.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitBtn;

    @FXML
    private Button myPackagesBtn;


    @FXML
    private Button acceptPackageBtn;
    @FXML
    private Text namefield;

    @FXML
    private Button sendPackageBtn;

    @FXML
    void initialize() {
        namefield.setText("Здравствуйте, " + data.getUser().getLogin() + "!");


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

        acceptPackageBtn.setOnAction(event ->
                Utils.changeScene(event,"acceptpackage.fxml", "Посылки"));
    }

}
