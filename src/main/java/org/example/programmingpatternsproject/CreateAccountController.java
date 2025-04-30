package org.example.programmingpatternsproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CreateAccountController {
    @FXML
    private Button cancelButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    public PasswordField passwordTextField;
    @FXML
    private PasswordField confirmPasswordTextField;
    @FXML
    private Button createAccountButton;
    @FXML
    private Label enterAccountDetailsLabel;

    ClientManager clientManager = ClientManager.getClients();
    Language language = Language.getInstance();

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleCreateAccountButtonAction() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        if (username.trim().isEmpty() || password.trim().isEmpty() || confirmPassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(language.getResourceBundle().getString("pleaseFillAllTheFields"));
            alert.showAndWait();
        } else {
            try {
                if (!password.equals(confirmPassword)) {
                    throw new PasswordNotMatchingException();
                }
                Client client = new Client(username, password);
                clientManager.addClient(client);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(language.getResourceBundle().getString("accountCreated"));
                alert.setContentText(language.getResourceBundle().getString("accountHasBeenCreated") + "\n" +
                        language.getResourceBundle().getString("userId") + ": " + client.getUserId() + "\n" +
                        language.getResourceBundle().getString("username") + ": " + client.getClientName());
                alert.showAndWait();
                Stage stage = (Stage) createAccountButton.getScene().getWindow();
                stage.close();
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
        cancelButton.setText(language.getResourceBundle().getString("cancel"));
        createAccountButton.setText(language.getResourceBundle().getString("createAccount"));
        usernameTextField.setPromptText(language.getResourceBundle().getString("username"));
        passwordTextField.setPromptText(language.getResourceBundle().getString("password"));
        confirmPasswordTextField.setPromptText(language.getResourceBundle().getString("confirmPassword"));
        enterAccountDetailsLabel.setText(language.getResourceBundle().getString("enterAccountDetails"));
    }
}
