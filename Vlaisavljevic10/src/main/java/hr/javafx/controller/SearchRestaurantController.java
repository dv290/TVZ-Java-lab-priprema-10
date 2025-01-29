package hr.javafx.controller;

import hr.javafx.data.Data;
import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.model.Restaurant;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.stream.Collectors;

public class SearchRestaurantController {

    @FXML
    private TextField restaurantNameField;

    @FXML
    private TableView<Restaurant> restaurantTable;

    @FXML
    private TableColumn<Restaurant, String> idColumn;

    @FXML
    private TableColumn<Restaurant, String> nameColumn;

    @FXML
    private TableColumn<Restaurant, String> addressColumn;

    @FXML
    private TableColumn<Restaurant, String> mealsColumn;

    @FXML
    private TableColumn<Restaurant, String> chefsColumn;

    @FXML
    private TableColumn<Restaurant, String> waitersColumn;

    @FXML
    private TableColumn<Restaurant, String> deliverersColumn;


    private final Data data = Data.getInstance();
    public void initialize() {

        ObservableList<Restaurant> restaurantsList = FXCollections.observableArrayList(data.getRestaurants());
        restaurantTable.setItems(restaurantsList);

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getAddress().getStreet() + ", " + cellData.getValue().getAddress().getCity()));


        mealsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMeals()
                        .stream()
                        .map(Meal::getName)
                        .collect(Collectors.joining(", "))
                )
        );


        chefsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getChefs()
                        .stream()
                        .map(chef -> chef.getFirstName() + " " + chef.getLastName())
                        .collect(Collectors.joining(", "))
                )
        );

        waitersColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getWaiters()
                        .stream()
                        .map(waiter -> waiter.getFirstName() + " " + waiter.getLastName())
                        .collect(Collectors.joining(", "))
                )
        );

        deliverersColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDeliverers()
                        .stream()
                        .map(deliverer -> deliverer.getFirstName() + " " + deliverer.getLastName())
                        .collect(Collectors.joining(", "))
                )
        );
    }

    @SuppressWarnings("unused")
    @FXML
    private void handleSearchButtonAction() {
        String searchQuery = restaurantNameField.getText().toLowerCase();
        ObservableList<Restaurant> filteredRestaurants = FXCollections.observableArrayList();

        for (Restaurant restaurant : data.getRestaurants()) {
            if(restaurant.getName().toLowerCase().contains(searchQuery)) {
                filteredRestaurants.add(restaurant);
            }
        }
        restaurantTable.setItems(filteredRestaurants);
    }
}
