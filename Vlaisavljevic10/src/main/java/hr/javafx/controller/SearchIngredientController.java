package hr.javafx.controller;

import hr.javafx.data.Data;
import hr.javafx.restaurant.model.Ingredient;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class SearchIngredientController {

    @FXML
    private TextField ingredientNameField;

    @FXML
    private TextField ingredientCategoryField;

    @FXML
    private TableView<Ingredient> ingredientTable;

    @FXML
    private TableColumn<Ingredient, Long> idColumn;

    @FXML
    private TableColumn<Ingredient, String> nameColumn;

    @FXML
    private TableColumn<Ingredient, String> categoryColumn;

    @FXML
    private TableColumn<Ingredient, String> kcalColumn;

    @FXML
    private TableColumn<Ingredient, String> prepMethodColumn;

    private final Data data = Data.getInstance();
    public void initialize() {

        ObservableList<Ingredient> ingredientsList = FXCollections.observableArrayList(data.getIngredients());
        ingredientTable.setItems(ingredientsList);

        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().getName()));
        kcalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKcal().toString()));
        prepMethodColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPreparationMethod()));
    }

    @SuppressWarnings("unused")
    @FXML
    private void handleSearchButtonAction() {
        String nameSearchQuery = ingredientNameField.getText().toLowerCase();
        String categorySearchQuery = ingredientCategoryField.getText().toLowerCase();
        ObservableList<Ingredient> filteredIngredients = FXCollections.observableArrayList();

        if (nameSearchQuery.isEmpty() && categorySearchQuery.isEmpty()) {
            filteredIngredients.addAll(data.getIngredients());
        }
        else {
            if (categorySearchQuery.isEmpty()) {
                for (Ingredient ingredient : data.getIngredients()) {
                    if(ingredient.getName().toLowerCase().contains(nameSearchQuery)) {
                        filteredIngredients.add(ingredient);
                    }
                }
            }

            if (nameSearchQuery.isEmpty()) {
                for (Ingredient ingredient : data.getIngredients()) {
                    if(ingredient.getCategory().getName().toLowerCase().contains(categorySearchQuery)) {
                        filteredIngredients.add(ingredient);
                    }
                }
            }
        }

        ingredientTable.setItems(filteredIngredients);
    }
}
