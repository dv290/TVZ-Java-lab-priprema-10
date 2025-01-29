package hr.javafx.restaurant.sort;

import hr.javafx.restaurant.model.Person;

import java.util.Comparator;

public class EmployeeSalaryComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return p2.getContract().getSalary().compareTo(p1.getContract().getSalary());
    }
}
