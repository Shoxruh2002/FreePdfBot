package services;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import repositories.AbstractRepository;
import repositories.LogRepasitory;

/**
 * @author Bekpulatov Shoxruh, Sun 1:49 PM. 12/19/2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogService {
    private static final LogService instance = new LogService();


    public void logWriter(String data) {
        LogRepasitory.getInstance().save(data);
    }


    public static LogService getInstance() {
        return instance;
    }

}
