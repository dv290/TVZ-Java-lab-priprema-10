package hr.javafx.restaurant.model;

import java.io.Serializable;

public abstract class Person extends Entity implements Serializable {
    String firstName;
    String lastName;
    private final String profession;

    protected Person(String firstName, String lastName, String profession, Long ID) {
        super(ID);
        this.firstName = firstName;
        this.lastName = lastName;
        this.profession = profession;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfession() {return profession;}

    public abstract Contract getContract();
}
