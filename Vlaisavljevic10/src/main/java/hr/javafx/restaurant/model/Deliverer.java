package hr.javafx.restaurant.model;

import java.io.Serializable;
import java.util.Objects;

public class Deliverer extends Person implements Serializable {
    private Contract contract;
    private final Bonus bonus;
    private int brojDostava;
    private Long id;  // Added the id variable

    public Deliverer(DelivererBuilder builder) {
        super(builder.firstName, builder.lastName, "Deliverer", builder.id);
        this.contract = builder.contract;
        this.bonus = builder.bonus;
        this.id = builder.id;  // Set the id from the builder
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public int getBrojDostava() {
        return brojDostava;
    }

    public Long getId() {  // Getter for the id
        return id;
    }

    public void setId(Long id) {  // Setter for the id
        this.id = id;
    }

    public static class DelivererBuilder {
        private String firstName;
        private String lastName;
        private Contract contract;
        private Bonus bonus;
        private Long id;  // Added the id to the builder

        public DelivererBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public DelivererBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public DelivererBuilder setContract(Contract contract) {
            this.contract = contract;
            return this;
        }

        public DelivererBuilder setBonus(Bonus bonus) {
            this.bonus = bonus;
            return this;
        }

        public DelivererBuilder setId(Long id) {  // Setter for the id in the builder
            this.id = id;
            return this;
        }

        public Deliverer build() {
            return new Deliverer(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Deliverer deliverer = (Deliverer) o;
        return brojDostava == deliverer.brojDostava && Objects.equals(contract, deliverer.contract) && Objects.equals(bonus, deliverer.bonus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contract, bonus, brojDostava);
    }

    public Bonus getBonus() {
        return bonus;
    }
}
