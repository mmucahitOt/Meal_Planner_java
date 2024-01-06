package mealplanner.database.meal;

import mealplanner.InputTransformer;
import mealplanner.database.ingredient.IngredientDao;
import mealplanner.database.ingredient.IngredientDaoImpl;
import mealplanner.meal.Category;
import mealplanner.meal.Meal;
import mealplanner.meal.Meals;

import java.sql.*;
import java.util.ArrayList;

public class MealDaoImpl implements MealDao {

    Connection connection;
    @Override
    public void initConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS meals (\n" +
                    "\tmeal_id INT UNIQUE,\n" +
                    "\tmeal VARCHAR,\n" +
                    "\tcategory VARCHAR\n" +
                    ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insertMeal(Meal meal) {
        String insert = "INSERT INTO meals (meal_id, meal, category) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(3, meal.category.getName());
            preparedStatement.setString(2, meal.getName());
            preparedStatement.setInt(1, meal.mealId);
            preparedStatement.executeUpdate();

            return meal.mealId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Meal getMealById(int mealId, IngredientDaoImpl ingredientDao) {
        String query = "select meal_id, meal, category from meals where meal_id = " + mealId + ";";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()){
                Meal newMeal;
                String mealName = rs.getString("meal");
                String categoryName = rs.getString("category");
                newMeal = new Meal(mealId, mealName, Category.convertStringToMealCategory(categoryName));
                newMeal.setIngredient(ingredientDao.getIngredientsByMealId(newMeal));
                return newMeal;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Meal getMealByNameAndCategory(String category, String name, IngredientDaoImpl ingredientDao) {
        String query = "select meal_id, meal, category from meals where meal = '" + name + "' and category = '" + category + "' order by meal asc;";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                int mealId = rs.getInt("meal_id");
                String mealName = rs.getString("meal");
                String categoryName = rs.getString("category");
                Meal  newMeal = new Meal(mealId, mealName, Category.convertStringToMealCategory(categoryName));
                newMeal.setIngredient(ingredientDao.getIngredientsByMealId(newMeal));
                return newMeal;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Meals getMealsByCategory(InputTransformer transformer, Category category, IngredientDao ingredientDao) {
        Meals meals = new Meals();
        String query = "select meal_id, meal, category from meals;";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            meals.setMeals(new ArrayList<>());
            while (rs.next()) {
                int mealId = rs.getInt("meal_id");
                String mealName = rs.getString("meal");
                String categoryName = rs.getString("category");
                Meal newMeal = new Meal(mealId, mealName, Category.convertStringToMealCategory(categoryName));
                newMeal.setIngredient(ingredientDao.getIngredientsByMealId(newMeal));

                if (newMeal.category.equals(category)) {
                    meals.addMeal(newMeal);
                }
            }
            return meals;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Meals getMealsByCategoryAsc(InputTransformer transformer, Category category, IngredientDao ingredientDao) {
        Meals meals = new Meals();
        String query = "select meal_id, meal, category from meals order by meal asc;";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            meals.setMeals(new ArrayList<>());
            while (rs.next()) {
                int mealId = rs.getInt("meal_id");
                String mealName = rs.getString("meal");
                String categoryName = rs.getString("category");
                Meal newMeal = new Meal(mealId, mealName, Category.convertStringToMealCategory(categoryName));
                newMeal.setIngredient(ingredientDao.getIngredientsByMealId(newMeal));

                if (newMeal.category.equals(category)) {
                    meals.addMeal(newMeal);
                }
            }
            return meals;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
