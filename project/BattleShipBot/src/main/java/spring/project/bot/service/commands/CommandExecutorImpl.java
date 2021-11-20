package spring.project.bot.service.commands;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import spring.project.bot.exception.EntityNotFoundException;
import spring.project.bot.model.Chat;
import spring.project.bot.model.ChatState;
import spring.project.bot.model.DataMessage;
import spring.project.bot.model.UserMessage;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.TelegramService;

@Service
@AllArgsConstructor
public class CommandExecutorImpl implements CommandExecutor {
    private final ChatPlayerRepository chatPlayerRepository;
    private final TelegramService telegramService;

    private ChatState getState(Long chatId) {
        try {
            Chat chat = chatPlayerRepository.findById(chatId).orElseThrow(EntityNotFoundException::new);
            return chat.getChatState();
        } catch (EntityNotFoundException exception){
            return ChatState.FREE;
        }
    }


    @Override
    public void execute(Command command, DataMessage data) {
        if (command.getStates().contains(getState(data.getChatId()))) {
            command.execute(data);
        } else {
            failed(data);
        }
    }

    @Override
    public void failed(DataMessage data) {
        Integer userId=data.getUserId();
        telegramService.sendTextMessage(userId,data.getChatId(), UserMessage.NO_SUPPORT);
    }
}
