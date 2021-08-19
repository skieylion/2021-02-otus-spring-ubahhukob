package spring.project.bot.service.handlers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.model.HiddenSymbol;
import spring.project.bot.model.DataMessage;
import spring.project.bot.service.states.CommandExecutor;
import spring.project.bot.service.states.ServiceCommandFire;

@Service
@AllArgsConstructor
public class ServiceHandlerFire implements MessageBotHandler {

    private final ServiceCommandFire serviceCommandFire;
    private final CommandExecutor commandExecutor;


    @Override
    @SneakyThrows
    public boolean next(DataMessage data) {
        String msg = data.getMessageText();
        char one = HiddenSymbol.ONE;
        char zero = HiddenSymbol.ZERO;

        if (!(msg != null && (msg.indexOf(one, 0) > -1 || msg.indexOf(zero, 0) > -1))) {
            return true;
        }
        commandExecutor.execute(serviceCommandFire,data);
        return false;
    }
}
