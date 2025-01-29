package hr.javafx.restaurant.exception;

/**
 * Označena (checked) iznimka koja se baca kada se pokuša unijeti entitet (npr. kategoriju, jelo ili osobu)
 * s imenom koje već postoji u sustavu. Ova iznimka omogućuje identifikaciju pokušaja dupliciranja entiteta
 * na temelju naziva.
 *
 * Ova iznimka može biti korisna u slučajevima kada želimo spriječiti dupliciranje podataka u sustavu,
 * primjerice prilikom dodavanja novih kategorija ili jela u restoran.
 *
 * Osim osnovnog konstruktora koji prima samo poruku o grešci, ova iznimka podržava i različite varijante
 * s dodatnim parametrima kao što su uzrok (cause), omogućavanje suzbijanja i kontroliranje mogućnosti zapisivanja
 * u stack trace.
 *
 * @see Exception
 */

public class DuplicateEntityException extends Exception {

  public DuplicateEntityException(String message) {
    super(message);
  }

  public DuplicateEntityException(Throwable cause) {
    super(cause);
  }

  public DuplicateEntityException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicateEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public DuplicateEntityException() {
    super("Ovaj naziv se vec koristi! Pokusajte nesto drugo!\n");
  }
}
