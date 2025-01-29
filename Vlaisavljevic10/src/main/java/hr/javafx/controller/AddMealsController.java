package hr.javafx.controller;

import hr.javafx.data.Data;
import hr.javafx.data.MealDatabase;
import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Meal;
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
import java.util.stream.Collectors;

import static hr.javafx.main.Main.log;

public class AddMealsController {

    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private TextField priceTextField;

    private final Data data = Data.getInstance();
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
        String name = nameTextField.getText();
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        Category selectedCategory = categoryComboBox.getValue();

        String priceText = priceTextField.getText();
        BigDecimal price = new BigDecimal(priceText);

        Meal meal = new Meal(name, selectedCategory, data.getIngredients(), price);

        try {
            data.getMeals().add(meal);
            //writeMealToFile(meal);
            MealDatabase.insertNewMealToDatabase(meal);
            showSuccessAlert();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("Greska u handleAddButtonAction metodi u AddMealsController ");
        }
    }

    private void writeMealToFile(Meal meal) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dat/meals.txt", true))) {
            writer.write(meal.getId()+"\n");
            writer.write(meal.getName() + "\n");
            writer.write(meal.getCategory().getId() + "\n");
            writer.write(meal.getIngredient().stream()
                    .map(ingredient -> ingredient.getId().toString())
                    .collect(Collectors.joining(",")) + "\n");
            writer.write(meal.getPrice() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to ingredients.txt: " + e.getMessage());
        }
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Meal added successfully!");
        alert.showAndWait();
    }
}
