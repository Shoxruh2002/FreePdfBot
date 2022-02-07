package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bekpulatov Shoxruh, Sun 1:31 PM. 12/19/2021
 */
@Getter
@AllArgsConstructor
public enum UserState {
    ANONYMOUS(0),
    LANGUAGE(0),
    LANGUAGE_ANSWER(0),
    FULL_NAME(0),
    AGE(0),
    GENDER(0),
    PHONE_NUMBER(0),
    DELETE_ALL(0),
    AUTHORIZED(1),
    SEARCHING(1),
    ADDING(1),
    SETTINGS(1),
    UPDATE_FULL_NAME(1),
    UPDATE_AGE(1),
    UPDATE_LANGUAGE(1);

    private int degree;


    public static UserState getUserStateName(String name) {
        for (UserState value : UserState.values()) {
            if (value.name().equalsIgnoreCase(name))
                return value;
        }
        return null;
    }
}
