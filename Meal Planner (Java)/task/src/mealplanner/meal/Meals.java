package mealplanner.meal;

import java.util.ArrayList;

public class Meals {
    private ArrayList<Meal> meals = new ArrayList<>();

    public void  addMeal(Meal meal) {
        meals.add(meal);
    }

    public void  setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    public boolean isEmpty() {
        return this.meals.isEmpty();
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (meals.size() > 1) {
            stringBuilder.append("\n");
        }
        if (this.meals.isEmpty()) {
            return "No meals saved. Add a meal first.\n";
        }
        for (Meal meal: meals) {
            if (meals.indexOf(meal) == meals.size() - 1) {
                stringBuilder.append(meal.toString());
                break;
            }
            stringBuilder.append(meal.toString()).append("\n");
        }
        if (meals.size() > 1) {
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getMealNames() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Meal meal: meals) {
            if (meals.indexOf(meal) == meals.size() - 1) {
                stringBuilder.append(meal.getName());
                break;
            }
            stringBuilder.append(meal.getName()).append("\n");
        }
        return stringBuilder.toString();
    }

    public Meal getMeal(int index) {
        return this.meals.get(index);
    }
}
