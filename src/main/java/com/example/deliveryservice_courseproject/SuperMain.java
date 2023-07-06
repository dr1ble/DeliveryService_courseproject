package com.example.deliveryservice_courseproject;

import com.example.deliveryservice_courseproject.Models.DBConnection;

import java.sql.SQLException;

public class SuperMain {
    public static void main(String[] args) throws SQLException {
        Main.main(args);
        DBConnection.getInstance();
    }
}
