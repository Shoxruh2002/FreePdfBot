package handlers;

import FreePDFbot.FreePDF;
import buttons.MarkupButtons;
import emojis.Emojis;
import enums.Criteria;
import enums.Language;
import enums.UserState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import processors.SearchingProcess;
import repositories.BookRepository;
import repositories.UserRepository;

import java.util.Objects;

/**
 * @author Bekpulatov Shoxruh, Sun 1:37 PM. 12/19/2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CallBackHandler {
    private static final CallBackHandler instance = new CallBackHandler();
    private static final FreePDF BOT = FreePDF.getInstance();
    private static final BookRepository bookRepository = BookRepository.getInstance();
    private static final UserRepository userRepository = UserRepository.getInstance();
    private static final SearchingProcess searchingProcess = SearchingProcess.getInstance();

    public void handle(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        Message message = callbackQuery.getMessage();
        String chatId = message.getChatId().toString();
        if (StringUtils.isNumeric(data) && searchingProcess.checkBookSearching(chatId, data)) return;
        switch (data) {
            case Emojis.LEFT -> {
                if (bookRepository.getOFFSET() > 4) {
                    DeleteMessage deleteMessage = new DeleteMessage(chatId, callbackQuery.getMessage().getMessageId());
                    BOT.executeMSG(deleteMessage);
                    bookRepository.setOFFSET(bookRepository.getOFFSET() - 5);
                    searchingProcess.searching(chatId, bookRepository.searchingTitle(chatId));
                } else {
                    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(callbackQuery.getId());
                    answerCallbackQuery.setShowAlert(true);
                    answerCallbackQuery.setText("You are already in first page!!!");
                    BOT.executeMSG(answerCallbackQuery);
                }
            }
            case Emojis.LEFT + "MyBooks" -> {
                if (bookRepository.getOFFSET() > 4) {
                    DeleteMessage deleteMessage = new DeleteMessage(chatId, callbackQuery.getMessage().getMessageId());
                    BOT.executeMSG(deleteMessage);
                    bookRepository.setOFFSET(bookRepository.getOFFSET() - 5);
                    searchingProcess.searchMyBooks(chatId);
                } else {
                    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(callbackQuery.getId());
                    answerCallbackQuery.setShowAlert(true);
                    answerCallbackQuery.setText("You are already in first page!!!");
                    BOT.executeMSG(answerCallbackQuery);
                }
            }
            case Emojis.LEFT + "Show" -> {
                if (bookRepository.getOFFSET() > 4) {
                    DeleteMessage deleteMessage = new DeleteMessage(chatId, callbackQuery.getMessage().getMessageId());
                    BOT.executeMSG(deleteMessage);
                    bookRepository.setOFFSET(bookRepository.getOFFSET() - 5);
                    searchingProcess.searching(chatId, Criteria.getCriteriaByData(bookRepository.showingCriteria(chatId)));
                } else {
                    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(callbackQuery.getId());
                    answerCallbackQuery.setShowAlert(true);
                    answerCallbackQuery.setText("You are already in first page!!!");
                    BOT.executeMSG(answerCallbackQuery);
                }
            }
            case Emojis.CROSS -> {
                DeleteMessage deleteMessage = new DeleteMessage(chatId, callbackQuery.getMessage().getMessageId());
                BOT.executeMSG(deleteMessage);
            }
            case Emojis.RIGHT + "Show" -> {
                DeleteMessage deleteMessage = new DeleteMessage(chatId, callbackQuery.getMessage().getMessageId());
                BOT.executeMSG(deleteMessage);
                bookRepository.setOFFSET(bookRepository.getOFFSET() + 5);
                searchingProcess.searching(chatId, Criteria.getCriteriaByData(bookRepository.showingCriteria(chatId)));
            }
            case Emojis.RIGHT + "MyBooks" -> {
                DeleteMessage deleteMessage = new DeleteMessage(chatId, callbackQuery.getMessage().getMessageId());
                BOT.executeMSG(deleteMessage);
                bookRepository.setOFFSET(bookRepository.getOFFSET() + 5);
                searchingProcess.searchMyBooks(chatId);
            }
            case Emojis.RIGHT -> {

                DeleteMessage deleteMessage = new DeleteMessage(chatId, callbackQuery.getMessage().getMessageId());
                BOT.executeMSG(deleteMessage);
                bookRepository.setOFFSET(bookRepository.getOFFSET() + 5);
                searchingProcess.searching(chatId, bookRepository.searchingTitle(chatId));


            }
            case "uz", "ru", "en" -> {
                userRepository.changeUserLanguage(chatId, Objects.requireNonNull(Language.getByCode(data)));
                DeleteMessage deleteMessage = new DeleteMessage();
                deleteMessage.setChatId(message.getChatId().toString());
                deleteMessage.setMessageId(message.getMessageId());
                BOT.executeMSG(deleteMessage);
                SendMessage sendMessage = new SendMessage(chatId, "Your full name please");
                sendMessage.setReplyMarkup(new ForceReplyKeyboard());
                BOT.executeMSG(sendMessage);
                userRepository.setUserState(chatId, UserState.FULL_NAME);
            }
            case "male", "female" -> {
                userRepository.changeUserGender(chatId, data);
                DeleteMessage deleteMessage = new DeleteMessage(chatId, message.getMessageId());
                BOT.executeMSG(deleteMessage);
                SendMessage sendMessage = new SendMessage(chatId, Emojis.CONTACT + "Please enter your phone number");
                sendMessage.setReplyMarkup(MarkupButtons.shareContact());
                BOT.executeMSG(sendMessage);
                userRepository.setUserState(chatId, UserState.PHONE_NUMBER);
            }
            case "textbooks", "programming", "religious", "art" -> {
                BookRepository.getInstance().SessionCriteriaChange(chatId, data);
                DeleteMessage deleteMessage = new DeleteMessage(chatId, message.getMessageId());
                BOT.executeMSG(deleteMessage);
                SendMessage sendMessage = new SendMessage(chatId, "Book file in DocumentFormat");
                BOT.executeMSG(sendMessage);
            }
        }
    }

    public static CallBackHandler getInstance() {
        return instance;
    }


}
