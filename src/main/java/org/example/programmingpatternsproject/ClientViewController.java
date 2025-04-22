package org.example.programmingpatternsproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class ClientViewController {
    @FXML
    private Button logoutButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label userIdLabel;
    public Client sessionClient;

    public void loadClient(Client client) {
        sessionClient = client;
        usernameLabel.setText(usernameLabel.getText() + client.getClientName());
        userIdLabel.setText(userIdLabel.getText() + client.getUserId());
    }

    @FXML
    public void handleLogoutButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Logging out");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle("Airline System Login");
                    stage.setScene(scene);
                    stage.show();
                    Stage thisStage = (Stage) logoutButton.getScene().getWindow();
                    thisStage.close();
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

    }
}
