package repositories;

import FreePDFbot.FreePDF;
import configs.Propertyconfigs;
import emojis.Emojis;
import entities.User;
import enums.Language;
import enums.UserState;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.*;
import java.util.Objects;

import static configs.State.userState;

/**
 * @author Bekpulatov Shoxruh, Tue 4:06 PM. 12/21/2021
 */
public class UserRepository extends AbstractRepository {
    private static final UserRepository instance = new UserRepository();

    public static UserRepository getInstance() {
        return instance;
    }

    public void createUser(String chatId, User u) {
        query = new StringBuilder("insert into User" +
                " ( id , createdAt , role , chatId , fullname , age , gender , phonenumber , " +
                ("language , state , searchingIsWorking , sessionCriteria , searchingTitle, showingCriteria) " +
                        " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?);"));
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
            preparedStatement.setLong(1, u.getId());
            preparedStatement.setString(2, u.getCreatedAt());
            preparedStatement.setString(3, "User");
            preparedStatement.setString(4, chatId);
            preparedStatement.setString(5, u.getFullName());
            preparedStatement.setInt(6, u.getAge());
            preparedStatement.setString(7, u.getGender());
            preparedStatement.setString(8, u.getPhoneNumber());
            preparedStatement.setString(9, u.getLanguage());
            preparedStatement.setString(10, UserState.AUTHORIZED.toString());
            preparedStatement.setString(11, "0");
            preparedStatement.setString(12, "null");
            preparedStatement.setString(12, "null");
            preparedStatement.setString(14, "null");
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public UserState getUserState(String chatId) {
        var obj = new Object() {
            UserState state = null;
        };
        userState.forEach((K, V) -> {
            if (K.equals(chatId)) {
                obj.state = V.getState();
            }
        });
        return obj.state;
    }

    public UserState getUserStateFromDb(String chatId) {
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            query = new StringBuilder("select t.* from User t where t.chatId = '%s';".formatted(chatId));
            ResultSet resultSet = preparedStatement.executeQuery(query.toString());
            if (Objects.isNull(resultSet)) return null;
            if (resultSet.next()) {
                return UserState.getUserStateName(resultSet.getString("state"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setUserState(String chatid, UserState state) {
        if (Objects.isNull(userState.get(chatid))) {
            userState.put(chatid, new User());
        }
        User user = userState.get(chatid);
        user.setState(state);
        userState.put(chatid, user);
    }

    public void changeUserLanguage(String chatid, Language language) {
        User user = userState.get(chatid);
        user.setLanguage(language.toString());
        userState.put(chatid, user);
    }

    public void changeUserAge(String chatid, String age) {
        User user = userState.get(chatid);
        user.setAge(Integer.parseInt(age));
        userState.put(chatid, user);
    }

    public void changeUserGender(String chatid, String gender) {
        User user = userState.get(chatid);
        user.setGender(gender);
        userState.put(chatid, user);
    }

    public void changeUserFullname(String chatid, String text) {
        User user = userState.get(chatid);
        user.setFullName(text);
        userState.put(chatid, user);
    }

    public void changePhoneNumber(String chatId, String phone) {
        User user = userState.get(chatId);
        user.setPhoneNumber(phone);
        userState.put(chatId, user);
    }

    public String updateFullName(String chatId) {
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            query = new StringBuilder("select t.* from User t where t.chatId = '%s';".formatted(chatId));
            ResultSet resultSet = preparedStatement.executeQuery(query.toString());
            if (Objects.isNull(resultSet)) return null;
            if (resultSet.next()) {
                return resultSet.getString("fullname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changeUserStateFromDB(String chatId,UserState state) {
        query = new StringBuilder("update User set state = '" + state + "' where chatId = '");
        query.append(chatId);
        query.append("' ; ");
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            preparedStatement.execute(query.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public SendMessage updateFullNameContinue(String chatId, Message message) {
        if (message.hasText()) {
            query = new StringBuilder("update User set fullname = '" + message.getText() + "' where chatId = '");
            query.append(chatId);
            query.append("' ; ");
            try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
                 Statement preparedStatement = connection.createStatement()) {
                preparedStatement.execute(query.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return new SendMessage(chatId,"Your new full name: "  + message.getText());
        }
            return new SendMessage(chatId,"Wrong data");
    }

    public String updateAge(String chatId) {
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            query = new StringBuilder("select t.* from User t where t.chatId = '%s';".formatted(chatId));
            ResultSet resultSet = preparedStatement.executeQuery(query.toString());
            if (Objects.isNull(resultSet)) return null;
            if (resultSet.next()) {
                return resultSet.getString("age");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SendMessage updateAgeContinue(String chatId, Message message) {
        String text = message.getText();

        if (message.hasText() && StringUtils.isNumeric(text)) {
            query = new StringBuilder("update User set age = '" + message.getText() + "' where chatId = '");
            query.append(chatId);
            query.append("' ; ");
            try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
                 Statement preparedStatement = connection.createStatement()) {
                preparedStatement.execute(query.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return new SendMessage(chatId,"New age: "  + text);
        }
        return new SendMessage(chatId, "Invalid number\nPlease enter your age with numbers" + Emojis.WRONG + Emojis.WRONG);

    }

    public SendMessage updateLanguageContinue(String chatId, Message message) {
        return null;
    }

    public String updateLanguage(String chatId) {
        SendMessage sendMessage1 = new SendMessage(chatId, "Old age: " + UserRepository.getInstance().updateAge(chatId) + "\nEnter new Age: ");
        FreePDF.getInstance().executeMSG(sendMessage1);
        return null;
    }

}
