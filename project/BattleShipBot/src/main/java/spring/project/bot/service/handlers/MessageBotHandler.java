package spring.project.bot.service.handlers;

import spring.project.bot.model.DataMessage;

public interface MessageBotHandler {
    boolean next(DataMessage dataMessage);
}
