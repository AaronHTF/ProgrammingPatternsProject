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
    private Button logoutButton, bookFlightButton, cancelFlightButton, changeDateButton, changePasswordButton;
    @FXML
    private Label usernameLabel, userIdLabel;
    @FXML
    private TableView<TicketInformation> bookedFlightsTable;
    @FXML
    private TableColumn<TicketInformation, Integer> ticketIdColumn;
    @FXML
    private TableColumn<TicketInformation, String> sourceColumn, destinationColumn, dateColumn, classOfServiceColumn, statusColumn;

    Client sessionClient;
    ObservableList<TicketInformation> tickets;
    ArrayList<TicketInformation> ticketsInformation;
    Language language = Language.getInstance();

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
        loadTableContent();
    }

    public void loadTableContent() {
        bookedFlightsTable.setPlaceholder(new Label(language.getResourceBundle().getString("noBookedFlights")));
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

    public void handleBookFlightButtonAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookFlightView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        BookFlightViewController bookFlightViewController = fxmlLoader.getController();
        bookFlightViewController.loadClient(sessionClient);
        bookFlightViewController.loadMessages();
        bookFlightViewController.setParentController(this);
        stage.setTitle(language.getResourceBundle().getString("bookAFlight"));
        stage.setScene(scene);
        stage.show();
    }

    public void handleDeleteFlightButtonAction() {
        if (bookedFlightsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(language.getResourceBundle().getString("chooseAFlight"));
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(language.getResourceBundle().getString("cancelFlightConfirmation"));
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

    public void handleChangeDateButtonAction() throws IOException {
        if (bookedFlightsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(language.getResourceBundle().getString("chooseAFlight"));
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
            changeDateViewController.loadMessages();
            changeDateViewController.setParentController(this);
            stage.setTitle(language.getResourceBundle().getString("changeDate"));
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
        changePasswordViewController.loadMessages();
        stage.setTitle(language.getResourceBundle().getString("changePassword"));
        stage.setScene(scene);
        stage.show();
    }

    public void handleLogoutButtonAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, language.getResourceBundle().getString("logoutConfirmation"), ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(language.getResourceBundle().getString("loggingOut"));
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginView.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle(language.getResourceBundle().getString("title"));
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

    public void loadMessages() {
        usernameLabel.setText(language.getResourceBundle().getString("username") + ": " + sessionClient.getClientName());
        userIdLabel.setText(language.getResourceBundle().getString("userId") + ": " + sessionClient.getUserId());
        logoutButton.setText(language.getResourceBundle().getString("logout"));
        bookFlightButton.setText(language.getResourceBundle().getString("bookAFlight"));
        ticketIdColumn.setText(language.getResourceBundle().getString("ticketNumber"));
        sourceColumn.setText(language.getResourceBundle().getString("from"));
        destinationColumn.setText(language.getResourceBundle().getString("to"));
        dateColumn.setText(language.getResourceBundle().getString("date"));
        classOfServiceColumn.setText(language.getResourceBundle().getString("classOfService"));
        statusColumn.setText(language.getResourceBundle().getString("status"));
        cancelFlightButton.setText(language.getResourceBundle().getString("cancelFlight"));
        changeDateButton.setText(language.getResourceBundle().getString("changeDate"));
        changePasswordButton.setText(language.getResourceBundle().getString("changePassword"));
    }
}
