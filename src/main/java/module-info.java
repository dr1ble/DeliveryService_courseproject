module com.example.deliveryservice_courseproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.deliveryservice_courseproject to javafx.fxml;
    exports com.example.deliveryservice_courseproject;
    exports com.example.deliveryservice_courseproject.Controllers;
    opens com.example.deliveryservice_courseproject.Controllers to javafx.fxml;
    exports com.example.deliveryservice_courseproject.Other;
    opens com.example.deliveryservice_courseproject.Other to javafx.fxml;
    exports com.example.deliveryservice_courseproject.Models;
    opens com.example.deliveryservice_courseproject.Models to javafx.fxml;
}