package com.example.deliveryservice_courseproject.Controllers;

import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Models.Data;
import Models.SendPackagePageModel;
import com.example.deliveryservice_courseproject.DBConnection;
import com.example.deliveryservice_courseproject.Utils;
import Utils.AlertMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class SendPackagePageController {
    Data data = Data.getInstance();

    SendPackagePageModel sendPackageModel = new SendPackagePageModel();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private CheckBox fastCheckBox;

    @FXML
    private Button sendBtn;

    @FXML
    private ChoiceBox<String> toWho;

    @FXML
    private CheckBox usualCheckBox;

    @FXML
    private TextArea weightField;

    @FXML
    private Text packageIDtext;

    @FXML
    void initialize() throws SQLException {
        fastCheckBox.setOnAction(event -> {
            usualCheckBox.setSelected(false);
        });
        usualCheckBox.setOnAction(event -> {
            fastCheckBox.setSelected(false);
        });

        for (int i = 0; i < sendPackageModel.getClients().size(); i++) {
            toWho.getItems().addAll(sendPackageModel.getClients().get(i));
        }

        sendBtn.setOnAction(event -> {
            try {
                AlertMessage alertMessage = new AlertMessage();
                if(!toWho.getSelectionModel().isEmpty() && (!fastCheckBox.isSelected() || !usualCheckBox.isSelected())
                && !weightField.getText().isEmpty()) {
                    try {
                        DBConnection.getInstance().startDelivery(data.getClient().getId(),
                                toWho.getSelectionModel().getSelectedItem().split(" ")[0], fastCheckBox.isSelected() ? "1" : "0",
                                weightField.getText().trim(), "В обработке");
                    }
                    catch (Exception e){
                        return;
                    }
                    packageIDtext.setText("Ваш ID-посылки: " + DBConnection.getInstance().getPackageId());
                    alertMessage.informationMessage("Отправление передано в обработку! Запишите полученнный ID на коробке и отнесите ее в ближайший Центр Доставки");
                }
                else{
                    System.out.println("Не все поля выбраны!");
                    alertMessage.warningMessage("Не все поля заполнены. Невозможно продолжить оформление");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        backBtn.setOnAction(event -> Utils.changeScene(event,"mainpage.fxml","Главная страница"));
    }
}
