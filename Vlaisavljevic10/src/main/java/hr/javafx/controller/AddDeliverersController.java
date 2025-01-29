package hr.javafx.controller;

import hr.javafx.data.Data;
import hr.javafx.data.DelivererDatabase;
import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Deliverer;
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

public class AddDeliverersController {
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


        Deliverer deliverer = new Deliverer.DelivererBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setContract(selectedContract)
                .setId((long) data.getDeliverers().size()+1)
                .setBonus(new Bonus(new BigDecimal("100")))
                .build();

        try {
            data.getDeliverers().add(deliverer);
            DelivererDatabase.insertNewDelivererToDatabase(deliverer);
            showSuccessAlert();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("Greška u add metodi u klasi AddDeliverersController ");
        }
    }

    private void writeChefToFile(Deliverer deliverer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dat/deliverers.txt", true))) {
            writer.write(deliverer.getId()+"\n");
            writer.write(deliverer.getFirstName() + "\n");
            writer.write(deliverer.getLastName() + "\n");
            writer.write(deliverer.getContract().getId() +"\n");
            writer.write(deliverer.getBonus().bonus() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to deliverers.txt: " + e.getMessage());
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
