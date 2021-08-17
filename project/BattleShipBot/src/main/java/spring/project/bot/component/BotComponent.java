package spring.project.bot.component;

import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import spring.project.bot.model.*;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.BotConverter;
import spring.project.bot.service.handlers.MessageBotHandlerRunner;
import spring.project.common.model.Player;

@Component
public class BotComponent extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String name;
    @Value("${bot.token}")
    private String token;

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${nameQueueInOut}")
    private String nameQueueInOut;

    @Autowired
    private BotConverter botConverter;

    @Autowired
    private ChatPlayerRepository chatPlayerRepository;

    private Player player;

    @Autowired
    private MessageBotHandlerRunner messageBotHandlerRunner;



    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        MessageBotData messageBotData=new MessageBotData();
        messageBotData.setMessageId(update.getMessage().getMessageId());
        messageBotData.setChatId(update.getMessage().getChatId());
        messageBotData.setMessageText(update.getMessage().getText());
        messageBotHandlerRunner.run(messageBotData);
    }
}