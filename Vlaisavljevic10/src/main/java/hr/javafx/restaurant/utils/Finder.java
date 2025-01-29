package hr.javafx.restaurant.utils;

import hr.javafx.restaurant.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Util klasa u kojoj se pronalaze metode za specifične stavke koje se traže u poljima,
 * kao što su npr. dostavljač s najviše dostava, kuhar s najvišom plaćom, itd...
 */

public class Finder {

    public static void dostavljacNajviseDostava(Set<Deliverer> deliverers) {
        Finder.dostavljacNajviseDostavaMethod(deliverers);
    }

    public static void restoranNajskupljaNarudzba(List<Order> orders) {
        Finder.restoranNajskupljaNarudzbaMethod(orders);
    }

    public static void findHighestPaidEmployee(List<Person> employees) {
        Finder.findHighestPaidEmployeeMethod(employees);
    }

    public static void findLongestWorkingEmployee(List<Person> employees) {
        Finder.findLongestWorkingEmployeeMethod(employees);
    }

    public static void printMealWithMinMaxCalories(List<Meal> newMeals) {
        Finder.printMealWithMinMaxCaloriesMethod(newMeals);
    }



    private static void dostavljacNajviseDostavaMethod(Set<Deliverer> deliverers) {
        int maxDostave = 0;
        List<Deliverer> topDostavljac = new ArrayList<>();

        for (Deliverer deliverer : deliverers) {
            if (deliverer.getBrojDostava() > maxDostave) {
                maxDostave = deliverer.getBrojDostava();
                topDostavljac.clear();
                topDostavljac.add(deliverer);
            } else if (deliverer.getBrojDostava() == maxDostave) {
                topDostavljac.add(deliverer);
            }
        }

        System.out.println("Dostavljač/i s najviše dostava (" + maxDostave + "):");
        for (Deliverer dostavljac : topDostavljac) {
            System.out.println(dostavljac.getFirstName() + " " + dostavljac.getLastName() + ", Plaća: " + dostavljac.getContract().getSalary());
        }
    }



    private static void restoranNajskupljaNarudzbaMethod(List<Order> orders) {
        BigDecimal highestPrice = BigDecimal.ZERO;
        List<Restaurant> highestRestaurants = new ArrayList<>();

        for (Order order : orders) {
            BigDecimal currentPrice = order.getTotalPrice();
            Restaurant restaurant = order.getRestaurant();

            if (currentPrice.compareTo(highestPrice) > 0) {
                highestPrice = currentPrice;
                highestRestaurants.clear();
                highestRestaurants.add(restaurant);
            } else if (currentPrice.compareTo(highestPrice) == 0) {
                highestRestaurants.add(restaurant);
            }
        }

        System.out.println("Restorani s najskupljom narudzbom (cijena: " + highestPrice + "):");
        for (Restaurant restaurant : highestRestaurants) {
            System.out.println("Restoran: " + restaurant.getName());
        }
    }



    private static BigDecimal getSalary(Person employee) {
        return employee.getContract().getSalary();
    }

    private static void findHighestPaidEmployeeMethod(List<Person> employees) {
        Person highestPaid = employees.getFirst();
        BigDecimal highestSalary = getSalary(highestPaid);

        for (Person employee : employees) {
            if (employee != null) {
                BigDecimal salary = getSalary(employee);
                if (salary.compareTo(highestSalary) > 0) {
                    highestPaid = employee;
                    highestSalary = salary;
                }
            }
        }
        System.out.println("Najplaceniji radnik je: "+highestPaid.getFirstName()+
                " "+highestPaid.getLastName()+"sa placom od: "+highestPaid.getContract().getSalary());
    }



    private static void findLongestWorkingEmployeeMethod(List<Person> employees) {
        Person longestWorkingEmployee = employees.getFirst();
        long longestDuration = 0;

        for (Person employee : employees) {
            long duration = employee.getContract().getEndDate().toEpochDay() - employee.getContract().getStartDate().toEpochDay();

            if (duration > longestDuration || (duration == longestDuration && employee.getContract()
                    .getStartDate().isBefore(longestWorkingEmployee.getContract().getStartDate()))) {
                longestWorkingEmployee = employee;
                longestDuration = duration;
            }
        }

        System.out.println("Zaposlenik koji najduže radi je: " + longestWorkingEmployee.getFirstName() + " " +
                longestWorkingEmployee.getLastName());
    }



    private static void printMealWithMinMaxCaloriesMethod(List<Meal> newMeals) {
        Meal maxCalorieMeal = newMeals.getFirst();
        Meal minCalorieMeal = newMeals.getFirst();

        for (Meal meal : newMeals) {
            if (meal.getTotalKcal().compareTo(maxCalorieMeal.getTotalKcal()) > 0) {
                maxCalorieMeal = meal;
            }

            if (meal.getTotalKcal().compareTo(minCalorieMeal.getTotalKcal()) < 0) {
                minCalorieMeal = meal;
            }
        }

        System.out.println("Jelo sa najviše kalorija: ");
        System.out.println(maxCalorieMeal.getName());

        System.out.println("Jelo sa najmanje kalorija: ");
        System.out.println(minCalorieMeal.getName());
        System.out.println(minCalorieMeal.getCategory().getName());
        System.out.println(minCalorieMeal.getPrice());
    }
}
