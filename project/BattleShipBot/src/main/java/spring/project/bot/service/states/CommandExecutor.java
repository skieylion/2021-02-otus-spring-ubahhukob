package spring.project.bot.service.states;

import spring.project.bot.model.DataMessage;

public interface CommandExecutor {
    void execute(Command command, DataMessage data);
    void failed();
}
