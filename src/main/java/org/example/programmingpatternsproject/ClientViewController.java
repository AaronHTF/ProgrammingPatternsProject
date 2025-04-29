package org.example.programmingpatternsproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
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
    @FXML
    private Button bookFlightButton;

    Client sessionClient;
    ObservableList<TicketInformation> tickets;
    ArrayList<TicketInformation> ticketsInformation;

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
        loadTableContent();
    }

    public void loadTableContent() {
        bookedFlightsTable.setPlaceholder(new Label("You do not have any flights booked currently"));
        ArrayList<Ticket> clientTickets = sessionClient.getTickets();
        ticketsInformation = new ArrayList<>();
        for (Ticket ticket : clientTickets) {
            ticketsInformation.add(new TicketInformation(ticket, sessionClient));
        }
        setTableContent();
    }

    public void setTableContent() {
        tickets = FXCollections.observableArrayList(ticketsInformation);
        bookedFlightsTable.setItems(tickets);
    }

    @FXML
    public void handleBookFlightButtonAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookFlightView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        BookFlightViewController bookFlightViewController = fxmlLoader.getController();
        bookFlightViewController.loadClient(sessionClient);
        bookFlightViewController.setParentController(this);
        stage.setTitle("Book a Flight");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleDeleteFlightButtonAction() {
        if (bookedFlightsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please select a flight");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText("Are you sure you want to cancel this flight?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    int id = bookedFlightsTable.getSelectionModel().getSelectedItem().getTicketId();
                    TicketManager ticketManager = TicketManager.getTickets();
                    ticketManager.deleteTicketById(id);
                    loadTableContent();
                }
            });
        }
    }

    @FXML
    public void handleChangeDateButtonAction() throws IOException {
        if (bookedFlightsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please select a flight");
            alert.showAndWait();
        } else {
            int id = bookedFlightsTable.getSelectionModel().getSelectedItem().getTicketId();
            TicketManager ticketManager = TicketManager.getTickets();
            Ticket ticket = ticketManager.searchTicketById(id);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("changeDateView.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            ChangeDateViewController changeDateViewController = fxmlLoader.getController();
            changeDateViewController.loadDate(ticket);
            changeDateViewController.setParentController(this);
            stage.setTitle("Change date");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void handleChangePasswordButtonAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("changePasswordView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        ChangePasswordViewController changePasswordViewController = fxmlLoader.getController();
        changePasswordViewController.getSessionClient(sessionClient);
        stage.setTitle("Change password");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleLogoutButtonAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Logging out");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginView.fxml"));
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
