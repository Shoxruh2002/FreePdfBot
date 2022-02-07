import FreePDFbot.FreePDF;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @author Bekpulatov Shoxruh, Sun 1:12 PM. 12/19/2021
 */
public class Application {
    public static void main(String[] args) {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(FreePDF.getInstance());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
