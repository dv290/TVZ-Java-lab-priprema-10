package hr.javafx.restaurant.sort;

import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.model.Restaurant;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MostPopularMeal implements Comparator<Meal> {
    private final Map<Meal, List<Restaurant>> mealInRestaurantMap;

    public MostPopularMeal(Map<Meal, List<Restaurant>> mealInRestaurantMap) {
        this.mealInRestaurantMap = mealInRestaurantMap;
    }

    @Override
    public int compare(Meal meal1, Meal meal2) {
        int restaurants1 = mealInRestaurantMap.get(meal1).size();
        int restaurants2 = mealInRestaurantMap.get(meal2).size();
        return Integer.compare(restaurants2, restaurants1);
    }
}
