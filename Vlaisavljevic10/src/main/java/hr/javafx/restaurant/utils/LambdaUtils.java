package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.javafx.restaurant.model.Ingredient;
import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.model.Order;
import hr.javafx.restaurant.model.Restaurant;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Pomoćna klasa koja sadrži statičke metode za obradu podataka restorana i narudžbi koristeći lambda izraze.
 * Metode omogućuju:
 * - Pronalaženje restorana s najviše zaposlenih.
 * - Pronalaženje najčešće naručivanog jela.
 * - Ispis namirnica za svako jelo u narudžbi.
 * - Ispis ukupne cijene svake narudžbe.
 * - Grupiranje i ispis restorana prema gradu.
 */
public class LambdaUtils {
    private LambdaUtils(){}

    //Public facade metode koje pozivaju privatne metode koje obavljaju logiku, koriste se za pozivanje u mainu

    public static void findRestaurantWithMostEmployees(RestaurantLabourExchangeOffice<Restaurant> restaurantsOffice) {
        findRestaurantWithMostEmployeesMethod(restaurantsOffice);
    }

    public static void mostOrderedMeal(List<Order> orders) {
        mostOrderedMealMethod(orders);
    }

    public static void ingredientsInMeal(List<Order> orders) {
        ingredientsInMealMethod(orders);
    }

    public static void totalOrderPrice(List<Order> orders) {
        totalOrderPriceMethod(orders);
    }

    public static void getRestaurantsByCity(RestaurantLabourExchangeOffice<Restaurant> genericRestaurantList) {
        getRestaurantsByCityMethod(genericRestaurantList);
    }



    // Privatne metode koje obavljaju specifične operacije s logikom enkapsuliranom za svaku.

    /**
     * Privatna metoda koja pronalazi i ispisuje restoran s najviše zaposlenih.
     *
     * @param restaurantsOffice Ured restoranske radne razmjene koji sadrži popis restorana.
     */
    private static void findRestaurantWithMostEmployeesMethod(RestaurantLabourExchangeOffice<Restaurant> restaurantsOffice) {
        List<Restaurant> restaurants = restaurantsOffice.getRestaurants();
        Optional<Restaurant> restaurantWithMostEmployees = restaurants.stream().max(
                (res1, res2) -> Integer.compare(
                        res1.getChefs().size()+res1.getWaiters().size()+res1.getDeliverers().size(),
                        res2.getChefs().size()+res2.getWaiters().size()+res2.getDeliverers().size()));

        restaurantWithMostEmployees.ifPresentOrElse(
                restaurant -> System.out.println("Restoran s najviše zaposlenih je: " + restaurant.getName()+"\n" +
                        "Broj zaposlenih: "+restaurant.getChefs().size() + restaurant.getWaiters().size()+restaurant.getDeliverers().size()),

                () -> System.out.println("Nema dostupnih restorana! ")
        );
    }

    /**
     * Privatna metoda koja pronalazi i ispisuje najčešće naručivano jelo iz liste narudžbi.
     *
     * @param orders Lista narudžbi za analizu.
     */
    private static void mostOrderedMealMethod(List<Order> orders) {
        List<Meal> allMeals = orders.stream().flatMap(order -> order.getMeals().stream()).toList();

        Map<String, Long> mealCountMap = allMeals.stream().collect(Collectors.groupingBy(Meal::getName, Collectors.counting()));

        Map.Entry<String, Long> mostOrderedMeal = mealCountMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new RuntimeException("Nema narudžbi!"));

        System.out.println("Najčešće naručivano jelo je: " + mostOrderedMeal.getKey());
        System.out.println("Broj narudžbi: " + mostOrderedMeal.getValue());
    }

    /**
     * Privatna metoda koja ispisuje namirnice za svako jelo u danoj listi narudžbi.
     *
     * @param orders Lista narudžbi za analizu.
     */
    private static void ingredientsInMealMethod(List<Order> orders) {
        orders.forEach(order -> {
            System.out.println("Namirnice za narudžbu #" + order.getId() + ":");

            order.getMeals().stream()
                    .flatMap(meal -> meal.getIngredient().stream())
                    .map(Ingredient::getName)
                    .forEach(System.out::println);
        }
        );
    }

    /**
     * Privatna metoda koja ispisuje ukupnu cijenu svake narudžbe u danoj listi narudžbi.
     *
     * @param orders Lista narudžbi za analizu.
     */
    private static void totalOrderPriceMethod(List<Order> orders) {
        orders.forEach(order -> System.out.println("Ukupna cijena narudžbe '" + order.getId()+"': "+order.getTotalPrice()));
    }

    /**
     * Privatna metoda koja grupira restorane prema gradu iz liste restorana i ispisuje nazive restorana po gradovima.
     *
     * @param genericRestaurantList Ured restoranske radne razmjene koji sadrži popis restorana.
     */
    private static void getRestaurantsByCityMethod(RestaurantLabourExchangeOffice<Restaurant> genericRestaurantList) {
        genericRestaurantList.getRestaurants().stream()
                .collect(Collectors.groupingBy(
                        restaurant -> restaurant.getAddress().getCity(),
                        Collectors.toList() ) )
                .forEach((city, restaurants) -> {
                    System.out.println("Restorani u " + city + ":");
                    restaurants.forEach(restaurant -> System.out.println(restaurant.getName()));
                });
    }
}
