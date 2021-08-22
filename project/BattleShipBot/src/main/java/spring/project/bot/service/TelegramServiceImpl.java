package spring.project.bot.service;

import lombok.AllArgsConstructor;
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
import spring.project.bot.model.UserSettings;
import spring.project.bot.repository.UserSettingsRepository;
import spring.project.bot.service.converters.ConverterBattleField;
import spring.project.bot.service.converters.ConverterCommand;
import spring.project.common.model.BattleField;
import spring.project.common.model.Point;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TelegramServiceImpl implements TelegramService {

    private final ConverterCommand converterCommand;
    private final EventManager eventManager;
    private final LocaleService localeService;

    private void call(Object object){
        eventManager.notifyAll(object);
    }

    private String localize(Integer userId,String text){
        return localeService.localize(userId,text);
    }

    @Override
    @SneakyThrows
    public void sendTextMessageWithoutReplyKeyboardMarkup(Integer userId,Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(localize(userId,text));
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
        sendMessage.setText(localize(userId,text));
        call(sendMessage);
    }

    @Override
    @SneakyThrows
    public void sendTextMessageWithKeyboardButtons(Integer userId,Long chatId, String text, List<String> captions) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(localize(userId,text));
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
        sendMessage.setText(localize(userId,text));
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
        sendMessage.setText(localize(userId,text));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> table = new ArrayList<>();

        for (int i = 0; i < battleField.getRows(); i++) {
            KeyboardRow keyboardRow = new KeyboardRow();
            for (int j = 0; j < battleField.getColumns(); j++) {
                keyboardRow.add(new KeyboardButton(battleField.getCell(i, j).toString() + converterCommand.convertPointToString(new Point(j, i), battleField.getColumns())));
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
