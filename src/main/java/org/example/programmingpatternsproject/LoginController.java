package org.example.programmingpatternsproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public Button createAccountButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField userIdTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button englishButton;
    @FXML
    private Button frenchButton;
    @FXML
    private Label titleLabel;
    @FXML
    private Label loginLabel;

    ClientManager clientManager = ClientManager.getClients();
    Locale locale;
    ResourceBundle resourceBundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void handleCreateAccountButtonAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createAccountView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Create Account");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleLoginButtonAction() throws IOException {
        String userId = userIdTextField.getText();
        String password = passwordTextField.getText();

        if (userId.trim().isEmpty() || password.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please fill all the fields!");
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
                        stage.setTitle("Airline Management System");
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
                        stage.setTitle("Airline Management System");
                        stage.setScene(scene);
                        stage.show();
                        Stage thisStage = (Stage) loginButton.getScene().getWindow();
                        thisStage.close();
                    }
                }
            }
            catch (ClientNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("User ID error!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            catch (WrongPasswordException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Incorrect Password");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        }
    }

    public void changeLanguage(Locale locale, ResourceBundle resourceBundle) {
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
        locale = Locale.of("en", "US");
        resourceBundle = ResourceBundle.getBundle("Messages", locale);
        changeLanguage(locale, resourceBundle);
    }

    public void handleFrenchButtonAction() {
        englishButton.setDisable(false);
        frenchButton.setDisable(true);
        locale = Locale.of("fr", "CA");
        resourceBundle = ResourceBundle.getBundle("Messages", locale);
        changeLanguage(locale, resourceBundle);
    }
}
