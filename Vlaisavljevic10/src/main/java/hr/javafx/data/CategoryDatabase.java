package hr.javafx.data;

import hr.javafx.restaurant.model.Category;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class CategoryDatabase {

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

    public static List<Category> getAllCategoriesFromDatabase()
            throws SQLException, IOException
    {
        List<Category> categories = new ArrayList<>();

        try (Connection connection = connectToDatabase();
             Statement sqlStatement = connection.createStatement();
             ResultSet resultSet = sqlStatement.executeQuery("SELECT *" + "FROM CATEGORY"))

        {

            while (resultSet.next()) {
                Category newCategory = getCategoryFromResultSet(resultSet);
                categories.add(newCategory);
            }
        }
        return categories;
    }

    private static Category getCategoryFromResultSet(ResultSet resultSet)
        throws SQLException
    {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String categoryDescription = resultSet.getString("description");

        Category category = new Category.CategoryBuilder()
                .setName(name)
                .setDescription(categoryDescription)
                .build();

        category.setId(id);

        return category;
    }

    public static void insertNewCategoryToDatabase(Category category)
            throws SQLException, IOException
    {
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO CATEGORY (NAME, DESCRIPTION) VALUES(?, ?)",
                     Statement.RETURN_GENERATED_KEYS))
        {
            // Set only the NAME and DESCRIPTION values (ID is auto-generated)
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());

            // Execute the update
            stmt.executeUpdate();

            // Retrieve the auto-generated ID and set it in the Category object
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    category.setId(generatedKeys.getLong(1)); // Assign the generated ID to the category
                }
            }
        }
        finally {activeConnectionWithDatabase = false;}
    }

    public static boolean isActiveConnectionWithDatabase() {
        return activeConnectionWithDatabase;
    }

    private CategoryDatabase() {
        throw new IllegalStateException("Utility class");
    }
}
