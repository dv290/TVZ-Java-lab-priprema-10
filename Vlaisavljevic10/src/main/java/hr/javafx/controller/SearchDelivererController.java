package hr.javafx.controller;

import hr.javafx.data.Data;
import hr.javafx.restaurant.model.Deliverer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;
import java.util.Set;

public class SearchDelivererController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TableView<Deliverer> delivererTable;

    @FXML
    private TableColumn<Deliverer, String> idColumn;

    @FXML
    private TableColumn<Deliverer, String> firstNameColumn;

    @FXML
    private TableColumn<Deliverer, String> lastNameColumn;

    @FXML
    private TableColumn<Deliverer, String> contractColumn;

    @FXML
    private TableColumn<Deliverer, String> bonusColumn;


    private final Data data = Data.getInstance();
    public void initialize() {

        ObservableList<Deliverer> deliverersList = FXCollections.observableArrayList(Optional.ofNullable(data.getDeliverers()).orElse(Set.of()));
        delivererTable.setItems(deliverersList);

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        contractColumn.setCellValueFactory(cellData -> new SimpleStringProperty("("+cellData.getValue().getContract().getId().toString() + ")    " + cellData.getValue().getContract().getContractType()));
        bonusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBonus().bonus().toString()));
    }

    @SuppressWarnings("unused")
    @FXML
    private void handleSearchButtonAction() {
        String firstNameSearchQuery = firstNameField.getText().toLowerCase();
        String lastNameSearchQuery = lastNameField.getText().toLowerCase();
        ObservableList<Deliverer> filteredDeliverers = FXCollections.observableArrayList();

        if (firstNameSearchQuery.isEmpty() && lastNameSearchQuery.isEmpty()) {
            filteredDeliverers.addAll(data.getDeliverers());
        }
        else {
            if (lastNameSearchQuery.isEmpty()) {
                for (Deliverer deliverer : data.getDeliverers()) {
                    if(deliverer.getFirstName().toLowerCase().contains(firstNameSearchQuery)) {
                        filteredDeliverers.add(deliverer);
                    }
                }
            }

            if (firstNameSearchQuery.isEmpty()) {
                for (Deliverer deliverer : data.getDeliverers()) {
                    if(deliverer.getLastName().toLowerCase().contains(lastNameSearchQuery)) {
                        filteredDeliverers.add(deliverer);
                    }
                }
            }
        }
        delivererTable.setItems(filteredDeliverers);
    }

}
