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
        String insertClients = "INSERT INTO " + DBConsts.CLIENTS_TABLE + "(" + DBConsts.CLIENTS_NAME + "," + DBConsts.CLIENTS_NUMBER + "," + DBConsts.CLIENTS_ADDRESS + ", " + DBConsts.CLIENTS_NEARDC +"," + DBConsts.CLIENTS_USERID +")" + "VALUES(?,?,?,?,?)";

        PreparedStatement preStatementClients = getConnection().prepareStatement(insertClients);
        preStatementClients.setString(1, client.getName());
        preStatementClients.setString(2, client.getNumber());
        preStatementClients.setString(3, client.getAddress());
        preStatementClients.setString(4, client.getNearest_dc_id());
        preStatementClients.setString(5, getID(user.getLogin()));

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
    public User getCurUserData(String login, String password) throws SQLException{
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

    public Courier getCourierData(String login) throws SQLException{
        ResultSet resultSet = null;
        String selectUserdata = "SELECT couriers.id, couriers.name, couriers.number, couriers.delivery_center_id, couriers.user_id FROM couriers JOIN users ON couriers.user_id = users.id WHERE login" + "=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectUserdata);
        preparedStatement.setString(1, login);
        resultSet = preparedStatement.executeQuery();
        Courier courier = null;

        if (resultSet.next()) {
            courier = new Courier(resultSet.getString("id"), resultSet.getString("name"),
                    resultSet.getString("number"), resultSet.getString("delivery_center_id"), resultSet.getString("user_id"));
        }
        return courier;
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
        String getClients = "SELECT  user_id, name, number, nearest_dc_id FROM clients WHERE user_id NOT IN (" + "?)";

        PreparedStatement psGetClients = getConnection().prepareStatement(getClients);

        psGetClients.setString(1, getID(login));

        resultSet = psGetClients.executeQuery();
        return resultSet;
    }


    public void startDelivery(String senderId, String recieverId, String type, String weight, String status, String nearestdc) throws SQLException {
        String insertPackages = "INSERT INTO " + DBConsts.PACKAGES_TABLE + "(" + DBConsts.PACKAGES_TYPEOFDELIVERY + "," + DBConsts.PACKAGES_WEIGHT + "," + DBConsts.PACKAGES_STATUS +
                "," + DBConsts.PACKAGES_SENDERID + "," + DBConsts.PACKAGES_RECIPIENTID + "," + DBConsts.PACKAGES_RECEIVINGCENTERID + ")" + "VALUES(?,?,?,?,?,?)" + ";";
        PreparedStatement preStatementInsertPackages = getConnection().prepareStatement(insertPackages);
        preStatementInsertPackages.setString(1, type); preStatementInsertPackages.setString(2, weight);
        preStatementInsertPackages.setString(3, status); preStatementInsertPackages.setString(4, senderId);
        preStatementInsertPackages.setString(5, recieverId); preStatementInsertPackages.setString(6, nearestdc);
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

    public ObservableList<Package> getUnacceptedDataPackages() throws SQLException {
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

    public ObservableList<Package> getDataPackages() throws SQLException {
        ObservableList<Package> list = FXCollections.observableArrayList();
        PreparedStatement ps = getConnection().prepareStatement("select * from packages");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            list.add(new Package(rs.getString("id"),rs.getString("type_of_delivery"),
                    rs.getString("weight"), rs.getString("status"), rs.getString("date_start"),
                    rs.getString("date_end"), rs.getString("courier_id"), rs.getString("sender_id"),
                    rs.getString("recipient_id"), rs.getString("departcenter_id"), rs.getString("receivingcenter_id")));
        }
        return list;
    }

    public ObservableList<DeliveryCenter> getdepartDC(String curdcId) throws SQLException {
        ObservableList<DeliveryCenter> list = FXCollections.observableArrayList();
        PreparedStatement ps = getConnection().prepareStatement("select * from delivery_centers WHERE id NOT IN(" + "?)");
        ps.setString(1, curdcId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            list.add(new DeliveryCenter(rs.getString("id"), rs.getString("name"), rs.getString("address")));
        }
        return list;
    }

    public ObservableList<Courier> getDataCourierForCurDC(String curdcId) throws SQLException {
        ObservableList<Courier> list = FXCollections.observableArrayList();
        PreparedStatement ps = getConnection().prepareStatement("select * from couriers WHERE delivery_center_id"+ "=?");
        ps.setString(1, curdcId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            list.add(new Courier(rs.getString("id"), rs.getString("name"), rs.getString("number"), curdcId, rs.getString("user_id")));
        }
        return list;
    }


    public ObservableList<DeliveryCenter> getDCdata() throws SQLException {
        ObservableList<DeliveryCenter> list = FXCollections.observableArrayList();
        PreparedStatement ps = getConnection().prepareStatement("select * from delivery_centers");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            list.add(new DeliveryCenter(rs.getString("id"), rs.getString("name"), rs.getString("address")));
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

//    public void acceptPackage(String id, String status, String reciveCenterId, String sendCenterId, String dateSend, String courier_id) throws SQLException {
////        String updatePackage = "UPDATE " + DBConsts.PACKAGES_TABLE + " SET " + DBConsts.PACKAGES_STATUS +  "=?" + "," +  DBConsts.PACKAGES_RECEIVINGCENTERID + "=?"
////                + "," + DBConsts.PACKAGES_SENDCENTERID + "=?" + "," + DBConsts.PACKAGES_DATESTART + "=?" + "," +  DBConsts.PACKAGES_COURIERID + "=?"  + " WHERE " + DBConsts.PACKAGES_ID + "=?";
//        String updatePackage = "UPDATE packages  SET status =?, receivingcenter_id =?, departcenter_id =?, date_start =?, courier_id =? WHERE id =?";
//        PreparedStatement psUpdatePackage = getConnection().prepareStatement(updatePackage);
//
//        psUpdatePackage.setString(1, status);
//        psUpdatePackage.setString(2, reciveCenterId);
//        psUpdatePackage.setString(3, sendCenterId);
//        psUpdatePackage.setString(4, dateSend);
//        psUpdatePackage.setString(5, courier_id);
//        psUpdatePackage.setString(6, id);
//
//        psUpdatePackage.executeUpdate();
//    }

    public void acceptPackage(String id, String status, String sendCenterId, String dateSend, String courier_id) throws SQLException {
//        String updatePackage = "UPDATE " + DBConsts.PACKAGES_TABLE + " SET " + DBConsts.PACKAGES_STATUS +  "=?" + "," +  DBConsts.PACKAGES_RECEIVINGCENTERID + "=?"
//                + "," + DBConsts.PACKAGES_SENDCENTERID + "=?" + "," + DBConsts.PACKAGES_DATESTART + "=?" + "," +  DBConsts.PACKAGES_COURIERID + "=?"  + " WHERE " + DBConsts.PACKAGES_ID + "=?";
        String updatePackage = "UPDATE packages  SET status =?, departcenter_id =?, date_start =?, courier_id =? WHERE id =?";
        PreparedStatement psUpdatePackage = getConnection().prepareStatement(updatePackage);

        psUpdatePackage.setString(1, status);
        psUpdatePackage.setString(2, sendCenterId);
        psUpdatePackage.setString(3, dateSend);
        psUpdatePackage.setString(4, courier_id);
        psUpdatePackage.setString(5, id);

        psUpdatePackage.executeUpdate();
    }

    public void signUpCourier(Courier courier, User user) throws SQLException {
        String insertCourier = "INSERT INTO couriers (name,number,delivery_center_id,user_id) VALUES (?,?,?,?)";

        PreparedStatement preStatementClients = getConnection().prepareStatement(insertCourier);
        preStatementClients.setString(1, courier.getName());
        preStatementClients.setString(2, courier.getNumber());
        preStatementClients.setString(3, courier.getDelivery_center_id());
        preStatementClients.setString(4, getID(user.getLogin()));

        preStatementClients.executeUpdate();
    }

    public ObservableList<Package> getDataPackagesForCur(Courier courier, String status) throws SQLException {
        ObservableList<Package> list = FXCollections.observableArrayList();
        PreparedStatement ps = getConnection().prepareStatement("select * from packages WHERE packages.status " +  "=?" +  " AND courier_id " + "=?");
        ps.setString(1, status);
        ps.setString(2, courier.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            list.add(new Package(rs.getString("id"),rs.getString("type_of_delivery"),
                    rs.getString("weight"), rs.getString("status"), rs.getString("date_start"),
                    rs.getString("date_end"), rs.getString("courier_id"), rs.getString("sender_id"),
                    rs.getString("recipient_id"), rs.getString("departcenter_id"), rs.getString("receivingcenter_id")));
        }
        return list;
    }

    public void setPackageStatus(String status, String packageid) throws SQLException {
        String setStatus = "UPDATE packages SET status" + "=?" + " WHERE id " + "=?";

        PreparedStatement preStatementUpdateUsers = getConnection().prepareStatement(setStatus);

        preStatementUpdateUsers.setString(1, status);
        preStatementUpdateUsers.setString(2, packageid);
        preStatementUpdateUsers.executeUpdate();
    }

    public void setPackageEndDate(String date, String packageid) throws SQLException {
        String setStatus = "UPDATE packages SET date_end" + "=?" + " WHERE id " + "=?";

        PreparedStatement preStatementUpdateUsers = getConnection().prepareStatement(setStatus);

        preStatementUpdateUsers.setString(1, date);
        preStatementUpdateUsers.setString(2, packageid);
        preStatementUpdateUsers.executeUpdate();
    }


    public Client getRecipientData(String id) throws SQLException{
        ResultSet resultSet = null;
//        String selectUserdata = "SELECT " + DBConsts.CLIENTS_ID + " AS client_id" + ", " + DBConsts.CLIENTS_NAME + ", " + DBConsts.CLIENTS_NUMBER + ", " + DBConsts.CLIENTS_ADDRESS + " FROM " + DBConsts.CLIENTS_TABLE +
//                " JOIN " + DBConsts.USERS_TABLE + " ON " + DBConsts.CLIENTS_TABLE + "." + DBConsts.CLIENTS_USERID + "=" + DBConsts.USERS_TABLE + "." + DBConsts.USERS_ID +
//                " WHERE " + DBConsts.USERS_TABLE + "." + DBConsts.USERS_LOGIN  + "=?";
        String selectUserdata = "SELECT clients.name, clients.number, clients.address FROM clients JOIN packages ON clients.id = packages.recipient_id WHERE clients.id" + "=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectUserdata);
        preparedStatement.setString(1, id);
        resultSet = preparedStatement.executeQuery();
        Client client = null;

        if (resultSet.next()) {
            String name = resultSet.getString(DBConsts.CLIENTS_NAME);
            String number = resultSet.getString(DBConsts.CLIENTS_NUMBER);
            String address = resultSet.getString(DBConsts.CLIENTS_ADDRESS);
            client = new Client("", name, number, address, "","");
        }
        return client;
    }

    public ObservableList<User> getUsersData() throws SQLException {
        ObservableList<User> list = FXCollections.observableArrayList();
        PreparedStatement ps = getConnection().prepareStatement("select * from users");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            list.add(new User(rs.getString("id"),rs.getString("login"),rs.getString("password"),rs.getString("accesslevel")));
        }
        return list;
    }

    public void deleteUser(String id) throws SQLException {
        String deleteUser = "DELETE FROM users WHERE id =?";
        PreparedStatement preStatementUsers = getConnection().prepareStatement(deleteUser);

        preStatementUsers.setString(1, id);


        preStatementUsers.executeUpdate();
    }

    public void updateUser(User user) throws SQLException {
        String updateUser = "UPDATE users SET login =?, password =?, accesslevel =? WHERE id =?";
        PreparedStatement preStatementUsers = getConnection().prepareStatement(updateUser);

        preStatementUsers.setString(1, user.getLogin());
        preStatementUsers.setString(2, HashCoder.toHash(user.getPassword()));
        preStatementUsers.setString(3, user.getAccesslevel());
        preStatementUsers.setString(4, user.getId());

        preStatementUsers.executeUpdate();
    }

    public ObservableList<Client> getClientsData() throws SQLException {
        ObservableList<Client> list = FXCollections.observableArrayList();
        PreparedStatement ps = getConnection().prepareStatement("select * from clients");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            list.add(new Client(rs.getString("id"), rs.getString("name"), rs.getString("number"), rs.getString("address"), rs.getString("nearest_dc_id"), rs.getString("user_id")));
        }
        return list;
    }

    public void updateClient(Client client) throws SQLException {
        String updateClient = "UPDATE clients SET name =?, number =?, address =?, nearest_dc_id =? WHERE id =?";
        PreparedStatement preStatementUsers = getConnection().prepareStatement(updateClient);

        preStatementUsers.setString(1, client.getName());
        preStatementUsers.setString(2, client.getNumber());
        preStatementUsers.setString(3, client.getAddress());
        preStatementUsers.setString(4, client.getNearest_dc_id());
        preStatementUsers.setString(5, client.getId());

        preStatementUsers.executeUpdate();
    }

    public void deleteClient(String id, String user_id) throws SQLException {
        String deleteClient = "DELETE FROM clients WHERE id =?";
        PreparedStatement preStatementUsers = getConnection().prepareStatement(deleteClient);

        preStatementUsers.setString(1, id);

        String deleteClientfromUsers = "DELETE FROM users WHERE id =?";
        PreparedStatement psClient = getConnection().prepareStatement(deleteClientfromUsers);
        psClient.setString(1,user_id);

        psClient.executeUpdate();
    }

    public boolean checkDc(String dc) throws SQLException {
        String checkDc  = "SELECT *  from delivery_centers WHERE id =?";
        PreparedStatement psDc = getConnection().prepareStatement(checkDc);

        psDc.setString(1,dc);

        ResultSet resultSet = psDc.executeQuery();
        return resultSet.next(); // если есть хотя бы одна строка в результате запроса, значит логин существует
    }

    public ObservableList<Courier> getCouriersData() throws SQLException {
        ObservableList<Courier> list = FXCollections.observableArrayList();
        PreparedStatement ps = getConnection().prepareStatement("select * from couriers");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            list.add(new Courier(rs.getString("id"), rs.getString("name"), rs.getString("number"), rs.getString("delivery_center_id"), rs.getString("user_id")));
        }
        return list;
    }

    public void updateCourier(Courier courier) throws SQLException {
        String updateCourier = "UPDATE couriers SET name =?, number =?, delivery_center_id =? WHERE id =?";
        PreparedStatement preStatementUsers = getConnection().prepareStatement(updateCourier);

        preStatementUsers.setString(1, courier.getName());
        preStatementUsers.setString(2, courier.getNumber());
        preStatementUsers.setString(3, courier.getDelivery_center_id());
        preStatementUsers.setString(4, courier.getId());

        preStatementUsers.executeUpdate();
    }

    public void deleteCourier(String id, String user_id) throws SQLException {
        String deleteCourier = "DELETE FROM couriers WHERE id =?";
        PreparedStatement preStatementUsers = getConnection().prepareStatement(deleteCourier);

        preStatementUsers.setString(1, id);

        String deleteCourierfromUsers = "DELETE FROM users WHERE id =?";
        PreparedStatement psClient = getConnection().prepareStatement(deleteCourierfromUsers);
        psClient.setString(1,user_id);

        psClient.executeUpdate();
    }

    public void updatePackage(Package pckage) throws SQLException {
        String updatePackage = "UPDATE packages SET type_of_delivery =?, weight =?, status =?, date_start =?, date_end =?, courier_id =?" +
                ", sender_id =?, recipient_id =?, departcenter_id =?, receivingcenter_id =? WHERE id =?";
        PreparedStatement prstPackage = getConnection().prepareStatement(updatePackage);

        prstPackage.setString(1, pckage.getType_of_delivery());
        prstPackage.setString(2, pckage.getWeight());
        prstPackage.setString(3, pckage.getStatus());
        prstPackage.setString(4, pckage.getDate_start());
        prstPackage.setString(5, pckage.getDate_end());
        prstPackage.setString(6, pckage.getCourier_id());
        prstPackage.setString(7, pckage.getSender_id());
        prstPackage.setString(8, pckage.getRecipient_id());
        prstPackage.setString(9, pckage.getDepartcenter_id());
        prstPackage.setString(10, pckage.getReceivingcenter_id());
        prstPackage.setString(11, pckage.getId());

        prstPackage.executeUpdate();
    }

    public boolean checkCourier(String courierid) throws SQLException {
        String checkDc  = "SELECT *  from couriers WHERE id =?";
        PreparedStatement psDc = getConnection().prepareStatement(checkDc);

        psDc.setString(1,courierid);

        ResultSet resultSet = psDc.executeQuery();
        return resultSet.next(); // если есть хотя бы одна строка в результате запроса, значит логин существует
    }

    public boolean checkClient(String clientid) throws SQLException {
        String checkDc  = "SELECT *  from clients WHERE id =?";
        PreparedStatement psDc = getConnection().prepareStatement(checkDc);

        psDc.setString(1,clientid);

        ResultSet resultSet = psDc.executeQuery();
        return resultSet.next(); // если есть хотя бы одна строка в результате запроса, значит логин существует
    }

    public void addPackage(Package pckage) throws SQLException {
        String insertPackages = "INSERT INTO packages(type_of_delivery, weight, status, date_start, date_end, courier_id, sender_id, recipient_id, departcenter_id, receivingcenter_id) VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preStatementInsertPackages = getConnection().prepareStatement(insertPackages);

        preStatementInsertPackages.setString(1, pckage.getType_of_delivery());
        preStatementInsertPackages.setString(2, pckage.getWeight());
        preStatementInsertPackages.setString(3, pckage.getStatus());
        preStatementInsertPackages.setString(4, pckage.getDate_start());
        preStatementInsertPackages.setString(5, pckage.getDate_end());
        preStatementInsertPackages.setString(6, pckage.getCourier_id());
        preStatementInsertPackages.setString(7, pckage.getSender_id());
        preStatementInsertPackages.setString(8, pckage.getRecipient_id());
        preStatementInsertPackages.setString(9, pckage.getDepartcenter_id());
        preStatementInsertPackages.setString(10, pckage.getReceivingcenter_id());

        preStatementInsertPackages.executeUpdate();
    }

    public void deletePackage(String id) throws SQLException {
        String deletePackage = "DELETE FROM packages WHERE id =?";
        PreparedStatement preStatementPackages = getConnection().prepareStatement(deletePackage);

        preStatementPackages.setString(1, id);
        preStatementPackages.executeUpdate();
    }

    public void updateDeliveryCenter(DeliveryCenter deliveryCenter) throws SQLException{
        String updateDeliveryCenter = "UPDATE delivery_centers SET name =?, address =? WHERE id =?";
        PreparedStatement prstupdateDC = getConnection().prepareStatement(updateDeliveryCenter);

        prstupdateDC.setString(1, deliveryCenter.getName());
        prstupdateDC.setString(2, deliveryCenter.getAddress());
        prstupdateDC.setString(3, deliveryCenter.getId());


        prstupdateDC.executeUpdate();
    }

    public void addDeliveryCenter(DeliveryCenter deliveryCenter) throws SQLException{

        String insertDeliveryCenter = "INSERT INTO delivery_centers(name, address) VALUES(?,?)";
        PreparedStatement prStInsertDC = getConnection().prepareStatement(insertDeliveryCenter);

        prStInsertDC.setString(1, deliveryCenter.getName());
        prStInsertDC.setString(2, deliveryCenter.getAddress());

        prStInsertDC.executeUpdate();
    }

    public void deleteDeliveryCenter(String id) throws SQLException {
        String deleteDC = "DELETE FROM delivery_centers WHERE id =?";
        PreparedStatement prstDC = getConnection().prepareStatement(deleteDC);

        prstDC.setString(1, id);
        prstDC.executeUpdate();
    }
}
