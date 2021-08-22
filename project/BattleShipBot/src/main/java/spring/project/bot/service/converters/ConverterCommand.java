package spring.project.bot.service.converters;

import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import spring.project.common.model.BattleField;
import spring.project.common.model.BattleFieldVO;
import spring.project.common.model.Point;

public interface ConverterCommand {
    String convertPointToString(Point point, int sizeColumn);
    Point convertStringCodeToPoint(String codeString, int sizeColumn);
}
