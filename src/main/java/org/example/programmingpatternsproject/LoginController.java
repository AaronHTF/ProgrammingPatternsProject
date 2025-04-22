package org.example.programmingpatternsproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
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

    ClientManager clientManager = ClientManager.getClients();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientManager.loadClientsFromDatabase();
    }

    @FXML
    public void handleCreateAccountButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createAccount.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Create Account");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleLoginButtonAction(ActionEvent event) throws IOException {
        String userId = userIdTextField.getText();
        String password = passwordTextField.getText();

        if (userId.trim().isEmpty() || password.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please fill all the fields!");
            alert.showAndWait();
        } else {
            try {
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
}
