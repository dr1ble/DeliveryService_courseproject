package Models;

import Utils.HashCoder;
import com.example.deliveryservice_courseproject.Client;
import com.example.deliveryservice_courseproject.DBConnection;
import com.example.deliveryservice_courseproject.LoginPage;
import com.example.deliveryservice_courseproject.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageModel {
    private  User user;
    private Client client;

    public boolean loginUser(String login, String password) throws SQLException {

        DBConnection.getInstance().getUser(login, HashCoder.toHash(password));

        ResultSet resultSet = DBConnection.getInstance().getUser(login, HashCoder.toHash(password));
        int count = 0;
        while (resultSet.next()){
            count++;
        }
        if(count>=1){
            System.out.println("Success authorization! Hello " + login + "!");
            user = DBConnection.getInstance().getUserData(login, password);
            client = DBConnection.getInstance().getClientData(login);
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
}
