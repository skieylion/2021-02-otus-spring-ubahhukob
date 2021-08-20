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

    private void joinTo(Integer userId,Long chatId, String partnerId) {
        Player player = rabbitService.joinToBattle(partnerId);
        Chat chat = new Chat();
        chat.setPlayer(player);
        chat.setChatId(chatId);
        chat.setUserId(userId);
        chat.setChatState(ChatState.CONFIG);
        chatPlayerRepository.save(chat);
        telegramService.sendTextMessageWithKeyboardButtons(userId,chatId, UserMessage.JOIN, Collections.singletonList(UserCommand.GENERATE));
    }

    @Override
    public void execute(DataMessage data) {
        Long chatId=data.getChatId();
        String partnerId=data.getPartnerId();
        Integer userId=data.getUserId();

        Optional<Chat> chatOptional = chatPlayerRepository.findById(chatId);
        if (chatOptional.isEmpty()) {
            joinTo(userId,chatId, partnerId);
        } else {
            Chat chat = chatOptional.get();
            Player player = chat.getPlayer();
            if (partnerId.equals(player.getId())) {
                joinTo(userId,chatId, partnerId);
            } else if (partnerId.equals(player.getEnemyId())) {
                telegramService.sendTextMessageWithoutReplyKeyboardMarkup(userId,chatId, UserMessage.LINK_PARTNER);
            } else {
                telegramService.sendTextMessageWithoutReplyKeyboardMarkup(userId,chatId, UserMessage.NO_LINK);
            }
        }
    }
}
