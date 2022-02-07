package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Bekpulatov Shoxruh, Sun 3:07 PM. 12/19/2021
 */
public class BaseUtils {
    public static String toString(Object obj) {
        return gson().toJson(obj);
    }

    public static Long generateUniqueId(){
        return System.currentTimeMillis();
    }


    public static Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
