package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bekpulatov Shoxruh, Thu 4:02 PM. 12/23/2021
 */
@Getter
@AllArgsConstructor
public enum Criteria {
    RELIGIOUS("religious"),
    PROGRAMMING("programming"),
    ART("art"),
    TEXTBOOKS("textbooks"),
    MY_BOOKS("my_book");
    private String data;

    public static Criteria getCriteriaByData(String data) {
        for (Criteria value : Criteria.values()) {
            if (value.getData().equals(data)) {
                return value;
            }
        }
        return null;
    }
}
