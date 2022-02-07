package handlers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Bekpulatov Shoxruh, Sun 1:37 PM. 12/19/2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateHandler {
    private static final UpdateHandler instance = new UpdateHandler();

    public void handle(Update update) {
        if (update.hasCallbackQuery()) CallBackHandler.getInstance().handle(update.getCallbackQuery());
        else if (update.hasMessage()) MessageHandler.getInstance().handle(update.getMessage());
        else System.out.println("Not found");
    }


    public static UpdateHandler getInstance() {
        return instance;
    }
}
