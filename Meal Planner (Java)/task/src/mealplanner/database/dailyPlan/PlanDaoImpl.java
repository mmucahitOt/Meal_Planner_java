package mealplanner.database.dailyPlan;

import mealplanner.database.ingredient.IngredientDaoImpl;
import mealplanner.database.meal.MealDaoImpl;
import mealplanner.meal.Category;
import mealplanner.meal.Meal;
import mealplanner.menu.WeekDay;
import mealplanner.plan.Plan;

import java.sql.*;
import java.util.HashMap;

public class PlanDaoImpl implements PlanDao {
    Connection connection;
    @Override
    public void initConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS plan (\n" +
                    "\tmeal_option VARCHAR,\n" +
                    "\tcategory VARCHAR,\n" +
                    "\tmeal_id INT\n" +
                    ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Plan getDailyPlansByPlan(MealDaoImpl mealDao, IngredientDaoImpl ingredientDao) {
        String query = "select meal_option, category, meal_id from plan order by meal_option asc";
        try (Statement statement = connection.createStatement()) {
            HashMap<WeekDay, HashMap<Category, Meal>> meals = new HashMap<>();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                HashMap<Category, Meal> dailyPlan = new HashMap<>();
                int meal_id = rs.getInt("meal_id");
                String day = rs.getString("meal_option");
                WeekDay weekDay = WeekDay.convertStringToWeekDay(day);
                String ctr = rs.getString("category");
                Category category = Category.convertStringToMealCategory(ctr);
                Meal meal = mealDao.getMealById(meal_id, ingredientDao);
                dailyPlan.put(category, meal);
                meals.put(weekDay, dailyPlan);
            }
            Plan plan = new Plan();
            plan.setMeals(meals);
            return plan;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<Category, Meal> getDailyPlanByDay(String day_, MealDaoImpl mealDao, IngredientDaoImpl ingredientDao) {
        String query = "select meal_option, category, meal_id from plan where meal_option='" + day_ + "';";
        try (Statement statement = connection.createStatement()) {
            HashMap<Category, Meal> dailyPlan = new HashMap<>();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {

                int meal_id = rs.getInt("meal_id");
                String day = rs.getString("meal_option");
                WeekDay weekDay = WeekDay.convertStringToWeekDay(day);
                String ctr = rs.getString("category");
                Category category = Category.convertStringToMealCategory(ctr);
                Meal meal = mealDao.getMealById(meal_id, ingredientDao);
                dailyPlan.put(category, meal);
            }
            return dailyPlan;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Plan getPlan(MealDaoImpl mealDao, IngredientDaoImpl ingredientDao) {
        String query = "select meal_option, category, meal_id from plan order by meal_option asc";
        HashMap<WeekDay, HashMap<Category, Meal>> meals = new HashMap<>();
        for (WeekDay day: WeekDay.weekDays()) {
            HashMap<Category, Meal> dailyPlan = getDailyPlanByDay(day.getName(), mealDao, ingredientDao);
            meals.put(day, dailyPlan);
        }
        Plan plan = new Plan();
        plan.setMeals(meals);
        return plan;
    }
    @Override
    public void insertDailyPlan(WeekDay day, Category category, Meal meal)  {
        String insert = "INSERT INTO plan (meal_option, category, meal_id) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setInt(3, meal.mealId);
            preparedStatement.setString(2, category.getName());
            preparedStatement.setString(1, day.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertPlan(Plan plan) {
        for (WeekDay day: WeekDay.weekDays()) {
            for (Category category: Category.mealCategories()) {
                Meal meal = plan.getMeal(day, category);
                this.insertDailyPlan(day, category, meal);
            }
        }
    }

    public void deletePlan() {
        try (Statement statement = connection.createStatement()) {
            // Statement execution
            statement.executeUpdate("DELETE FROM plan");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
