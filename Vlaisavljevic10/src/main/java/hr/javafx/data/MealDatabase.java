package hr.javafx.data;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;
import hr.javafx.restaurant.model.Meal;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static hr.javafx.main.Main.log;

public class MealDatabase {

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

    public static Set<Meal> getAllMealsFromDatabase()
        throws SQLException, IOException
    {
        Set<Meal> meals = new HashSet<>();

        try(Connection connection = connectToDatabase();
            Statement sqlStatement = connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery("SELECT *" + "FROM MEAL"))
        {
            while (resultSet.next()) {
                Meal newMeal = getMealFromResultSet(resultSet);
                meals.add(newMeal);
            }
        }
        return meals;
    }


    private static Meal getMealFromResultSet(ResultSet resultSet)
        throws SQLException
    {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Long categoryId = resultSet.getLong("category_id");
        BigDecimal price = resultSet.getBigDecimal("price");

        Category category = getCategoryById(categoryId);
        Set<Ingredient> ingredients = new HashSet<>();

        Meal meal = new Meal(name,category, ingredients, price);
        meal.setId(id);

        return meal;
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
            log.error("SQL Exception in MealDatabase, method getCategoryById ");
            throw new SQLException(e);
        }
    }


    public static void insertNewMealToDatabase(Meal meal)
            throws SQLException, IOException
    {
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO MEAL (NAME, CATEGORY_ID, PRICE) VALUES(?,?,?)",
                     Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, meal.getName());
            stmt.setLong(2,meal.getCategory().getId());
            stmt.setBigDecimal(3, meal.getPrice());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    meal.setId(generatedKeys.getLong(1));
                }
            }
        }
        finally {activeConnectionWithDatabase = false;}
    }

    public static boolean isActiveConnectionWithDatabase() {
        return activeConnectionWithDatabase;
    }

    private MealDatabase() {
        throw new IllegalStateException("Utility class");
    }
}
