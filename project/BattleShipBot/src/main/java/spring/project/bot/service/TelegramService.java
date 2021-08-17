package spring.project.bot.service;

import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.common.model.BattleField;

import java.util.List;

public interface TelegramService {
    void sendTextMessageWithoutReplyKeyboardMarkup(Long chatId,String text) throws TelegramApiException;
    void sendTextMessage(Long chatId,String text) throws TelegramApiException;
    void sendTextMessageWithKeyboardButtons(Long chatId,String text,List<String> captions) throws TelegramApiException;
    void sendTextMessageWithReplyKeyboardMarkup(Long chatId, String text, ReplyKeyboardMarkup replyKeyboardMarkup) throws TelegramApiException;
    void sendPhoto(Long chatId, InputFile inputFile) throws TelegramApiException;
    void sendBattleField(Long chatId,String text, BattleField battleField) throws TelegramApiException;
    void deleteMessage(Long chatId,Integer messageId) throws TelegramApiException;
}
