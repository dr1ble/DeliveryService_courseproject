package com.example.deliveryservice_courseproject;
import java.sql.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class HomePage extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
        primaryStage.setTitle("DeliveryService");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) throws SQLException {
        launch();
        DBConnection db= DBConnection.getInstance();
        Statement statement = db.getConnection().createStatement();
        String query = "SELECT * FROM A" ;
        ResultSet result = statement.executeQuery(query);
        System.out.println(result);
        while (result.next()){
            int id = result.getInt("id");
            System.out.print(" id = " + id);
        }
    }
}