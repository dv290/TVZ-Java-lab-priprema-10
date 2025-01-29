package hr.javafx.restaurant.enums;

/**
 * Enum koji se koristi za određivanje vrste zaposlenja prilikom kreiranja
 * instance klase Contract unutar objekta tipa Deliverer, Chef i Waiter.
 */

public enum ContractType {
    FULL_TIME("FULL-TIME"),
    PART_TIME("PART-TIME");

    private final String description;

    ContractType(String description) {
        this.description = description;
    }

    public String getType() {
        return description;
    }
}
