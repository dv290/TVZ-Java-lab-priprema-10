package hr.javafx.restaurant.sort;

import hr.javafx.restaurant.model.Ingredient;

import java.util.Comparator;

public class IngredientsSortAlphabetically implements Comparator<Ingredient> {
    @Override
    public int compare(Ingredient ing1, Ingredient ing2) {
        return ing1.getName().compareTo(ing2.getName());
    }
}
