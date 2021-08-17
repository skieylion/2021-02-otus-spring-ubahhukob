package spring.project.bot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.common.model.BattleField;
import spring.project.common.model.Point;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TelegramServiceImpl implements TelegramService{

    private final TelegramLongPollingBot telegramLongPollingBot;
    private final BotConverter botConverter;

    @Override
    public void sendTextMessageWithoutReplyKeyboardMarkup(Long chatId,String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        ReplyKeyboardRemove replyKeyboardRemove=new ReplyKeyboardRemove();
        replyKeyboardRemove.setRemoveKeyboard(true);
        sendMessage.setReplyMarkup(replyKeyboardRemove);
        telegramLongPollingBot.execute(sendMessage);
    }

    @Override
    public void sendTextMessage(Long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        telegramLongPollingBot.execute(sendMessage);
    }

    @Override
    public void sendTextMessageWithKeyboardButtons(Long chatId,String text, List<String> captions) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
        List<KeyboardRow> table=new ArrayList<>();

        captions.forEach(caption->{
            KeyboardRow keyboardRow=new KeyboardRow();
            keyboardRow.add(new KeyboardButton(caption));
            table.add(keyboardRow);
        });

        replyKeyboardMarkup.setKeyboard(table);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        telegramLongPollingBot.execute(sendMessage);
    }

    @Override
    public void sendTextMessageWithReplyKeyboardMarkup(Long chatId, String text, ReplyKeyboardMarkup replyKeyboardMarkup) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        telegramLongPollingBot.execute(sendMessage);
    }

    @Override
    public void sendPhoto(Long chatId, InputFile inputFile) throws TelegramApiException {
        SendPhoto sendPhoto=new SendPhoto();
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setChatId(String.valueOf(chatId));
        telegramLongPollingBot.execute(sendPhoto);
    }

    @Override
    public void sendBattleField(Long chatId,String text, BattleField battleField) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
        List<KeyboardRow> table=new ArrayList<>();

        for(int i=0;i<battleField.getRows();i++){
            KeyboardRow keyboardRow=new KeyboardRow();
            for(int j=0;j<battleField.getColumns();j++){
                keyboardRow.add(new KeyboardButton(battleField.getCell(i,j).toString()+botConverter.convertPointToString(new Point(j,i),battleField.getColumns())));
            }
            table.add(keyboardRow);
        }

        replyKeyboardMarkup.setKeyboard(table);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        telegramLongPollingBot.execute(sendMessage);
    }

    @Override
    public void deleteMessage(Long chatId, Integer messageId) throws TelegramApiException {
        DeleteMessage deleteMessage=new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatId));
        deleteMessage.setMessageId(messageId);
        telegramLongPollingBot.execute(deleteMessage);
    }
}
