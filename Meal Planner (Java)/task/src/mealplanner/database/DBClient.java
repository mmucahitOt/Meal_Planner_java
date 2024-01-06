package mealplanner.database;

import mealplanner.InputTransformer;
import mealplanner.database.ingredient.IngredientDaoImpl;
import mealplanner.database.meal.MealDaoImpl;
import mealplanner.meal.Category;
import mealplanner.meal.Meal;
import mealplanner.meal.Meals;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DBClient {
    String DB_URL = "jdbc:postgresql:meals_db";
    String USER = "postgres";
    String PASS = "1111";

    Connection connection;

    public DBClient(DaoConnection[] daos) throws SQLException {

        this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        for (DaoConnection dao: daos) {
            dao.initConnection(connection);
            dao.createTable();
        }
    }


    public void emptyDb() {
        try (Statement statement = connection.createStatement()) {
            // Statement execution
            statement.executeUpdate("drop table if exists meals");
            statement.executeUpdate("drop table if exists ingredients");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws SQLException {
        // Statement creation
        connection.close();
    }
}
