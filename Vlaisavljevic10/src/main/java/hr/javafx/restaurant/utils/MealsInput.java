package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;
import hr.javafx.restaurant.model.Meal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static hr.javafx.main.Main.log;

/**
 * Pomoćna klasa za unos jela u sustav. Omogućava korisniku unos naziva jela, odabir kategorije, cijene jela,
 * te dodavanje sastojaka. Također osigurava da ne postoji duplikat naziva jela u sustavu.
 */
public class MealsInput {
    private MealsInput(){}

    public static void loadMeals(Set<Meal> meals, List<Category> categories, Set<Ingredient> ingredients) {
        String filePath = "dat/meals.txt";

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while( line != null && !line.trim().isEmpty() ) {
                String name = br.readLine();

                Long mealCategoryId = Long.parseLong(br.readLine().trim());
                Category category = null;
                for (Category cat : categories) {
                    if (cat.getId().equals(mealCategoryId)) {
                        category = cat;
                        break;
                    }
                }

                String ingredientIdsLine = br.readLine().trim();
                String[] ingredientIdsArray = ingredientIdsLine.split(",");
                List<Long> ingredientIds = new ArrayList<>();
                for (String id : ingredientIdsArray) {
                    ingredientIds.add(Long.parseLong(id));
                }

                Set<Ingredient> mealsIngredients = new HashSet<>();
                for(Ingredient ingredient : ingredients) {
                    for(Long id : ingredientIds) {
                        if(id.equals(ingredient.getId())) {
                            mealsIngredients.add(ingredient);
                        }
                    }
                }

                BigDecimal price = new BigDecimal(br.readLine().trim());

                meals.add(new Meal(name, category, mealsIngredients, price));

                line = br.readLine();
            }
        } catch (IOException e) {
            log.error("Došlo je do pogreške pri čitanju datoteke meals.txt: ",e);
        }
    }
}
