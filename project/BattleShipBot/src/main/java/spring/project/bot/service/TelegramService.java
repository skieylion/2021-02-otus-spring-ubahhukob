package spring.project.bot.service;

import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.common.model.BattleField;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;

public interface TelegramService {
    void sendTextMessageWithoutReplyKeyboardMarkup(Long chatId, String text);

    void sendTextMessage(Long chatId, String text);

    void sendTextMessageWithKeyboardButtons(Long chatId, String text, List<String> captions);

    void sendTextMessageWithReplyKeyboardMarkup(Long chatId, String text, ReplyKeyboardMarkup replyKeyboardMarkup);

    void sendPhoto(Long chatId, InputFile inputFile);

    void sendBattleField(Long chatId, String text, BattleField battleField);

    void deleteMessage(Long chatId, Integer messageId);

    void setAction(Function<Object,Void> func);
}
