package spring.project.bot.service.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.bot.model.MessageBotData;

import java.io.IOException;

public interface MessageBotHandler {
    boolean next(MessageBotData messageBotData) throws TelegramApiException, IOException;
}
