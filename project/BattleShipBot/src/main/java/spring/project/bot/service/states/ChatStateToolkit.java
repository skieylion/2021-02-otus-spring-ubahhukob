package spring.project.bot.service.states;

public interface ChatStateToolkit {
    void update(Long chatId, spring.project.bot.model.ChatState chatState);

    void deleteAll(Long chatId);
}
