package spring.project.bot.service.handlers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.model.DataMessage;
import spring.project.bot.model.UserCommand;
import spring.project.bot.service.commands.CommandExecutor;
import spring.project.bot.service.commands.ServiceCommandGo;
import spring.project.bot.service.commands.ServiceCommandLanguage;

@Service
@AllArgsConstructor
public class ServiceHandlerLanguage implements MessageBotHandler {

    private final ServiceCommandLanguage serviceCommandLanguage;
    private final CommandExecutor commandExecutor;

    @Override
    @SneakyThrows
    public boolean next(DataMessage dataMessage) {
        String command = dataMessage.getMessageText();
        if(command==null) {
            return true;
        }
        boolean isLanguage = UserCommand.LANGUAGES.contains(command);
        if (!isLanguage) {
            return true;
        }
        commandExecutor.execute(serviceCommandLanguage, dataMessage);

        return false;
    }
}
