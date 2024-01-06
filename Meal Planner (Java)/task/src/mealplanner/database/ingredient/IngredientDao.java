package mealplanner.database.ingredient;

import mealplanner.database.DaoConnection;
import mealplanner.ingredient.Ingredient;
import mealplanner.meal.Meal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public interface IngredientDao extends DaoConnection {
    public ArrayList<Ingredient> getIngredientsByMealId(Meal meal);
    public void insertIngredient(Ingredient ingredient);
}
