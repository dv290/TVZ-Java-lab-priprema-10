package hr.javafx.restaurant.model;

import java.util.Objects;

public class Waiter extends Person {
    private Contract contract;
    private final Bonus bonus;
    private Long id;  // Added the id variable

    private Waiter(WaiterBuilder builder) {
        super(builder.firstName, builder.lastName, "Waiter", builder.id);
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

    public Long getId() {  // Getter for the id
        return id;
    }

    public void setId(Long id) {  // Setter for the id
        this.id = id;
    }

    public static class WaiterBuilder {
        private String firstName;
        private String lastName;
        private Contract contract;
        private Bonus bonus;
        private Long id;  // Added the id to the builder

        public WaiterBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public WaiterBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public WaiterBuilder setContract(Contract contract) {
            this.contract = contract;
            return this;
        }

        public WaiterBuilder setBonus(Bonus bonus) {
            this.bonus = bonus;
            return this;
        }

        public WaiterBuilder setId(Long id) {  // Setter for the id in the builder
            this.id = id;
            return this;
        }

        public Waiter build() {
            return new Waiter(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Waiter waiter = (Waiter) o;
        return Objects.equals(contract, waiter.contract) && Objects.equals(bonus, waiter.bonus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contract, bonus);
    }

    public Bonus getBonus() {
        return bonus;
    }
}
