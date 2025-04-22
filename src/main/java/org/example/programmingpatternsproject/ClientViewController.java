package org.example.programmingpatternsproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClientViewController {
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
}
