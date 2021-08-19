package spring.project.bot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import spring.project.common.model.BattleField;
import spring.project.common.model.BattleFieldVO;
import spring.project.common.model.Point;

import java.io.IOException;

public interface BotConverter {
    ReplyKeyboardMarkup convert(BattleField battleField);

    InputFile convertToImage(BattleField battleField);

    String convertPointToString(Point point, int sizeColumn);

    Point convertStringCodeToPoint(String codeString, int sizeColumn);

    BattleField convertToBattleField(BattleFieldVO battleFieldVO);
}
