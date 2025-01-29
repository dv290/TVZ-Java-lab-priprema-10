package hr.javafx.controller;

import hr.javafx.data.Data;
import hr.javafx.restaurant.model.Waiter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;
import java.util.Set;

public class SearchWaiterController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TableView<Waiter> waiterTable;

    @FXML
    private TableColumn<Waiter, String> idColumn;

    @FXML
    private TableColumn<Waiter, String> firstNameColumn;

    @FXML
    private TableColumn<Waiter, String> lastNameColumn;

    @FXML
    private TableColumn<Waiter, String> contractColumn;

    @FXML
    private TableColumn<Waiter, String> bonusColumn;


    private final Data data = Data.getInstance();
    public void initialize() {

        ObservableList<Waiter> waitersList = FXCollections.observableArrayList(Optional.ofNullable(data.getWaiters()).orElse(Set.of()));
        waiterTable.setItems(waitersList);

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        contractColumn.setCellValueFactory(cellData -> new SimpleStringProperty("("+cellData.getValue().getContract().getId().toString() + ")    " + cellData.getValue().getContract().getContractType()));
        bonusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBonus().bonus().toString()));
    }

    @FXML
    private void handleSearchButtonAction() {
        String firstNameSearchQuery = firstNameField.getText().toLowerCase();
        String lastNameSearchQuery = lastNameField.getText().toLowerCase();
        ObservableList<Waiter> filteredWaiters = FXCollections.observableArrayList();

        if (firstNameSearchQuery.isEmpty() && lastNameSearchQuery.isEmpty()) {
            filteredWaiters.addAll(data.getWaiters());
        }
        else {
            if (lastNameSearchQuery.isEmpty()) {
                for (Waiter waiter : data.getWaiters()) {
                    if(waiter.getFirstName().toLowerCase().contains(firstNameSearchQuery)) {
                        filteredWaiters.add(waiter);
                    }
                }
            }

            if (firstNameSearchQuery.isEmpty()) {
                for (Waiter waiter : data.getWaiters()) {
                    if(waiter.getLastName().toLowerCase().contains(lastNameSearchQuery)) {
                        filteredWaiters.add(waiter);
                    }
                }
            }
        }
        waiterTable.setItems(filteredWaiters);
    }
}
