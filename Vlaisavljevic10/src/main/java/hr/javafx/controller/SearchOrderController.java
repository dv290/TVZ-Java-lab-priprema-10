package hr.javafx.controller;

import hr.javafx.data.Data;
import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.model.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.stream.Collectors;

public class SearchOrderController {

    @FXML
    private TextField orderIdField;

    @FXML
    private TextField restaurantNameField;

    @FXML
    private TextField mealsInOrderField;

    @FXML
    private TextField delivererField;

    @FXML
    private TextField deliveryDateTimeField;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, String> idColumn;

    @FXML
    private TableColumn<Order, String> restaurantNameColumn;

    @FXML
    private TableColumn<Order, String> mealsColumn;

    @FXML
    private TableColumn<Order, String> delivererColumn;

    @FXML
    private TableColumn<Order, String> dateTimeColumn;


    private final Data data = Data.getInstance();
    public void initialize() {

        ObservableList<Order> ordersList = FXCollections.observableArrayList(data.getOrders());
        orderTable.setItems(ordersList);

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));



        restaurantNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRestaurant().getName()));

        mealsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMeals()
                        .stream()
                        .map(Meal::getName)
                        .collect(Collectors.joining(", "))
                )
        );

        delivererColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeliverer().getFirstName()+" "
        + cellData.getValue().getDeliverer().getLastName()));

        dateTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeliveryDateAndTime().toString()));



    }

    @SuppressWarnings("unused")
    @FXML
    private void handleSearchButtonAction() {
        String orderIdQuery = orderIdField.getText().toLowerCase();
        String restaurantNameQuery = restaurantNameField.getText().toLowerCase();
        String mealsInOrderQuery = mealsInOrderField.getText().toLowerCase();
        String delivererQuery = delivererField.getText().toLowerCase();
        String deliveryDateTimeQuery = deliveryDateTimeField.getText().toLowerCase();

        ObservableList<Order> filteredOrders = FXCollections.observableArrayList();

        for (Order order : data.getOrders()) {
            boolean matches = orderIdQuery.isEmpty() || order.getId().toString().toLowerCase().contains(orderIdQuery);

            if (!restaurantNameQuery.isEmpty() && !order.getRestaurant().getName().toLowerCase().contains(restaurantNameQuery)) {
                matches = false;
            }
            if (!mealsInOrderQuery.isEmpty() && !order.getMeals().toString().toLowerCase().contains(mealsInOrderQuery)) {
                matches = false;
            }
            if (!delivererQuery.isEmpty() && !order.getDeliverer().getFirstName().toLowerCase().contains(delivererQuery) &&
                    !order.getDeliverer().getLastName().toLowerCase().contains(delivererQuery)) {
                matches = false;
            }
            if (!deliveryDateTimeQuery.isEmpty() && !order.getDeliveryDateAndTime().toString().toLowerCase().contains(deliveryDateTimeQuery)) {
                matches = false;
            }

            if (matches) {
                filteredOrders.add(order);
            }
        }

        orderTable.setItems(filteredOrders);
    }
}
