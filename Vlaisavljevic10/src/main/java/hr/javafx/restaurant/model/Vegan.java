package hr.javafx.restaurant.model;

public sealed interface Vegan permits VeganMeal {
    boolean isDairyFree();
    String getVeganIngredients();
}
