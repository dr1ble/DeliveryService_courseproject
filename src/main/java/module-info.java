module com.example.deliveryservice_courseproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.deliveryservice_courseproject to javafx.fxml;
    exports com.example.deliveryservice_courseproject;
}