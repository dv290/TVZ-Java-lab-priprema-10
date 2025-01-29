package hr.javafx.restaurant.model;

import java.io.Serializable;

public class Address extends Entity implements Serializable {
    private final String street;
    private final String houseNumber;
    private final String city;
    private final String postalCode;
    private static Long idCounter = 1L;

    public Address() {
        super();
        this.street = null;
        this.houseNumber = null;
        this.city = null;
        this.postalCode = null;
    }

    private Address(AddressBuilder builder) {
        super(idCounter);
        this.street = builder.street;
        this.houseNumber = builder.houseNumber;
        this.city = builder.city;
        this.postalCode = builder.postalCode;
        idCounter++;
    }

    public void setId(Long newId) {
        super.setId(newId);
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public static class AddressBuilder {
        private String street;
        private String houseNumber;
        private String city;
        private String postalCode;

        public AddressBuilder setStreet(String street) {
            this.street = street;
            return this;
        }

        public AddressBuilder setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public AddressBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public AddressBuilder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
