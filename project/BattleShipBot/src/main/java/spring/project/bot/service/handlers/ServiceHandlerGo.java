package spring.project.bot.service.handlers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.model.DataMessage;
import spring.project.bot.service.states.CommandExecutor;
import spring.project.bot.service.states.ServiceCommandGo;

@Service
@AllArgsConstructor
public class ServiceHandlerGo implements MessageBotHandler {

    private final ServiceCommandGo serviceCommandGo;
    private final CommandExecutor commandExecutor;

    @Override
    @SneakyThrows
    public boolean next(DataMessage dataMessage) {
        String command = dataMessage.getMessageText();
        if(command==null) {
            return true;
        }
        boolean isGo = command.indexOf("go", 0) > -1;
        if (!isGo) {
            return true;
        }
        commandExecutor.execute(serviceCommandGo, dataMessage);

        return false;
    }
}
