package handlers;

import FreePDFbot.FreePDF;
import buttons.ReplyKeyboardButtons;
import emojis.Emojis;
import enums.UserState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import processors.*;
import repositories.BookRepository;
import repositories.UserRepository;

import java.util.Objects;

/**
 * @author Bekpulatov Shoxruh, Sun 1:34 PM. 12/19/2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageHandler {
    private static final MessageHandler instance = new MessageHandler();
    private static final FreePDF BOT = FreePDF.getInstance();
    private static final UserRepository userRepository = UserRepository.getInstance();
    private static final ShowingProcess showingProcess = ShowingProcess.getInstance();

    public void handle(Message message) {
        String chatId = message.getChatId().toString();
        UserState dbState = userRepository.getUserStateFromDb(chatId);
        UserState state;
        if (Objects.isNull(dbState)) {
            state = userRepository.getUserState(chatId);
        } else state = dbState;


        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Choose menu");
        if (state == null || state.getDegree() == 0) {
            AuthorizationProcess.getInstance().process(message, state);
        } else if (state.equals(UserState.AUTHORIZED) && !(message.getText().equals("/settings") || message.getText().equals(Emojis.SETTINGS + "Settings"))) {
            sendMessage.setReplyMarkup(ReplyKeyboardButtons.menuKeyboard());
            sendMessage.setChatId(chatId);
            BOT.executeMSG(sendMessage);
            if (message.hasText()) {
                String data = message.getText();
                if ((Emojis.SEARCH + "Search Book").equals(data) || "/search".equals(data)) {
                    userRepository.changeUserStateFromDB(chatId, UserState.SEARCHING);
                    SearchingProcess.getInstance().search(chatId);
                } else if ((Emojis.ADD + "Add book").equals(data) || "/add".equals(data)) {
                    userRepository.changeUserStateFromDB(chatId, UserState.ADDING);
                    AddingProcess.addBook(message);
                } else if ((Emojis.KABA + "Religious books").equals(data) || "/religious".equals(data)) {
                    showingProcess.religious(chatId);
                } else if ((Emojis.BOOKS + "Art books").equals(data) || "/art".equals(data)) {
                    showingProcess.art(chatId);
                } else if ((Emojis.STUDENT + "Textbooks").equals(data) || "/textbooks".equals(data)) {
                    showingProcess.textbooks(chatId);
                } else if ((Emojis.CODER + "Programming books").equals(data) || "/programming".equals(data)) {
                    showingProcess.programming(chatId);
                } else if ((Emojis.BOOK2 + "My books").equals(data)) {
                    showingProcess.myBook(chatId);
                }
            }
        } else if (state.equals(UserState.AUTHORIZED) && (message.getText().equals("/settings") || message.getText().equals(Emojis.SETTINGS + "Settings"))) {
            sendMessage.setReplyMarkup(ReplyKeyboardButtons.showSettingMenuKeyboard());
            sendMessage.setChatId(chatId);
            BOT.executeMSG(sendMessage);
            userRepository.changeUserStateFromDB(chatId, UserState.SETTINGS);
        } else if (state.equals(UserState.SETTINGS) && message.getText().equals(Emojis.UPDATE + " Update full name")) {
            userRepository.changeUserStateFromDB(chatId, UserState.UPDATE_FULL_NAME);
            SendMessage sendMessage1 = new SendMessage(chatId, "Your old name: " + userRepository.updateFullName(chatId) + "\nEnter new FullName: ");
            BOT.executeMSG(sendMessage1);
        } else if (state.equals(UserState.SETTINGS) && message.getText().equals(Emojis.UPDATE + " Update Age")) {
            userRepository.changeUserStateFromDB(chatId, UserState.UPDATE_AGE);
            SendMessage sendMessage1 = new SendMessage(chatId, "Old age: " + userRepository.updateAge(chatId) + "\nEnter new Age: ");
            BOT.executeMSG(sendMessage1);
        } else if (state.equals(UserState.SETTINGS) && message.getText().equals(Emojis.PHONE + " Contatc admin")) {
            SendMessage sendMessage1 = new SendMessage(chatId, "@TheProgrammerr , @NajmiddinB1113 ,@axmedov_d_99, @MuhammadkomilMurodillayev  are  admins of project, If you have any  questions you can contact with admin;");
            BOT.executeMSG(sendMessage1);
        } else if (state.equals(UserState.SETTINGS) && message.getText().equals(Emojis.UPDATE + " Update language")) {
            userRepository.changeUserStateFromDB(chatId, UserState.UPDATE_LANGUAGE);
            SendMessage sendMessage1 = new SendMessage(chatId, "Old LANGUAGE: " + userRepository.updateLanguage(chatId) + "\nEnter new LANGUAGE: ");
            BOT.executeMSG(sendMessage1);
        } else if (state.equals(UserState.SETTINGS) && message.getText().equals(Emojis.BACK + " Back")) {
            userRepository.changeUserStateFromDB(chatId, UserState.AUTHORIZED);
            sendMessage.setReplyMarkup(ReplyKeyboardButtons.menuKeyboard());
            sendMessage.setChatId(chatId);
            BOT.executeMSG(sendMessage);
        } else if (state.equals(UserState.UPDATE_FULL_NAME)) {
            SendMessage message1 = userRepository.updateFullNameContinue(chatId, message);
            userRepository.changeUserStateFromDB(chatId, UserState.AUTHORIZED);
            BOT.executeMSG(message1);
            sendMessage.setReplyMarkup(ReplyKeyboardButtons.menuKeyboard());
            sendMessage.setChatId(chatId);
            BOT.executeMSG(sendMessage);
        } else if (state.equals(UserState.UPDATE_AGE)) {
            SendMessage message1 = userRepository.updateAgeContinue(chatId, message);
            userRepository.changeUserStateFromDB(chatId, UserState.AUTHORIZED);
            BOT.executeMSG(message1);
            sendMessage.setReplyMarkup(ReplyKeyboardButtons.menuKeyboard());
            sendMessage.setChatId(chatId);
            BOT.executeMSG(sendMessage);
        } else if (state.equals(UserState.UPDATE_LANGUAGE)) {
            SendMessage message1 = userRepository.updateLanguageContinue(chatId, message);
            userRepository.changeUserStateFromDB(chatId, UserState.AUTHORIZED);
            BOT.executeMSG(message1);
            sendMessage.setReplyMarkup(ReplyKeyboardButtons.menuKeyboard());
            sendMessage.setChatId(chatId);
            BOT.executeMSG(sendMessage);
        } else if (state.equals(UserState.SEARCHING)) {
            if (!message.hasText()) {
                SendMessage sendMessage2 = new SendMessage(chatId, "Invalid Book Title" + Emojis.WRONG + Emojis.WRONG + Emojis.WRONG);
                BOT.executeMSG(sendMessage2);
            } else {
                BookRepository.getInstance().setOFFSET(0);
                SearchingProcess.getInstance().searching(chatId, message.getText());
                userRepository.changeUserStateFromDB(chatId, UserState.AUTHORIZED);
            }
        } else if (state.equals(UserState.ADDING)) {
            if (Objects.nonNull(BookRepository.getInstance().SessionCriteria(chatId))) {
                AddingProcess.creatingBook(message);
                userRepository.changeUserStateFromDB(chatId, UserState.AUTHORIZED);
            } else {
                SendMessage sendMessage2 = new SendMessage(chatId, "Something went Wrong");
                BOT.executeMSG(sendMessage2);
            }
        }
    }

    public static MessageHandler getInstance() {
        return instance;
    }


}
