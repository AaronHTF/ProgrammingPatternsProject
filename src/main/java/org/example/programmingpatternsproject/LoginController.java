package org.example.programmingpatternsproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private TextField userIdTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button englishButton, frenchButton, loginButton, createAccountButton;
    @FXML
    private Label titleLabel, loginLabel;

    ClientManager clientManager = ClientManager.getClients();
    Language language = Language.getInstance();

    public void handleCreateAccountButtonAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createAccountView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        CreateAccountController createAccountController = fxmlLoader.getController();
        createAccountController.loadMessages();
        stage.setTitle(language.getResourceBundle().getString("createAccount"));
        stage.setScene(scene);
        stage.show();
    }

    public void handleLoginButtonAction() throws IOException {
        String userId = userIdTextField.getText();
        String password = passwordTextField.getText();

        if (userId.trim().isEmpty() || password.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(language.getResourceBundle().getString("pleaseFillAllTheFields"));
            alert.showAndWait();
        } else {
            try {
                if (userId.equals("1000000")) {
                    Admin admin = Admin.getAdmin();
                    if (!password.equals(admin.getPassword())) {
                        throw new WrongPasswordException();
                    } else {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adminView.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(fxmlLoader.load());
                        AdminViewController adminViewController = fxmlLoader.getController();
                        adminViewController.loadMessages();
                        stage.setTitle(language.getResourceBundle().getString("title"));
                        stage.setScene(scene);
                        stage.show();
                        Stage thisStage = (Stage) loginButton.getScene().getWindow();
                        thisStage.close();
                    }
                } else {
                    Client client = clientManager.searchClientById(userId);
                    if (!client.getPassword().equals(password)) {
                        throw new WrongPasswordException();
                    } else {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clientView.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(fxmlLoader.load());
                        ClientViewController clientViewController = fxmlLoader.getController();
                        clientViewController.loadClient(client);
                        clientViewController.loadMessages();
                        stage.setTitle(language.getResourceBundle().getString("title"));
                        stage.setScene(scene);
                        stage.show();
                        Stage thisStage = (Stage) loginButton.getScene().getWindow();
                        thisStage.close();
                    }
                }
            }
            catch (ClientNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(language.getResourceBundle().getString("userIdError"));
                alert.setContentText(language.getResourceBundle().getString("clientNotFoundException"));
                alert.showAndWait();
            }
            catch (WrongPasswordException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(language.getResourceBundle().getString("incorrectPassword"));
                alert.setContentText(language.getResourceBundle().getString("wrongPasswordException"));
                alert.showAndWait();
            }

        }
    }

    public void changeLanguage(ResourceBundle resourceBundle) {
        frenchButton.setText(resourceBundle.getString("french"));
        englishButton.setText(resourceBundle.getString("english"));
        titleLabel.setText(resourceBundle.getString("title"));
        userIdTextField.setPromptText(resourceBundle.getString("userId"));
        passwordTextField.setPromptText(resourceBundle.getString("password"));
        loginButton.setText(resourceBundle.getString("login"));
        loginLabel.setText(resourceBundle.getString("login"));
        createAccountButton.setText(resourceBundle.getString("createAccount"));
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setTitle(resourceBundle.getString("title"));
    }

    public void handleEnglishButtonAction() {
        frenchButton.setDisable(false);
        englishButton.setDisable(true);
        language.changeToEnglish();
        changeLanguage(language.getResourceBundle());
    }

    public void handleFrenchButtonAction() {
        englishButton.setDisable(false);
        frenchButton.setDisable(true);
        language.changeToFrench();
        changeLanguage(language.getResourceBundle());
    }
}
