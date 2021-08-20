package spring.project.bot.service.commands;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.exception.EntityNotFoundException;
import spring.project.bot.model.*;
import spring.project.bot.repository.ChatForPartnerRepository;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.BattleField;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    @SneakyThrows
    public void execute(DataMessage data) {
        Long chatId=data.getChatId();
        Integer userId=data.getUserId();
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
            Chat chatFirst=chatPlayerRepository.findById(firstChatId).orElseThrow(EntityNotFoundException::new);
            Chat chatReverse=chatPlayerRepository.findById(reverseChatId).orElseThrow(EntityNotFoundException::new);
            telegramService.sendBattleField(chatFirst.getUserId(),firstChatId, UserMessage.GAME_START, battleField);
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatReverse.getUserId(),reverseChatId, UserMessage.GAME_START_PARTNER);
        } else {
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(userId,chatId, UserMessage.WAIT_PARTNER);
        }

        ChatForPartner chatForPartner = new ChatForPartner();
        chatForPartner.setPartnerId(playerId);
        chatForPartner.setChatId(chatId);
        chatForPartnerRepository.save(chatForPartner);
    }
}
