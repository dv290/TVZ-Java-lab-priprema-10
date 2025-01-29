package hr.javafx.restaurant.generics;

import hr.javafx.restaurant.model.Restaurant;

import java.io.Serializable;
import java.util.List;

/**
 * Predstavlja ured za razmjenu radne snage koji upravlja popisom restorana.
 * Ova generička klasa podržava rad s bilo kojim tipom koji proširuje klasu {@link Restaurant}.
 *
 * @param <T> tip restorana kojim upravlja ovaj ured, mora proširivati {@link Restaurant}.
 */
public class RestaurantLabourExchangeOffice <T extends Restaurant> implements Serializable {

    /**
     * Popis restorana kojima upravlja ured za razmjenu radne snage.
     */
    private final List<T> restaurants;

    /**
     * Konstruktor koji inicijalizira ured za razmjenu radne snage s danim popisom restorana.
     *
     * @param restaurants popis restorana kojima će ured upravljati.
     */
    public RestaurantLabourExchangeOffice(List<T> restaurants) {
        this.restaurants = restaurants;
    }


    /**
     * Vraća popis restorana kojima upravlja ured za razmjenu radne snage.
     *
     * @return {@code List} restorana.
     */
    public List<T> getRestaurants() {
        return restaurants;
    }

}
