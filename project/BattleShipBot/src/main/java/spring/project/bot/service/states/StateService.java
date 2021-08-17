package spring.project.bot.service.states;

import spring.project.bot.model.ChatState;
import spring.project.bot.service.states.ChatStateService;

public interface StateService {
    ChatStateService getState(Long chatId);
}
