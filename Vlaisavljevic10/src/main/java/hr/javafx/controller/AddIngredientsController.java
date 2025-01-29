package hr.javafx.controller;

import hr.javafx.data.Data;
import hr.javafx.data.IngredientDatabase;
import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class AddIngredientsController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField kcalTextField;

    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private TextField preparationTextField;

    private final Data data = Data.getInstance();

    @FXML
    public void initialize() {
        ObservableList<Category> categories = FXCollections.observableArrayList(data.getCategories());
        categoryComboBox.setItems(categories);

        categoryComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Category category) {
                return category != null ? category.getName() : "";
            }

            @Override
            public Category fromString(String string) {
                return null;
            }
        });

    }


    @FXML
    private void handleAddButtonAction() {
        String name = nameTextField.getText().toLowerCase();
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        String kcalText = kcalTextField.getText();
        BigDecimal calories = new BigDecimal(kcalText);

        Category selectedCategory = categoryComboBox.getValue();

        String prepMethod = preparationTextField.getText();


        Ingredient ingredient = new Ingredient(name,selectedCategory,calories,prepMethod);

        try {
            data.getIngredients().add(ingredient);
            //writeIngredientToFile(ingredient);
            IngredientDatabase.insertNewIngredientToDatabase(ingredient);

            nameTextField.clear();
            kcalTextField.clear();
            preparationTextField.clear();
            categoryComboBox.getSelectionModel().clearSelection();

            showSuccessAlert();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("Try blok u handleAddButtonAction se nije izvrsio (Ingredients Database / AddIngredientsController)!");
        }
    }

    private void writeIngredientToFile(Ingredient ingredient) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dat/ingredients.txt", true))) {
            writer.write(ingredient.getId()+"\n");
            writer.write(ingredient.getName() + "\n");
            writer.write(ingredient.getCategory().getId() + "\n");
            writer.write(ingredient.getKcal() +"\n");
            writer.write(ingredient.getPreparationMethod() + "\n");
        } catch (IOException e) {
            log.error(e.getMessage());
            System.out.println("Error writing to ingredients.txt: " + e.getMessage());
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
