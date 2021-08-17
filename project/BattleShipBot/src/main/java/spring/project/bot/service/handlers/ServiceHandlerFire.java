package spring.project.bot.service.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.bot.model.MessageBotData;
import spring.project.bot.service.states.StateService;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ServiceHandlerFire implements MessageBotHandler {

    private final StateService stateService;


    @Override
    public boolean next(MessageBotData messageBotData) throws TelegramApiException, IOException {
        String message=messageBotData.getMessageText();
        Long chatId =messageBotData.getChatId();
        Integer messageId =messageBotData.getMessageId();

        if(message!=null&&(message.indexOf("\u200C",0)>-1||message.indexOf("\u200B",0)>-1)){
            stateService.getState(chatId).fire(chatId,messageId,message);
            return false;
        }
        return true;
    }
}
