package hr.javafx.data;

import hr.javafx.restaurant.enums.ContractType;
import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Chef;
import hr.javafx.restaurant.model.Contract;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static hr.javafx.main.Main.log;

public class ChefDatabase {

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

    public static Set<Chef> getAllChefsFromDatabase()
        throws SQLException, IOException
    {
        Set<Chef> chefs = new HashSet<>();

        try (Connection connection = connectToDatabase();
             Statement sqlStatement = connection.createStatement();
             ResultSet resultSet = sqlStatement.executeQuery("SELECT *" + "FROM CHEF"))
        {
            while (resultSet.next()) {
                Chef newChef = getChefsFromResultSet(resultSet);
                chefs.add(newChef);
            }
        }
        return chefs;
    }

    private static Chef getChefsFromResultSet(ResultSet resultSet)
        throws SQLException
    {
        Long id = resultSet.getLong("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        Long contractId = resultSet.getLong("id");

        Contract contract = getContractById(contractId);

        return new Chef.ChefBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setId(id)
                .setContract(contract)
                .setBonus(new Bonus(new BigDecimal("200.0")))
                .build();
    }


    public static Contract getContractById(Long contractId) {
        String query = "SELECT *" + "FROM CONTRACT WHERE id = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, contractId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    BigDecimal salary = resultSet.getBigDecimal("salary");
                    LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                    LocalDate endDate = resultSet.getDate("end_date").toLocalDate();
                    String contractTypeString = resultSet.getString("type");

                    ContractType contractType = ContractType.valueOf(contractTypeString);

                    Contract contract = new Contract(salary, startDate, endDate, contractType);
                    contract.setId(id);
                    return contract;
                } else {
                    throw new FileNotFoundException("Contract not found for id: " + contractId);
                }
            }
        } catch (SQLException | IOException e) {
            log.error("SQL / IO exception in ChefDatabase class! ");
            throw new RuntimeException("Database error occurred", e);
        }
    }

    public static void insertNewChefToDatabase(Chef chef)
            throws SQLException, IOException
    {
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO CHEF (FIRST_NAME, LAST_NAME, SALARY) VALUES(?,?,?)",
                     Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, chef.getFirstName());
            stmt.setString(2,chef.getLastName());
            stmt.setBigDecimal(3, chef.getContract().getSalary());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    chef.setId(generatedKeys.getLong(1));
                }
            }
        }
        finally {activeConnectionWithDatabase = false;}
    }

    public static boolean isActiveConnectionWithDatabase() {
        return activeConnectionWithDatabase;
    }

    private ChefDatabase() {
        throw new IllegalStateException("Utility class");
    }
}
