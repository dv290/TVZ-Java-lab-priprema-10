package hr.javafx.data;

import hr.javafx.restaurant.model.Address;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class AddressDatabase {

    private static boolean activeConnectionWithDatabase = false;

    private static synchronized Connection connectToDatabase() throws IOException, SQLException {
        activeConnectionWithDatabase = true;
        Properties props = new Properties();

        try (FileReader fileReader = new FileReader("database.properties")) {
            props.load(fileReader);
        }
        finally {activeConnectionWithDatabase = false;}


        return DriverManager.getConnection(
                props.getProperty("databaseUrl"),
                props.getProperty("username"),
                props.getProperty("password")
        );
    }

    public static Set<Address> getAllAddressesFromDatabase()
        throws SQLException, IOException
    {
        Set<Address> addresses = new HashSet<>();

        try (Connection connection = connectToDatabase();
             Statement sqlStatement = connection.createStatement();
             ResultSet resultSet = sqlStatement.executeQuery("SELECT * " + "FROM ADDRESS")) {
            while (resultSet.next()) {
                Address newAddress = getAddressesFromResultSet(resultSet);
                addresses.add(newAddress);
            }
        }
        return addresses;
    }


    private static Address getAddressesFromResultSet(ResultSet resultSet)
        throws SQLException
    {
        Long id = resultSet.getLong("id");
        String street = resultSet.getString("street");
        String houseNumber = resultSet.getString("house_number");
        String city = resultSet.getString("city");
        String postalCode = resultSet.getString("postal_code");

        Address address = new Address.AddressBuilder()
                .setStreet(street)
                .setHouseNumber(houseNumber)
                .setCity(city)
                .setPostalCode(postalCode)
                .build();

        address.setId(id);
        return address;
    }

    public static void insertNewAddressToDatabase(Address address)
            throws SQLException, IOException
    {
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO ADDRESS (STREET, HOUSE_NUMBER, CITY, POSTAL_CODE) VALUES(?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, address.getStreet());
            stmt.setString(2,address.getHouseNumber());
            stmt.setString(3, address.getCity());
            stmt.setString(4, address.getPostalCode());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    address.setId(generatedKeys.getLong(1));
                }
            }
        }
        finally {activeConnectionWithDatabase = false;}
    }

    public static boolean isActiveConnectionWithDatabase() {
        return activeConnectionWithDatabase;
    }

    private AddressDatabase() {
        throw new IllegalStateException("Utility class");
    }
}
