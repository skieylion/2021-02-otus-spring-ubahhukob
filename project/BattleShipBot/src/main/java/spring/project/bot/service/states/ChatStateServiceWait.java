package spring.project.bot.service.states;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.bot.model.Chat;
import spring.project.bot.model.ChatForPartner;
import spring.project.bot.model.ChatState;
import spring.project.bot.repository.ChatForPartnerRepository;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.BotConverter;
import spring.project.bot.service.RabbitService;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatStateServiceWait extends ChatStateService {
    private final ChatState chatState = ChatState.WAIT;
    private final ChatStateServiceFree chatStateServiceFree;
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
    public void join(String playerId) { }

    @Override
    public void generate(Long chatId) {}

    @Override
    public void go(Long chatId) { }

    @Override
    public void fire(Long chatId,Integer messageId,String messageText) { }
}
