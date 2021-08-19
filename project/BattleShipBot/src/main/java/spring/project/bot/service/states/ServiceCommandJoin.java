package spring.project.bot.service.states;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.bot.model.Chat;
import spring.project.bot.model.ChatState;
import spring.project.bot.model.DataMessage;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.RabbitService;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServiceCommandJoin implements Command {

    private final List<ChatState> states = Collections.singletonList(ChatState.FREE);
    private final ChatPlayerRepository chatPlayerRepository;
    private final RabbitService rabbitService;
    private final TelegramService telegramService;

    @Override
    public List<ChatState> getStates() {
        return states;
    }

    private void joinTo(Long chatId, String partnerId) {
        Player player = rabbitService.joinToBattle(partnerId);
        Chat chat = new Chat();
        chat.setPlayer(player);
        chat.setChatId(chatId);
        chat.setChatState(ChatState.CONFIG);
        chatPlayerRepository.save(chat);
        telegramService.sendTextMessageWithKeyboardButtons(chatId, "Nice. You are joined to the game. Next generate a new field", Collections.singletonList("generate"));
    }

    @Override
    public void execute(DataMessage data) {
        Long chatId=data.getChatId();
        String partnerId=data.getPartnerId();

        Optional<Chat> chatOptional = chatPlayerRepository.findById(chatId);
        if (chatOptional.isEmpty()) {
            joinTo(chatId, partnerId);
        } else {
            Chat chat = chatOptional.get();
            Player player = chat.getPlayer();
            if (partnerId.equals(player.getId())) {
                joinTo(chatId, partnerId);
            } else if (partnerId.equals(player.getEnemyId())) {
                telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatId, "Sorry. The link is for your partner");
            } else {
                telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatId, "Sorry. The bot don't know the link");
            }
        }
    }
}
