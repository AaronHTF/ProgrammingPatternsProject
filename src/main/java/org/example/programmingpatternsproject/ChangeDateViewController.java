package org.example.programmingpatternsproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ChangeDateViewController {
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label changeDateLabel;

    ClientViewController controller;
    Ticket ticket;
    Language language = Language.getInstance();

    public void loadDate(Ticket ticket) {
        LocalDate minDate = LocalDate.now();
        datePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(minDate));
                    }
                }
        );

        this.ticket = ticket;
        datePicker.setValue(LocalDate.parse(ticket.getDate()));
    }

    public void handleConfirmButtonAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(language.getResourceBundle().getString("changeDateConfirmation"));
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                TicketManager ticketManager = TicketManager.getTickets();
                ticketManager.updateTicket(ticket.getTicketId(), ticket.getUserId(), ticket.getFlightId(), datePicker.getValue().toString(), ticket.getClassOfService(), ticket.getStatus());
                controller.loadTableContent();
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void handleCancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setParentController(ClientViewController controller) {
        this.controller = controller;
    }

    public void loadMessages() {
        changeDateLabel.setText(language.getResourceBundle().getString("changeDate"));
        cancelButton.setText(language.getResourceBundle().getString("cancel"));
        confirmButton.setText(language.getResourceBundle().getString("confirm"));
    }
}
