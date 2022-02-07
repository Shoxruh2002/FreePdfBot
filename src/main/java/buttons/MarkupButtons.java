package buttons;

import org.checkerframework.checker.units.qual.K;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;

/**
 * @author Bekpulatov Shoxruh, Sun 1:21 PM. 12/19/2021
 */
public class MarkupButtons {
    public static ReplyKeyboard shareContact() {
        ReplyKeyboardMarkup board = new ReplyKeyboardMarkup();
        KeyboardButton phone = new KeyboardButton("Share My contact");
        phone.setRequestContact(true);
        board.setSelective(true);
        board.setResizeKeyboard(true);
        KeyboardRow row = new KeyboardRow();
        row.add(phone);
        board.setKeyboard(List.of(row));
        return board;
    }
}
