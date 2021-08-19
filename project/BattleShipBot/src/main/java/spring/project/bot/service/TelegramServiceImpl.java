package spring.project.bot.service;

import lombok.SneakyThrows;
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
import spring.project.common.model.BattleField;
import spring.project.common.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

@Service
public class TelegramServiceImpl implements TelegramService {

    private final BotConverter botConverter;
    private final MessageSource messageSource;

    private String locale="ru-RU";

    public TelegramServiceImpl(BotConverter botConverter, MessageSource messageSource) {
        this.botConverter = botConverter;
        this.messageSource = messageSource;
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

    @Override
    public void setLocale(String locale) {
        this.locale=locale;
    }

    private String locale(String text){
        try {
            return messageSource.getMessage(text, null, Locale.forLanguageTag(locale));
        } catch (NoSuchMessageException exception){
            return text;
        }
    }

    @Override
    @SneakyThrows
    public void sendTextMessageWithoutReplyKeyboardMarkup(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(locale(text));
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setRemoveKeyboard(true);
        sendMessage.setReplyMarkup(replyKeyboardRemove);
        call(sendMessage);
    }

    @Override
    @SneakyThrows
    public void sendTextMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(locale(text));
        call(sendMessage);
    }

    @Override
    @SneakyThrows
    public void sendTextMessageWithKeyboardButtons(Long chatId, String text, List<String> captions) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(locale(text));
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
    public void sendTextMessageWithReplyKeyboardMarkup(Long chatId, String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(locale(text));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        call(sendMessage);
    }

    @Override
    @SneakyThrows
    public void sendPhoto(Long chatId, InputFile inputFile) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setChatId(String.valueOf(chatId));
        call(sendPhoto);
    }

    @Override
    @SneakyThrows
    public void sendBattleField(Long chatId, String text, BattleField battleField) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(locale(text));
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
    public void deleteMessage(Long chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatId));
        deleteMessage.setMessageId(messageId);
        call(deleteMessage);
    }

}
