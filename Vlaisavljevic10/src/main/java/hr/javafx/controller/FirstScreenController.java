package hr.javafx.controller;

import hr.javafx.main.MainApplication;
import hr.javafx.threads.HighestEmployeeSalaryThread;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

import static hr.javafx.main.Main.log;

public class FirstScreenController {

    @FXML
    Label highestPaidEmployeeLabel = new Label();

    @FXML
    public void initialize() {
        HighestEmployeeSalaryThread hest = new HighestEmployeeSalaryThread(highestPaidEmployeeLabel);
        Thread thread = new Thread(hest);
        thread.start();
    }

    public void showFirstScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/hr/javafx/firstScreen.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainApplication.getStage().setScene(scene);
            MainApplication.getStage().show();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void showScreen(String path) {
        MainApplication.showWindow(path);
    }

    public void showTheCategoriesSearch() {
        MainApplication.showWindow("/hr/javafx/theCategoriesSearch.fxml");
    }

    public void showTheCategoriesAdd() {
        MainApplication.showWindow("/hr/javafx/theCategoriesAdd.fxml");
    }


    public void showChefsSearch() {
        MainApplication.showWindow("/hr/javafx/chefsSearch.fxml");
    }

    public void showChefsAdd() {
        MainApplication.showWindow("/hr/javafx/chefsAdd.fxml");
    }


    public void showContractsSearch() {
        MainApplication.showWindow("/hr/javafx/contractsSearch.fxml");
    }

    public void showContractsAdd() {
        MainApplication.showWindow("/hr/javafx/contractsAdd.fxml");
    }


    public void showDeliverersSearch() {
        MainApplication.showWindow("/hr/javafx/deliverersSearch.fxml");
    }

    public void showDelivererAdd() {
        MainApplication.showWindow("/hr/javafx/deliverersAdd.fxml");
    }


    public void showIngredientsSearch() {
        MainApplication.showWindow("/hr/javafx/ingredientsSearch.fxml");
    }

    public void showIngredientsAdd() {
        MainApplication.showWindow("/hr/javafx/ingredientsAdd.fxml");
    }


    public void showMealsSearch() {
        MainApplication.showWindow("/hr/javafx/mealsSearch.fxml");
    }

    public void showMealsAdd() {
        MainApplication.showWindow("/hr/javafx/mealsAdd.fxml");
    }


    public void showOrdersSearch() {
        MainApplication.showWindow("/hr/javafx/ordersSearch.fxml");
    }

    public void showRestaurantsSearch() {
        MainApplication.showWindow("/hr/javafx/restaurantsSearch.fxml");
    }

    public void showRestaurantsAdd() {
        MainApplication.showWindow("/hr/javafx/restaurantsAdd.fxml");
    }


    public void showWaitersSearch() {
        MainApplication.showWindow("/hr/javafx/waitersSearch.fxml");
    }

    public void showWaitersAdd() {
        MainApplication.showWindow("/hr/javafx/waitersAdd.fxml");
    }
}