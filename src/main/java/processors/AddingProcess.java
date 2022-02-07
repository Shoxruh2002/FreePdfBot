package processors;

import FreePDFbot.FreePDF;
import buttons.InlinekeyboardButtons;
import emojis.Emojis;
import enums.UserState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import repositories.BookRepository;
import repositories.UserRepository;

import java.util.Objects;

/**
 * @author Bekpulatov Shoxruh, Thu 4:29 PM. 12/23/2021
 */
public class AddingProcess {
    private static final FreePDF BOT = FreePDF.getInstance();
    private static final UserRepository userRepository = UserRepository.getInstance();
    private static final BookRepository bookRepository = BookRepository.getInstance();


    public static void addBook(Message message) {
        bookRepository.SessionCriteriaChange(message.getChatId().toString(), null);
        String chatId = message.getChatId().toString();
        SendMessage sendMessage = new SendMessage(chatId, "Please choose criteria : ");
        sendMessage.setReplyMarkup(InlinekeyboardButtons.criteria());
        BOT.executeMSG(sendMessage);
    }

    public static void creatingBook(Message message) {
        if (Objects.isNull(bookRepository.SessionCriteria(message.getChatId().toString())) || !message.hasDocument()) {
            SendMessage sendMessage = new SendMessage(message.getChatId().toString(), "Something went wrong" + Emojis.WRONG);
            BOT.executeMSG(sendMessage);
            userRepository.changeUserStateFromDB(message.getChatId().toString(), UserState.AUTHORIZED);
            return;
        }
        bookRepository.create(bookRepository.SessionCriteria(message.getChatId().toString()), message);
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), "Book succesfully added" + Emojis.SUCCESS + Emojis.SUCCESS + Emojis.SUCCESS);
        BOT.executeMSG(sendMessage);
        bookRepository.SessionCriteriaChange(message.getChatId().toString(), null);
    }
}
