package processors;

import FreePDFbot.FreePDF;
import buttons.ReplyKeyboardButtons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * @author Bekpulatov Shoxruh, Thu 4:37 PM. 12/23/2021
 */
public class SettingsProcess {
    private static final SettingsProcess instance = new SettingsProcess();
    public void settings(String chatId) {

    }

    public static SettingsProcess getInstance() {
        return instance;
    }

}
