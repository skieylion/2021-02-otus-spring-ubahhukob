package spring.project.bot.service.handlers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.model.DataMessage;
import spring.project.bot.service.states.*;

@Service
@AllArgsConstructor
public class ServiceHandlerGenerate implements MessageBotHandler {

    private final ServiceCommandGenerate serviceCommandGenerate;
    private final CommandExecutor commandExecutor;

    @Override
    @SneakyThrows
    public boolean next(DataMessage dataMessage) {

        String command = dataMessage.getMessageText();
        if (command == null) {
            return true;
        }

        boolean isGenerate = command.indexOf("generate", 0) > -1;
        boolean isNew = command.indexOf("new", 0) > -1;
        if (!(isGenerate || isNew)) {
            return true;
        }
        commandExecutor.execute(serviceCommandGenerate,dataMessage);
        return false;
    }
}
