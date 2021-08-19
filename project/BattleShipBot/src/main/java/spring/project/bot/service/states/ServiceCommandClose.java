package spring.project.bot.service.states;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.bot.model.Chat;
import spring.project.bot.model.ChatForPartner;
import spring.project.bot.model.ChatState;
import spring.project.bot.model.DataMessage;
import spring.project.bot.repository.ChatForPartnerRepository;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.BattleField;

import java.util.*;

@Service
@AllArgsConstructor
public class ServiceCommandClose implements Command {

    private final ChatStateToolkit chatStateToolkit;

    private final List<ChatState> states = Arrays.asList(ChatState.CONFIG,ChatState.PLAY,ChatState.WAIT);

    @Override
    public List<ChatState> getStates() {
        return states;
    }

    @Override
    public void execute(DataMessage data) {
        chatStateToolkit.deleteAll(data.getChatId());
    }
}
