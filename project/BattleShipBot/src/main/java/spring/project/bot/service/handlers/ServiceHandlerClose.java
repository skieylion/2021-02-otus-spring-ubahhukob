package spring.project.bot.service.handlers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.model.DataMessage;
import spring.project.bot.service.states.CommandExecutor;
import spring.project.bot.service.states.ServiceCommandClose;
import spring.project.bot.service.states.ServiceCommandJoin;
import spring.project.bot.service.states.ServiceCommandStart;

@Service
@AllArgsConstructor
public class ServiceHandlerClose implements MessageBotHandler {

    private final ServiceCommandClose serviceCommandClose;
    private final CommandExecutor commandExecutor;

    @Override
    @SneakyThrows
    public boolean next(DataMessage dataMessage) {
        String command = dataMessage.getMessageText();
        if (command == null) return true;
        boolean isClose = command.indexOf("/close", 0) > -1;
        if (!isClose) {
            return true;
        }
        commandExecutor.execute(serviceCommandClose, dataMessage);
        return false;
    }
}
