package spring.project.bot.service.states;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.bot.model.Chat;
import spring.project.bot.model.ChatForPartner;
import spring.project.bot.repository.ChatForPartnerRepository;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.RabbitService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatStateToolkitImpl implements ChatStateToolkit {
    private final ChatForPartnerRepository chatForPartnerRepository;
    private final RabbitService rabbitService;
    private final ChatPlayerRepository chatPlayerRepository;

    @Override
    public void update(Long chatId, spring.project.bot.model.ChatState chatState) {
        Chat chat = chatPlayerRepository.findById(chatId).orElseThrow();
        chat.setChatState(chatState);
        chatPlayerRepository.save(chat);
    }

    @Override
    public void deleteAll(Long chatId) {
        Chat chat = chatPlayerRepository.findById(chatId).orElseThrow();
        Optional<ChatForPartner> chatForPartnerOptional = chatForPartnerRepository.findById(chat.getPlayer().getId());
        chatForPartnerOptional.ifPresent(chatForPartner -> chatForPartnerRepository.deleteById(chatForPartner.getPartnerId()));
        chatPlayerRepository.deleteById(chat.getChatId());
        rabbitService.close(chat.getPlayer().getId());
    }
}
