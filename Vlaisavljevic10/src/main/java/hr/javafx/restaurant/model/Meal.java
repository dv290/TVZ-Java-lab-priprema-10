package hr.javafx.restaurant.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

public class Meal extends Entity implements Serializable {
    private String name;
    private Category category;
    private Set<Ingredient> ingredient;
    private BigDecimal price;
    private static Long idCounter=1L;


    public Meal(String name, Category category, Set<Ingredient> ingredient ,BigDecimal price) {
        super(idCounter);
        this.name = name;
        this.category = category;
        this.ingredient = ingredient;
        this.price = price;
        idCounter++;
    }

    public void setId(Long newId) {
        super.setId(newId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Ingredient> getIngredient() {
        return ingredient;
    }

    public void setIngredient(Set<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalKcal() {
        BigDecimal totalKcal = new BigDecimal("0");
        for(Ingredient ing : ingredient)
            totalKcal = totalKcal.add(ing.getKcal());
        return totalKcal;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(name, meal.name) && Objects.equals(category, meal.category) && Objects.equals(ingredient, meal.ingredient) && Objects.equals(price, meal.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, ingredient, price);
    }
}
