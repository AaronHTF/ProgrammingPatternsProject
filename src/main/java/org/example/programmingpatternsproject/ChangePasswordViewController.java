package org.example.programmingpatternsproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class ChangePasswordViewController {
    @FXML
    private PasswordField currentPasswordTextField, newPasswordTextField, confirmNewPasswordTextField;
    @FXML
    private Button cancelButton, confirmButton;
    @FXML
    private Label changePasswordLabel;

    Client sessionClient;
    Language language = Language.getInstance();

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
            alert.setHeaderText(language.getResourceBundle().getString("pleaseFillAllTheFields"));
            alert.showAndWait();
        } else {
            try {
                if (!currentPassword.equals(sessionClient.getPassword())) {
                    throw new WrongPasswordException();
                } else if (!newPassword.equals(confirmNewPassword)) {
                    throw new PasswordNotMatchingException();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                    alert.setHeaderText(language.getResourceBundle().getString("changePasswordConfirmation"));
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
                alert.setHeaderText(language.getResourceBundle().getString("incorrectPassword"));
                alert.setContentText(language.getResourceBundle().getString("wrongPasswordException"));
                alert.showAndWait();
            }
            catch (PasswordNotMatchingException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(language.getResourceBundle().getString("invalidPassword"));
                alert.setContentText(language.getResourceBundle().getString("passwordNotMatchingException"));
                alert.showAndWait();
            }
        }
    }

    public void loadMessages() {
        changePasswordLabel.setText(language.getResourceBundle().getString("changePassword"));
        currentPasswordTextField.setPromptText(language.getResourceBundle().getString("currentPassword"));
        newPasswordTextField.setPromptText(language.getResourceBundle().getString("newPassword"));
        confirmNewPasswordTextField.setPromptText(language.getResourceBundle().getString("confirmNewPassword"));
        confirmButton.setText(language.getResourceBundle().getString("confirm"));
        cancelButton.setText(language.getResourceBundle().getString("cancel"));
    }

    public void getSessionClient(Client client) {
        sessionClient = client;
    }
}
