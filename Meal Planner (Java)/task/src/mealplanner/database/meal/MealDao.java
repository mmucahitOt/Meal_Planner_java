package mealplanner.database.meal;

import mealplanner.InputTransformer;
import mealplanner.database.DaoConnection;
import mealplanner.database.ingredient.IngredientDao;
import mealplanner.database.ingredient.IngredientDaoImpl;
import mealplanner.meal.Category;
import mealplanner.meal.Meal;
import mealplanner.meal.Meals;

import java.sql.Connection;

public interface MealDao extends DaoConnection {

    void initConnection(Connection connection);
    int insertMeal(Meal meal);

    public Meal getMealById(int mealId, IngredientDaoImpl ingredientDao);
    public Meals getMealsByCategory(InputTransformer transformer, Category category, IngredientDao ingredientDao);

}
