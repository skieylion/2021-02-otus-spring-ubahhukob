package spring.project.bot.service.commands;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.bot.model.*;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.RabbitService;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.Player;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceCommandStart implements Command {

    private final List<ChatState> states = Collections.singletonList(ChatState.FREE);
    private final ChatPlayerRepository chatPlayerRepository;
    private final RabbitService rabbitService;
    private final TelegramService telegramService;

    @Override
    public List<ChatState> getStates() {
        return states;
    }

    @Override
    public void execute(DataMessage data) {
        Long chatId=data.getChatId();
        Integer userId=data.getUserId();
        Player player = rabbitService.startBattle();
        Chat chat = new Chat();
        chat.setPlayer(player);
        chat.setChatId(chatId);
        chat.setUserId(userId);
        chat.setChatState(ChatState.CONFIG);
        chatPlayerRepository.save(chat);
        rabbitService.joinToBattle(player.getId());
        telegramService.sendTextMessageWithoutReplyKeyboardMarkup(userId,chatId, UserMessage.HELLO);
        String url = "https://t.me/XXXmeIxBot?start=" + player.getEnemyId();
        telegramService.sendTextMessageWithKeyboardButtons(userId,chatId, url, Collections.singletonList(UserCommand.GENERATE));
    }
}