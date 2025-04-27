package org.example.programmingpatternsproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

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

    ClientManager clientManager = ClientManager.getClients();

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
            alert.setHeaderText("Please fill all the fields!");
            alert.showAndWait();
        } else {
            try {
                if (!password.equals(confirmPassword)) {
                    throw new PasswordNotMatchingException();
                }
                Client client = new Client(username, password);
                clientManager.addClient(client);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Account Created!");
                alert.setContentText("Your account has been created!\n" +
                        "User ID: " + client.getUserId() +
                        "\nUsername: " + client.getClientName());
                alert.showAndWait();
                Stage stage = (Stage) createAccountButton.getScene().getWindow();
                stage.close();
            }
            catch (PasswordNotMatchingException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Password!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
