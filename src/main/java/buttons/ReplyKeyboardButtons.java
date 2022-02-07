package buttons;

import emojis.Emojis;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;

/**
 * @author Bekpulatov Shoxruh, Wed 12:17 AM. 12/22/2021
 */
@Getter
@Setter
public class ReplyKeyboardButtons {
    private static ReplyKeyboardMarkup board = new ReplyKeyboardMarkup();
    private static ReplyKeyboardMarkup settingBoard = new ReplyKeyboardMarkup();

    public static ReplyKeyboard menuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton search = new KeyboardButton(Emojis.SEARCH + "Search Book");
        row1.add(search);

        KeyboardRow row2 = new KeyboardRow();
        KeyboardButton addBOOk = new KeyboardButton(Emojis.ADD + "Add book");
        row2.add(addBOOk);

        KeyboardRow row3 = new KeyboardRow();
        KeyboardButton diniy = new KeyboardButton(Emojis.KABA + "Religious books");
        KeyboardButton badiy = new KeyboardButton(Emojis.BOOKS + "Art books");
        row3.add(diniy);
        row3.add(badiy);

        KeyboardRow row4 = new KeyboardRow();
        KeyboardButton darslik = new KeyboardButton(Emojis.STUDENT + "Textbooks");
        KeyboardButton coding = new KeyboardButton(Emojis.CODER + "Programming books");
        row4.add(darslik);
        row4.add(coding);

        KeyboardRow row5 = new KeyboardRow();
//        KeyboardButton topBooks = new KeyboardButton(Emojis.TOP_BOOK + "Top books");
        KeyboardButton myBooks = new KeyboardButton( Emojis.BOOK2+"My books");
//        row5.add(topBooks);
        row5.add(myBooks);

        KeyboardRow row6 = new KeyboardRow();
        KeyboardButton settings = new KeyboardButton(Emojis.SETTINGS + "Settings");
        board.setSelective(true);
        board.setResizeKeyboard(true);
        row6.add(settings);


        board.setKeyboard(Arrays.asList(row1, row2, row5, row3, row4, row6));
        board.setResizeKeyboard(true);
        board.setSelective(true);
        return board;
    }

    public static ReplyKeyboard showSettingMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton changeFullName = new KeyboardButton(Emojis.UPDATE + " Update full name");
        KeyboardButton changeLanguage = new KeyboardButton(Emojis.PHONE + " Contatc admin");
        KeyboardButton changeAge = new KeyboardButton(Emojis.UPDATE + " Update Age");
        KeyboardButton deleteMyBook = new KeyboardButton(Emojis.DELETE + "Delete my book");
        row1.add(changeFullName);
        row1.add(changeLanguage);
        row1.add(changeAge);
//        row1.add(deleteMyBook);
        KeyboardRow row2 = new KeyboardRow();
        KeyboardButton back = new KeyboardButton(Emojis.BACK + " Back");
        row2.add(back);
        board.setKeyboard(Arrays.asList(row1, row2));
        board.setResizeKeyboard(true);
        board.setSelective(true);
        return board;
    }




}
