package hr.javafx.restaurant.utils;
import java.util.InputMismatchException;
import java.util.Scanner;

import static hr.javafx.main.Main.log;

/**
 * Utility klasa za unos cijelobrojnih vrijednosti putem {@link Scanner} objekta.
 */

public class IntInput {
    private IntInput(){}

    /**
     * Čita cijelobrojnu vrijednost iz danog ulaza putem {@link Scanner} objekta.
     * Metoda kontinuirano traži unos od korisnika sve dok ne unese valjanu cijelobrojnu vrijednost.
     * Ako se unese neispravan podatak (npr. nenumerička vrijednost), metoda ispisuje poruku o pogrešci,
     * bilježi grešku u log datoteku i ponovno traži unos ispravne vrijednosti.
     *
     * @param scanner instanca {@link Scanner} klase koja se koristi za čitanje korisničkog unosa
     * @return unesena cijelobrojna vrijednost
     */

    public static int inputInt (Scanner scanner) {
        int value = -1;
        boolean validInput = false;

        do {
            try {
                value = scanner.nextInt();
                scanner.nextLine();
                validInput = true;
            } catch (InputMismatchException e) {
                log.info(Messages.INVALID_INPUT_MESSAGE);
                System.out.println("Pogresan unos! Unesi cijelobrojnu vrijednost!");
                scanner.nextLine();
            }
        } while (!validInput);
        return value;
    }
}
