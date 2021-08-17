package spring.project.bot.service.states;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import spring.project.bot.model.Chat;
import spring.project.bot.model.ChatForPartner;
import spring.project.bot.model.ChatState;
import spring.project.bot.repository.ChatForPartnerRepository;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.service.BotConverter;
import spring.project.bot.service.RabbitService;
import spring.project.bot.service.TelegramService;
import spring.project.common.model.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatStateServicePlay extends ChatStateService {
    private final ChatState chatState = ChatState.PLAY;
    private final ChatPlayerRepository chatPlayerRepository;
    private final BotConverter botConverter;
    private final RabbitService rabbitService;
    private final TelegramService telegramService;
    private final ChatStateServiceFree chatStateServiceFree;
    private final ChatForPartnerRepository chatForPartnerRepository;
    private final ChatStateToolkit chatStateToolkit;


    private BattleField convertEnemyField(BattleField battleField) {
        BattleField battleFieldEnemy=new BattleField(battleField.getRows(),battleField.getColumns());
        battleFieldEnemy.setField(battleField.getField());
        for(int i=0;i<battleFieldEnemy.getRows();i++){
            for (int j=0;j<battleFieldEnemy.getColumns();j++){
                CellType cellType=battleFieldEnemy.getCell(i,j);
                List<CellType> array= Arrays.asList(CellType.DAMAGE,CellType.VOID,CellType.MISS);
                if(!array.contains(cellType)){
                    battleFieldEnemy.setCell(i,j,CellType.VOID);
                }
            }
        }
        return battleFieldEnemy;
    }

    private String extractCommand(String command){
        return command.replaceAll("[^\u200C\u200B]","");
    }

    @Override
    public ChatState getChatState() {
        return chatState;
    }

    @Override
    public void start(Long chatId) throws JsonProcessingException, TelegramApiException {
        chatStateToolkit.deleteAll(chatId);
        chatStateServiceFree.start(chatId);
    }

    @Override
    public void start(Long chatId, String partnerId) throws JsonProcessingException, TelegramApiException {
        chatStateToolkit.deleteAll(chatId);
        chatStateServiceFree.start(chatId,partnerId);
    }

    @Override
    public void fire(Long chatId,Integer messageId,String messageText) throws TelegramApiException, JsonProcessingException {

        telegramService.deleteMessage(chatId,messageId);
        String code=extractCommand(messageText);
        Chat chat=chatPlayerRepository.findById(chatId).orElseThrow();
        Point point=botConverter.convertStringCodeToPoint(code, chat.getPlayer().getField().getSizeColumn());
        FireResponse fireResponse=rabbitService.fire(chat.getPlayer().getId(),point);
        FireResult result=fireResponse.getFireResult();
        ChatForPartner chatEnemy = chatForPartnerRepository.findById(chat.getPlayer().getEnemyId()).orElseThrow();
        if(FireResult.HIT.equals(result)||FireResult.KILLED.equals(result)) {
            String answerToEnemy=FireResult.HIT.equals(result)?"Partner hit to your ship":"Partner killed your ship";
            String answerToMe=FireResult.HIT.equals(result)?"You hit to partner's ship":"You killed partner's ship";
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatEnemy.getChatId(),answerToEnemy);
            BattleField battleField=fireResponse.getEnemyField();
            telegramService.sendBattleField(chatId,answerToMe,battleField);
        } else if(FireResult.WIN.equals(result)){
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatEnemy.getChatId(),"Game over. Your partner is winner");
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatId,"That is great! You are winner");
            chatStateToolkit.deleteAll(chatId);
            chatStateToolkit.deleteAll(chatEnemy.getChatId());
        } else {
            chatStateToolkit.update(chatId,ChatState.WAIT);
            chatStateToolkit.update(chatEnemy.getChatId(),ChatState.PLAY);
            telegramService.sendTextMessageWithoutReplyKeyboardMarkup(chatId,"You have missed. Next a turn is for partner");
            telegramService.sendBattleField(chatEnemy.getChatId(),"It's your turn",convertEnemyField(fireResponse.getField()));
        }
    }

    @Override
    public void join(String playerId) {

    }

    @Override
    public void generate(Long chatId) {

    }

    @Override
    public void go(Long chatId) {

    }
}
