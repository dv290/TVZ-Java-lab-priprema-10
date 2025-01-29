package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.javafx.restaurant.model.Deliverer;
import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.model.Order;
import hr.javafx.restaurant.model.Restaurant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static hr.javafx.main.Main.*;

/**
 * Pomoćna klasa za unos podataka vezanih za narudžbe u sustav.
 * Omogućuje korisnicima unos restorana, dostavljača, te datuma i vremena isporuke za novu narudžbu.
 */

public class OrderInput {
    private OrderInput(){}

    public static void loadOrders(List<Order> orders,
                                  RestaurantLabourExchangeOffice<Restaurant> restaurants,
                                  Set<Meal> meals,
                                  Set<Deliverer> deliverers) {


        try (BufferedReader br = new BufferedReader(new FileReader("dat/orders.txt"))) {
            List<Meal> mealsList = new ArrayList<>(meals);
            List<Deliverer> deliverersList = new ArrayList<>(deliverers);

            String line = br.readLine();
            while ( line != null && !line.trim().isEmpty() ) {

                Long restaurantId = Long.parseLong(br.readLine());
                Optional<Restaurant> optionalOrderRestaurant = restaurants.getRestaurants().stream()
                        .filter(restaurant -> restaurant.getId().equals(restaurantId)).findFirst();

                Restaurant orderRestaurant = optionalOrderRestaurant.orElseThrow();

                /*
                      Restaurant orderRestaurant = null;
                for(Restaurant restaurant : restaurants.getRestaurants()) {
                    if(restaurant.getId().equals(restaurantId)) {
                        orderRestaurant = restaurant;
                    }
                }
                 */

                String mealIdsString = br.readLine();
                String[] mealIds = mealIdsString.split(",");
                List<Meal> orderMeals = new ArrayList<>();
                for(String id : mealIds) {
                    Long mealIdLong = Long.parseLong(id);
                    for(Meal meal : mealsList) {
                        if(meal.getId().equals(mealIdLong)) {
                            orderMeals.add(meal);
                            break;
                        }
                    }
                }

                Long delivererId = Long.parseLong(br.readLine().trim());
                Optional<Deliverer> optionalOrderDeliverer = deliverersList.stream()
                        .filter(deliverer -> deliverer.getId().equals(delivererId))
                        .findFirst();

                Deliverer orderDeliverer = optionalOrderDeliverer.orElseThrow();

                String deliveryDateAndTimeString = br.readLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                LocalDateTime deliveryDateAndTime = LocalDateTime.parse(deliveryDateAndTimeString, formatter);


                    Order order = new Order(orderRestaurant, orderMeals, orderDeliverer, deliveryDateAndTime);
                    orders.add(order);

                line = br.readLine();
            }
        } catch (IOException e) {
            log.error("Greška prilikom čitanja datoteke orders.txt", e);
            System.err.println("Pogreška prilikom učitavanja datoteke orders.txt.");
        } catch (NumberFormatException e) {
            System.err.println("Pogreška u formatu broja. Provjerite sadržaj datoteke orders.txt.");
        } catch (DateTimeParseException e) {
            System.err.println("Pogreška u formatu datuma. Provjerite redak s datumom u datoteci orders.txt.");
        }
    }
}