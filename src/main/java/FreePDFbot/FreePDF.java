package FreePDFbot;

import configs.GetAll;
import entities.User;
import handlers.UpdateHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.LogService;
import utils.BaseUtils;

import java.util.Map;


/**
 * @author Bekpulatov Shoxruh, Sat 2:34 PM. 12/18/2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FreePDF extends TelegramLongPollingBot {
    private static final FreePDFbot.FreePDF instance = new FreePDF();

    @Override
    public String getBotToken() {
        return "5069155233:AAE2VnxPihXwkTR_3CTzRZ2cucNYjxxazfM";
    }

    @Override
    public void onUpdateReceived(Update update) {
        LogService.getInstance().logWriter(BaseUtils.toString(update));
        UpdateHandler.getInstance().handle(update);
    }

    @Override
    public String getBotUsername() {
        return "@freePdfLibrarybot";
    }

    public void executeMSG(BotApiMethod<?> message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeMSG(SendPhoto photo) {
        try {
            execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void executeMSG(SendDocument photo) {
        try {
            execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static FreePDFbot.FreePDF getInstance() {

        return instance;
    }
}
