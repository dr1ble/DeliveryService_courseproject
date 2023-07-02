package Models;

import Utils.HashCoder;
import com.example.deliveryservice_courseproject.Client;
import com.example.deliveryservice_courseproject.Courier;
import com.example.deliveryservice_courseproject.DBConnection;
import com.example.deliveryservice_courseproject.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageModel {
    private  User user;
    private Client client;
    private Courier courier;

    public boolean login(String login, String password) throws SQLException {

        DBConnection.getInstance().getUser(login, HashCoder.toHash(password));

        ResultSet resultSet = DBConnection.getInstance().getUser(login, HashCoder.toHash(password));

        return resultSet.next();
    }

    public boolean loginClient(String login, String password) throws SQLException {

        DBConnection.getInstance().getUser(login, HashCoder.toHash(password));

        ResultSet resultSet = DBConnection.getInstance().getUser(login, HashCoder.toHash(password));
        int count = 0;
        while (resultSet.next()){
            count++;
        }
        if(count>=1){
            System.out.println("Успешная авторизация! Здравствуйте " + login + "!");
            user = DBConnection.getInstance().getUserData(login, password);
            System.out.println(user.getId());
            client = DBConnection.getInstance().getClientData(login);
            System.out.println(client.getId());
            return true;
        }
        return false;
    }

    public boolean loginManager(String login, String password) throws SQLException {

        ResultSet resultSet = DBConnection.getInstance().getUser(login, HashCoder.toHash(password));
        int count = 0;
        while (resultSet.next()){
            count++;
        }
        if(count>=1){
            System.out.println("Успешная авторизация! Здравствуйте " + login + " (manager)!");
            user = DBConnection.getInstance().getUserData(login, password);
            client = null;
            return true;
        }
        return false;
    }

    public boolean loginCourier(String login, String password) throws SQLException {

        ResultSet resultSet = DBConnection.getInstance().getUser(login, HashCoder.toHash(password));
        int count = 0;
        while (resultSet.next()){
            count++;
        }
        if(count>=1){
            System.out.println("Успешная авторизация! Здравствуйте " + login + " (courier)!");
            user = DBConnection.getInstance().getUserData(login, password);
            client = null;
            courier =  DBConnection.getInstance().getCourierData(login);
            return true;
        }
        return false;
    }

    public User getUser() {
        return user;
    }

    public Client getClient() {
        return client;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
