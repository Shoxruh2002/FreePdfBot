package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bekpulatov Shoxruh, Tue 4:09 PM. 12/21/2021
 */
@Getter
@AllArgsConstructor
public enum Language {
    RU("ru"),
    UZ("uz"),
    EN("en");

    private String code;

    public static Language getByCode(String lang) {
        for (Language language : values()) {
            if (language.getCode().equalsIgnoreCase(lang)) return language;
        }
        return null;
    }
}
