package spring.project.bot.service.states;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.bot.model.ChatState;
import spring.project.common.model.Player;

import java.io.IOException;

@Data
public abstract class ChatStateService {

    public abstract ChatState getChatState();
    public abstract void start(Long chatId) throws JsonProcessingException, TelegramApiException;
    public abstract void start(Long chatId,String partnerId) throws JsonProcessingException, TelegramApiException;
    public abstract void join(String playerId);
    public abstract void generate(Long chatId) throws IOException, TelegramApiException;
    public abstract void go(Long chatId) throws TelegramApiException;
    public abstract void fire(Long chatId,Integer messageId,String messageText) throws TelegramApiException, JsonProcessingException;
}