package hr.javafx.controller;

import hr.javafx.data.CategoryDatabase;
import hr.javafx.data.Data;
import hr.javafx.restaurant.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static hr.javafx.main.Main.log;

public class AddCategoryController {
    @FXML
    private TextField categoryNameTextField;

    @FXML
    private TextField categoryDescriptionTextField;


    private final Data data = Data.getInstance();

    @FXML
    private void handleAddButtonAction() {
        String name = categoryNameTextField.getText();
        String description = categoryDescriptionTextField.getText();

        if (name == null || name.isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Category name cannot be empty.");
            return;
        }

        if (description == null || description.isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Category description cannot be empty.");
            return;
        }

        name = name.trim().toLowerCase();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        Category category = new Category.CategoryBuilder()
                .setName(name)
                .setDescription(description.trim())
                .build();

        try {
            data.getCategories().add(category);
            //writeCategoryToFile(category);
            CategoryDatabase.insertNewCategoryToDatabase(category);

            categoryNameTextField.clear();
            categoryDescriptionTextField.clear();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Category added successfully!");
        }
        catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add category: " + e.getMessage());
            log.error(e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void writeCategoryToFile(Category category) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dat/categories.txt", true))) {
            writer.write(category.getId().toString());
            writer.newLine();
            writer.write(category.getName());
            writer.newLine();
            writer.write(category.getDescription());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to categories.txt: " + e.getMessage());
        }
    }
}
