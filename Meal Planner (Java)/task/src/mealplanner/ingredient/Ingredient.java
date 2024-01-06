package mealplanner.ingredient;

public class Ingredient {
    public int meal_id;
    public int ingredient_id;
    public String ingredient;

    public Ingredient(int mealId, int ingredientId, String ingredient) {
        this.meal_id = mealId;
        this.ingredient_id = ingredientId;
        this.ingredient = ingredient;
    }


    @Override
    public String toString() {

        return ingredient;
    }

}
