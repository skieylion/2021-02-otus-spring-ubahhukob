package spring.project.bot.service.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import spring.project.common.model.BattleField;
import spring.project.common.model.BattleFieldVO;
import spring.project.common.model.Point;

import java.io.IOException;

public interface ConverterBattleField {
    ReplyKeyboardMarkup convertToKeyboard(BattleField battleField);
    InputFile convertToImage(BattleField battleField);
    BattleField convertToBattleField(BattleFieldVO battleFieldVO);
}
