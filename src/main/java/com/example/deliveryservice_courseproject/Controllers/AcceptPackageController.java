package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.DBConnection;
import com.example.deliveryservice_courseproject.Package;
import com.example.deliveryservice_courseproject.Utils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.w3c.dom.events.MouseEvent;

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
    private TextField IdcourierField;

    @FXML
    private TextField receivingcenterField;

    @FXML
    private TextField senddateField;

    @FXML
    private DatePicker senddateDatePicker;


    @FXML
    private TextField sendingcenterField;

    @FXML
    private TextField statusField;

    @FXML
    private Text packageIDTEXT;

    @FXML
    void getPackageOnClick (){
        acceptPackage.setOnMouseClicked(mouseEvent -> {
            Package pckge = acceptPackage.getSelectionModel().getSelectedItem();
            packageIDTEXT.setText(pckge.getId());
            statusField.setText(pckge.getStatus());
            receivingcenterField.setText(pckge.getReceivingcenter_id());
            sendingcenterField.setText(pckge.getDepartcenter_id());
//            senddateField.setText(pckge.getDate_start());
            IdcourierField.setText(pckge.getCourier_id());

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

        listPackages = DBConnection.getInstance().getDataPackages();
        System.out.println(listPackages.toString());
        acceptPackage.setItems(listPackages);



        injectBtn1.setOnAction(event ->
        {
            try {
                if(!packageIDTEXT.getText().isEmpty() && !statusField.getText().isEmpty() && !receivingcenterField.getText().isEmpty()
                        && !sendingcenterField.getText().isEmpty() && !String.valueOf(senddateDatePicker.getValue()).isEmpty() && !IdcourierField.getText().isEmpty()) {
                    DBConnection.getInstance().acceptPackage(packageIDTEXT.getText().trim(), statusField.getText().trim(),
                            receivingcenterField.getText().trim(), sendingcenterField.getText().trim(), String.valueOf(senddateDatePicker.getValue()), IdcourierField.getText());
                }
                else System.out.println("Не все поля заполнены. Невозможно подтвердить поссылку!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        backBtn.setOnAction(event -> Utils.changeScene(event,"managermain.fxml", "Главная страница (Менеджер)"));
    }

}
