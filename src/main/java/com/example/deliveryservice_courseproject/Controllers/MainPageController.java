package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Models.MainPageModel;
import com.example.deliveryservice_courseproject.Other.AlertMessage;
import com.example.deliveryservice_courseproject.Models.Data;
import com.example.deliveryservice_courseproject.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MainPageController {
    Data data = Data.getInstance();
    MainPageModel mainPageModel = new MainPageModel();

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
    private Button sendPackageBtn;

    @FXML
    private Button myPackagesBtn;


    @FXML
    void initialize() {
        namefield.setText("Здравствуйте, " + data.getClient().getName() + " !"); // !!!!!!!!!!!!
        lkBtn.setOnAction(event -> Utils.changeScene(event, "personalcabinet.fxml", "Личный кабинет"));
        sendPackageBtn.setOnAction(event -> Utils.changeScene(event,"sendpackagepage.fxml", "Отправить посылку"));
        myPackagesBtn.setOnAction(event -> Utils.changeScene(event, "mypackagespage.fxml", "Отправления"));




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
