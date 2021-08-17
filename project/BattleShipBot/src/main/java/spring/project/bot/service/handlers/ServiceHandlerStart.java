package spring.project.bot.service.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.bot.model.MessageBotData;
import spring.project.bot.service.states.StateService;

@Service
@AllArgsConstructor
public class ServiceHandlerStart implements MessageBotHandler{

    private final StateService stateService;

    @Override
    public boolean next(MessageBotData messageBotData) throws TelegramApiException, JsonProcessingException {
        String command=messageBotData.getMessageText();
        if(command!=null) {
            boolean isStart=command.indexOf("/start",0)>-1;
            //boolean isRestart=command.indexOf("/restart",0)>-1;
            if(isStart){
                String partnerId=command.replace("/start","").trim();
                Long chatId=messageBotData.getChatId();
                if(!partnerId.equals("")){
                    stateService.getState(chatId).start(chatId,partnerId);
                } else {
                    stateService.getState(chatId).start(chatId);
                }
                return false;
            }
        }

        return true;
    }
}
