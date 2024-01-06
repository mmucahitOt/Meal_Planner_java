package mealplanner.plan;

import mealplanner.InputTransformer;
import mealplanner.ingredient.Ingredient;
import mealplanner.meal.Category;
import mealplanner.meal.Meal;
import mealplanner.menu.WeekDay;

import java.util.HashMap;

public class Plan {
    HashMap<WeekDay, HashMap<Category, Meal>> meals;

    public Plan() {
        this.meals = new HashMap<>();
        for (WeekDay day: WeekDay.weekDays()) {
            this.meals.put(day, new HashMap<>());
            for (Category category: Category.mealCategories()) {
                this.meals.get(day).put(category, null);
            }
        }
    }
    public String prepareShoppingList(InputTransformer inputTransformer) {
        HashMap<String, Integer> ingredients = new HashMap<>();
        for (WeekDay day: WeekDay.weekDays()) {
            for (Category category: Category.mealCategories()) {
                Meal meal = this.getMeal(day, category);
                for(Ingredient ingredient: meal.getIngredients()) {
                    String[] ings = inputTransformer.toArrayOfIngredients2(ingredient.ingredient);
                    for (String ing: ings) {
                        if (ingredients.get(ing) == null) {
                            ingredients.put(ing, 1);
                        } else {
                            int previousAmount = ingredients.get(ing);
                            ingredients.put(ing, previousAmount + 1);
                        }
                    }
                }
            }
        }

        StringBuilder str = new StringBuilder();

        for (String ingredient: ingredients.keySet()) {
            int amount = ingredients.get(ingredient);
            if (amount == 1) {
                str.append(ingredient).append("\n");
                continue;
            }
            str.append(ingredient).append(" x").append(amount).append("\n");
        }

        return str.toString();
    }
    public boolean isEmpty() {
        return this.getMeal(WeekDay.MONDAY, Category.LUNCH) == null;
    }

    public void setMeal(WeekDay day, Category category, Meal meal) {
        this.meals.get(day).put(category, meal);
    }

    public void setMeals(HashMap<WeekDay, HashMap<Category, Meal>> meals) {
        this.meals = meals;
    }

     public Meal getMeal(WeekDay day, Category category) {
        return this.meals.get(day).get(category);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (WeekDay day: WeekDay.weekDays()) {
            str.append(day).append("\n");
            for (Category category: Category.mealCategories()) {
                Meal meal = this.getMeal(day, category);
                str.append(category).append(": ").append(meal.getName()).append("\n");
            }
            str.append("\n");
        }
        return str.toString();
    }
}
