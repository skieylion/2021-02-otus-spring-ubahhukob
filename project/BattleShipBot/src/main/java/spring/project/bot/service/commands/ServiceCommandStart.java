package spring.project.bot.service.commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.project.bot.model.*;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.RabbitService;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.Player;

import java.util.Collections;
import java.util.List;

@Service
public class ServiceCommandStart implements Command {

    private final List<ChatState> states = Collections.singletonList(ChatState.FREE);
    private final ChatPlayerRepository chatPlayerRepository;
    private final RabbitService rabbitService;
    private final TelegramService telegramService;
    private final String templateURL;

    public ServiceCommandStart(
            ChatPlayerRepository chatPlayerRepository,
            RabbitService rabbitService,
            TelegramService telegramService,
            @Value("${templateURL}") String templateURL)
    {
        this.chatPlayerRepository = chatPlayerRepository;
        this.rabbitService = rabbitService;
        this.telegramService = telegramService;
        this.templateURL = templateURL;
    }

    @Override
    public List<ChatState> getStates() {
        return states;
    }

    @Override
    public void execute(DataMessage data) {
        Long chatId=data.getChatId();
        Integer userId=data.getUserId();
        Player player = rabbitService.startBattle();
        Chat chat = new Chat(chatId,player,ChatState.CONFIG,userId);
        chatPlayerRepository.save(chat);
        rabbitService.joinToBattle(player.getId());
        telegramService.sendTextMessageWithoutReplyKeyboardMarkup(userId,chatId, UserMessage.HELLO);
        String url = templateURL + player.getEnemyId();
        telegramService.sendTextMessageWithKeyboardButtons(userId,chatId, url, Collections.singletonList(UserCommand.GENERATE));
    }
}