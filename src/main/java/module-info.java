module org.example.programmingpatternsproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.programmingpatternsproject to javafx.fxml;
    exports org.example.programmingpatternsproject;
}