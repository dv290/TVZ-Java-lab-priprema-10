package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.javafx.restaurant.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static hr.javafx.main.Main.log;

/**
 * Pomoćna klasa za unos podataka vezanih za restorane u sustav.
 * Omogućuje korisnicima unos imena restorana, adrese te selekciju jela, kuhara, konobara i dostavljača za svaki restoran.
 */

public class RestaurantsInput {
    private RestaurantsInput(){}

    public static void loadRestaurants(RestaurantLabourExchangeOffice<Restaurant> restaurants, Set<Address> addresses, Set<Meal> meals, Set<Chef> chefs, Set<Waiter> waiters, Set<Deliverer> deliverers) {
        String filePath = "dat/restaurants.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            while (line != null && !line.trim().isEmpty()) {
                String name = br.readLine().trim();

                Long restaurantAddressId = Long.parseLong(br.readLine().trim());
                Address address = null;
                for (Address adr : addresses) {
                    if (adr.getId().equals(restaurantAddressId)) {
                        address = adr;
                        break;
                    }
                }

                String mealsString = br.readLine().trim();
                String[] mealIds = mealsString.split(",");
                Set<Meal> restaurantMeals = new HashSet<>();
                for (String id : mealIds) {
                    Long mealIdLong = Long.parseLong(id.trim());
                    for (Meal meal : meals) {
                        if (meal.getId().equals(mealIdLong)) {
                            restaurantMeals.add(meal);
                            break;
                        }
                    }
                }

                String chefsString = br.readLine().trim();
                String[] chefIds = chefsString.split(",");
                Set<Chef> restaurantChefs = new HashSet<>();
                for (String id : chefIds) {
                    Long chefIdLong = Long.parseLong(id.trim());
                    for (Chef chef : chefs) {
                        if (chef.getId().equals(chefIdLong)) {
                            restaurantChefs.add(chef);
                            break;
                        }
                    }
                }

                String waitersString = br.readLine().trim();
                String[] waiterIds = waitersString.split(",");
                Set<Waiter> restaurantWaiters = new HashSet<>();
                for (String id : waiterIds) {
                    Long waiterIdLong = Long.parseLong(id.trim());
                    for (Waiter waiter : waiters) {
                        if (waiter.getId().equals(waiterIdLong)) {
                            restaurantWaiters.add(waiter);
                            break;
                        }
                    }
                }

                String deliverersString = br.readLine().trim();
                String[] delivererIds = deliverersString.split(",");
                Set<Deliverer> restaurantDeliverers = new HashSet<>();
                for (String id : delivererIds) {
                    Long delivererIdLong = Long.parseLong(id.trim());
                    for (Deliverer deliverer : deliverers) {
                        if (deliverer.getId().equals(delivererIdLong)) {
                            restaurantDeliverers.add(deliverer);
                            break;
                        }
                    }
                }

                Restaurant restaurant = new Restaurant(name, address, restaurantMeals, chefs, waiters, deliverers);
                restaurants.getRestaurants().add(restaurant);

                line = br.readLine();
            }
        } catch (IOException e) {
            log.error("Došlo je do pogreške pri čitanju datoteke restorana: ", e);
        }
    }
}