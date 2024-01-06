package mealplanner.database;

import java.sql.Connection;

public interface DaoConnection {
    void initConnection(Connection connection);
    void createTable();
}
