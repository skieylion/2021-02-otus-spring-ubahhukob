package spring.project.bot.service.states;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.exception.EntityNotFoundException;
import spring.project.bot.model.Chat;
import spring.project.bot.model.ChatState;
import spring.project.bot.model.DataMessage;
import spring.project.bot.repository.ChatPlayerRepository;

@Service
@AllArgsConstructor
public class CommandExecutorImpl implements CommandExecutor {
    private final ChatPlayerRepository chatPlayerRepository;

    private ChatState getState(Long chatId) {
        try {
            Chat chat = chatPlayerRepository.findById(chatId).orElseThrow(EntityNotFoundException::new);
            return chat.getChatState();
        } catch (EntityNotFoundException exception){
            return ChatState.FREE;
        }
    }


    @Override
    @SneakyThrows
    public void execute(Command command, DataMessage data) {
        if (command.getStates().contains(getState(data.getChatId()))) {
            command.execute(data);
        } else {
            failed();
        }
    }

    @Override
    public void failed() {
        //команда не выполнима (сообщение)
    }
}
