package com.example.deliveryservice_courseproject;

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

    public void signUpUser(String name, String number, String address, String login, String password) throws SQLException {
        String insertUsers = "INSERT INTO " + DBConsts.USERS_TABLE + "(" + DBConsts.USERS_LOGIN + "," + DBConsts.USERS_PASSWORD + ")" + "VALUES(?,?)" + ";";
        String insertClients = "INSERT INTO " + DBConsts.CLIENTS_TABLE + "(" + DBConsts.CLIENTS_NAME + "," + DBConsts.CLIENTS_NUMBER + "," + DBConsts.CLIENTS_ADDRESS + "," + DBConsts.CLIENTS_LOGIN +")" + "VALUES(?,?,?,?)";
        PreparedStatement preStatementUsers = getConnection().prepareStatement(insertUsers);
        preStatementUsers.setString(1, login); preStatementUsers.setString(2, password);

        PreparedStatement preStatementClients = getConnection().prepareStatement(insertClients);
        preStatementClients.setString(1, name);
        preStatementClients.setString(2, number);
        preStatementClients.setString(3, address);
        preStatementClients.setString(4, login);

        preStatementUsers.executeUpdate();
        preStatementClients.executeUpdate();
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

}
