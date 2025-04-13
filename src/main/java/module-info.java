module org.example.programmingpatternsproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.programmingpatternsproject to javafx.fxml;
    exports org.example.programmingpatternsproject;
}