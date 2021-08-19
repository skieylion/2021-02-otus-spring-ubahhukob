package spring.project.bot.service.states;

import spring.project.bot.model.ChatState;
import spring.project.bot.model.DataMessage;

import java.util.List;

public interface Command {
    List<ChatState> getStates();
    void execute(DataMessage data);
}
