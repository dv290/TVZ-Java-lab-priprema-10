package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static hr.javafx.main.Main.log;

/**
 * Pomoćna klasa za unos sastojaka u sustav. Omogućava korisniku unos naziva sastojka, odabir kategorije, unos kalorija
 * i opisa pripreme. Također osigurava da ne postoji duplikat naziva sastojka u sustavu.
 */

public class IngredientsInput {
    private IngredientsInput(){}

    public static void loadIngredients(Set<Ingredient> ingredients, List<Category> categories) {
        String filePath = "dat/ingredients.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ( line != null && !line.trim().isEmpty() ) {
                String name = br.readLine().trim();
                Long ingredientCategoryId = Long.parseLong(br.readLine().trim());
                BigDecimal kcal = new BigDecimal(br.readLine().trim());
                String preparationMethod = br.readLine().trim();

                Category category = null;
                for (Category cat : categories) {
                    if (cat.getId().equals(ingredientCategoryId)) {
                        category = cat;
                        break;
                    }
                }
                if (category == null)
                    throw new IllegalArgumentException("Kategorija s ID-em " + ingredientCategoryId + " nije pronađena.");


                ingredients.add(new Ingredient(name, category, kcal, preparationMethod));
                line = br.readLine();
            }
        } catch (IOException e) {
            log.error("Došlo je do pogreške pri čitanju datoteke ingredients.txt: ", e);
        }
    }
}

/*
    Category category = categories.stream()
            .filter(cat -> cat.getId().equals(categoryId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Kategorija s ID-em " + categoryId + " nije pronađena."));
 */