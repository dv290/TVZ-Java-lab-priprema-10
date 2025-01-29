package hr.javafx.data;

import hr.javafx.restaurant.enums.ContractType;
import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Waiter;

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

public class WaiterDatabase {

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

    public static Set<Waiter> getAllWaitersFromDatabase()
            throws SQLException, IOException
    {
        Set<Waiter> waiters = new HashSet<>();

        try (Connection connection = connectToDatabase();
             Statement sqlStatement = connection.createStatement();
             ResultSet resultSet = sqlStatement.executeQuery("SELECT * FROM WAITER"))
        {
            while (resultSet.next()) {
                Waiter newWaiter = getWaitersFromResultSet(resultSet);
                waiters.add(newWaiter);
            }
        }
        return waiters;
    }

    private static Waiter getWaitersFromResultSet(ResultSet resultSet)
        throws SQLException
    {
        Long id = resultSet.getLong("id");
        String first_name = resultSet.getString("first_name");
        String last_name = resultSet.getString("last_name");
        Long contract_id = resultSet.getLong("id");

        Contract contract = getContractById(contract_id);

        return new Waiter.WaiterBuilder()
                .setFirstName(first_name)
                .setLastName(last_name)
                .setId(id)
                .setContract(contract)
                .setBonus(new Bonus(new BigDecimal("100.0")))
                .build();
    }

    public static Contract getContractById(Long contractId) {
        String query = "SELECT * FROM CONTRACT WHERE id = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, contractId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    BigDecimal salary = resultSet.getBigDecimal("salary");
                    LocalDate start_date = resultSet.getDate("start_date").toLocalDate();
                    LocalDate end_date = resultSet.getDate("end_date").toLocalDate();
                    String contract_type = resultSet.getString("type");

                    ContractType contractType = ContractType.valueOf(contract_type);

                    Contract contract = new Contract(salary, start_date, end_date, contractType);
                    contract.setId(id);
                    return contract;
                } else {
                    throw new FileNotFoundException("Contract not found for id: " + contractId);
                }
            }
        } catch (SQLException | IOException e) {
            log.error("SQL / IO exception in WaiterDatabase class! ");
            throw new RuntimeException("Database error occurred", e);
        }
    }

    public static void insertNewWaiterToDatabase(Waiter waiter)
            throws SQLException, IOException
    {
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO WAITER (FIRST_NAME, LAST_NAME, SALARY) VALUES(?,?,?)",
                     Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, waiter.getFirstName());
            stmt.setString(2, waiter.getLastName());
            stmt.setBigDecimal(3, waiter.getContract().getSalary());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    waiter.setId(generatedKeys.getLong(1));
                }
            }
        }
        finally {activeConnectionWithDatabase = false;}
    }

    public static boolean isActiveConnectionWithDatabase() {
        return activeConnectionWithDatabase;
    }

    private WaiterDatabase() {
        throw new IllegalStateException("Utility class");
    }
}
