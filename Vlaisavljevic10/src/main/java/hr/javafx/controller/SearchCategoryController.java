package hr.javafx.controller;

import hr.javafx.data.Data;
import hr.javafx.restaurant.model.Category;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class SearchCategoryController {

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Category> categoryTableView;

    @FXML
    private TableColumn<Category, Long> categoryIdColumn;

    @FXML
    private TableColumn<Category, String> categoryNameColumn;

    @FXML
    private TableColumn<Category, String> categoryDescriptionColumn;


    private final Data data = Data.getInstance();
    public void initialize() {

        ObservableList<Category> categoryList = FXCollections.observableArrayList(data.getCategories());
        categoryTableView.setItems(categoryList);

        categoryIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        categoryNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        categoryDescriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
    }


    @FXML
    private void handleSearchButtonAction() {
        String searchQuery = searchTextField.getText().toLowerCase();
        ObservableList<Category> filteredCategories = FXCollections.observableArrayList();

        for (Category category : data.getCategories()) {
            if (category.getName().toLowerCase().contains(searchQuery)) {
                filteredCategories.add(category);
            }
        }

        categoryTableView.setItems(filteredCategories);
    }
}
