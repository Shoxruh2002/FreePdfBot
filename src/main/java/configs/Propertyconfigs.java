package configs;

import repositories.UserRepository;

import java.io.*;
import java.util.Properties;

/**
 * @author Bekpulatov Shoxruh, Sun 1:21 PM. 12/19/2021
 */
public class Propertyconfigs {
    private static Properties p;
    private static Properties en = new Properties();
    private static Properties uz = new Properties();
    private static Properties ru = new Properties();

    static {
        try (FileReader fileReader = new FileReader("src/main/resources/application.properties")) {
            p = new Properties();
            p.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return p.getProperty(key);
    }
}
