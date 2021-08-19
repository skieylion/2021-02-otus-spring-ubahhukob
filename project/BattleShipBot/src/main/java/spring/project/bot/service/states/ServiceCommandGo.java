package spring.project.bot.service.states;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import spring.project.bot.model.Chat;
import spring.project.bot.model.ChatForPartner;
import spring.project.bot.model.ChatState;
import spring.project.bot.model.DataMessage;
import spring.project.bot.repository.ChatForPartnerRepository;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.BotConverter;
import spring.project.bot.service.RabbitService;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.BattleField;
import spring.project.common.model.Player;

import java.util.*;

@Service
@AllArgsConstructor
public class ServiceCommandGo implements Command {

    private final List<ChatState> states = Collections.singletonList(ChatState.CONFIG);
    private final ChatPlayerRepository chatPlayerRepository;
    private final TelegramService telegramService;
    private final ChatForPartnerRepository chatForPartnerRepository;

    @Override
    public List<ChatState> getStates() {
        return states;
    }

    @Override
    public void execute(DataMessage data) {
        Long chatId=data.getChatId();
        Chat chat = chatPlayerRepository.findById(chatId).orElseThrow();
        String playerId = chat.getPlayer().getId();
        String enemyId = chat.getPlayer().getEnemyId();
        Optional<ChatForPartner> chatEnemy = chatForPartnerRepository.findById(enemyId);
        if (chatEnemy.isPresent()) {
            ChatForPartner chatForPartner = chatEnemy.get();
            Long chatEnemyId = chatForPartner.getChatId();
            Long firstChatId = new Random().nextBoolean() ? chatId : chatEnemyId;
            Long reverseChatId = !firstChatId.equals(chatId) ? chatId : chatEnemyId;
            Chat firstChat = chatPlayerRepository.findById(firstChatId).orElseThrow();
            firstChat.setChatState(ChatState.PLAY);
            chatPlayerRepository.save(firstChat);
            Chat reverseChat = chatPlayerRepository.findById(reverseChatId).orElseThrow();
            reverseChat.setChatState(ChatState.WAIT);
            chatPlayerRepository.save(reverseChat);
            BattleField battleField = new BattleField(10, 10);
            telegramService.sendBattleField(firstChatId, "Game is started. It's your turn", battleField);
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(reverseChatId, "Game is started. You wait your partner's turn");
        } else {
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatId, "wait your partner");
        }

        ChatForPartner chatForPartner = new ChatForPartner();
        chatForPartner.setPartnerId(playerId);
        chatForPartner.setChatId(chatId);
        chatForPartnerRepository.save(chatForPartner);
    }
}
