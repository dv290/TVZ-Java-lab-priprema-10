package hr.javafx.restaurant.model;

public sealed interface Meat permits Grill{
    boolean isGrilled();
    String getMeatType();
}
