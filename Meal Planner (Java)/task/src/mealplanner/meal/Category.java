package mealplanner.meal;

import mealplanner.error.ErrorMessage;

import java.util.InputMismatchException;

public enum Category implements ErrorMessage {
    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner");

    private String name;

    public static final String errorMessage = "WrongCategory";
    Category(String name) {
        this.name = name;
    }


    public static Category convertStringToMealCategory(String input) {
        switch (input) {
            case "breakfast":
                return BREAKFAST;
            case "lunch":
                return LUNCH;
            case "dinner":
                return DINNER;
            default:
                throw new InputMismatchException(errorMessage);
        }
    }

    public String getName() {
        return name;
    }

    public static Category[] mealCategories() {
        return new Category[] {BREAKFAST, LUNCH, DINNER};
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
