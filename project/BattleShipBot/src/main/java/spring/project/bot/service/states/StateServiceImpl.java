package spring.project.bot.service.states;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.bot.model.Chat;
import spring.project.bot.model.ChatState;
import spring.project.bot.repository.ChatPlayerRepository;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StateServiceImpl implements StateService{

    private final ChatPlayerRepository chatPlayerRepository;
    private final List<ChatStateService> chatStateServiceList;
    private final ChatStateService chatStateServicePrimary;

    @Override
    public ChatStateService getState(Long chatId) {
        Optional<Chat> chatOptional=chatPlayerRepository.findById(chatId);
        if(chatOptional.isPresent()) {
            Chat chat = chatOptional.get();
            ChatState chatState = chat.getChatState();
            for (var state : chatStateServiceList) {
                if (state.getChatState() == chatState) return state;
            }
        }
        return chatStateServicePrimary;
    }
}
