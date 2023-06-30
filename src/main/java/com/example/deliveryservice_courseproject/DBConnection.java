package com.example.deliveryservice_courseproject;

import Utils.HashCoder;

import java.sql.*;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;
    private String url = "jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2289_deliveryservice";
    private String username = "std_2289_deliveryservice";
    private String password = "qwerty123";


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
        String insertUsers = "INSERT INTO " + DBConsts.USERS_TABLE + "(" + DBConsts.USERS_LOGIN + "," + DBConsts.USERS_PASSWORD + ")" + "VALUES(?,?)" + ";";
        PreparedStatement preStatementUsers = getConnection().prepareStatement(insertUsers);
        preStatementUsers.setString(1, user.getLogin()); preStatementUsers.setString(2, HashCoder.toHash(user.getPassword()));
        preStatementUsers.executeUpdate();

        signUpClient(user);
    }

    public void signUpClient(User user) throws SQLException {
        String insertClients = "INSERT INTO " + DBConsts.CLIENTS_TABLE + "(" + DBConsts.CLIENTS_NAME + "," + DBConsts.CLIENTS_NUMBER + "," + DBConsts.CLIENTS_ADDRESS + "," + DBConsts.CLIENTS_USERID +")" + "VALUES(?,?,?,?)";

        PreparedStatement preStatementClients = getConnection().prepareStatement(insertClients);
        preStatementClients.setString(1, user.getName());
        preStatementClients.setString(2, user.getNumber());
        preStatementClients.setString(3, user.getAddress());
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
        String selectUserdata = "SELECT " + DBConsts.CLIENTS_NAME + ", " + DBConsts.CLIENTS_NUMBER + ", " + DBConsts.CLIENTS_ADDRESS + " FROM " + DBConsts.CLIENTS_TABLE +
                " JOIN " + DBConsts.USERS_TABLE + " ON " + DBConsts.CLIENTS_TABLE + "." + DBConsts.CLIENTS_USERID + "=" + DBConsts.USERS_TABLE + "." + DBConsts.USERS_ID +
                " WHERE " + DBConsts.USERS_TABLE + "." + DBConsts.USERS_LOGIN  + "=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(selectUserdata);
        preparedStatement.setString(1, login);
        resultSet = preparedStatement.executeQuery();
        User user = null;

        if (resultSet.next()) {
            String name = resultSet.getString(DBConsts.CLIENTS_NAME);
            String number = resultSet.getString(DBConsts.CLIENTS_NUMBER);
            String address = resultSet.getString(DBConsts.CLIENTS_ADDRESS);
            user = new User(name, number, address, login, HashCoder.toHash(password));
        }

        return user;
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
}
