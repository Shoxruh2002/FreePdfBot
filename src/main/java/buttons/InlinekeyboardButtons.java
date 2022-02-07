package buttons;

import emojis.Emojis;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;

/**
 * @author Bekpulatov Shoxruh, Sun 1:21 PM. 12/19/2021
 */
public class InlinekeyboardButtons {
    private static final InlineKeyboardMarkup board = new InlineKeyboardMarkup();

    private static List<InlineKeyboardButton> getRow(InlineKeyboardButton... buttons) {
        return Arrays.stream(buttons).toList();
    }

    public static ReplyKeyboard LanguageInlineKeyboard() {
        InlineKeyboardButton uz = new InlineKeyboardButton(Emojis.UZ + " Uzbek");
        uz.setCallbackData("uz");

        InlineKeyboardButton ru = new InlineKeyboardButton(Emojis.RU + " Russian");
        ru.setCallbackData("ru");

        InlineKeyboardButton en = new InlineKeyboardButton(Emojis.EN + " English");
        en.setCallbackData("en");
        board.setKeyboard(Arrays.asList(getRow(uz), getRow(ru), getRow(en)));
        return board;
    }

    public static ReplyKeyboard GenderInlineKeyboard(String chatId) {
        InlineKeyboardButton male = new InlineKeyboardButton(Emojis.MALE + "Male");
        male.setCallbackData("male");

        InlineKeyboardButton female = new InlineKeyboardButton(Emojis.FEMALE + "Female");
        female.setCallbackData("female");

        board.setKeyboard(Arrays.asList(getRow(male), getRow(female)));
        return board;
    }

    public static ReplyKeyboard criteria() {
        InlineKeyboardButton religious = new InlineKeyboardButton(Emojis.KABA + "Religious books");
        religious.setCallbackData("religious");

        InlineKeyboardButton art = new InlineKeyboardButton(Emojis.BOOKS + "Art books");
        art.setCallbackData("art");

        InlineKeyboardButton programming = new InlineKeyboardButton(Emojis.CODER + "Programming books");
        programming.setCallbackData("programming");

        InlineKeyboardButton textbooks = new InlineKeyboardButton(Emojis.STUDENT + "Textbooks");
        textbooks.setCallbackData("textbooks");

        board.setKeyboard(Arrays.asList(getRow(religious), getRow(textbooks), getRow(programming), getRow(art)));
        return board;
    }
}
