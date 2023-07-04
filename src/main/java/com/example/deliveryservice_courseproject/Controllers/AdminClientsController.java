package com.example.deliveryservice_courseproject.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.deliveryservice_courseproject.Models.Client;
import com.example.deliveryservice_courseproject.Models.DBConnection;
import com.example.deliveryservice_courseproject.Models.User;
import com.example.deliveryservice_courseproject.Utils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.deliveryservice_courseproject.Other.AlertMessage;
import javafx.scene.layout.Pane;


public class AdminClientsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressclientField;

    @FXML
    private Button backBtn;

    @FXML
    private Button clearFieldsBtn;

    @FXML
    private Button clientAddBtn;

    @FXML
    private Button clientDeleteBtn;

    @FXML
    private TextField clientIdField;

    @FXML
    private Button clientUpdateBtn;

    @FXML
    private TextField clientnameField;

    @FXML
    private TabPane clientsPane;

    @FXML
    private TableView<Client> clientsTable;

    @FXML
    private TableColumn<Client, String> clientsaddressColumn;

    @FXML
    private TableColumn<Client, String> clientsidColumn;

    @FXML
    private TableColumn<Client, String> clientsnameColumn;

    @FXML
    private TableColumn<Client, String> clientsneardcColumn;

    @FXML
    private TableColumn<Client, String> clientsnumberColumn;

    @FXML
    private TableColumn<Client, String> clientsuseridColumn;

    @FXML
    private TextField clientuseridField;

    @FXML
    private TextField neardcidField;

    @FXML
    private TextField numberclientField;

    @FXML
    private Button usercancelBtn;

    @FXML
    private TextField userloginField;

    @FXML
    private PasswordField userpassField;

    @FXML
    private Pane userregPane;

    @FXML
    private Button userregBtn;

    ObservableList<Client> clientsData;

    DBConnection db;

    {
        try {
            db = DBConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    AlertMessage am = new AlertMessage();


    @FXML
    void getClientOnClick (){
        clientsTable.setOnMouseClicked(mouseEvent -> {
            if(!clientsTable.getSelectionModel().isEmpty()) {
                Client client = clientsTable.getSelectionModel().getSelectedItem();
                clientIdField.setText(client.getId());
                clientuseridField.setText(client.getUser_id());
                clientnameField.setText(client.getName());
                numberclientField.setText(client.getNumber());
                addressclientField.setText(client.getAddress());
                neardcidField.setText(client.getNearest_dc_id());
            }
        });
    }

    @FXML
    void getUserRegPane(){
        clientsPane.setDisable(true);
        userregPane.setVisible(true);
    }

    @FXML
    void getMainPane(){
        clientsPane.setDisable(false);
        userregPane.setVisible(false);
    }


    void fillTable() throws SQLException {
        clientsData = db.getClientsData();
        clientsTable.setItems(clientsData);

        clientsidColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientsnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientsnumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        clientsaddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        clientsuseridColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        clientsneardcColumn.setCellValueFactory(new PropertyValueFactory<>("nearest_dc_id"));

    }
    void clearFields(){
        clientIdField.clear();
        clientuseridField.clear();
        clientnameField.clear();
        numberclientField.clear();
        addressclientField.clear();
        neardcidField.clear();
        userloginField.clear();
        userpassField.clear();
    }


    @FXML
    void initialize() throws SQLException {
        getClientOnClick();
        fillTable();


        clientUpdateBtn.setOnAction(event -> {
            if(!clientIdField.getText().isEmpty() && !clientuseridField.getText().isEmpty() && !clientnameField.getText().isEmpty()
                    && !numberclientField.getText().isEmpty() && !addressclientField.getText().isEmpty() && !neardcidField.getText().isEmpty()){
                try {
                    am.confirmationMessage("Вы действительно хотите обновить данные этого клиента?");
                    if(am.checkconfirm()) {
                        updateClient();
                        clearFields();
                        initialize();
                        am.informationMessage("Обновление данных прошло успешно");
                    }
                } catch (Exception e) {
                    am.warningMessage("По данному ID нет центра доставки. Выберите другой ID");

                }
            }
            else
                am.warningMessage("Заполните поля перед выполнением команд");
        });

        clientDeleteBtn.setOnAction(event -> {
            if(!clientIdField.getText().isEmpty() && !clientuseridField.getText().isEmpty() && !clientnameField.getText().isEmpty()
                    && !numberclientField.getText().isEmpty() && !addressclientField.getText().isEmpty() && !neardcidField.getText().isEmpty()){
                try {
                    am.confirmationMessage("Вы действительно хотите удалить данного клиента?");
                    if(am.checkconfirm()) {
                        deleteClient();
                        clearFields();
                        initialize();
                        am.informationMessage("Удаление пользователя прошло успешно");
                    }
                    else am.warningMessage("Ошибка при удалении");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else
                am.warningMessage("Заполните поля перед выполнением команд");
        });

        clientAddBtn.setOnAction(event -> {
            if (!clientIdField.getText().isEmpty() && !clientuseridField.getText().isEmpty() && !clientnameField.getText().isEmpty()
                    && !numberclientField.getText().isEmpty() && !addressclientField.getText().isEmpty() && !neardcidField.getText().isEmpty()) {
                try {
                    am.confirmationMessage("Вы действительно хотите добавить данного клиента?");
                    if (am.checkconfirm()) {
                        getUserRegPane();
                        usercancelBtn.setOnAction(event1 -> getMainPane());
                        userregBtn.setOnAction(event1 -> {
                            if (!userloginField.getText().isEmpty() && !userpassField.getText().isEmpty()) {
                                try {
                                    if (!db.checkLogin(userloginField.getText())) {
                                        addClient();
                                        getMainPane();
                                        clearFields();
                                        initialize();
                                        am.informationMessage("Добавление клиента прошло успешно");
                                    } else {
                                        am.warningMessage("Логин уже занят. Выберите другой логин");
                                    }
                                } catch (SQLException e) {
                                    am.errorMessage("По данному ID нет центра доставки. Выберите другой ID");
                                    getMainPane();
                                }
                            } else {
                                am.informationMessage("Заполните все поля для регистрации пользвователя");
                            }
                        });
                    }
                } catch (RuntimeException e) {
                    am.errorMessage("По данному ID нет центра доставки. Выберите другой ID");
                }
            } else {
                am.warningMessage("Заполните поля перед выполнением команд");
            }
        });


        clearFieldsBtn.setOnAction(event -> clearFields());

        backBtn.setOnAction(event -> Utils.changeScene(event,"adminmain.fxml", "Главная страница (Администратор)"));
    }


    private void updateClient() throws Exception {

        String id = clientIdField.getText().trim();
        String name = clientnameField.getText().trim();
        String number = numberclientField.getText().trim();
        String address = addressclientField.getText().trim();
        String idneardc = neardcidField.getText().trim();
        String user_id = clientuseridField.getText().trim();

        Client client = new Client(id, name, number, address, idneardc, user_id);
        db.updateClient(client);
    }

    private void addClient() throws SQLException {
        String name = clientnameField.getText().trim();
        String number = numberclientField.getText().trim();
        String address = addressclientField.getText().trim();
        String neardc = neardcidField.getText().trim();
        String login = userloginField.getText();
        String password = userpassField.getText();

        User user = new User("",login, password, "0");
        Client client = new Client("", name, number, address, neardc, "");
        if (db.checkDc(neardc)) {
            db.signUpUser(user);
            db.signUpClient(client, user);
        }
        else{
            throw new SQLException();
        }
    }

    private void deleteClient() throws Exception {

        String id = clientIdField.getText().trim();
        String user_id = clientuseridField.getText().trim();

        db.deleteClient(id, user_id);
    }

}
