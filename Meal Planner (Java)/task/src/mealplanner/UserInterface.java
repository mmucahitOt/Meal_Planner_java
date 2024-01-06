package mealplanner;

import mealplanner.database.DBClient;
import mealplanner.database.DaoConnection;
import mealplanner.database.IdManager;
import mealplanner.database.dailyPlan.PlanDaoImpl;
import mealplanner.database.ingredient.IngredientDaoImpl;
import mealplanner.database.meal.MealDaoImpl;
import mealplanner.fileManager.FileManager;
import mealplanner.ingredient.Ingredient;
import mealplanner.meal.Meal;
import mealplanner.meal.Category;
import mealplanner.meal.Meals;
import mealplanner.menu.Command;
import mealplanner.menu.WeekDay;
import mealplanner.plan.Plan;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInterface {
    public static DBClient client;

    private static IngredientDaoImpl ingredientDao;
    private static MealDaoImpl mealDao;
    private static PlanDaoImpl planDao;
    static IdManager idManager;

    public static void serializeIdManager() {
        try {
            IdManager.serialize(idManager);
        } catch (IOException e) {
            System.out.println("Serialization is not succesfull");
        }
    }
    public static void init() {
        ingredientDao = new IngredientDaoImpl();
        mealDao = new MealDaoImpl();
        planDao = new PlanDaoImpl();
        try {
            client = new DBClient(new DaoConnection[]{ingredientDao, mealDao, planDao});
            idManager = IdManager.deserialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException | ClassNotFoundException e) {
            idManager = new IdManager();
        }
    }
    static Meals meals = new Meals();

    static Command command;
    static Scanner scanner = new Scanner(System.in);
    static InputTransformer inputTransformer = new InputTransformer();

    public UserInterface() throws SQLException {
    }

    public static void requestCommand() {
        System.out.println("What would you like to do (add, show, plan, save, exit)?");

        String commandInput = scanner.nextLine();
        command = inputTransformer.toCommand(commandInput);
    }

    public static void addMeal() throws SQLException {

        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");

        Category category;
        while (true) {
            try {
                String categoryInput = scanner.nextLine();
                category = inputTransformer.toCategory(categoryInput);
                break;
            } catch (InputMismatchException exception) {
                if (exception.getMessage().equals(Category.errorMessage)) {
                    System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                }
            }
        }

        System.out.println("Input the meal's name:");
        Meal meal;
        while (true) {
            try {
                String mealName = scanner.nextLine();
                Pattern javaPattern = Pattern.compile("^([a-z]+[ ]?)*[a-z]+$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = javaPattern.matcher(mealName);
                if (!matcher.find()) {
                    throw new InputMismatchException();
                }
                meal = new Meal(idManager.getMealId(), mealName, category);
                break;
            } catch (InputMismatchException exception) {
                System.out.println("Wrong format. Use letters only!");
            }
        }

        System.out.println("Input the ingredients:");
        String[] ingredients;
        while (true) {
            try {
                String ingredientsInput = scanner.nextLine();
                Pattern javaPattern = Pattern.compile("^(([a-z]+[ ]?)*[a-z]+[,][ ]?)*(([a-z]+[ ]?)*[a-z]+)+$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = javaPattern.matcher(ingredientsInput);
                if (ingredientsInput.isEmpty() | ingredientsInput.isBlank() | !matcher.find()) {
                    throw new InputMismatchException();
                }
                ingredients = inputTransformer.toArrayOfIngredients(ingredientsInput);
                for (String ingredient: ingredients) {
                    if (!ingredient.isEmpty()) {
                        Ingredient newIngredient = new Ingredient(meal.mealId, idManager.getIngredientId(), ingredient);
                        meal.addIngredient(newIngredient);
                        ingredientDao.insertIngredient(newIngredient);
                    }
                }
                break;
            } catch (InputMismatchException exception) {
                System.out.println("Wrong format. Use letters only!");
            }
        }


        //meals.addMeal(meal);
        int mealId = mealDao.insertMeal(meal);
        System.out.println("The meal has been added!");
    }


    public static void showMealsByCategory() {
        System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
        Category category;
        while (true) {
            try {
                String categoryInput = scanner.nextLine();
                category = inputTransformer.toCategory(categoryInput);
                break;
            } catch (InputMismatchException exception) {
                if (exception.getMessage().equals(Category.errorMessage)) {
                    System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                }
            }
        }

        meals = mealDao.getMealsByCategory(inputTransformer, category, ingredientDao);
        if (meals.isEmpty()) {
            System.out.println("No meals found.");
            return;
        }
        System.out.println("Category: " + category.getName());
        System.out.print(meals);
    }

    public static void planWeek() throws SQLException {
        WeekDay[] weekDays = WeekDay.weekDays();
        Category[] categories = Category.mealCategories();
        Plan plan = new Plan();
        for (WeekDay day: weekDays) {
            System.out.println(day);
            for (Category category: categories) {
                String mealName;
                Meals meals;
                meals = mealDao.getMealsByCategoryAsc(inputTransformer, category, ingredientDao);
                if (meals.isEmpty()) {
                    System.out.println(meals);
                    return;
                }
                System.out.println(meals.getMealNames());
                System.out.println("Choose the " + category + " for " + day + " from the list above:");
                while (true) {
                    mealName = scanner.nextLine();
                    Meal meal = mealDao.getMealByNameAndCategory(category.getName(), mealName, ingredientDao);
                    if (meal == null) {
                        System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
                        continue;
                    };
                    plan.setMeal(day, category, meal);
                    break;
                }
            }
            System.out.println("Yeah! We planned the meals for " + day + ".");
        }
        System.out.println();
        planDao.deletePlan();
        planDao.insertPlan(plan);
        System.out.println(plan);
    }

    private static void requestMealsByTime() {}




    public static void exit() throws SQLException {

        System.out.println("Bye!");
        serializeIdManager();
        UserInterface.client.closeConnection();
        System.exit(0);
    }

    public static void savePlan() {
        Plan plan = planDao.getPlan(mealDao, ingredientDao);
        if (plan.isEmpty()) {
            System.out.println("Unable to save. Plan your meals first.");
            return;
        }
        System.out.println("Input a filename:");
        String filename = scanner.nextLine();
        String content = plan.prepareShoppingList(inputTransformer);
        FileManager fileManager = new FileManager();
        fileManager.setFilename(filename);
        fileManager.writeFile(content);
        System.out.println("Saved!");
    }


}
