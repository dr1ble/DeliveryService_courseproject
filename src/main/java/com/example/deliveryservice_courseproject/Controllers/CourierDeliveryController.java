package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Models.Data;
import com.example.deliveryservice_courseproject.DBConnection;
import com.example.deliveryservice_courseproject.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import Utils.AlertMessage;
public class CourierDeliveryController {

    Data data = Data.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Button curgavepckgBtn;

    @FXML
    private Button curtakepackageBtn;

    @FXML
    private ChoiceBox<String> pckgestodeliveryChoice;

    @FXML
    private TextField recipientAddressField;

    @FXML
    private TextField recipientNameField;

    @FXML
    private DatePicker enddeliveryDate;

    @FXML
    private TextField recipientNumberField;

    @FXML
    private ChoiceBox<String> whatpackageChoice;


    AlertMessage am = new AlertMessage();
    DBConnection db;

    {
        try {
            db = DBConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void insertChoice() throws SQLException {
        pckgestodeliveryChoice.getItems().clear();
        for (int i = 0; i < db.getDataPackagesForCur(data.getCourier(), "Принята").size(); i++) {
            pckgestodeliveryChoice.getItems().addAll(db.getDataPackagesForCur(data.getCourier(), "Принята").get(i).getId() + " " + db.getDataPackagesForCur(data.getCourier(), "Принята").get(i).getWeight());
        }
        whatpackageChoice.getItems().clear();
        for (int i = 0; i < db.getDataPackagesForCur(data.getCourier(), "У курьера").size(); i++) {
            whatpackageChoice.getItems().addAll(db.getDataPackagesForCur(data.getCourier(), "У курьера").get(i).getId() + " "
                    + db.getDataPackagesForCur(data.getCourier(), "У курьера").get(i).getWeight() + " " + db.getDataPackagesForCur(data.getCourier(), "У курьера").get(i).getRecipient_id());
        }
    }
    void insertFields() throws SQLException {
        if(whatpackageChoice.getValue() != null) {
            recipientNameField.setText(db.getRecipientData(whatpackageChoice.getValue().split(" ")[2]).getName());
            recipientAddressField.setText(db.getRecipientData(whatpackageChoice.getValue().split(" ")[2]).getAddress());
            recipientNumberField.setText(db.getRecipientData(whatpackageChoice.getValue().split(" ")[2]).getNumber());
        }
        else ;
    }

    @FXML
    void initialize() throws SQLException {

        insertChoice();

        whatpackageChoice.setOnAction(event ->
        {
            try {
                insertFields();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        curtakepackageBtn.setOnAction(event -> {
            if(pckgestodeliveryChoice.getValue()!=null){
                try {
                    am.confirmationMessage("Вы подтверждаете что доставите эту посылку?");
                    if(am.checkconfirm()){
                        db.setPackageStatus("У курьера", pckgestodeliveryChoice.getValue().split(" ")[0]);
                        System.out.println("Посылка взята");
                        initialize();
                        pckgestodeliveryChoice.getSelectionModel().clearSelection();
                        am.informationMessage("Посылка взята");
                    }
                    else{
                        ;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println("Посылка не выбрана");
            }
        });

        curgavepckgBtn.setOnAction(event ->{
            if(whatpackageChoice.getValue()!=null  && enddeliveryDate.getValue() != null){
                try {
                    am.confirmationMessage("Вы подтверждаете что посылка отдана клиенту?");
                    if(am.checkconfirm()) {
                        db.setPackageStatus("Получена", whatpackageChoice.getValue().split(" ")[0]);
                        db.setPackageEndDate(String.valueOf(enddeliveryDate.getValue()), whatpackageChoice.getValue().split(" ")[0]);
                        initialize();
                        whatpackageChoice.getSelectionModel().clearSelection();
                        recipientNameField.clear();
                        recipientAddressField.clear();
                        recipientNumberField.clear();
                        enddeliveryDate.setValue(null);
                        am.informationMessage("Посылка получена клиентом");
                    }
                    else{
                        ;
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        backBtn.setOnAction(event -> Utils.changeScene(event,"couriermain.fxml", "Главная страница (Курьер)"));

    }

}