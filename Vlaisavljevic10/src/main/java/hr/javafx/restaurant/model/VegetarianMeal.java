package hr.javafx.restaurant.model;

import java.math.BigDecimal;
import java.util.Set;

public non-sealed class VegetarianMeal extends Meal implements Vegetarian {

    public VegetarianMeal(String name, Category category, Set<Ingredient> ingredient , BigDecimal price) {
        super(name, category, ingredient, price);
    }

    @Override
    public boolean isPlantBased() {
        return true;
    }

    @Override
    public String getVegetarianIngredients() {
        return "Popis vegeterijanskih sastojaka: ";
    }
}
