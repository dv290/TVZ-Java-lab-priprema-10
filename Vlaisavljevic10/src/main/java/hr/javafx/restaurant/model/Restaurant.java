package hr.javafx.restaurant.model;

import java.io.Serializable;
import java.util.Set;

public class Restaurant extends Entity implements Serializable {
    private String name;
    private Address address;
    private Set<Meal> meals;
    private Set<Chef> chefs;
    private Set<Waiter> waiters;
    private Set<Deliverer> deliverers;
    private static Long idCounter=1L;

    public Restaurant(String name, Address address, Set<Meal> meals, Set<Chef> chefs, Set<Waiter> waiters, Set<Deliverer> deliverers) {
        super(idCounter);
        this.name = name;
        this.address = address;
        this.meals = meals;
        this.chefs = chefs;
        this.waiters = waiters;
        this.deliverers = deliverers;
        idCounter++;
    }

    public void setId(Long newId) {
        super.setId(newId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    public Set<Chef> getChefs() {
        return chefs;
    }

    public void setChefs(Set<Chef> chefs) {
        this.chefs = chefs;
    }

    public Set<Waiter> getWaiters() { return waiters; }

    public void setWaiters(Set<Waiter> waiters) {
        this.waiters = waiters;
    }

    public Set<Deliverer> getDeliverers() { return deliverers; }

    public void setDeliverers(Set<Deliverer> deliverers) {
        this.deliverers = deliverers;
    }
}
