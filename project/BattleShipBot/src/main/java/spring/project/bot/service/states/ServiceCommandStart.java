package spring.project.bot.service.states;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.exception.EntityNotFoundException;
import spring.project.bot.model.*;
import spring.project.bot.repository.ChatForPartnerRepository;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.BotConverter;
import spring.project.bot.service.RabbitService;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.*;

import java.util.Arrays;
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
        Player player = rabbitService.startBattle();
        Chat chat = new Chat();
        chat.setPlayer(player);
        chat.setChatId(chatId);
        chat.setChatState(ChatState.CONFIG);
        chatPlayerRepository.save(chat);
        rabbitService.joinToBattle(player.getId());
        telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatId, "Hello. Give this link your partner");
        String url = "https://t.me/XXXmeIxBot?start=" + player.getEnemyId();
        telegramService.sendTextMessageWithKeyboardButtons(chatId, url, Collections.singletonList("generate"));
    }
}
