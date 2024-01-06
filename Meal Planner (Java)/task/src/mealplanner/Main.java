package mealplanner;

import java.sql.SQLException;
import java.util.InputMismatchException;

import static java.lang.System.exit;

public class Main {
  public static void main(String[] args) {
    UserInterface.init();
    //UserInterface.client.emptyDb();
    while (true) {
      try {
        UserInterface.requestCommand();
        switch (UserInterface.command) {
          case ADD:
            UserInterface.addMeal();
            break;
          case SHOW:
            UserInterface.showMealsByCategory();
            break;
          case PLAN:
            UserInterface.planWeek();
            break;
          case SAVE:
            UserInterface.savePlan();
            break;
          case EXIT:
            UserInterface.exit();
        }
      }
      catch (InputMismatchException exception) {
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
