package hr.javafx.data;

import hr.javafx.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.javafx.restaurant.model.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

public class RestaurantDatabase {

    private static boolean activeConnectionWithDatabase = false;

    private static synchronized Connection connectToDatabase() throws IOException, SQLException {
        activeConnectionWithDatabase = true;
        Properties props = new Properties();

        try(FileReader fileReader = new FileReader("database.properties")) {
            props.load(fileReader);
        }
        finally {activeConnectionWithDatabase = false;}

        return DriverManager.getConnection(
                props.getProperty("databaseUrl"),
                props.getProperty("username"),
                props.getProperty("password")
        );
    }

    public static RestaurantLabourExchangeOffice<Restaurant> getAllRestaurantsFromDatabase(
            Set<Meal> meals, Set<Chef> chefs, Set<Waiter> waiters, Set<Deliverer> deliverers)
            throws SQLException, IOException
    {
        RestaurantLabourExchangeOffice<Restaurant> restaurants = new RestaurantLabourExchangeOffice<>(new ArrayList<>());

        try (Connection connection = connectToDatabase();
             Statement sqlStatement = connection.createStatement();
             ResultSet resultSet = sqlStatement.executeQuery("SELECT *" + "FROM RESTAURANT")) {

            while (resultSet.next()) {
                Restaurant newRestaurant = getRestaurantFromResultSet(resultSet, meals, chefs, waiters, deliverers);
                restaurants.getRestaurants().add(newRestaurant);
            }
        }
        return restaurants;
    }

    private static Restaurant getRestaurantFromResultSet(
            ResultSet resultSet, Set<Meal> meals, Set<Chef> chefs, Set<Waiter> waiters, Set<Deliverer> deliverers)
            throws SQLException, IOException
    {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Long addressId = resultSet.getLong("address_id");

        Address address = getAddressById(addressId);

        Restaurant restaurant = new Restaurant(name, address, meals, chefs, waiters, deliverers);
        restaurant.setId(id);
        return restaurant;
    }

    public static Address getAddressById(Long addressId)
            throws SQLException, IOException
    {
        String query = "SELECT *" + "FROM ADDRESS WHERE ID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, addressId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String street = resultSet.getString("street");
                    String houseNumber = resultSet.getString("house_number");
                    String city = resultSet.getString("city");
                    String postalCode = resultSet.getString("postal_code");

                    Address address = new Address.AddressBuilder()
                            .setStreet(street)
                            .setCity(city)
                            .setHouseNumber(houseNumber)
                            .setPostalCode(postalCode)
                            .build();

                    address.setId(id);

                    return address;

                } else {
                    throw new SQLException("Address not found for ID: " + addressId);
                }
            }
        }
    }

    public static void insertNewRestaurantToDatabase(Restaurant restaurant)
        throws SQLException, IOException
    {
        try(Connection connection = connectToDatabase();
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO RESTAURANT (NAME, ADDRESS_ID) VALUES(?,?)",
                    Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, restaurant.getName());
            stmt.setLong(2, restaurant.getAddress().getId());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    restaurant.setId(generatedKeys.getLong(1));
                }
            }
        }
        finally {activeConnectionWithDatabase = false;}
    }

    public static boolean isActiveConnectionWithDatabase() {
        return activeConnectionWithDatabase;
    }

    private RestaurantDatabase() {
        throw new IllegalStateException("Utility class");
    }
}
