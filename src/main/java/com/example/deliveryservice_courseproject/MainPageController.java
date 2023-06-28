package com.example.deliveryservice_courseproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MainPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitBtn;

    @FXML
    private Button lkBtn;

    @FXML
    private ImageView lkImage;

    @FXML
    private Text namefield;


    @FXML
    void initialize() {
        namefield.setText("Здравствуйте, " + " john " + " !"); // !!!!!!!!!!!!
        lkBtn.setOnAction(event -> Utils.changeScene(event, "personalcabinet.fxml", "Личный кабинет"));

        exitBtn.setOnAction(event -> {
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.confirmationMessage("Вы действительно хотите выйти из аккаунта?");
            if (alertMessage.checkconfirm())
                Utils.changeScene(event, "homepage.fxml", "DeliveryService");
            else {
                ;
            }
        });
    }

}
