module org.example.programmingpatternsproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    requires java.sql;


    opens org.example.programmingpatternsproject to javafx.fxml;
    exports org.example.programmingpatternsproject;
}