module com.example.moneyapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.moneyapp to javafx.fxml;
    exports com.example.moneyapp;
}