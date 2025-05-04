// -------------------------------------------------------
// Final Project
//        Written by: Aaron Ho Tim Fat (2314854)
//        For “Programming Patterns” Section 2 – Winter 2025
// --------------------------------------------------------

package org.example.programmingpatternsproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Airline System Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
