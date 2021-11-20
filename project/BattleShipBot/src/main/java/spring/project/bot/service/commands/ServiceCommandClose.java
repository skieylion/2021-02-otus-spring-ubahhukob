package spring.project.bot.service.commands;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.bot.model.ChatState;
import spring.project.bot.model.DataMessage;

import java.util.Arrays;
import java.util.List;

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
