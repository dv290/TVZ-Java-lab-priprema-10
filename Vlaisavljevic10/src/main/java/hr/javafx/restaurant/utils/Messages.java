package hr.javafx.restaurant.utils;

/**
 * Utility klasa koja sadrži poruke za obavještavanje korisnika o greškama prilikom unosa podataka.
 * Ove poruke se koriste za standardizaciju poruka o greškama ili obavijestima u aplikaciji.
 */

public class Messages {
    private Messages(){}

    public static final String INVALID_INPUT_MESSAGE = "Pogresan unos! Pokusajte ponovo!";
    public static final String INVALID_BIG_DECIMAL_INPUT = "Pogresno unesena BigDecimal vrijednost. ";
    public static final String INVALID_CATEGORY_SELECTION = "Odabrana kategorija ne postoji ili nije pronadena. ";
    public static final String INVALID_INT_INPUT = "Pogresan unos int varijable. ";
    public static final String NEGATIVE_NUMBER_INPUT = "Unesen je negativan broj. ";
    public static final String INSUFFICIENT_FUNDS_INPUT = "Unesen je premalen broj za placu. ";
}
