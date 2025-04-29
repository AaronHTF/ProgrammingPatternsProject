package org.example.programmingpatternsproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


public class ChangePasswordViewController {
    @FXML
    private PasswordField currentPasswordTextField;
    @FXML
    private PasswordField newPasswordTextField;
    @FXML
    private PasswordField confirmNewPasswordTextField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;

    Client sessionClient;

    public void handleCancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void handleConfirmButtonAction() {
        String currentPassword = currentPasswordTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String confirmNewPassword = confirmNewPasswordTextField.getText();

        if (currentPassword.trim().isEmpty() || newPassword.trim().isEmpty() || confirmNewPassword.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please fill all the boxes before confirming");
            alert.showAndWait();
        } else {
            try {
                if (!currentPassword.equals(sessionClient.getPassword())) {
                    throw new WrongPasswordException();
                } else if (!newPassword.equals(confirmNewPassword)) {
                    throw new PasswordNotMatchingException();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                    alert.setHeaderText("Are you sure you want to change your password?");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            ClientManager clientManager = ClientManager.getClients();
                            clientManager.changeClientPassword(sessionClient, newPassword);
                        }
                    });
                    Stage stage = (Stage) confirmButton.getScene().getWindow();
                    stage.close();
                }
            }
            catch (WrongPasswordException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Incorrect Password");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            catch (PasswordNotMatchingException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Password!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void getSessionClient(Client client) {
        sessionClient = client;
    }
}
