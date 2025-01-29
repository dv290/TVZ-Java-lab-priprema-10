package hr.javafx.controller;

import hr.javafx.data.ContractDatabase;
import hr.javafx.data.Data;
import hr.javafx.restaurant.enums.ContractType;
import hr.javafx.restaurant.model.Contract;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static hr.javafx.main.Main.log;

public class AddContractsController {

    @FXML
    private TextField salaryTextField;

    @FXML
    private ComboBox<ContractType> contractTypeComboBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    private final Data data = Data.getInstance();
    @FXML
    public void initialize() {
        ObservableList<ContractType> contractTypes = FXCollections.observableArrayList(ContractType.values());
        contractTypeComboBox.setItems(contractTypes);

        contractTypeComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(ContractType contractType) {
                return contractType != null ? contractType.getType() : "";
            }

            @Override
            public ContractType fromString(String s) {
                return null;
            }
        });
    }


    @FXML
    private void handleAddButtonAction() {
        String salaryText = salaryTextField.getText();
        BigDecimal salary = new BigDecimal(salaryText);

        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate == null || endDate == null) {
            System.out.println("Please select both start and end dates.");
            return;
        }

        ContractType selectedContract = contractTypeComboBox.getValue();

        Contract contract = new Contract(salary, startDate, endDate, selectedContract);

        try {
            data.getContracts().add(contract);
            //writeContractToFile(contract);
            ContractDatabase.insertNewContractToDatabase(contract);
            showSuccessAlert();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("Greška u add metodi u klasi AddContractsController ");
        }
    }

    private void writeContractToFile(Contract contract) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dat/contracts.txt", true))) {
            writer.write(contract.getId()+"\n");
            writer.write(contract.getSalary() + "\n");
            writer.write(contract.getStartDate().format(FORMATTER) + "\n");
            writer.write(contract.getEndDate().format(FORMATTER) + "\n");
            writer.write(contract.getContractType().toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to contracts.txt: " + e.getMessage());
        }
    }


    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Contract added successfully!");
        alert.showAndWait();
    }
}
