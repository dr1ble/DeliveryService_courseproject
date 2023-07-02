package com.example.deliveryservice_courseproject;

import Utils.HashCoder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;
    private final String url = "jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2289_deliveryservice";
    private final String username = "std_2289_deliveryservice";
    private final String password = "qwerty123";


    private DBConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    public void signUpUser(User user) throws SQLException {
        String insertUsers = "INSERT INTO " + DBConsts.USERS_TABLE + "(" + DBConsts.USERS_LOGIN + "," + DBConsts.USERS_PASSWORD + ", " + DBConsts.USERS_ACCESS + ")"
                + "VALUES(?,?,?)" + ";";
        PreparedStatement preStatementUsers = getConnection().prepareStatement(insertUsers);

        preStatementUsers.setString(1, user.getLogin());
        preStatementUsers.setString(2, HashCoder.toHash(user.getPassword()));
        preStatementUsers.setString(3, user.getAccesslevel());

        preStatementUsers.executeUpdate();
    }

    public void signUpClient(Client client, User user) throws SQLException {
        String insertClients = "INSERT INTO " + DBConsts.CLIENTS_TABLE + "(" + DBConsts.CLIENTS_NAME + "," + DBConsts.CLIENTS_NUMBER + "," + DBConsts.CLIENTS_ADDRESS + "," + DBConsts.CLIENTS_USERID +")" + "VALUES(?,?,?,?)";

        PreparedStatement preStatementClients = getConnection().prepareStatement(insertClients);
        preStatementClients.setString(1, client.getName());
        preStatementClients.setString(2, client.getNumber());
        preStatementClients.setString(3, client.getAddress());
        preStatementClients.setString(4, getID(user.getLogin()));

        preStatementClients.executeUpdate();
    }

        public String getID(String login) throws SQLException {
        ResultSet resultSet = null;
        String getId = "SELECT id FROM " + DBConsts.USERS_TABLE + " WHERE " + DBConsts.USERS_LOGIN + "=?";
        PreparedStatement ps = getConnection().prepareStatement(getId);
        ps.setString(1, login);
        resultSet = ps.executeQuery();

        String id = null;
        if (resultSet.next()) {
            id = resultSet.getString((DBConsts.USERS_ID));
        }
        return id;
    }

    public ResultSet getUser(String login, String password) throws SQLException {
        ResultSet resultSet = null;

        String selectUsers = "SELECT * FROM " + DBConsts.USERS_TABLE + " WHERE " + DBConsts.USERS_LOGIN + "=? AND " + DBConsts.USERS_PASSWORD + "=?";

        PreparedStatement preparedStatement = getConnection().prepareStatement(selectUsers);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);

        resultSet = preparedStatement.executeQuery();

        return resultSet;
    }

    public boolean checkLogin(String login) throws SQLException {
        String selectLogin = "SELECT " + DBConsts.USERS_LOGIN + " FROM " + DBConsts.USERS_TABLE + " WHERE " + DBConsts.USERS_LOGIN + "=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectLogin);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next(); // если есть хотя бы одна строка в результате запроса, значит логин существует
    }
    public User getUserData(String login, String password) throws SQLException{
        ResultSet resultSet = null;
        String selectUserdata = "SELECT * FROM " + DBConsts.USERS_TABLE + " WHERE " + DBConsts.USERS_LOGIN  + " =?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectUserdata);
        preparedStatement.setString(1, login);
        resultSet = preparedStatement.executeQuery();
        User user = null;

        if (resultSet.next()) {
            user = new User(resultSet.getString("id"), login,  HashCoder.toHash(password), resultSet.getString("accesslevel"));
        }
        return user;
    }

    public Client getClientData(String login) throws SQLException{
        ResultSet resultSet = null;
//        String selectUserdata = "SELECT " + DBConsts.CLIENTS_ID + " AS client_id" + ", " + DBConsts.CLIENTS_NAME + ", " + DBConsts.CLIENTS_NUMBER + ", " + DBConsts.CLIENTS_ADDRESS + " FROM " + DBConsts.CLIENTS_TABLE +
//                " JOIN " + DBConsts.USERS_TABLE + " ON " + DBConsts.CLIENTS_TABLE + "." + DBConsts.CLIENTS_USERID + "=" + DBConsts.USERS_TABLE + "." + DBConsts.USERS_ID +
//                " WHERE " + DBConsts.USERS_TABLE + "." + DBConsts.USERS_LOGIN  + "=?";
        String selectUserdata = "SELECT clients.id, clients.name, clients.number, clients.address FROM clients JOIN users ON clients.user_id = users.id WHERE login" + "=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectUserdata);
        preparedStatement.setString(1, login);
        resultSet = preparedStatement.executeQuery();
        Client client = null;

        if (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString(DBConsts.CLIENTS_NAME);
            String number = resultSet.getString(DBConsts.CLIENTS_NUMBER);
            String address = resultSet.getString(DBConsts.CLIENTS_ADDRESS);
            client = new Client(id, name, number, address, "","");
        }
        return client;
    }

    public void updatePass(String login, String password) throws SQLException {
        String updateUsers = "UPDATE " + DBConsts.USERS_TABLE + " SET " + DBConsts.USERS_PASSWORD + "=?" + " WHERE " + DBConsts.USERS_LOGIN + "=? ";

        PreparedStatement preStatementUpdateUsers = getConnection().prepareStatement(updateUsers);

        preStatementUpdateUsers.setString(1, password);
        preStatementUpdateUsers.setString(2, login);

        preStatementUpdateUsers.executeUpdate();
    }

    public void updateLogin(String login, String newlogin) throws SQLException {
        String updateUsers = "UPDATE " + DBConsts.USERS_TABLE + " SET " + DBConsts.USERS_LOGIN + "=?" + " WHERE " +
        DBConsts.CLIENTS_LOGIN + "=?";

        PreparedStatement preStatementUpdateUsers = getConnection().prepareStatement(updateUsers);

        preStatementUpdateUsers.setString(1, newlogin);
        preStatementUpdateUsers.setString(2, login);
        preStatementUpdateUsers.executeUpdate();
    }

    public void updateName(String login, String newname) throws SQLException {
        String updateUsers = "UPDATE " + DBConsts.CLIENTS_TABLE + " SET " + DBConsts.CLIENTS_NAME + "=?" + " WHERE " +
                DBConsts.CLIENTS_USERID + "=?";

        PreparedStatement preStatementUpdateUsers = getConnection().prepareStatement(updateUsers);

        preStatementUpdateUsers.setString(1, newname);
        preStatementUpdateUsers.setString(2, getID(login));
        preStatementUpdateUsers.executeUpdate();
    }

    public void updateNumber(String login, String newNumber) throws SQLException {
        String updateUsers = "UPDATE " + DBConsts.CLIENTS_TABLE + " SET " + DBConsts.CLIENTS_NUMBER + "=?" + " WHERE " +
                DBConsts.CLIENTS_USERID + "=?";

        PreparedStatement preStatementUpdateUsers = getConnection().prepareStatement(updateUsers);

        preStatementUpdateUsers.setString(1, newNumber);
        preStatementUpdateUsers.setString(2, getID(login));
        preStatementUpdateUsers.executeUpdate();
    }

    public void updateAddress(String login, String newAddress) throws SQLException {
        String updateUsers = "UPDATE " + DBConsts.CLIENTS_TABLE + " SET " + DBConsts.CLIENTS_NUMBER + "=?" + " WHERE " +
                DBConsts.CLIENTS_USERID + "=?";

        PreparedStatement preStatementUpdateUsers = getConnection().prepareStatement(updateUsers);

        preStatementUpdateUsers.setString(1, newAddress);
        preStatementUpdateUsers.setString(2, getID(login));
        preStatementUpdateUsers.executeUpdate();
    }
    public ResultSet getClients(String login) throws SQLException {
        ResultSet resultSet = null;
//        String getClients = "SELECT " + DBConsts.CLIENTS_USERID + "," + DBConsts.CLIENTS_NAME + "," + DBConsts.CLIENTS_NUMBER + " FROM " + DBConsts.CLIENTS_TABLE
//                + " WHERE" + DBConsts.USERS_ID + "<>" + "=?";
        String getClients = "SELECT  user_id, name, number FROM clients WHERE user_id NOT IN (" + "?)";

        PreparedStatement psGetClients = getConnection().prepareStatement(getClients);

        psGetClients.setString(1, getID(login));

        resultSet = psGetClients.executeQuery();
        return resultSet;
    }


    public void startDelivery(String senderId, String recieverId, String type, String weight, String status) throws SQLException {
        String insertPackages = "INSERT INTO " + DBConsts.PACKAGES_TABLE + "(" + DBConsts.PACKAGES_TYPEOFDELIVERY + "," + DBConsts.PACKAGES_WEIGHT + "," + DBConsts.PACKAGES_STATUS +
                "," + DBConsts.PACKAGES_SENDERID + "," + DBConsts.PACKAGES_RECIPIENTID + ")" + "VALUES(?,?,?,?,?)" + ";";
        PreparedStatement preStatementInsertPackages = getConnection().prepareStatement(insertPackages);
        preStatementInsertPackages.setString(1, type); preStatementInsertPackages.setString(2, weight);
        preStatementInsertPackages.setString(3, status); preStatementInsertPackages.setString(4, senderId); preStatementInsertPackages.setString(5, recieverId);
        preStatementInsertPackages.executeUpdate();

    }

    public String getPackageId() throws SQLException {
        ResultSet resultSet = null;
        String getPackageId =  "SELECT ID FROM packages ORDER BY ID desc LIMIT 1";
        PreparedStatement psGetId = getConnection().prepareStatement(getPackageId);
        resultSet = psGetId.executeQuery();

        String id  = "";
        if (resultSet.next()) {
            id = resultSet.getString("id");
        }
        return id;
    }

    public ObservableList<Package> getDataPackagesForCurrent(String id, String who) throws SQLException {
        ObservableList<Package> list = FXCollections.observableArrayList();
        PreparedStatement ps = getConnection().prepareStatement("select * from packages where " +  who + " =?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            list.add(new Package(rs.getString("id"),rs.getString("type_of_delivery"),
                    rs.getString("weight"), rs.getString("status"), rs.getString("date_start"),
                    rs.getString("date_end"), rs.getString("courier_id"), rs.getString("sender_id"),
                    rs.getString("recipient_id"), rs.getString("departcenter_id"), rs.getString("receivingcenter_id")));
        }
        return list;
    }

    public ObservableList<Package> getDataPackages() throws SQLException {
        ObservableList<Package> list = FXCollections.observableArrayList();
        String status = "В обработке";
        PreparedStatement ps = getConnection().prepareStatement("select * from packages WHERE packages.status"+ "=?");
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            list.add(new Package(rs.getString("id"),rs.getString("type_of_delivery"),
                    rs.getString("weight"), rs.getString("status"), rs.getString("date_start"),
                    rs.getString("date_end"), rs.getString("courier_id"), rs.getString("sender_id"),
                    rs.getString("recipient_id"), rs.getString("departcenter_id"), rs.getString("receivingcenter_id")));
        }
        return list;
    }

    public int getAccessLevel(String login) throws SQLException {
        ResultSet resultSet = null;

        String getAccessLevel =  "SELECT accesslevel FROM users WHERE login " + "=?";
        PreparedStatement psGetAccess = getConnection().prepareStatement(getAccessLevel);
        psGetAccess.setString(1, login);
        resultSet = psGetAccess.executeQuery();

        String accesslevel  = "";
        if (resultSet.next()) {
            accesslevel = resultSet.getString("accesslevel");
        }
        return Integer.parseInt(accesslevel);
    }

    public void acceptPackage(String id, String status, String reciveCenterId, String sendCenterId, String dateSend, String courier_id) throws SQLException {
        String updatePackage = "UPDATE " + DBConsts.PACKAGES_TABLE + " SET " +  DBConsts.PACKAGES_STATUS +  "=?" + "," +  DBConsts.PACKAGES_RECEIVINGCENTERID + "=?"
                + "," + DBConsts.PACKAGES_SENDCENTERID + "=?" + "," + DBConsts.PACKAGES_DATESTART + "=?" + "," +  DBConsts.PACKAGES_COURIERID + "=?"  + " WHERE " + DBConsts.PACKAGES_ID + "=?";

        PreparedStatement psUpdatePackage = getConnection().prepareStatement(updatePackage);

        psUpdatePackage.setString(1, status);
        psUpdatePackage.setString(2, reciveCenterId);
        psUpdatePackage.setString(3, sendCenterId);
        psUpdatePackage.setString(4, dateSend);
        psUpdatePackage.setString(5, courier_id);
        psUpdatePackage.setString(6, id);

        psUpdatePackage.executeUpdate();
    }
}
