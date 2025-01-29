package hr.javafx.restaurant.sort;

import hr.javafx.restaurant.model.Person;

import java.util.Comparator;

public class EmployeeContractDuradionComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        long duration1 = p1.getContract().getEndDate().toEpochDay() - p1.getContract().getStartDate().toEpochDay();
        long duration2 = p2.getContract().getEndDate().toEpochDay() - p2.getContract().getStartDate().toEpochDay();

        return Long.compare(duration1,duration2);
    }
}
