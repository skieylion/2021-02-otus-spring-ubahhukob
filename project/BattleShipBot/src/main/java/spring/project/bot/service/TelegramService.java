package spring.project.bot.service;

import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.common.model.BattleField;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;

public interface TelegramService {
    void sendTextMessageWithoutReplyKeyboardMarkup(Integer userId,Long chatId, String text);

    void sendTextMessage(Integer userId,Long chatId, String text);

    void sendTextMessageWithKeyboardButtons(Integer userId,Long chatId, String text, List<String> captions);

    void sendTextMessageWithReplyKeyboardMarkup(Integer userId,Long chatId, String text, ReplyKeyboardMarkup replyKeyboardMarkup);

    void sendPhoto(Integer userId,Long chatId, InputFile inputFile);

    void sendBattleField(Integer userId,Long chatId, String text, BattleField battleField);

    void deleteMessage(Integer userId,Long chatId, Integer messageId);

    void setAction(Function<Object,Void> func);
}
