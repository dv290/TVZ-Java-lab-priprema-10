package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.javafx.restaurant.model.Ingredient;
import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.model.Person;
import hr.javafx.restaurant.model.Restaurant;

import java.util.*;

public class Comparators {

    public static void findHighestPaidEmployeeInEachRestaurant(RestaurantLabourExchangeOffice<Restaurant> restaurantsOffice) {
        findHighestPaidEmployeeInEachRestaurantMethod(restaurantsOffice);
    }

    public static void sortEmployeesByContractDuration(RestaurantLabourExchangeOffice<Restaurant> restaurantsOffice) {
        sortEmployeesByContractDurationMethod(restaurantsOffice);
    }

    public static void sortMostPopularMeal(Map<Meal, List<Restaurant>> mealInRestaurantMap) {
        sortMostPopularMealMethod(mealInRestaurantMap);
    }

    public static void sortIngredientsAlphabetically(Set<Ingredient> ingredients) {
        sortIngredientsAlphabeticallyMethod(ingredients);
    }



    private static void findHighestPaidEmployeeInEachRestaurantMethod(RestaurantLabourExchangeOffice<Restaurant> restaurantsOffice) {
        for (Restaurant restaurant : restaurantsOffice.getRestaurants()) {

            List<Person> employees = new ArrayList<>();
            employees.addAll(restaurant.getChefs());
            employees.addAll(restaurant.getWaiters());
            employees.addAll(restaurant.getDeliverers());

            //employees.sort(new EmployeeSalaryComparator());
            employees.sort( (p1, p2) -> p2.getContract().getSalary().compareTo(p1.getContract().getSalary()) );

            if (!employees.isEmpty()) {
                System.out.println("Najveću plaća u restoranu " + restaurant.getName() + " ima:");
                System.out.println(employees.getFirst().getFirstName()+" "+employees.getFirst().getLastName());
            } else {
                System.out.println("Restoran " + restaurant.getName() + " nema zaposlenike.");
            }
        }
    }


    private static void sortEmployeesByContractDurationMethod(RestaurantLabourExchangeOffice<Restaurant> restaurantsOffice) {
        for(Restaurant restaurant : restaurantsOffice.getRestaurants()) {
            List<Person> employees = new ArrayList<>();
            employees.addAll(restaurant.getChefs());
            employees.addAll(restaurant.getWaiters());
            employees.addAll(restaurant.getDeliverers());

            /*
                employees.sort( (p1, p2) -> Long.compare(
                        p1.getContract().getEndDate().toEpochDay() - p1.getContract().getStartDate().toEpochDay(),
                        p2.getContract().getEndDate().toEpochDay() - p2.getContract().getStartDate().toEpochDay() )  );
             */
            employees.sort(Comparator.comparingLong(p -> p.getContract().getEndDate().toEpochDay() - p.getContract().getStartDate().toEpochDay()));

            System.out.println("Zaposlenici u restoranu "+ restaurant.getName()+" sortirani prema trajanju ugovora: ");
            for(Person employee : employees){
                long duration = employee.getContract().getEndDate().toEpochDay() - employee.getContract().getStartDate().toEpochDay();
                System.out.println(employee.getFirstName()+" "+employee.getLastName());
                System.out.println("Trajanje ugovora: "+duration+" dana\n");
            }
        }
    }

    private static void sortMostPopularMealMethod(Map<Meal, List<Restaurant>> mealInRestaurantMap) {
        List<Meal> meals = new ArrayList<>(mealInRestaurantMap.keySet());
        meals.sort((meal1, meal2) -> Integer.compare(mealInRestaurantMap.get(meal2).size(), mealInRestaurantMap.get(meal1).size()));

        System.out.println("Jela sortirana po broju restorana (od najviše prema najmanje):");
        for (Meal meal : meals) {
            System.out.println(meal.getName() + " - broj restorana: " + mealInRestaurantMap.get(meal).size());
        }
    }

    private static void sortIngredientsAlphabeticallyMethod(Set<Ingredient> ingredients) {
        List<Ingredient> sortedIngredients = new ArrayList<>(ingredients);
        //sortedIngredients.sort((ing1, ing2) -> ing1.getName().compareTo(ing2.getName()));
        sortedIngredients.sort(Comparator.comparing(Ingredient::getName));
        System.out.println("Namirnice sortirane po abecedi: ");
        for(Ingredient ingredient : sortedIngredients) {
            System.out.println(ingredient.getName());
        }
        System.out.println("\n");
    }

}
