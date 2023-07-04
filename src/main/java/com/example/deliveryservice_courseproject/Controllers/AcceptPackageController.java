package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Models.DBConnection;
import com.example.deliveryservice_courseproject.*;
import com.example.deliveryservice_courseproject.Models.Package;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import com.example.deliveryservice_courseproject.Other.AlertMessage;

public class AcceptPackageController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<Package, String> courierIdColumn;

    @FXML
    private TableColumn<Package, String> departcenterColumn;

    @FXML
    private TableColumn<Package, String> endDateColumn;

    @FXML
    private TableColumn<Package, String> packageidColumn;

    @FXML
    private TableColumn<Package, String> recievecenterColumn;

    @FXML
    private TableColumn<Package, String> recipientIdColumn;
    @FXML
    private TableColumn<Package, String> senderIdColumn;

    @FXML
    private TableColumn<Package, String> startDateColumn;

    @FXML
    private TableColumn<Package, String> statusColumn;

    @FXML
    private TableColumn<Package, String> typedeliveryColumn;

    @FXML
    private TableColumn<Package, String> weightColumn;

    @FXML
    private TableView<Package> acceptPackage;

    ObservableList<Package> listPackages;

    @FXML
    private Button injectBtn1;

    @FXML
    private TextField receivingcenterField;
    @FXML
    private DatePicker senddateDatePicker;

    @FXML
    private ChoiceBox<String> IdcourierChoice;
    @FXML
    private ChoiceBox<String> sendingcenterChoice;

    @FXML
    private TextField statusField;

    @FXML
    private Text packageIDTEXT;

    private String curDc;

    DBConnection db;

    {
        try {
            db = DBConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void getPackageOnClick (){
        acceptPackage.setOnMouseClicked(mouseEvent -> {
            if(!acceptPackage.getSelectionModel().isEmpty()) {
                Package pckge = acceptPackage.getSelectionModel().getSelectedItem();
                packageIDTEXT.setText("ID: " + pckge.getId());
                statusField.setText(pckge.getStatus());
                statusField.setText(statusField.getText().replace("В обработке", "Принята"));
                receivingcenterField.setText(pckge.getReceivingcenter_id());
                curDc = pckge.getReceivingcenter_id();
                try {
                    sendingcenterChoice.getItems().clear();
                    IdcourierChoice.getItems().clear();
                    for (int i = 0; i < db.getDataCourierForCurDC(curDc).size(); i++) {
                        String courier_name = db.getDataCourierForCurDC(curDc).get(i).getName();
                        String courier_id = db.getDataCourierForCurDC(curDc).get(i).getId();
                        IdcourierChoice.getItems().addAll(courier_name + " " + courier_id);
                    }
                    for (int i = 0; i < db.getdepartDC(curDc).size(); i++) {
                        String dc_address = db.getdepartDC(curDc).get(i).getAddress();
                        String dc_id = db.getdepartDC(curDc).get(i).getId();
                        sendingcenterChoice.getItems().addAll(dc_id + " " + dc_address);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
//            senddateField.setText(pckge.getDate_start());
//            IdcourierField.setText(pckge.getCourier_id());
            }

        });
    }


    @FXML
    void initialize() throws SQLException {
        getPackageOnClick();

        packageidColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typedeliveryColumn.setCellValueFactory(new PropertyValueFactory<>("type_of_delivery"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_start"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_end"));
        courierIdColumn.setCellValueFactory(new PropertyValueFactory<>("courier_id"));
        senderIdColumn.setCellValueFactory(new PropertyValueFactory<>("sender_id"));
        recipientIdColumn.setCellValueFactory(new PropertyValueFactory<>("recipient_id"));
        departcenterColumn.setCellValueFactory(new PropertyValueFactory<>("departcenter_id"));
        recievecenterColumn.setCellValueFactory(new PropertyValueFactory<>("receivingcenter_id"));

        listPackages = db.getUnacceptedDataPackages();
        acceptPackage.setItems(listPackages);




        injectBtn1.setOnAction(event ->
        {
            try {
                AlertMessage am = new AlertMessage();
                if(!packageIDTEXT.getText().isEmpty() && !statusField.getText().isEmpty() && receivingcenterField.getText() != null && sendingcenterChoice.getValue() != null && senddateDatePicker.getValue() != null && IdcourierChoice.getValue() != null) {
                    am.confirmationMessage("Вы подтверждаете отправку?");
                    if(am.checkconfirm()) {
                        db.acceptPackage(packageIDTEXT.getText().split(" ")[1], statusField.getText().trim(), sendingcenterChoice.getValue().split(" ")[0], String.valueOf(senddateDatePicker.getValue()), IdcourierChoice.getValue().split(" ")[1]);
                        am.informationMessage("Отправка подтверждена!");
                        initialize();
                        packageIDTEXT.setText("");
                        statusField.clear();
                    }
                    else{
                        am.warningMessage("Отправка не подтверждена");
                    }
                }
                else{
                    am.warningMessage("Не все поля заполнены. Невозможно подтвердить поссылку!");
                    System.out.println("Не все поля заполнены. Невозможно подтвердить поссылку!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });




        backBtn.setOnAction(event -> Utils.changeScene(event,"managermain.fxml", "Главная страница (Менеджер)"));
    }

}
