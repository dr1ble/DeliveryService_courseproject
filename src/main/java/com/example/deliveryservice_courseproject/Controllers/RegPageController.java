package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Utils.AlertMessage;
import com.example.deliveryservice_courseproject.Client;
import com.example.deliveryservice_courseproject.DBConnection;
import com.example.deliveryservice_courseproject.User;
import com.example.deliveryservice_courseproject.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressField;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button regBtn;

    @FXML
    void initialize() throws SQLException {

        regBtn.setOnAction(event -> {
            AlertMessage alertMessage = new AlertMessage();
            try {
                if (!nameField.getText().isEmpty() && !numberField.getText().isEmpty() && !addressField.getText().isEmpty()
                        && !loginField.getText().isEmpty() && !passField.getText().isEmpty()) {
                    if (!DBConnection.getInstance().checkLogin(loginField.getText())) {
                        signUp();
                        System.out.println("Успешная регистрация!");
                        alertMessage.informationMessage("Регистрация прошла успешно");
                        Utils.changeScene(event, "loginpage.fxml", "DeliveryService");
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
                Utils.changeScene(event, "loginpage.fxml", "DeliveryService");
            else {
                ;
            }
        });
    }
    private void signUp() throws SQLException {
        String name = nameField.getText();
        String number = numberField.getText();
        String address = addressField.getText();
        String login = loginField.getText();
        String password = passField.getText();

        User user = new User("",login, password, "0");
        Client client = new Client("", name, number, address, "", "");
        DBConnection.getInstance().signUpUser(user);
        DBConnection.getInstance().signUpClient(client, user);
    }

}
