module com.example.mainapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mainapp to javafx.fxml;
    exports com.example.mainapp;
    exports com.example.mainapp.Iterator;
    opens com.example.mainapp.Iterator to javafx.fxml;
}