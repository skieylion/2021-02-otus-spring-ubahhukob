package spring.project.bot.service.commands;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import spring.project.bot.exception.EntityNotFoundException;
import spring.project.bot.model.*;
import spring.project.bot.repository.ChatForPartnerRepository;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.converters.ConverterBattleField;
import spring.project.bot.service.RabbitService;
import spring.project.bot.service.TelegramService;
import spring.project.bot.service.converters.ConverterCommand;
import spring.project.common.model.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceCommandFire implements Command {

    private final List<ChatState> states = Collections.singletonList(ChatState.PLAY);

    private final ChatPlayerRepository chatPlayerRepository;
    private final ConverterBattleField converterBattleField;
    private final RabbitService rabbitService;
    private final TelegramService telegramService;
    private final ChatForPartnerRepository chatForPartnerRepository;
    private final ChatStateToolkit chatStateToolkit;
    private final ConverterCommand converterCommand;


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

    private Point getPointByMessageText(int size,String messageText) throws EntityNotFoundException {
        String code = extractCommand(messageText);
        return converterCommand.convertStringCodeToPoint(code, size);
    }

    private boolean isWin(FireResult result){
        if(FireResult.WIN.equals(result)){
            return true;
        }
        return false;
    }
    private boolean isHitOrKilled(FireResult result){
        if(FireResult.HIT.equals(result) || FireResult.KILLED.equals(result)){
            return true;
        }
        return false;
    }
    private void actionHitOrKilled(FireResponse fireResponse,Params params){
        String answerToEnemy = FireResult.HIT.equals(fireResponse.getFireResult()) ? UserMessage.PARTNER_HIT : UserMessage.PARTNER_KILLED;
        String answerToMe = FireResult.HIT.equals(fireResponse.getFireResult()) ? UserMessage.YOU_HIT : UserMessage.YOU_KILLED;
        telegramService.sendTextMessageWithoutReplyKeyboardMarkup(params.enemyUserId,params.chatEnemyId, answerToEnemy);
        BattleField battleField = fireResponse.getEnemyField();
        telegramService.sendBattleField(params.currentUserId, params.chatCurrentId, answerToMe, battleField);
        telegramService.sendPhoto(params.enemyUserId, params.chatEnemyId, converterBattleField.convertToImage(battleField));
    }
    private void actionWin(Params params){
        telegramService.sendTextMessageWithoutReplyKeyboardMarkup(params.enemyUserId, params.chatEnemyId, UserMessage.GAME_OVER);
        telegramService.sendTextMessageWithoutReplyKeyboardMarkup(params.currentUserId, params.chatCurrentId, UserMessage.WINNER);
        chatStateToolkit.deleteAll(params.chatCurrentId);
        chatStateToolkit.deleteAll(params.chatEnemyId);
    }
    private void actionOther(BattleField battleField,Params params){
        chatStateToolkit.update(params.chatCurrentId, ChatState.WAIT);
        chatStateToolkit.update(params.chatEnemyId, ChatState.PLAY);
        telegramService.sendTextMessageWithoutReplyKeyboardMarkup(params.currentUserId, params.chatCurrentId, UserMessage.YOU_MISSED);
        telegramService.sendBattleField(params.enemyUserId, params.chatEnemyId, UserMessage.YOUR_TURN, convertEnemyField(battleField));
    }

    @Override
    @SneakyThrows
    public void execute(DataMessage data) {
        Params params=new Params(data);
        telegramService.deleteMessage(params.currentUserId,params.chatCurrentId, params.messageId);
        Point point=getPointByMessageText(params.sizeColumn, params.messageText);
        FireResponse fireResponse = rabbitService.fireOnShip(params.currentPlayerId, point);
        FireResult result = fireResponse.getFireResult();

        if (isHitOrKilled(result)) {
            actionHitOrKilled(fireResponse,params);
        } else if (isWin(result)) {
            actionWin(params);
        } else {
            actionOther(fireResponse.getField(),params);
        }
    }

    private final class Params {
        private final Long chatCurrentId;
        private final Long chatEnemyId;
        private final Integer currentUserId;
        private final Integer enemyUserId;
        private final Integer messageId;
        private final String messageText;
        private final int sizeColumn;
        private final String currentPlayerId;

        private Params(DataMessage data) throws EntityNotFoundException {
            this.chatCurrentId = data.getChatId();
            this.messageId = data.getMessageId();
            this.messageText = data.getMessageText();
            this.currentUserId=data.getUserId();
            Chat chat = chatPlayerRepository.findById(this.chatCurrentId).orElseThrow(EntityNotFoundException::new);
            this.sizeColumn=chat.getPlayer().getField().getSizeColumn();
            this.currentPlayerId=chat.getPlayer().getId();
            String enemyPlayerId=chat.getPlayer().getEnemyId();
            ChatForPartner chatEnemy = chatForPartnerRepository.findById(enemyPlayerId).orElseThrow(EntityNotFoundException::new);
            this.chatEnemyId=chatEnemy.getChatId();
            Chat chatEnemyForUser = chatPlayerRepository.findById(chatEnemyId).orElseThrow(EntityNotFoundException::new);
            this.enemyUserId=chatEnemyForUser.getUserId();
        }
    }
}
