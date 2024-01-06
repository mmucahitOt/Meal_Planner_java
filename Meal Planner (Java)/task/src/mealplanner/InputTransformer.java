package mealplanner;

import mealplanner.meal.Category;
import mealplanner.menu.Command;

public class InputTransformer {
    public Category toCategory(String input) {
        return Category.convertStringToMealCategory(input);
    }

    public String[] toArrayOfIngredients(String input) {
        return input.split(" ,|, ");
    }
    public String[] toArrayOfIngredients2(String input) {
        return input.split(",");
    }

    public Command toCommand(String input) {
        return Command.convertStringToCommand(input);
    }
}
