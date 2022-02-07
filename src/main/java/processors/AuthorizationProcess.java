package processors;

import FreePDFbot.FreePDF;
import buttons.InlinekeyboardButtons;
import buttons.ReplyKeyboardButtons;
import configs.Propertyconfigs;
import configs.State;
import emojis.Emojis;
import enums.UserState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import repositories.UserRepository;

import java.io.File;
import java.util.Objects;


/**
 * @author Bekpulatov Shoxruh, Sun 10:28 PM. 12/19/2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorizationProcess {
    private static final FreePDF BOT = FreePDF.getInstance();
    private static final AuthorizationProcess instance = new AuthorizationProcess();
    private static final UserRepository userRepository = UserRepository.getInstance();


    public void process(Message message, UserState state) {
        String chatid = message.getChatId().toString();
        if (UserState.LANGUAGE.equals(state) || Objects.isNull(state)) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatid);
            sendPhoto.setPhoto(new InputFile(new File(Propertyconfigs.get("bot.logo"))));
            BOT.executeMSG(sendPhoto);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatid);
            sendMessage.setText("Please choose language!!!");
            sendMessage.setReplyMarkup(InlinekeyboardButtons.LanguageInlineKeyboard());
            BOT.executeMSG(sendMessage);
            userRepository.setUserState(chatid, UserState.DELETE_ALL);
        } else if (UserState.FULL_NAME.equals(state)) {
            userRepository.changeUserFullname(chatid, message.getText());
            SendMessage sendMessage = new SendMessage(chatid, "Pleas enter you age : ");
            sendMessage.setReplyMarkup(new ForceReplyKeyboard());
            BOT.executeMSG(sendMessage);
            userRepository.setUserState(chatid, UserState.AGE);
        } else if (UserState.AGE.equals(state)) {
            String text = message.getText();
            if (StringUtils.isNumeric(text)) {
                userRepository.changeUserAge(chatid, message.getText());
                SendMessage sendMessage = new SendMessage(chatid, "Please enter your gender : ");
                sendMessage.setReplyMarkup(InlinekeyboardButtons.GenderInlineKeyboard(chatid));
                BOT.executeMSG(sendMessage);
                userRepository.setUserState(chatid, UserState.DELETE_ALL);
            } else {
                SendMessage sendMessage = new SendMessage(chatid, "Invalid number\nPlease enter your age with numbers" + Emojis.WRONG + Emojis.WRONG);
                BOT.executeMSG(sendMessage);
            }
        } else if ((UserState.PHONE_NUMBER.equals(state))) {
            if (message.hasContact()) {
                userRepository.changePhoneNumber(chatid, message.getContact().getPhoneNumber());
                SendMessage sendMessage = new SendMessage(chatid, "Successfully authorizated" + Emojis.SUCCESS + Emojis.SUCCESS + Emojis.SUCCESS);
                BOT.executeMSG(sendMessage);
                userRepository.createUser(chatid, State.getUser(chatid));
                SendMessage sendMessage1 = new SendMessage();
                sendMessage1.setReplyMarkup(ReplyKeyboardButtons.menuKeyboard());
                sendMessage1.setChatId(chatid);
                sendMessage1.setText("Please choose menu");
                BOT.executeMSG(sendMessage1);
                userRepository.setUserState(chatid, UserState.AUTHORIZED);
            } else {
                SendMessage sendMessage = new SendMessage(chatid, "Invalid Contact\nPlease enter your Contact" + Emojis.WRONG + Emojis.WRONG);
                BOT.executeMSG(sendMessage);
            }
        }
        if (UserState.DELETE_ALL.equals(state)) {
            DeleteMessage deleteMessage = new DeleteMessage(chatid, message.getMessageId());
            BOT.executeMSG(deleteMessage);
        }
    }

    public static AuthorizationProcess getInstance() {
        return instance;
    }
}