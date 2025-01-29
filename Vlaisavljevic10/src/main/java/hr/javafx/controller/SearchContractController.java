package hr.javafx.controller;

import hr.javafx.data.Data;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.threads.SortingContractsThread;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;

public class SearchContractController {

    @FXML
    private TextField contractIdField;

    @FXML
    private TableView<Contract> contractTable;

    @FXML
    private TableColumn<Contract, String> idColumn;

    @FXML
    private TableColumn<Contract, String> salaryColumn;

    @FXML
    private TableColumn<Contract, String> startTimeColumn;

    @FXML
    private TableColumn<Contract, String> endTimeColumn;

    @FXML
    private TableColumn<Contract, String> contractTypeColumn;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy.");


    private SortingContractsThread sortingThread;
    private Thread thread;
    private final Data data = Data.getInstance();

    public void initialize() {

        ObservableList<Contract> contractsList = FXCollections.observableArrayList(data.getContracts());
        contractTable.setItems(contractsList);

        idColumn.setCellValueFactory( cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        salaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSalary().toString()));
        startTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartDate().format(FORMATTER)));
        endTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndDate().format(FORMATTER)));
        contractTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContractType().getType()));

        sortingThread = new SortingContractsThread(contractTable);
        thread = new Thread(sortingThread);
        thread.start();
    }

    @FXML
    private void handleSearchButtonAction() {
        if (sortingThread != null) {
            sortingThread.stopThread();
        }

        String searchQuery = contractIdField.getText().toLowerCase();
        ObservableList<Contract> filteredContracts = FXCollections.observableArrayList();

        for(Contract contract : data.getContracts()) {
            if(contract.getId().toString().toLowerCase().contains(searchQuery)) {
                filteredContracts.add(contract);
            }
        }

        contractTable.setItems(filteredContracts);

        if (searchQuery.isEmpty()) {
            sortingThread = new SortingContractsThread(contractTable);
            thread = new Thread(sortingThread);
            thread.start();
        }
    }
}
