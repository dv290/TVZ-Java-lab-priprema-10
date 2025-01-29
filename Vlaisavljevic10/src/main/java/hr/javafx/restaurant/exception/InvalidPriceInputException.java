package hr.javafx.restaurant.exception;

/**
 * Runtime iznimka koja se baca kada korisnik unese neispravan format cijene prilikom unosa.
 * Ova iznimka se koristi kada unesena cijena nije u ispravnom formatu ili nije prihvaćena od strane sustava,
 * na primjer, kada je uneseni broj negativan ili nije brojčana vrijednost.
 *
 * Izboreno je korištenje {@link RuntimeException} klase jer se smatra da su greške u unosu cijene
 * nepredvidive i korisnik bi trebao imati priliku ispraviti unos bez potrebe za obavezno obradom iznimke
 * kroz try-catch blokove.
 *
 * Osim osnovnog konstruktora koji prima samo poruku o grešci, ova iznimka podržava i različite varijante
 * s dodatnim parametrima kao što su uzrok (cause), omogućavanje suzbijanja i kontroliranje mogućnosti zapisivanja
 * u stack trace.
 *
 * @see RuntimeException
 */

public class InvalidPriceInputException extends RuntimeException {

    public InvalidPriceInputException(String message) {
        super(message);
    }

    public InvalidPriceInputException(Throwable cause) {
        super(cause);
    }

    public InvalidPriceInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPriceInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InvalidPriceInputException() {
        super("Unjeli ste pogresan format place! Unesite ispravan iznos!\n");
    }
}
