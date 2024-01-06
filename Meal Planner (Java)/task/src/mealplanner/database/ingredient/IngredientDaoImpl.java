package mealplanner.database.ingredient;

import mealplanner.ingredient.Ingredient;
import mealplanner.meal.Meal;
import mealplanner.meal.Meals;

import java.sql.*;
import java.util.ArrayList;

public class IngredientDaoImpl implements IngredientDao {
    Connection connection;

    @Override
    public void initConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ingredients (\n" +
                    "\tmeal_id INT,\n" +
                    "\tingredient_id INT UNIQUE,\n" +
                    "\tingredient VARCHAR\n" +
                    ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Ingredient> getIngredientsByMealId(Meal meal) {
        String query = "select meal_id, ingredient_id, ingredient from ingredients where meal_id = " + meal.mealId+ ";";
        try (Statement statement = connection.createStatement()) {
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int mealId = rs.getInt("meal_id");
                int ingredient_id = rs.getInt("ingredient_id");
                String ingredient = rs.getString("ingredient");
                ingredients.add(new Ingredient(mealId, ingredient_id, ingredient));
            }
            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertIngredient(Ingredient ingredient) {
        String insert = "INSERT INTO ingredients (meal_id, ingredient_id, ingredient) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(3, ingredient.ingredient);
            preparedStatement.setInt(2, ingredient.ingredient_id);
            preparedStatement.setInt(1, ingredient.meal_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
