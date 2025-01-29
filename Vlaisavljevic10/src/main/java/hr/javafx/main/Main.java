package hr.javafx.main;

import hr.javafx.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.javafx.restaurant.model.*;
import hr.javafx.restaurant.utils.*;
import hr.javafx.restaurant.utils.ContractInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final int MINIMALNA_PLACA = 1000;

    public static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        log.info("Aplikacija pokrenuta. ");

        List<Category> categories = new ArrayList<>();
        Set<Ingredient> ingredients = new HashSet<>();
        Set<Meal> meals = new HashSet<>();
        Set<Chef> chefs = new HashSet<>();
        Set<Waiter> waiters = new HashSet<>();
        Set<Deliverer> deliverers = new HashSet<>();
        RestaurantLabourExchangeOffice<Restaurant> restaurants = new RestaurantLabourExchangeOffice<>(new ArrayList<>());
        List<Order> orders = new ArrayList<>();
        Map<Meal, List<Restaurant>> mealInRestaurantMap = new HashMap<>();
        AtomicInteger contractIndex = new AtomicInteger(0);

        List<Contract> contracts = new ArrayList<>();
        Set<Address> addresses = new HashSet<>();



        try {
            ContractInput.loadContracts(contracts);
            AddressInput.loadAddresses(addresses);
            CategoryInput.loadCategories(categories); //3
            IngredientsInput.loadIngredients(ingredients, categories); //5
            MealsInput.loadMeals(meals, categories, ingredients); //3
            ChefsInput.loadChefs(chefs,contracts,contractIndex); //3
            WaitersInput.loadWaiters(waiters,contracts,contractIndex); //3
            DelivererInput.loadDeliverers(deliverers, contracts, contractIndex); //3
            RestaurantsInput.loadRestaurants(restaurants,addresses ,meals, chefs, waiters, deliverers); //3
            OrderInput.loadOrders(orders, restaurants, meals, deliverers); //3
        }
        catch (Exception e) {
            System.out.println("Dogodila se greška prilikom učitavanja podataka u Data klasi! "+e.getMessage());
        }

        SerializationUtils.contractsSerializationAndDeserialization(contracts);
        SerializationUtils.restaurantSerializationAndDeserialization(restaurants);
        SerializationUtils.ordersSerializationAndDeserialization(orders);

        Finder.dostavljacNajviseDostava(deliverers);
        Finder.restoranNajskupljaNarudzba(orders);
        //Finder.printMealWithMinMaxCalories(newMeals);

        MealInRestaurant.mapMealInRestaurant(restaurants, mealInRestaurantMap);
        MealInRestaurant.displayRestaurantsForMeal(mealInRestaurantMap, scanner);

        Comparators.findHighestPaidEmployeeInEachRestaurant(restaurants);
        Comparators.sortEmployeesByContractDuration(restaurants);
        Comparators.sortMostPopularMeal(mealInRestaurantMap);
        Comparators.sortIngredientsAlphabetically(ingredients);

        LambdaUtils.findRestaurantWithMostEmployees(restaurants);
        LambdaUtils.mostOrderedMeal(orders);
        LambdaUtils.ingredientsInMeal(orders);
        LambdaUtils.totalOrderPrice(orders);
        LambdaUtils.getRestaurantsByCity(restaurants);

        scanner.close();
    }
}