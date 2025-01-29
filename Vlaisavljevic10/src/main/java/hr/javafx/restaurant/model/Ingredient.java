package hr.javafx.restaurant.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Ingredient extends Entity implements Serializable {
    private String name;
    private Category category;
    private BigDecimal kcal;
    private String preparationMethod;
    private static Long idCounter=1L;

    public Ingredient(String name, Category category, BigDecimal kcal, String preparationMethod) {
        super(idCounter);
        this.name = name;
        this.category = category;
        this.kcal = kcal;
        this.preparationMethod = preparationMethod;
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

    public BigDecimal getKcal() {
        return kcal;
    }

    public void setKcal(BigDecimal kcal) {
        this.kcal = kcal;
    }

    public String getPreparationMethod() {
        return preparationMethod;
    }

    public void setPreparationMethod(String preparationMethod) {
        this.preparationMethod = preparationMethod;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(kcal, that.kcal) && Objects.equals(preparationMethod, that.preparationMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, kcal, preparationMethod);
    }
}
