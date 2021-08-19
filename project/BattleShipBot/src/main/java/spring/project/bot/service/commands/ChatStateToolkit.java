package spring.project.bot.service.commands;

public interface ChatStateToolkit {
    void update(Long chatId, spring.project.bot.model.ChatState chatState);

    void deleteAll(Long chatId);
}
