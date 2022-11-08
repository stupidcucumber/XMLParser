module com.example.xmlparser {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.xmlparser to javafx.fxml;
    exports com.example.xmlparser;
}