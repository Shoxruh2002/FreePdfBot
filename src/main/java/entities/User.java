package entities;

import enums.UserState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Bekpulatov Shoxruh, Sun 1:31 PM. 12/19/2021
 */
@Getter
@Setter
@NoArgsConstructor
public class User extends Auditable {
    private String fullName;
    private Integer age;
    private String gender;
    private String phoneNumber;
    private String language;
    private String role;
    private UserState state;
    private String chatId;

    public User(String role) {
        super();
        this.role = role;
    }
}
