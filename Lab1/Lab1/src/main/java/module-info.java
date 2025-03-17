module com.example.lab {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.lab1 to javafx.fxml;
    exports com.example.lab1;
    exports com.example.lab1.Towers;
    opens com.example.lab1.Towers to javafx.fxml;
    exports com.example.lab1.Factories;
    opens com.example.lab1.Factories to javafx.fxml;
    exports com.example.lab1.Minions;
    opens com.example.lab1.Minions to javafx.fxml;
}