package hr.javafx.restaurant.model;

import java.math.BigDecimal;
import java.util.Set;

public non-sealed class Grill extends Meal implements Meat {

    public Grill(String name, Category category, Set<Ingredient> ingredient , BigDecimal price) {
        super(name, category, ingredient, price);
    }

    @Override
    public boolean isGrilled() {
        return true;
    }

    @Override
    public String getMeatType(){
        return "Tip mesa: ";
    }
}
