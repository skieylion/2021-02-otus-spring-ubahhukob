package spring.project.bot.component;

import io.netty.channel.Channel;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.bot.model.*;
import spring.project.bot.service.TelegramService;
import spring.project.bot.service.handlers.MessageBotHandlerRunner;

import javax.annotation.PostConstruct;
import java.util.Observable;
import java.util.Observer;


@Component
public class BotComponent extends TelegramLongPollingBot {
    private final String name;
    private final String token;
    private final MessageBotHandlerRunner messageBotHandlerRunner;
    private final TelegramService telegramService;


    public BotComponent(
            @Value("${bot.name}") String name,
            @Value("${bot.token}") String token,
            MessageBotHandlerRunner messageBotHandlerRunner,
            TelegramService telegramService
    ) {
        this.name = name;
        this.token = token;
        this.messageBotHandlerRunner = messageBotHandlerRunner;
        this.telegramService = telegramService;
    }

    @PostConstruct
    public void init(){
        telegramService.setAction(message -> {
            try {
                if(message instanceof SendPhoto){
                    execute((SendPhoto)message);
                } else if(message instanceof SendMessage){
                    execute((SendMessage)message);
                } else if(message instanceof DeleteMessage){
                    execute((DeleteMessage)message);
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return null;
        });
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
        messageBotHandlerRunner.run(dataMessage);
    }

}