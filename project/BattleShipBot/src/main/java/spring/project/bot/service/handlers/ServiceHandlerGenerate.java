package spring.project.bot.service.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.bot.model.MessageBotData;
import spring.project.bot.service.states.StateService;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ServiceHandlerGenerate implements MessageBotHandler{

    private final StateService stateService;

    @Override
    public boolean next(MessageBotData messageBotData) throws TelegramApiException, IOException {

        String command=messageBotData.getMessageText();
        if(command!=null) {

            boolean isGenerate=command.indexOf("generate",0)>-1;
            boolean isNew=command.indexOf("new",0)>-1;
            //команда старт или restart
            if(isGenerate||isNew) {
                Long chatId=messageBotData.getChatId();
                stateService.getState(chatId).generate(chatId);
                return false;
            }
        }
        return true;
    }
}
