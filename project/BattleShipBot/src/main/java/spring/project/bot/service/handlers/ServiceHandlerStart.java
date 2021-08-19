package spring.project.bot.service.handlers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.model.DataMessage;
import spring.project.bot.model.UserCommand;
import spring.project.bot.service.commands.*;

@Service
@AllArgsConstructor
public class ServiceHandlerStart implements MessageBotHandler {

    private final ServiceCommandStart serviceCommandStart;
    private final ServiceCommandJoin serviceCommandJoin;
    private final CommandExecutor commandExecutor;

    @Override
    @SneakyThrows
    public boolean next(DataMessage dataMessage) {
        String command = dataMessage.getMessageText();
        if (command == null) return true;
        boolean isStart = command.indexOf(UserCommand.START, 0) > -1;
        String partnerId = command.replace(UserCommand.START, "").trim();

        if (isStart && (!partnerId.equals(""))) {
            dataMessage.setPartnerId(partnerId);
            commandExecutor.execute(serviceCommandJoin, dataMessage);
            return false;
        } else if(isStart){
            commandExecutor.execute(serviceCommandStart, dataMessage);
            return false;
        }

        return true;
    }
}
