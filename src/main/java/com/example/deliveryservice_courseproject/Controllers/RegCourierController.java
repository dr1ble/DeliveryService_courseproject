package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Models.*;
import com.example.deliveryservice_courseproject.Other.AlertMessage;
import com.example.deliveryservice_courseproject.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegCourierController {

    RegCourier regCourier = new RegCourier();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelBtn;

    @FXML
    private ChoiceBox<String> deliverycenterChoice;

    @FXML
    private TextField logincourField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button regBtn;
    AlertMessage alertMessage = new AlertMessage();


    DBConnection db;
    {
        try {
            db = DBConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void initialize() throws SQLException {
        ObservableList<DeliveryCenter> dcList= regCourier.getObservableDcList();

        for (int i = 0; i < dcList.size(); i++) {
            deliverycenterChoice.getItems().addAll(dcList.get(i).getId() + " " + dcList.get(i).getAddress() + " " + dcList.get(i).getName());
        }

        regBtn.setOnAction(event -> {
            try {
                if (!nameField.getText().isEmpty() && !numberField.getText().isEmpty() && deliverycenterChoice.getValue() != null
                        && !logincourField.getText().isEmpty() && !passField.getText().isEmpty()) {
                    System.out.println(logincourField.getText());
                    if (!db.checkLogin(logincourField.getText())) {
                        regCourier.signUp(nameField.getText(), numberField.getText(), deliverycenterChoice.getValue().split(" ")[0], logincourField.getText(), passField.getText());
                        System.out.println("Успешная регистрация курьера!");
                        alertMessage.informationMessage("Успешная регистрация курьера!");
                        Utils.changeScene(event, "managermain.fxml", "Главная страница (Менеджер)");
                    } else {
                        System.out.println("Логин уже занят");
                        alertMessage.warningMessage("Логин уже занят. Выберите другой логин");
                    }
                } else {
                    alertMessage.errorMessage("Регистрационные поля не заполнены!");
                    System.out.println("Регистрационные поля не заполнены!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        cancelBtn.setOnAction(event -> {
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.confirmationMessage("Вы действительно хотите отменить регистрацию?");
            if (alertMessage.checkconfirm())
                Utils.changeScene(event, "managermain.fxml", "Главная страница (Менеджер)");
            else {
                ;
            }
        });
    }

//    private ArrayList<String> getFieldsData(){
//        ArrayList<String> data = new ArrayList<String>();
//        data.add(nameField.getText().trim());
//        data.add(numberField.getText().trim());
//        data.add(deliverycenterChoice.getValue().split(" ")[0]);
//        data.add(logincourField.getText().trim());
//        data.add(passField.getText().trim());
//        return data;
//    }
}
