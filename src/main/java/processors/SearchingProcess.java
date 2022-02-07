package processors;

import FreePDFbot.FreePDF;
import dtos.BookDto;
import emojis.Emojis;
import enums.Criteria;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bekpulatov Shoxruh, Thu 4:22 PM. 12/23/2021
 */
@Getter
@Setter
public class SearchingProcess {
    private static final SearchingProcess instance = new SearchingProcess();
    private static BookRepository repository = BookRepository.getInstance();
    private static final FreePDF BOT = FreePDF.getInstance();

    public static SearchingProcess getInstance() {
        return instance;
    }

    public void search(String chatId) {
        BookRepository.getInstance().SessionCriteriaChange(chatId, null);
        repository.setOFFSET(0);
        SendMessage sendMessage = new SendMessage(chatId, "Send Book Title");
        BOT.executeMSG(sendMessage);
    }

    public void searching(String chatId, String text) {
        repository.searchingTitleChange(chatId, text);
        List<BookDto> books = repository.search(text);
        sendingBooksByTitle(chatId, books);
    }

    public void sendingBooksByTitle(String chatId, List<BookDto> books) {
        SendMessage sendMessage = new SendMessage(chatId, "Books List");
        if (books.size() > 0) {
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> list = new ArrayList<>();
            List<InlineKeyboardButton> row = new ArrayList<>();

            int i = 1;
            for (BookDto book : books) {
                InlineKeyboardButton btn = new InlineKeyboardButton("" + i++);
                btn.setCallbackData(book.getId());
                row.add(btn);
            }

            List<InlineKeyboardButton> row2 = new ArrayList<>();
            InlineKeyboardButton left = new InlineKeyboardButton(Emojis.LEFT);
            left.setCallbackData(Emojis.LEFT);
            row2.add(left);
            InlineKeyboardButton cross = new InlineKeyboardButton(Emojis.CROSS);
            cross.setCallbackData(Emojis.CROSS);
            row2.add(cross);
            if (i == 6) {
                InlineKeyboardButton right = new InlineKeyboardButton(Emojis.RIGHT);
                right.setCallbackData(Emojis.RIGHT);
                row2.add(right);
            }

            list.add(row);
            list.add(row2);
            keyboard.setKeyboard(list);

            i = 1;
            for (BookDto book : books) {
                sendMessage.setText(sendMessage.getText() + "\n" + i++ + ". " + book.getTitle());
            }

            sendMessage.setReplyMarkup(keyboard);

        } else sendMessage.setText("Book not found!!!");
        BOT.executeMSG(sendMessage);

    }

    public void searching(String chatId, Criteria criteria) {
        List<BookDto> books = repository.search(criteria);
        sendingBooksByCriteria(chatId, books);
    }

    public void searchMyBooks(String chatId) {
        List<BookDto> books = repository.searchMyBooks(chatId);
        SendMyBooks(chatId, books);
    }

    private void SendMyBooks(String chatId, List<BookDto> books) {
        SendMessage sendMessage = new SendMessage(chatId, "Books List");
        if (books.size() > 0) {
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> list = new ArrayList<>();
            List<InlineKeyboardButton> row = new ArrayList<>();

            int i = 1;
            for (BookDto book : books) {
                InlineKeyboardButton btn = new InlineKeyboardButton("" + i++);
                btn.setCallbackData(book.getId());
                row.add(btn);
            }

            List<InlineKeyboardButton> row2 = new ArrayList<>();
            InlineKeyboardButton left = new InlineKeyboardButton(Emojis.LEFT);
            left.setCallbackData(Emojis.LEFT + "MyBooks");
            row2.add(left);
            InlineKeyboardButton cross = new InlineKeyboardButton(Emojis.CROSS);
            cross.setCallbackData(Emojis.CROSS);
            row2.add(cross);
            if (i == 6) {
                InlineKeyboardButton right = new InlineKeyboardButton(Emojis.RIGHT);
                right.setCallbackData(Emojis.RIGHT + "MyBooks");
                row2.add(right);
            }

            list.add(row);
            list.add(row2);
            keyboard.setKeyboard(list);

            i = 1;
            for (BookDto book : books) {
                sendMessage.setText(sendMessage.getText() + "\n" + i++ + ". " + book.getTitle());
            }
            sendMessage.setReplyMarkup(keyboard);
        } else sendMessage.setText("Book not found!!!");
        BOT.executeMSG(sendMessage);
    }

    private void sendingBooksByCriteria(String chatId, List<BookDto> books) {
        SendMessage sendMessage = new SendMessage(chatId, "Books List");
        if (books.size() > 0) {
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> list = new ArrayList<>();
            List<InlineKeyboardButton> row = new ArrayList<>();

            int i = 1;
            for (BookDto book : books) {
                InlineKeyboardButton btn = new InlineKeyboardButton("" + i++);
                btn.setCallbackData(book.getId());
                row.add(btn);
            }
            List<InlineKeyboardButton> row2 = new ArrayList<>();
            InlineKeyboardButton left = new InlineKeyboardButton(Emojis.LEFT);
            left.setCallbackData(Emojis.LEFT + "Show");
            row2.add(left);
            InlineKeyboardButton cross = new InlineKeyboardButton(Emojis.CROSS);
            cross.setCallbackData(Emojis.CROSS);
            row2.add(cross);
            if (i == 6) {
                InlineKeyboardButton right = new InlineKeyboardButton(Emojis.RIGHT);
                right.setCallbackData(Emojis.RIGHT + "Show");
                row2.add(right);
            }

            list.add(row);
            list.add(row2);
            keyboard.setKeyboard(list);

            i = 1;
            for (BookDto book : books) {
                sendMessage.setText(sendMessage.getText() + "\n" + i++ + ". " + book.getTitle());
            }
            sendMessage.setReplyMarkup(keyboard);
        } else sendMessage.setText("Book not found!!!");
        BOT.executeMSG(sendMessage);
    }

    public boolean checkBookSearching(String chatId, String data) {
        String fileId = repository.findBookById(data);
        if (fileId == null) return false;
        SendDocument sendDocument = new SendDocument(chatId, new InputFile(fileId));
        BOT.executeMSG(sendDocument);
        return true;
    }
}
