package spring.project.bot.service.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.bot.model.MessageBotData;
import spring.project.bot.service.states.StateService;

@Service
@AllArgsConstructor
public class ServiceHandlerGo implements MessageBotHandler{

    private final StateService stateService;

    @Override
    public boolean next(MessageBotData messageBotData) throws TelegramApiException {
        String command=messageBotData.getMessageText();
        Long chatId=messageBotData.getChatId();
        if(command!=null) {
            boolean isGo=command.indexOf("go",0)>-1;
            if(isGo){
                stateService.getState(chatId).go(chatId);
                return false;
            }
        }

        return true;
    }
}
