package spring.project.bot.service.handlers;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.bot.model.MessageBotData;

import java.io.IOException;

public interface MessageBotHandlerRunner {
    void run(MessageBotData messageBotData) throws IOException, TelegramApiException;
}
