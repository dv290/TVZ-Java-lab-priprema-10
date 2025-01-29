package hr.javafx.restaurant.model;

import java.math.BigDecimal;
import java.util.Set;

public non-sealed class VeganMeal extends Meal implements Vegan{

    public VeganMeal(String name, Category category, Set<Ingredient> ingredient , BigDecimal price) {
        super(name, category,ingredient,price);
    }

    @Override
    public boolean isDairyFree() {
        return true;
    }

    @Override
    public String getVeganIngredients() {
        return "Popis veganskih sastojaka: ";
    }
}
