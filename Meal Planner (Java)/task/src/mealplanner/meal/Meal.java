package mealplanner.meal;

import mealplanner.ingredient.Ingredient;

import java.util.ArrayList;
import java.util.Objects;

public class Meal {
    public Category category;
    private final String name;

    public int mealId;

    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    public Meal(int mealId,String name, Category category) {
        this.name = name;
        this.category = category;
        this.mealId = mealId;
    }


    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
    public void setIngredient(ArrayList<Ingredient> ingredients) {

        this.ingredients = ingredients;
    }

    public String ingredientToVarChar() {
        StringBuilder str = new StringBuilder();
        for (Ingredient ingredient: this.ingredients) {
            str.append(ingredient).append(" ");
        }
        return str.toString();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Name: ").append(this.name).append("\n");
        str.append("Ingredients:\n");
        for (Ingredient ingredient: this.ingredients) {
            //if (this.ingredients.indexOf(ingredient) == this.ingredients.size() - 1) {
            //    str.append(ingredient);
            //    break;
            //}
            str.append(ingredient).append("\n");
        }
        return str.toString();
    }


    public boolean equals(Category category) {
        return Objects.equals(this.name, category.name());
    }
}
