package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Models.Data;
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
import org.w3c.dom.events.MouseEvent;

public class MyPackagesController {
    Data data = Data.getInstance();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;


    @FXML
    private TableView<Package> recievePackage;
    @FXML
    private TableColumn<Package,String> endDateColumn;

    @FXML
    private TableColumn<Package,String> idColumn;

    @FXML
    private TableColumn<Package,String> senderIdColumn;

    @FXML
    private TableColumn<Package,String> startDateColumn;

    @FXML
    private TableColumn<Package,String> statusColumn;

    @FXML
    private TableColumn<Package,String> weightColumn;

    @FXML
    private TableView<Package> sendTable;

    @FXML
    private TableColumn<Package,String> sendendDateColumn;

    @FXML
    private TableColumn<Package,String> sendidColumn;

    @FXML
    private TableColumn<Package,String> sendstartDateColumn;

    @FXML
    private TableColumn<Package,String> sendstatusColumn;

    @FXML
    private TableColumn<Package,String> sendweightColumn;

    @FXML
    private TableColumn<Package,String> recipientIdColumn;

    ObservableList<Package> listPackages;
    @FXML
    void initialize() throws SQLException {
        System.out.println();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_start"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_end"));
        senderIdColumn.setCellValueFactory(new PropertyValueFactory<>("sender_id"));

        listPackages = DBConnection.getInstance().getDataPackagesForCurrent(data.getClient().getId(), "recipient_id");
        System.out.println(listPackages.toString());
        recievePackage.setItems(listPackages);

        sendidColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        sendweightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        sendstatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        sendstartDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_start"));
        sendendDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_end"));
        recipientIdColumn.setCellValueFactory(new PropertyValueFactory<>("recipient_id"));

        listPackages = DBConnection.getInstance().getDataPackagesForCurrent(data.getClient().getId(), "sender_id");
        System.out.println(listPackages.toString());
        sendTable.setItems(listPackages);


        backBtn.setOnAction(event -> Utils.changeScene(event, "mainpage.fxml", "Личный кабинет"));
    }

}
