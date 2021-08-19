package spring.project.bot.service.commands;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.bot.model.*;
import spring.project.bot.repository.ChatForPartnerRepository;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.BattleField;

import java.util.*;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class ServiceCommandLanguage implements Command {

    private final List<ChatState> states = Arrays.asList(ChatState.CONFIG,ChatState.FREE,ChatState.PLAY,ChatState.WAIT);
    private final TelegramService telegramService;

    @Override
    public List<ChatState> getStates() {
        return states;
    }

    @Override
    public void execute(DataMessage data) {
        telegramService.setLocale(data.getMessageText().replaceAll("/",""));
    }
}
