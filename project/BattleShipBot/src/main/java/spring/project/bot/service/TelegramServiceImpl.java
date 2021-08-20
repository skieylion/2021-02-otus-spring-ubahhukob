package spring.project.bot.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import spring.project.bot.exception.EntityNotFoundException;
import spring.project.bot.model.UserSettings;
import spring.project.bot.repository.UserSettingsRepository;
import spring.project.common.model.BattleField;
import spring.project.common.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

@Service
public class TelegramServiceImpl implements TelegramService {

    private final BotConverter botConverter;
    private final MessageSource messageSource;
    private final UserSettingsRepository userSettingsRepository;
    private final String localeDefault;

    public TelegramServiceImpl(
            BotConverter botConverter,
            MessageSource messageSource,
            UserSettingsRepository userSettingsRepository,
            @Value("${localeDefault}") String localeDefault
    ) {
        this.botConverter = botConverter;
        this.messageSource = messageSource;
        this.userSettingsRepository = userSettingsRepository;
        this.localeDefault = localeDefault;
    }

    private Function<Object,Void> action;

    @SneakyThrows
    private void call(Object object){
        if(action!=null){
            action.apply(object);
        }
    }

    @Override
    public void setAction(Function<Object,Void> func) {
        action=func;
    }

    private String getLocale(Integer userId){
        String locale=localeDefault;
        Optional<UserSettings> userSettingsOptional = userSettingsRepository.findById(userId);
        if(userSettingsOptional.isPresent()){
            locale=userSettingsOptional.get().getLocale();
        }
        locale=(locale==null|| locale.equals(""))?localeDefault:locale;
        return locale;
    }

    private String locale(Integer userId,String text){
        try {
            return messageSource.getMessage(text, null, Locale.forLanguageTag(getLocale(userId)));
        } catch (NoSuchMessageException exception){
            return text;
        }
    }

    @Override
    @SneakyThrows
    public void sendTextMessageWithoutReplyKeyboardMarkup(Integer userId,Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(locale(userId,text));
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setRemoveKeyboard(true);
        sendMessage.setReplyMarkup(replyKeyboardRemove);
        call(sendMessage);
    }

    @Override
    @SneakyThrows
    public void sendTextMessage(Integer userId,Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(locale(userId,text));
        call(sendMessage);
    }

    @Override
    @SneakyThrows
    public void sendTextMessageWithKeyboardButtons(Integer userId,Long chatId, String text, List<String> captions) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(locale(userId,text));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> table = new ArrayList<>();

        captions.forEach(caption -> {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(caption));
            table.add(keyboardRow);
        });

        replyKeyboardMarkup.setKeyboard(table);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        call(sendMessage);
    }

    @Override
    @SneakyThrows
    public void sendTextMessageWithReplyKeyboardMarkup(Integer userId,Long chatId, String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(locale(userId,text));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        call(sendMessage);
    }

    @Override
    @SneakyThrows
    public void sendPhoto(Integer userId,Long chatId, InputFile inputFile) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setChatId(String.valueOf(chatId));
        call(sendPhoto);
    }

    @Override
    @SneakyThrows
    public void sendBattleField(Integer userId,Long chatId, String text, BattleField battleField) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(locale(userId,text));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> table = new ArrayList<>();

        for (int i = 0; i < battleField.getRows(); i++) {
            KeyboardRow keyboardRow = new KeyboardRow();
            for (int j = 0; j < battleField.getColumns(); j++) {
                keyboardRow.add(new KeyboardButton(battleField.getCell(i, j).toString() + botConverter.convertPointToString(new Point(j, i), battleField.getColumns())));
            }
            table.add(keyboardRow);
        }

        replyKeyboardMarkup.setKeyboard(table);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        call(sendMessage);
    }

    @Override
    @SneakyThrows
    public void deleteMessage(Integer userId,Long chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatId));
        deleteMessage.setMessageId(messageId);
        call(deleteMessage);
    }

}
