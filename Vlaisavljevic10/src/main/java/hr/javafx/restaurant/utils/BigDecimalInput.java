package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.exception.InvalidPriceInputException;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

import static hr.javafx.main.Main.MINIMALNA_PLACA;
import static hr.javafx.main.Main.log;


/**
 * Utility klasa u kojoj se kreira metoda inputBigDecimal
 */

public class BigDecimalInput {
    private BigDecimalInput(){}
    /**
     * Čita vrijednost tipa BigDecimal iz danog ulaza putem {@link Scanner} objekta.
     * Ova metoda kontinuirano traži unos od korisnika sve dok ne unese valjanu BigDecimal vrijednost.
     * Ako se unese neispravan podatak (npr. nenumerička vrijednost), metoda ispisuje poruku o grešci,
     * bilježi grešku u log datoteku i ponovno traži unos ispravne numeričke vrijednosti.
     * @param scanner instanca {@link Scanner} klase koja se koristi za čitanje korisničkog unosa
     * @return BigDecimal vrijednost koju je korisnik unio
     */
    public static BigDecimal inputBigDecimal(Scanner scanner) {
        BigDecimal value = BigDecimal.ZERO;
        boolean validInput = false;

        do {
            try {
                value = scanner.nextBigDecimal();
                scanner.nextLine();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Pogresan unos! Unesite brojcanu vrijednost!");
                log.info(Messages.INVALID_BIG_DECIMAL_INPUT);
                scanner.nextLine();
            }
        }while(!validInput);
        return value;
    }

    public static BigDecimal inputSalary(Scanner scanner) {

        BigDecimal value = BigDecimal.ZERO;
        boolean validInput = false;
        do {
            try {
                value = scanner.nextBigDecimal();
                scanner.nextLine();

                if (value.compareTo(BigDecimal.valueOf(MINIMALNA_PLACA)) < 0) {
                    throw new InvalidPriceInputException(Messages.INSUFFICIENT_FUNDS_INPUT);
                }
                validInput = true;
            }
            catch (InvalidPriceInputException e) {
                System.out.println("Cijena ne moze biti negativna! Unesi ponovo! ");
                log.info(Messages.INSUFFICIENT_FUNDS_INPUT);
                scanner.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println("Pogresan unos! Unesite brojcanu vrijednost!");
                log.info(Messages.INVALID_BIG_DECIMAL_INPUT);
                scanner.nextLine();
            }

        }while(!validInput);
        return value;
    }

    public static BigDecimal inputPrice(Scanner scanner) {
        BigDecimal value = BigDecimal.ZERO;
        boolean validInput = false;

        do {
            try {
                value = scanner.nextBigDecimal();
                scanner.nextLine();

                if (value.compareTo(BigDecimal.ZERO) < 0) {
                    throw new InvalidPriceInputException(Messages.NEGATIVE_NUMBER_INPUT);
                }

                validInput = true;
            } catch (InvalidPriceInputException e) {
                System.out.println("Cijena ne moze biti negativna! Unesi ponovo! ");
                log.info(Messages.NEGATIVE_NUMBER_INPUT);
                scanner.nextLine();
            }

            catch (InputMismatchException e) {
                System.out.println("Pogresan unos! Unesite brojcanu vrijednost!");
                log.info(Messages.INVALID_BIG_DECIMAL_INPUT);
                scanner.nextLine();
            }

        }while(!validInput);
        return value;
    }
}


