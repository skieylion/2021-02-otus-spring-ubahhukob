package spring.project.bot.service.handlers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.model.DataMessage;
import spring.project.bot.model.UserCommand;
import spring.project.bot.service.commands.CommandExecutor;
import spring.project.bot.service.commands.ServiceCommandClose;

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
        boolean isClose = command.indexOf(UserCommand.CLOSE, 0) > -1;
        if (!isClose) {
            return true;
        }
        commandExecutor.execute(serviceCommandClose, dataMessage);
        return false;
    }
}
