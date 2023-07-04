package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.DBConnection;
import com.example.deliveryservice_courseproject.Package;
import com.example.deliveryservice_courseproject.Utils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class AllPackagesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    ObservableList<Package> packagesData;

    @FXML
    private TableView<Package> allPackage;

    @FXML
    private Button backBtn;
    @FXML
    private TableColumn<Package, String> courierIdColumn;

    @FXML
    private TextField courierid;

    @FXML
    private TextField departcenter;

    @FXML
    private TableColumn<Package, String> departcenterColumn;

    @FXML
    private TableColumn<Package, String> endDateColumn;

    @FXML
    private TextField enddate;

    @FXML
    private Text packageIDTEXT;

    @FXML
    private TextField packageId;

    @FXML
    private TableColumn<Package, String> packageidColumn;

    @FXML
    private TextField receivecenter;

    @FXML
    private TextField recepientid;

    @FXML
    private TableColumn<Package, String> recievecenterColumn;

    @FXML
    private TableColumn<Package, String> recipientIdColumn;

    @FXML
    private TableColumn<Package, String> senderIdColumn;

    @FXML
    private TextField senderid;

    @FXML
    private TableColumn<Package, String> startDateColumn;

    @FXML
    private TextField startdate;

    @FXML
    private TextField status;

    @FXML
    private TableColumn<Package, String> statusColumn;

    @FXML
    private TextField typedelivery;

    @FXML
    private TableColumn<Package, String> typedeliveryColumn;

    @FXML
    private TextField weight;

    @FXML
    private TableColumn<Package, String> weightColumn;

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
        allPackage.setOnMouseClicked(mouseEvent -> {
            if(!allPackage.getSelectionModel().isEmpty()) {
                Package pckge = allPackage.getSelectionModel().getSelectedItem();
                packageId.setText(pckge.getId());
                typedelivery.setText(pckge.getType_of_delivery());
                weight.setText(pckge.getWeight());
                status.setText(pckge.getStatus());
                startdate.setText(pckge.getDate_start());
                enddate.setText(pckge.getDate_end());
                courierid.setText(pckge.getCourier_id());
                senderid.setText(pckge.getSender_id());
                recepientid.setText(pckge.getRecipient_id());
                departcenter.setText(pckge.getDepartcenter_id());
                receivecenter.setText(pckge.getReceivingcenter_id());
            }

        });
    }

    void fillTable() throws SQLException {
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

        packagesData = db.getDataPackages();
        allPackage.setItems(packagesData);

    }

    @FXML
    void initialize() throws SQLException {
        getPackageOnClick();
        fillTable();


        backBtn.setOnAction(event -> Utils.changeScene(event,"managermain.fxml", "Главная страница (Менеджер)"));
    }

}
