package Models;

import com.example.deliveryservice_courseproject.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SendPackagePageModel {
    Data data = Data.getInstance();
    public List<String> getClients() throws SQLException {
        ResultSet resultSet = DBConnection.getInstance().getClients(data.getUser().getLogin());
        List<String> clients = new ArrayList<>();

        while (resultSet.next()){
            clients.add(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4));
        }
        return clients;
    }
}
