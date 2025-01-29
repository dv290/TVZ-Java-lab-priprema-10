package hr.javafx.data;

import hr.javafx.restaurant.enums.ContractType;
import hr.javafx.restaurant.model.Contract;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ContractDatabase {

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

    public static List<Contract> getAllContractsFromDatabase()
        throws SQLException, IOException
    {
        List<Contract> contracts = new ArrayList<>();

        try (Connection connection = connectToDatabase();
             Statement sqlStatement = connection.createStatement();
             ResultSet resultSet = sqlStatement.executeQuery("SELECT *" + "FROM CONTRACT")) {
            while (resultSet.next()) {
                Contract newContract = getContractFromResultSet(resultSet);
                contracts.add(newContract);
            }
        }
        return contracts;
    }

    private static Contract getContractFromResultSet(ResultSet resultSet)
        throws SQLException
    {
        Long id = resultSet.getLong("id");
        BigDecimal salary = resultSet.getBigDecimal("salary");
        LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
        LocalDate endDate = resultSet.getDate("end_date").toLocalDate();
        String contract_type = resultSet.getString("type");

        ContractType contractType = ContractType.valueOf(contract_type);


        Contract contract = new Contract(salary, startDate, endDate, contractType);
        contract.setId(id);

        return contract;
    }

    public static void insertNewContractToDatabase(Contract contract)
        throws SQLException, IOException
    {
        try(Connection connection = connectToDatabase();
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO CONTRACT (SALARY, START_DATE, END_DATE, TYPE) VALUES (?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setBigDecimal(1, contract.getSalary());
            stmt.setDate(2, Date.valueOf(contract.getStartDate()));
            stmt.setDate(3,Date.valueOf(contract.getEndDate()));
            stmt.setString(4, String.valueOf(contract.getContractType()));

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    contract.setId(generatedKeys.getLong(1));
                }
            }
        }
        finally {activeConnectionWithDatabase = false;}
    }

    public static boolean isActiveConnectionWithDatabase() {
        return activeConnectionWithDatabase;
    }

    private ContractDatabase() {
        throw new IllegalStateException("Utility class");
    }
}
