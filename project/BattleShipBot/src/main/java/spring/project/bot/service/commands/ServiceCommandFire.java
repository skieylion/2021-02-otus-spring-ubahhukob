package spring.project.bot.service.commands;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.exception.EntityNotFoundException;
import spring.project.bot.model.*;
import spring.project.bot.repository.ChatForPartnerRepository;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.BotConverter;
import spring.project.bot.service.RabbitService;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceCommandFire implements Command {

    private final List<ChatState> states = Collections.singletonList(ChatState.PLAY);

    private final ChatPlayerRepository chatPlayerRepository;
    private final BotConverter botConverter;
    private final RabbitService rabbitService;
    private final TelegramService telegramService;
    private final ChatForPartnerRepository chatForPartnerRepository;
    private final ChatStateToolkit chatStateToolkit;


    private BattleField convertEnemyField(BattleField battleField) {
        BattleField battleFieldEnemy = new BattleField(battleField.getRows(), battleField.getColumns());
        battleFieldEnemy.setField(battleField.getField());
        for (int i = 0; i < battleFieldEnemy.getRows(); i++) {
            for (int j = 0; j < battleFieldEnemy.getColumns(); j++) {
                CellType cellType = battleFieldEnemy.getCell(i, j);
                List<CellType> array = Arrays.asList(CellType.DAMAGE, CellType.VOID, CellType.MISS);
                if (!array.contains(cellType)) {
                    battleFieldEnemy.setCell(i, j, CellType.VOID);
                }
            }
        }
        return battleFieldEnemy;
    }

    private String extractCommand(String command) {
        return command.replaceAll("[^" + HiddenSymbol.ONE + HiddenSymbol.ZERO + "]", "");
    }

    @Override
    public List<ChatState> getStates() {
        return states;
    }

    @Override
    @SneakyThrows
    public void execute(DataMessage data) {
        Long chatId = data.getChatId();
        Integer messageId = data.getMessageId();
        String messageText = data.getMessageText();

        telegramService.deleteMessage(chatId, messageId);
        String code = extractCommand(messageText);
        Chat chat = chatPlayerRepository.findById(chatId).orElseThrow(EntityNotFoundException::new);
        Point point = botConverter.convertStringCodeToPoint(code, chat.getPlayer().getField().getSizeColumn());

        FireResponse fireResponse = rabbitService.fireOnShip(chat.getPlayer().getId(), point);
        FireResult result = fireResponse.getFireResult();

        ChatForPartner chatEnemy = chatForPartnerRepository.findById(chat.getPlayer().getEnemyId()).orElseThrow(EntityNotFoundException::new);
        if (FireResult.HIT.equals(result) || FireResult.KILLED.equals(result)) {
            String answerToEnemy = FireResult.HIT.equals(result) ? UserMessage.PARTNER_HIT : UserMessage.PARTNER_KILLED;
            String answerToMe = FireResult.HIT.equals(result) ? UserMessage.YOU_HIT : UserMessage.YOU_KILLED;
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatEnemy.getChatId(), answerToEnemy);
            BattleField battleField = fireResponse.getEnemyField();
            telegramService.sendBattleField(chatId, answerToMe, battleField);
            telegramService.sendPhoto(chatEnemy.getChatId(), botConverter.convertToImage(battleField));
        } else if (FireResult.WIN.equals(result)) {
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatEnemy.getChatId(), UserMessage.GAME_OVER);
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatId, UserMessage.WINNER);
            chatStateToolkit.deleteAll(chatId);
            chatStateToolkit.deleteAll(chatEnemy.getChatId());
        } else {
            chatStateToolkit.update(chatId, ChatState.WAIT);
            chatStateToolkit.update(chatEnemy.getChatId(), ChatState.PLAY);
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatId, UserMessage.YOU_MISSED);
            telegramService.sendBattleField(chatEnemy.getChatId(), UserMessage.YOUR_TURN, convertEnemyField(fireResponse.getField()));
        }
    }
}
