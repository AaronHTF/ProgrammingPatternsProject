package org.example.programmingpatternsproject;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientViewController implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label userIdLabel;
    @FXML
    private TableView<TicketInformation> bookedFlightsTable;
    @FXML
    private TableColumn<TicketInformation, Integer> ticketIdColumn;
    @FXML
    private TableColumn<TicketInformation, String> sourceColumn;
    @FXML
    private TableColumn<TicketInformation, String> destinationColumn;
    @FXML
    private TableColumn<TicketInformation, String> dateColumn;
    @FXML
    private TableColumn<TicketInformation, String> classOfServiceColumn;
    @FXML
    private TableColumn<TicketInformation, String> statusColumn;

    Client sessionClient;
    TicketManager ticketManager = TicketManager.getTickets();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        classOfServiceColumn.setCellValueFactory(new PropertyValueFactory<>("classOfService"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void loadClient(Client client) {
        sessionClient = client;
        usernameLabel.setText(usernameLabel.getText() + client.getClientName());
        userIdLabel.setText(userIdLabel.getText() + client.getUserId());

        ArrayList<Ticket> clientTickets = sessionClient.getTickets();
        ArrayList<TicketInformation> ticketsInformation = new ArrayList<>();

        ObservableList<TicketInformation> tickets;
        for (Ticket ticket : clientTickets) {
            ticketsInformation.add(new TicketInformation(ticket, sessionClient));
        }
        tickets = FXCollections.observableArrayList(ticketsInformation);
        bookedFlightsTable.setItems(tickets);
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
