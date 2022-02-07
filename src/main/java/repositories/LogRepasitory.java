package repositories;

import configs.Propertyconfigs;

import java.sql.*;

/**
 * @author Bekpulatov Shoxruh, Sun 10:13 PM. 12/19/2021
 */
public class LogRepasitory extends AbstractRepository {
    private static final LogRepasitory instance = new LogRepasitory();

    public void save(String data) {
        query.append("insert into Log (log) values ( '").append(data).append("');");
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()){
            preparedStatement.execute(query.toString());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static LogRepasitory
    getInstance() {
        return instance;
    }
}
