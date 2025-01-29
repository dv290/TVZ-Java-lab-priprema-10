package hr.javafx.restaurant.generics;

/**
 * Generička klasa koja služi za spremanje dvaju različitih podataka različitih tipova.
 *
 * @param <S> tip prvog podatka.
 * @param <T> tip drugog podatka.
 */
public class GenericDataContainer <S,T>{
    private S firstData;
    private T secondData;

    /**
     * Konstruktor koji inicijalizira objekt s dva podatka.
     *
     * @param firstData  prvi podatak generičkog tipa.
     * @param secondData drugi podatak generičkog tipa.
     */
    public GenericDataContainer(S firstData, T secondData) {
        this.firstData = firstData;
        this.secondData = secondData;
    }

    public S getFirstData() {
        return firstData;
    }

    public void setFirstData(S firstData) {
        this.firstData = firstData;
    }

    public T getSecondData() {
        return secondData;
    }

    public void setSecondData(T secondData) {
        this.secondData = secondData;
    }
}