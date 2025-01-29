package hr.javafx.data;

import hr.javafx.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.javafx.restaurant.model.*;
import hr.javafx.restaurant.utils.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Data {
    private static final Data instance = new Data();

    private Data() {initialize();}

    public static Data getInstance() {
        return instance;
    }

    private List<Category> categories = new ArrayList<>();
    private Set<Ingredient> ingredients = new HashSet<>();
    private Set<Meal> meals = new HashSet<>();
    private Set<Chef> chefs = new HashSet<>();
    private Set<Waiter> waiters = new HashSet<>();
    private Set<Deliverer> deliverers = new HashSet<>();
    private RestaurantLabourExchangeOffice<Restaurant> restaurants = new RestaurantLabourExchangeOffice<>(new ArrayList<>());
    private final List<Order> orders = new ArrayList<>();
    private List<Contract> contracts = new ArrayList<>();
    private final AtomicInteger contractIndex = new AtomicInteger(0);
    private Set<Address> addresses = new HashSet<>();

    public void initialize() {
        try {
            contracts = ContractDatabase.getAllContractsFromDatabase();//ContractInput.loadContracts(contracts);
            addresses = AddressDatabase.getAllAddressesFromDatabase();//AddressInput.loadAddresses(addresses);
            categories = CategoryDatabase.getAllCategoriesFromDatabase(); //CategoryInput.loadCategories(categories);
            ingredients = IngredientDatabase.getAllIngredientsFromDatabase(); //IngredientsInput.loadIngredients(ingredients, categories);
            meals = MealDatabase.getAllMealsFromDatabase(); //MealsInput.loadMeals(meals, categories, ingredients);
            chefs = ChefDatabase.getAllChefsFromDatabase();//ChefsInput.loadChefs(chefs, contracts, contractIndex);
            waiters = WaiterDatabase.getAllWaitersFromDatabase(); //WaitersInput.loadWaiters(waiters, contracts, contractIndex);
            deliverers = DelivererDatabase.getAllDeliverersFromDatabase(); //DelivererInput.loadDeliverers(deliverers, contracts, contractIndex);
            restaurants = RestaurantDatabase.getAllRestaurantsFromDatabase(meals, chefs, waiters, deliverers); //RestaurantsInput.loadRestaurants(restaurants, addresses, meals, chefs, waiters, deliverers);
            OrderInput.loadOrders(orders, restaurants, meals, deliverers);

        } catch (Exception e) {
            System.out.println("Dogodila se greška prilikom učitavanja podataka u Data klasi! " + e.getMessage());
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public Set<Chef> getChefs() {
        return chefs;
    }

    public Set<Waiter> getWaiters() {return waiters;}

    public Set<Deliverer> getDeliverers() {
        return deliverers;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants.getRestaurants();
    }

    public List<Order> getOrders() {return orders;}

    public List<Contract> getContracts() {
        return contracts;
    }

    public Set<Address> getAddresses() {return addresses;}
}
