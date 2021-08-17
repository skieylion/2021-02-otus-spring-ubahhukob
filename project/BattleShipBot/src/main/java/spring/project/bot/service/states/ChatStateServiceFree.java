package spring.project.bot.service.states;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.bot.model.Chat;
import spring.project.bot.model.ChatState;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.BotConverter;
import spring.project.bot.service.RabbitService;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.Player;

import java.util.*;

@Service
@Primary
@AllArgsConstructor
public class ChatStateServiceFree extends ChatStateService {
    private final ChatState chatState=ChatState.FREE;
    private final ChatPlayerRepository chatPlayerRepository;
    private final RabbitService rabbitService;
    private final TelegramService telegramService;

    private void joinTo(Long chatId,String partnerId) throws JsonProcessingException, TelegramApiException {
        Player player=rabbitService.join(partnerId);
        Chat chat =new Chat();
        chat.setPlayer(player);
        chat.setChatId(chatId);
        chat.setChatState(ChatState.CONFIG);
        chatPlayerRepository.save(chat);
        telegramService.sendTextMessageWithKeyboardButtons(chatId,"Nice. You are joined to the game. Next generate a new field", Collections.singletonList("generate"));
    }

    @Override
    public ChatState getChatState() {
        return chatState;
    }

    @Override
    public void start(Long chatId) throws JsonProcessingException, TelegramApiException {
        Player player=rabbitService.start();
        Chat chat =new Chat();
        chat.setPlayer(player);
        chat.setChatId(chatId);
        chat.setChatState(ChatState.CONFIG);
        chatPlayerRepository.save(chat);
        rabbitService.join(player.getId());
        telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatId,"Hello. Give this link your partner");
        String url="https://t.me/XXXmeIxBot?start="+player.getEnemyId();
        telegramService.sendTextMessageWithKeyboardButtons(chatId,url, Collections.singletonList("generate"));
    }

    @Override
    public void start(Long chatId, String partnerId) throws JsonProcessingException, TelegramApiException {
        Optional<Chat> chatOptional=chatPlayerRepository.findById(chatId);
        if(chatOptional.isEmpty()){
            joinTo(chatId,partnerId);
        } else {
            Chat chat = chatOptional.get();
            Player player=chat.getPlayer();
            if(partnerId.equals(player.getId())){
                joinTo(chatId,partnerId);
            } else if(partnerId.equals(player.getEnemyId())){
                telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatId,"Sorry. The link is for your partner");
            } else {
                telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatId,"Sorry. The bot don't know the link");
            }
        }
    }

    @Override
    public void join(String playerId) {

    }

    @Override
    public void generate(Long chatId) {

    }

    @Override
    public void go(Long chatId) {

    }

    @Override
    public void fire(Long chatId,Integer messageId,String messageText) {

    }
}
