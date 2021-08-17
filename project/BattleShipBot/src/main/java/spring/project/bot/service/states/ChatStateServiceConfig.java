package spring.project.bot.service.states;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.bot.model.Chat;
import spring.project.bot.model.ChatForPartner;
import spring.project.bot.model.ChatState;
import spring.project.bot.repository.ChatForPartnerRepository;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.BotConverter;
import spring.project.bot.service.RabbitService;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.BattleField;
import spring.project.common.model.Player;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class ChatStateServiceConfig extends ChatStateService {
    private final ChatState chatState = ChatState.CONFIG;
    private final ChatPlayerRepository chatPlayerRepository;
    private final BotConverter botConverter;
    private final RabbitService rabbitService;
    private final TelegramService telegramService;
    private final ChatStateServiceFree chatStateServiceFree;
    private final ChatForPartnerRepository chatForPartnerRepository;
    private final ChatStateToolkit chatStateToolkit;

    @Override
    public ChatState getChatState() {
        return chatState;
    }

    @Override
    public void start(Long chatId) throws JsonProcessingException, TelegramApiException {
        chatStateToolkit.deleteAll(chatId);
        chatStateServiceFree.start(chatId);
    }

    @Override
    public void start(Long chatId, String partnerId) throws JsonProcessingException, TelegramApiException {
        chatStateToolkit.deleteAll(chatId);
        chatStateServiceFree.start(chatId,partnerId);
    }

    @Override
    public void join(String playerId) {

    }

    @Override
    public void generate(Long chatId) throws IOException, TelegramApiException {
        Chat chat = chatPlayerRepository.findById(chatId).orElseThrow();
        Player player = chat.getPlayer();
        player = rabbitService.generate(player.getId());
        chat.setPlayer(player);
        chatPlayerRepository.save(chat);
        InputFile inputFile = botConverter.convertToImage(player.getField());
        telegramService.sendPhoto(chatId, inputFile);
        telegramService.sendTextMessageWithKeyboardButtons(chatId, "it's your field", Arrays.asList("go", "new"));
    }

    @Override
    public void go(Long chatId) throws TelegramApiException {
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
            BattleField battleField=new BattleField(10,10);
            telegramService.sendBattleField(firstChatId, "Game is started. It's your turn",battleField);
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(reverseChatId, "Game is started. You wait your partner's turn");
        } else {
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatId, "wait your partner");
        }

        ChatForPartner chatForPartner = new ChatForPartner();
        chatForPartner.setPartnerId(playerId);
        chatForPartner.setChatId(chatId);
        chatForPartnerRepository.save(chatForPartner);
    }

    @Override
    public void fire(Long chatId,Integer messageId,String messageText) {

    }
}
