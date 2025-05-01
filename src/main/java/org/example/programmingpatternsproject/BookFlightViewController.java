package org.example.programmingpatternsproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class BookFlightViewController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private ChoiceBox<String> sourceChoiceBox;
    @FXML
    private ChoiceBox<String> destinationChoiceBox;
    @FXML
    private ChoiceBox<String> classOfServiceChoiceBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label titleLabel;
    @FXML
    private Label fromLabel;
    @FXML
    private Label toLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label classOfServiceLabel;

    private ClientViewController controller;
    Client sessionClient;
    ArrayList<Flight> flights;
    HashSet<String> sourceList;
    HashSet<String> destinationList;
    String source;
    String destination;
    String classOfService;
    LocalDate date;
    Database db = Database.getInstance();
    Language language = Language.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadChoiceBoxes();
    }

    public void handleConfirmButtonAction() {
        if (source == null || destination == null || classOfService == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(language.getResourceBundle().getString("fillBoxes"));
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(language.getResourceBundle().getString("confirmFlight"));
            alert.setContentText(language.getResourceBundle().getString("bookAFlight") +
                    "\n" + language.getResourceBundle().getString("from") + " " + source +
                    "\n" + language.getResourceBundle().getString("to") + " " + destination +
                    "\n" + language.getResourceBundle().getString("date") + " " + date +
                    "\n" + language.getResourceBundle().getString("classOfService") + " " + classOfService);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    TicketManager ticketManager = TicketManager.getTickets();
                    for (Flight flight : flights) {
                        if (flight.getSource().equals(source) && flight.getDestination().equals(destination)) {
                            Ticket ticket = new Ticket(sessionClient.getUserId(), flight.getFlightId(), date.toString(), classOfService);
                            ticketManager.addTicket(ticket);
                            controller.ticketsInformation.add(new TicketInformation(ticket, sessionClient));
                            break;
                        }
                    }
                    controller.setTableContent();
                    Stage stage = (Stage) cancelButton.getScene().getWindow();
                    stage.close();
                }
            });
        }
    }

    public void handleCancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void selectSource(ActionEvent event) {
        source = sourceChoiceBox.getValue();
        destinationList = new HashSet<>();
        for (Flight flight : flights) {
            if (flight.getSource().equals(source)) {
                destinationList.add(flight.getDestination());
            }
        }
        destinationChoiceBox.getItems().setAll(destinationList);
    }

    public void selectDestination(ActionEvent event) {
        destination = destinationChoiceBox.getValue();
    }

    public void selectClassOfService(ActionEvent event) {
        classOfService = classOfServiceChoiceBox.getValue();
    }

    public void selectDate(ActionEvent event) {
        date = datePicker.getValue();
    }

    public void loadClient(Client client) {
        sessionClient = client;
    }

    public void loadChoiceBoxes() {
        LocalDate minDate = LocalDate.now();
        datePicker.setDayCellFactory(d ->
            new DateCell() {
                @Override public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(item.isBefore(minDate));
                }
            }
        );

        String[] classesOfService = {"Economy", "Premium Economy", "Business", "First Class"};
        flights = db.selectFlights();
        sourceList = new HashSet<>();
        for (Flight flight : flights) {
            sourceList.add(flight.getSource());
        }
        sourceChoiceBox.getItems().setAll(sourceList);
        classOfServiceChoiceBox.getItems().setAll(classesOfService);

        sourceChoiceBox.setOnAction(this::selectSource);
        destinationChoiceBox.setOnAction(this::selectDestination);
        datePicker.setOnAction(this::selectDate);
        classOfServiceChoiceBox.setOnAction(this::selectClassOfService);
    }

    public void loadMessages() {
        titleLabel.setText(language.getResourceBundle().getString("bookAFlight"));
        fromLabel.setText(language.getResourceBundle().getString("from"));
        toLabel.setText(language.getResourceBundle().getString("to"));
        dateLabel.setText(language.getResourceBundle().getString("date"));
        classOfServiceLabel.setText(language.getResourceBundle().getString("classOfService"));
        cancelButton.setText(language.getResourceBundle().getString("cancel"));
        confirmButton.setText(language.getResourceBundle().getString("confirm"));
    }

    public void setParentController(ClientViewController controller) {
        this.controller = controller;
    }
}
