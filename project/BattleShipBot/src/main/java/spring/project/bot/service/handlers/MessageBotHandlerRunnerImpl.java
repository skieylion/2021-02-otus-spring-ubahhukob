package spring.project.bot.service.handlers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.bot.model.MessageBotData;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageBotHandlerRunnerImpl implements MessageBotHandlerRunner{

    private final List<MessageBotHandler> handlers;

    @Override
    public void run(MessageBotData messageBotData) throws IOException, TelegramApiException {
        for (var handler:handlers) {
            if(!handler.next(messageBotData)) break;
        }
    }
}
