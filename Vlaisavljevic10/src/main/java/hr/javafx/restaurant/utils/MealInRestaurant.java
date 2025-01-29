package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MealInRestaurant {
    private MealInRestaurant(){}

    public static void mapMealInRestaurant (RestaurantLabourExchangeOffice<Restaurant> restaurantsOffice, Map<Meal, List<Restaurant>> mealInRestaurantMap){
        mapMealInRestaurantMethod(restaurantsOffice, mealInRestaurantMap);
    }

    public static void displayRestaurantsForMeal(Map<Meal, List<Restaurant>> mealInRestaurantMap, Scanner scanner) {
        displayRestaurantsForMealMethod(mealInRestaurantMap, scanner);
    }


    private static void mapMealInRestaurantMethod(RestaurantLabourExchangeOffice<Restaurant> restaurantsOffice, Map<Meal, List<Restaurant>> mealInRestaurantMap) {
        for(Restaurant restaurant : restaurantsOffice.getRestaurants()){
            for(Meal meal : restaurant.getMeals()){
                if (!mealInRestaurantMap.containsKey(meal)) {
                    mealInRestaurantMap.put(meal, new ArrayList<>());
                }

                if (restaurant.getMeals().contains(meal)) {
                    mealInRestaurantMap.get(meal).add(restaurant);
                }
            }
        }
    }


    private static void displayRestaurantsForMealMethod(Map<Meal, List<Restaurant>> mealInRestaurantMap, Scanner scanner) {
        System.out.println("\nOdaberite jedno od dostupnih jela:");
        for (Meal meal : mealInRestaurantMap.keySet()) {
            System.out.println(meal.getName());
        }

        System.out.print("Unesite ime jela: ");
        String odabranoJelo = scanner.nextLine();

        Meal odabraniMeal = null;
        for (Meal meal : mealInRestaurantMap.keySet()) {
            if (meal.getName().equalsIgnoreCase(odabranoJelo)) {
                odabraniMeal = meal;
                break;
            }
        }

        if (odabraniMeal != null) {
            List<Restaurant> restorani = mealInRestaurantMap.get(odabraniMeal);
            System.out.println("Restorani koji nude jelo '" + odabranoJelo + "':");
            for (Restaurant restaurant : restorani) {
                System.out.println(restaurant.getName());
            }
        } else {
            System.out.println("Jelo s imenom '" + odabranoJelo + "' ne postoji.");
        }
    }
}
