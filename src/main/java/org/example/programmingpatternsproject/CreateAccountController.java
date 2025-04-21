package org.example.programmingpatternsproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class CreateAccountController {
    @FXML
    private Button cancelButton;

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
