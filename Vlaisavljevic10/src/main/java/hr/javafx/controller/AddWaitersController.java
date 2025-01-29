package hr.javafx.controller;

import hr.javafx.data.Data;
import hr.javafx.data.WaiterDatabase;
import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Waiter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import static hr.javafx.main.Main.log;

public class AddWaitersController {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private ComboBox<Contract> contractComboBox;

    private final Data data = Data.getInstance();

    public void initialize() {
        contractComboBox.getItems().setAll(data.getContracts());

        contractComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Contract contract) {
                return contract != null ? contract.getId() + ". " + contract.getContractType().getType() : "";
            }

            @Override
            public Contract fromString(String s) {
                return null;
            }
        });
    }

    @SuppressWarnings("unused")
    @FXML
    private void handleAddButtonAction() {
        String firstName = firstNameTextField.getText().toLowerCase();
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();

        String lastName = lastNameTextField.getText().toLowerCase();
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();

        Contract selectedContract = contractComboBox.getValue();


        Waiter waiter = new Waiter.WaiterBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setContract(selectedContract)
                .setId((long) data.getChefs().size()+1)
                .setBonus(new Bonus(new BigDecimal("100")))
                .build();


        try {
            data.getWaiters().add(waiter);
            //writeWaiterToFile(waiter);
            WaiterDatabase.insertNewWaiterToDatabase(waiter);
            showSuccessAlert();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("Greška u add metodi u klasi AddWaitersController ");
        }
    }

    private void writeWaiterToFile(Waiter waiter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dat/waiters.txt", true))) {
            writer.write(waiter.getId()+"\n");
            writer.write(waiter.getFirstName() + "\n");
            writer.write(waiter.getLastName() + "\n");
            writer.write(waiter.getContract().getId() +"\n");
            writer.write(waiter.getBonus().bonus() + "\n");
        } catch (IOException e) {
            log.error("Error writing to waiters.txt: " + e.getMessage());
        }
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Ingredient added successfully!");
        alert.showAndWait();
    }
}
