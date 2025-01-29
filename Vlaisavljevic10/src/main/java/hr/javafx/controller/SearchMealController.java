package hr.javafx.controller;

import hr.javafx.data.Data;

import hr.javafx.restaurant.model.Ingredient;
import hr.javafx.restaurant.model.Meal;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Set;
import java.util.stream.Collectors;

public class SearchMealController {

    @FXML
    private TextField mealNameField;

    @FXML
    private TextField mealCategoryField;

    @FXML
    private TableView<Meal> mealTable;

    @FXML
    private TableColumn<Meal, Long> idColumn;

    @FXML
    private TableColumn<Meal, String> nameColumn;

    @FXML
    private TableColumn<Meal, String> categoryColumn;

    @FXML
    private TableColumn<Meal, String> priceColumn;

    @FXML
    private TableColumn<Meal, String> prepMethodColumn;



    private final Data data = Data.getInstance();
    public void initialize() {

        ObservableList<Meal> mealsList = FXCollections.observableArrayList(data.getMeals());
        mealTable.setItems(mealsList);

        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().getName()));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice().toString()));

        prepMethodColumn.setCellValueFactory(cellData -> {
            Set<Ingredient> ingredients = cellData.getValue().getIngredient();

            String ingredientNames = ingredients.stream()
                    .map(Ingredient::getName)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(ingredientNames);
        });

    }

    @SuppressWarnings("unused")
    @FXML
    private void handleSearchButtonAction() {
        String nameSearchQuery = mealNameField.getText().toLowerCase();
        String categorySearchQuery = mealCategoryField.getText().toLowerCase();
        ObservableList<Meal> filteredMeals = FXCollections.observableArrayList();


        if (nameSearchQuery.isEmpty() && categorySearchQuery.isEmpty()) {
            filteredMeals.addAll(data.getMeals());
        }
        else {
            if (categorySearchQuery.isEmpty()) {
                for (Meal meal : data.getMeals()) {
                    if(meal.getName().toLowerCase().contains(nameSearchQuery)) {
                        filteredMeals.add(meal);
                    }
                }
            }

            if (nameSearchQuery.isEmpty()) {
                for (Meal meal : data.getMeals()) {
                    if(meal.getCategory().getName().toLowerCase().contains(categorySearchQuery)) {
                        filteredMeals.add(meal);
                    }
                }
            }
        }


        mealTable.setItems(filteredMeals);
    }
}
