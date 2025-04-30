package org.example.programmingpatternsproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private TableView<Ticket> ticketsTable;
    @FXML
    private TableColumn<Ticket, Integer> ticketIdColumn;
    @FXML
    private TableColumn<Ticket, String> clientIdColumn;
    @FXML
    private TableColumn<Ticket, String> flightIdColumn;
    @FXML
    private TableColumn<Ticket, String> dateColumn;
    @FXML
    private TableColumn<Ticket, String> classOfServiceColumn;
    @FXML
    private TableColumn<Ticket, String> statusColumn;
    @FXML
    private Button filterByFlightButton;
    @FXML
    private Button sortByDateButton;
    @FXML
    private Button confirmTicket;
    @FXML
    private Button denyTicket;
    @FXML
    private Button resetFiltersButton;
    @FXML
    private ChoiceBox<String> sourceChoiceBox;
    @FXML
    private ChoiceBox<String> destinationChoiceBox;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private ChoiceBox<String> classChoiceBox;
    @FXML
    private Label sourceLabel;
    @FXML
    private Label destinationLabel;
    @FXML
    private Label filterByStatusLabel;
    @FXML
    private Label filterByClassLabel;

    TicketManager ticketManager = TicketManager.getTickets();
    ObservableList<Ticket> tickets;
    Database db = Database.getInstance();
    ArrayList<Flight> flights;
    HashSet<String> sourceList;
    HashSet<String> destinationList;
    Language language = Language.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        flightIdColumn.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        classOfServiceColumn.setCellValueFactory(new PropertyValueFactory<>("classOfService"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        setTableContent();
        loadChoiceBoxes();
    }

    public void handleFilterByFlightButtonAction() {
        String source = sourceChoiceBox.getValue();
        String destination = destinationChoiceBox.getValue();
        if (source == null && destination == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(language.getResourceBundle().getString("chooseSourceAndDestination"));
            alert.showAndWait();
        } else if (source == null) {
            tickets = FXCollections.observableArrayList(ticketManager.filterByDestination(destination));
            ticketsTable.setItems(tickets);
        } else if (destination == null) {
            tickets = FXCollections.observableArrayList(ticketManager.filterBySource(source));
            ticketsTable.setItems(tickets);
        } else {
            tickets = FXCollections.observableArrayList(ticketManager.filterByFlight(source, destination));
            ticketsTable.setItems(tickets);
        }
    }

    public void filterByClassOfService(ActionEvent event) {
        String classOfService = classChoiceBox.getValue();
        tickets = FXCollections.observableArrayList(ticketManager.filterByClassOfService(classOfService));
        ticketsTable.setItems(tickets);
    }

    public void filterByStatus(ActionEvent event) {
        String status = statusChoiceBox.getValue();
        tickets = FXCollections.observableArrayList(ticketManager.filterByStatus(status));
        ticketsTable.setItems(tickets);
    }

    public void handleConfirmTicketButtonAction() {
        if (ticketsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(language.getResourceBundle().getString("selectATicket"));
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(language.getResourceBundle().getString("confirmFlightTicket"));
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    ticketManager.confirmTicket(ticketsTable.getSelectionModel().getSelectedItem());
                    ticketManager.reloadTickets();
                    setTableContent();
                }
            });
        }
    }

    public void handleDenyTicketButtonAction() {
        if (ticketsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(language.getResourceBundle().getString("selectATicket"));
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(language.getResourceBundle().getString("denyFlightTicket"));
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    ticketManager.deny(ticketsTable.getSelectionModel().getSelectedItem());
                    ticketManager.reloadTickets();
                    setTableContent();
                }
            });
        }
    }

    public void handleSortByDateButtonAction() {
        ticketManager.sortByDate();
        setTableContent();
    }

    public void handleResetFiltersButtonAction() {
        ticketManager.reloadTickets();
        setTableContent();
    }

    public void chooseSource(ActionEvent event) {
        String source = sourceChoiceBox.getValue();
        destinationList = new HashSet<>();
        for (Flight flight : flights) {
            if (flight.getSource().equals(source)) {
                destinationList.add(flight.getDestination());
            }
        }
        destinationChoiceBox.getItems().setAll(destinationList);
    }

    public void chooseDestination(ActionEvent event) {
        String destination = destinationChoiceBox.getValue();
        sourceList = new HashSet<>();
        for (Flight flight : flights) {
            if (flight.getDestination().equals(destination)) {
                sourceList.add(flight.getSource());
            }
        }
        sourceChoiceBox.getItems().setAll(sourceList);
    }

    public void setTableContent() {
        tickets = FXCollections.observableArrayList(ticketManager.getList());
        ticketsTable.setItems(tickets);
    }

    public void loadChoiceBoxes() {
        String[] classesOfService = {"Economy", "Premium Economy", "Business", "First Class"};
        String[] status = {"Sent", "Confirmed", "Denied"};
        flights = db.selectFlights();
        sourceList = new HashSet<>();
        destinationList = new HashSet<>();
        for (Flight flight : flights) {
            sourceList.add(flight.getSource());
            destinationList.add(flight.getDestination());
        }
        sourceChoiceBox.getItems().setAll(sourceList);
        destinationChoiceBox.getItems().setAll(destinationList);
        classChoiceBox.getItems().setAll(classesOfService);
        statusChoiceBox.getItems().setAll(status);

        sourceChoiceBox.setOnAction(this::chooseSource);
        destinationChoiceBox.setOnAction(this::chooseDestination);
        classChoiceBox.setOnAction(this::filterByClassOfService);
        statusChoiceBox.setOnAction(this::filterByStatus);
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
        filterByFlightButton.setText(language.getResourceBundle().getString("filterByFlight"));
        sourceLabel.setText(language.getResourceBundle().getString("source"));
        destinationLabel.setText(language.getResourceBundle().getString("destination"));
        filterByStatusLabel.setText(language.getResourceBundle().getString("filterByStatus"));
        filterByClassLabel.setText(language.getResourceBundle().getString("filterByClass"));
        logoutButton.setText(language.getResourceBundle().getString("logout"));
        ticketIdColumn.setText(language.getResourceBundle().getString("ticketId"));
        clientIdColumn.setText(language.getResourceBundle().getString("clientId"));
        flightIdColumn.setText(language.getResourceBundle().getString("flightId"));
        dateColumn.setText(language.getResourceBundle().getString("date"));
        classOfServiceColumn.setText(language.getResourceBundle().getString("classOfService"));
        statusColumn.setText(language.getResourceBundle().getString("status"));
        sortByDateButton.setText(language.getResourceBundle().getString("sortByDate"));
        confirmTicket.setText(language.getResourceBundle().getString("confirmTicket"));
        denyTicket.setText(language.getResourceBundle().getString("denyTicket"));
        resetFiltersButton.setText(language.getResourceBundle().getString("resetFilters"));
    }
}
