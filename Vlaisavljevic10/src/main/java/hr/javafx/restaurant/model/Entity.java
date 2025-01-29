package hr.javafx.restaurant.model;

public abstract class Entity {

    private Long id;

    protected Entity() {
        this.id = null;
    }

    protected Entity(Long id) { this.id = id; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {this.id = id;}
}
