package hr.javafx.controller;

import hr.javafx.data.AddressDatabase;
import hr.javafx.data.Data;
import hr.javafx.data.RestaurantDatabase;
import hr.javafx.restaurant.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

import static hr.javafx.main.Main.log;

public class AddRestaurantsController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField houseNumberTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField postalCodeTextField;


    private final Data data = Data.getInstance();

    public void initialize() {
    }

    @FXML
    private void handleAddButtonAction() {
        String name = nameTextField.getText();

        Address address = new Address.AddressBuilder()
                .setCity(cityTextField.getText())
                .setHouseNumber(houseNumberTextField.getText())
                .setPostalCode(postalCodeTextField.getText())
                .setStreet(streetTextField.getText())
                .build();


        try {
            data.getAddresses().add(address);
            //writeAddressToFile(address);
            AddressDatabase.insertNewAddressToDatabase(address);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("Greška u add metodi u klasi AddRestaurantsController kod dodavanja nove Adrese! ");
        }

        Restaurant restaurant = new Restaurant(name,address, data.getMeals(),data.getChefs(),data.getWaiters(),data.getDeliverers());

        try {
            data.getRestaurants().add(restaurant);
            RestaurantDatabase.insertNewRestaurantToDatabase(restaurant);
            showSuccessAlert();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("Greška u add metodi u klasi AddRestaurantsController");
        }
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Restaurant added successfully!");
        alert.showAndWait();
    }

    private void writeAddressToFile(Address address) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dat/addresses.txt", true))) {
            writer.write(address.getId()+"\n");
            writer.write(address.getStreet() + "\n");
            writer.write(address.getHouseNumber() + "\n");
            writer.write(address.getCity() +"\n");
            writer.write(address.getPostalCode() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to addresses.txt: " + e.getMessage());
        }
    }

    private void writeRestaurantToFile(Restaurant restaurant) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dat/restaurants.txt", true))) {
            writer.write(restaurant.getId()+"\n");
            writer.write(restaurant.getName() + "\n");
            writer.write(restaurant.getAddress().getId() + "\n");

            writer.write(restaurant.getMeals().stream()
                    .map(meal -> String.valueOf(meal.getId()))
                    .collect(Collectors.joining(",")) + "\n");

            writer.write(restaurant.getChefs().stream()
                    .map(chef -> String.valueOf(chef.getId()))
                    .collect(Collectors.joining(",")) + "\n");

            writer.write(restaurant.getWaiters().stream()
                    .map(waiter -> String.valueOf(waiter.getId()))
                    .collect(Collectors.joining(",")) + "\n");

            writer.write(restaurant.getDeliverers().stream()
                    .map(deliverer -> String.valueOf(deliverer.getId()))
                    .collect(Collectors.joining(",")) + "\n");

        } catch (IOException e) {
            System.out.println("Error writing to ingredients.txt: " + e.getMessage());
        }
    }
}
