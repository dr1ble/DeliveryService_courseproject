package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Utils.AlertMessage;
import com.example.deliveryservice_courseproject.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegCourierController {

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

        for (int i = 0; i < db.getDCdata().size(); i++) {
            deliverycenterChoice.getItems().addAll(db.getDCdata().get(i).getId() + " " + db.getDCdata().get(i).getAddress() + " " + db.getDCdata().get(i).getName());
        }



        regBtn.setOnAction(event -> {
            try {
                if (!nameField.getText().isEmpty() && !numberField.getText().isEmpty() && deliverycenterChoice.getValue() != null
                        && !logincourField.getText().isEmpty() && !passField.getText().isEmpty()) {
                    System.out.println(logincourField.getText());
                    if (!db.checkLogin(logincourField.getText())) {
                        signUp();
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


    private void signUp() throws SQLException {
        String name = nameField.getText();
        String number = numberField.getText();
        String dcid = deliverycenterChoice.getValue().split(" ")[0];
        String login = logincourField.getText();
        String password = passField.getText();

        User user = new User("",login, password, "1");
        Courier courier = new Courier("",name,number,dcid,"");
        DBConnection.getInstance().signUpUser(user);
        DBConnection.getInstance().signUpCourier(courier, user);
    }
}
