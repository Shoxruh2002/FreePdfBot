package configs;

import entities.User;
import enums.UserState;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bekpulatov Shoxruh, Sun 1:21 PM. 12/19/2021
 */
public class State {
    public static final Map<String, User> userState = new HashMap<>();

    public synchronized static void setUser(String chatId, User user) {
        userState.put(chatId, user);
    }

    public static User getUser(String chatId) {
        return userState.get(chatId);
    }
}
