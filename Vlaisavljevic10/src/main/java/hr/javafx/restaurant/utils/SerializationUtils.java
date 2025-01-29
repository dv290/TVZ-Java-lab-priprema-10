package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.model.Order;
import hr.javafx.restaurant.model.Restaurant;

import java.io.*;
import java.util.List;

import static hr.javafx.main.Main.log;

public class SerializationUtils {
    private SerializationUtils(){}

    public static void contractsSerializationAndDeserialization(List<Contract> contracts) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("contracts.dat"))) {
            for(Contract contract : contracts) {
                out.writeObject(contract);
            }
            System.out.println("contracts.dat serijaliziran!\n");
        } catch (IOException e) {
            System.out.println("Greska contracts serijalizacija!");
            log.error("Greska kod serijalizacije contracts.dat",e);
        }

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("contracts.dat"))) {
            System.out.println("Podatci o pročitanom objektu: ");
            for(Contract contract : contracts) {
                System.out.println("ID: "+contract.getId());
                System.out.println("Vrsta ugovora: "+contract.getContractType());
                System.out.println("Početak rada: "+contract.getStartDate());
                System.out.println("Završetak rada: "+contract.getEndDate());
                System.out.println("Satnica: "+contract.getSalary()+"€");
            }
        } catch (IOException e) {
            System.out.println("Greska contracts deserijalizacija!");
            log.error("Greska kod deserijalizacije contracts.dat",e);
        }
        finally {
            System.out.println("\n");
        }
    }

    public static void restaurantSerializationAndDeserialization(RestaurantLabourExchangeOffice<Restaurant> restaurants) {
        List<Restaurant> restaurantsList = restaurants.getRestaurants();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("restaurants.dat"))) {
            for(Restaurant restaurant : restaurantsList) {
                out.writeObject(restaurant);
            }
            System.out.println("restaurants.dat serijaliziran!\n");

        } catch (IOException e) {
            System.out.println("Greska restoran serijalizacija!");
            log.error("Greska kod serijalizacije restaurants.dat",e);
        }

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("restaurants.dat"))) {
            System.out.println("Podatci o pročitanom objektu: ");
            for(Restaurant restaurant : restaurants.getRestaurants()) {
                System.out.println("Ime restorana: "+restaurant.getName());
                System.out.println("ID: "+restaurant.getId());
                System.out.println("Grad: "+restaurant.getAddress().getCity());
                System.out.println("Ulica: "+restaurant.getAddress().getStreet());
            }
        } catch (IOException e) {
            System.out.println("Greska restoran deserijalizacija!");
            log.error("Greska kod deserijalizacije restaurants.dat",e);
        } finally {
            System.out.println("\n");
        }
    }

    public static void ordersSerializationAndDeserialization(List<Order> orders) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("orders.dat"))) {
            for(Order order : orders) {
                out.writeObject(order);
            }
            System.out.println("orders.dat serijaliziran!\n");

        } catch (IOException e) {
            System.out.println("Greska orders serijalizacija!");
            log.error("Greska kod serijalizacije orders.dat",e);
        }

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("orders.dat"))) {
            System.out.println("Podatci o pročitanom objektu: ");
            for(Order order : orders) {
                System.out.println("ID narudžbe: "+order.getId());
                System.out.println("Iz restorana: "+order.getRestaurant().getName());
                System.out.println("Sadržaj narudžbe: ");
                for(Meal meal : order.getMeals()) {
                    System.out.println(meal.getName());
                }
                System.out.println("Dostavljač: "+order.getDeliverer().getFirstName()+" "+order.getDeliverer().getLastName()+"\n");
            }
        } catch (IOException e) {
            System.out.println("Greska orders deserijalizacija!");
            log.error("Greska kod deserijalizacije orders.dat",e);
        } finally {
            System.out.println("\n");
        }
    }
}
