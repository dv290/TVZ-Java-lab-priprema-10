package hr.javafx.controller;

import hr.javafx.data.Data;
import hr.javafx.restaurant.model.Chef;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;
import java.util.Set;


public class SearchChefController {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TableView<Chef> chefsTableView;

    @FXML
    private TableColumn<Chef, String> idColumn;

    @FXML
    private TableColumn<Chef, String> firstNameColumn;

    @FXML
    private TableColumn<Chef, String> lastNameColumn;

    @FXML
    private TableColumn<Chef, String> contractColumn;

    @FXML
    private TableColumn<Chef, String> bonusColumn;


    private final Data data = Data.getInstance();
    public void initialize() {

        ObservableList<Chef> chefsList = FXCollections.observableArrayList(Optional.ofNullable(data.getChefs()).orElse(Set.of()));
        chefsTableView.setItems(chefsList);

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        contractColumn.setCellValueFactory(cellData -> new SimpleStringProperty("("+cellData.getValue().getContract().getId().toString() + ")    " + cellData.getValue().getContract().getContractType()));
        bonusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBonus().bonus().toString()));
    }

    @FXML
    private void handleSearchButtonAction() {
        String firstNameSearchQuery = firstNameTextField.getText().toLowerCase();
        String lastNameSearchQuery = lastNameTextField.getText().toLowerCase();

        ObservableList<Chef> filteredChefs = getFilteredChefs(firstNameSearchQuery, lastNameSearchQuery);
        chefsTableView.setItems(filteredChefs);
    }

    private ObservableList<Chef> getFilteredChefs(String firstNameQuery, String lastNameQuery) {
        if (firstNameQuery.isEmpty() && lastNameQuery.isEmpty()) {
            return FXCollections.observableArrayList(data.getChefs());
        }

        if (lastNameQuery.isEmpty()) {
            return filterChefsByFirstName(firstNameQuery);
        }

        if (firstNameQuery.isEmpty()) {
            return filterChefsByLastName(lastNameQuery);
        }

        return FXCollections.observableArrayList(); // Default return, could handle other cases if needed
    }

    private ObservableList<Chef> filterChefsByFirstName(String firstNameQuery) {
        return FXCollections.observableArrayList(data.getChefs().stream()
                .filter(chef -> chef.getFirstName().toLowerCase().contains(firstNameQuery))
                .toList());
    }

    private ObservableList<Chef> filterChefsByLastName(String lastNameQuery) {
        return FXCollections.observableArrayList(data.getChefs().stream()
                .filter(chef -> chef.getLastName().toLowerCase().contains(lastNameQuery))
                .toList());
    }
}
