package spring.project.bot.service.handlers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.model.DataMessage;
import spring.project.bot.service.states.*;

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
        System.out.println(command);
        if (command == null) return true;
        boolean isStart = command.indexOf("/start", 0) > -1;
        String partnerId = command.replace("/start", "").trim();

        if (isStart && (!partnerId.equals(""))) {
            System.out.println("start part");
            dataMessage.setPartnerId(partnerId);
            commandExecutor.execute(serviceCommandJoin, dataMessage);
            return false;
        } else if(isStart){
            System.out.println("start -");
            commandExecutor.execute(serviceCommandStart, dataMessage);
            return false;
        }

        return true;
    }
}
