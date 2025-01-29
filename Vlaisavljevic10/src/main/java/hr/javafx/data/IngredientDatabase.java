package hr.javafx.data;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static hr.javafx.main.Main.log;

public class IngredientDatabase {

    private static boolean activeConnectionWithDatabase = false;

    private static synchronized Connection connectToDatabase() throws IOException, SQLException {
        activeConnectionWithDatabase = true;
        Properties props = new Properties();

        try(FileReader fileReader = new FileReader("database.properties")){
            props.load(fileReader);
        }
        finally {activeConnectionWithDatabase = false;}

        return DriverManager.getConnection(
                props.getProperty("databaseUrl"),
                props.getProperty("username"),
                props.getProperty("password")
        );
    }

    public static Set<Ingredient> getAllIngredientsFromDatabase()
        throws SQLException, IOException
    {
        Set<Ingredient> ingredients = new HashSet<>();

        try (Connection connection = connectToDatabase();
             Statement sqlStatement = connection.createStatement();
             ResultSet resultSet = sqlStatement.executeQuery("SELECT *" + "FROM INGREDIENT"))
        {
            while (resultSet.next()){
                Ingredient newIngredient = getIngredientFromResultSet(resultSet);
                ingredients.add(newIngredient);
            }
        }
        return ingredients;
    }


    private static Ingredient getIngredientFromResultSet(ResultSet resultSet)
        throws SQLException
    {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Long categoryId = resultSet.getLong("category_id");
        BigDecimal kcal = resultSet.getBigDecimal("kcal");
        String preparationMethod = resultSet.getString("preparation_method");

        Category category = getCategoryById(categoryId);

        Ingredient ingredient = new Ingredient(name, category, kcal, preparationMethod);
        ingredient.setId(id);
        return ingredient;
    }

    private static Category getCategoryById(Long categoryId) throws SQLException{
        String query = "SELECT *" + "FROM CATEGORY WHERE id = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, categoryId);

            return getCategory(categoryId, stmt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Category getCategory(Long categoryId, PreparedStatement stmt) throws SQLException {
        try (ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                Category category = new Category.CategoryBuilder()
                        .setName(name)
                        .setDescription(description)
                        .build();

                category.setId(id);

                return category;

            } else {
                throw new SQLException("Category not found for id: " + categoryId);
            }
        }catch(SQLException e){
            log.error("SQL Exception in IngredientDatabase, method getCategoryById ");
            throw new SQLException(e);
        }
    }

    public static void insertNewIngredientToDatabase(Ingredient ingredient)
            throws SQLException, IOException
    {
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO INGREDIENT (NAME, CATEGORY_ID, KCAL, PREPARATION_METHOD) VALUES(?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, ingredient.getName());
            stmt.setLong(2,ingredient.getCategory().getId());
            stmt.setBigDecimal(3, ingredient.getKcal());
            stmt.setString(4, ingredient.getPreparationMethod());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ingredient.setId(generatedKeys.getLong(1));
                }
            }
        }
        finally {activeConnectionWithDatabase = false;}
    }

    public static boolean isActiveConnectionWithDatabase() {
        return activeConnectionWithDatabase;
    }

    private IngredientDatabase() {
        throw new IllegalStateException("Utility class");
    }
}
