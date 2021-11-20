package spring.project.bot.service.handlers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.model.DataMessage;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageBotHandlerRunnerImpl implements MessageBotHandlerRunner {

    private final List<MessageBotHandler> handlers;

    @Override
    @SneakyThrows
    public void run(DataMessage dataMessage) {
        for (var handler : handlers) {
            if (!handler.next(dataMessage)) break;
        }
    }
}
