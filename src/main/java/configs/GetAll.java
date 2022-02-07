package configs;

import entities.Book;
import entities.User;
import enums.UserState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Botirov Najmiddin, Tue 12:24. 04/01/2022
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class GetAll {
    private static final GetAll instance = new GetAll();
    private static Connection connection;

    private Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Map<String, String> getAllState() {
        getConnection();
        Map<String, String> state = new HashMap<>();
        try (Statement statement = connection.createStatement()) {
            String sql = "select * from user";

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                state.put(resultSet.getString("chatId"), resultSet.getString("state"));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return state;
    }

    public static GetAll getInstance() {
        return instance;
    }

    public Map<String, User> getAll() {
        getConnection();
        Map<String, User> state = new HashMap<>();
        try (Statement statement = connection.createStatement()) {
            String sql = "select * from User";

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setCreatedAt(resultSet.getString("createdAt"));
                user.setFullName(resultSet.getString("fullname"));
                user.setAge(Integer.parseInt(resultSet.getString("age")));
                user.setGender(resultSet.getString("gender"));
                user.setPhoneNumber(resultSet.getString("phonenumber"));
                user.setLanguage(resultSet.getString("language"));
                user.setRole(resultSet.getString("role"));
                user.setState(UserState.valueOf(resultSet.getString("state")));
                user.setChatId(resultSet.getString("chatId"));
                state.put(resultSet.getString("chatId"), user);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return state;
    }


    public static void updates(String chatId, String data) {
        try (Statement statement = connection.createStatement()) {
            String sql = "update user set language = '%s' where chatId = '%s'".formatted(data, chatId);
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
