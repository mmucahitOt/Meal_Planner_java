package mealplanner.database.dailyPlan;

import mealplanner.database.DaoConnection;
import mealplanner.meal.Category;
import mealplanner.meal.Meal;
import mealplanner.menu.WeekDay;
import mealplanner.plan.Plan;

public interface PlanDao extends DaoConnection {
    void insertDailyPlan(WeekDay day, Category category, Meal meal);
    void insertPlan(Plan plan);
}
