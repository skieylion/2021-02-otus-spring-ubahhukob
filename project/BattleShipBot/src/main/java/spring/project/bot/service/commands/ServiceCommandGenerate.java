package spring.project.bot.service.commands;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import spring.project.bot.model.*;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.converters.ConverterBattleField;
import spring.project.bot.service.RabbitService;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceCommandGenerate implements Command {

    private final List<ChatState> states = Collections.singletonList(ChatState.CONFIG);
    private final ChatPlayerRepository chatPlayerRepository;
    private final RabbitService rabbitService;
    private final TelegramService telegramService;
    private final ConverterBattleField converterBattleField;


    @Override
    public List<ChatState> getStates() {
        return states;
    }

    @Override
    public void execute(DataMessage data) {
        Long chatId=data.getChatId();
        Integer userId=data.getUserId();
        Chat chat = chatPlayerRepository.findById(chatId).orElseThrow();
        Player player = chat.getPlayer();
        player = rabbitService.generateField(player.getId());
        chat.setPlayer(player);
        chatPlayerRepository.save(chat);
        InputFile inputFile = converterBattleField.convertToImage(converterBattleField.convertToBattleField(player.getField()));
        telegramService.sendPhoto(userId,chatId, inputFile);
        telegramService.sendTextMessageWithKeyboardButtons(userId,chatId, UserMessage.YOUR_FIELD, Arrays.asList(UserCommand.GO, UserCommand.NEW));
    }
}
