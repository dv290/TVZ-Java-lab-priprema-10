package hr.javafx.controller;

import hr.javafx.data.ChefDatabase;
import hr.javafx.data.Data;
import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Chef;
import hr.javafx.restaurant.model.Contract;
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

public class AddChefsController {
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


        Chef chef = new Chef.ChefBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setContract(contractComboBox.getValue())
                .setId((long) data.getChefs().size()+1)
                .setBonus(new Bonus(new BigDecimal("100")))
                .build();


        try {
            data.getChefs().add(chef);
            //writeChefToFile(chef);
            ChefDatabase.insertNewChefToDatabase(chef);
            showSuccessAlert();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("Greška u add metodi u klasi AddChefsController ");
        }
    }

    private void writeChefToFile(Chef chef) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dat/chefs.txt", true))) {
            writer.write(chef.getId()+"\n");
            writer.write(chef.getFirstName() + "\n");
            writer.write(chef.getLastName() + "\n");
            writer.write(chef.getContract().getId() +"\n");
            writer.write(chef.getBonus().bonus() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to chefs.txt: " + e.getMessage());
        }
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Chef added successfully!");
        alert.showAndWait();
    }
}
