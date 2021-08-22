package spring.project.bot.component;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import spring.project.bot.model.*;
import spring.project.bot.service.EventListener;
import spring.project.bot.service.EventManager;
import spring.project.bot.service.TelegramService;
import spring.project.bot.service.handlers.MessageBotHandlerRunner;


@Component
public class BotComponent extends TelegramLongPollingBot implements EventListener {
    private final String name;
    private final String token;
    private final MessageBotHandlerRunner messageBotHandlerRunner;

    public BotComponent(
            @Value("${bot.name}") String name,
            @Value("${bot.token}") String token,
            MessageBotHandlerRunner messageBotHandlerRunner,
            EventManager eventManager) {
        this.name = name;
        this.token = token;
        this.messageBotHandlerRunner = messageBotHandlerRunner;
        eventManager.subscribe(this);
    }

    @Override
    @SneakyThrows
    public void update(Object message) {
        if(message instanceof SendPhoto){
            execute((SendPhoto)message);
        } else if(message instanceof SendMessage){
            execute((SendMessage)message);
        } else if(message instanceof DeleteMessage){
            execute((DeleteMessage)message);
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        DataMessage dataMessage = new DataMessage();
        dataMessage.setMessageId(update.getMessage().getMessageId());
        dataMessage.setChatId(update.getMessage().getChatId());
        dataMessage.setMessageText(update.getMessage().getText());
        dataMessage.setUserId(update.getMessage().getFrom().getId());
        messageBotHandlerRunner.run(dataMessage);
    }


}