package spring.project.bot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import spring.project.bot.model.BattleFieldVO;

import java.io.IOException;

public interface BotConverter {
    ReplyKeyboardMarkup convert(BattleFieldVO battleFieldVO) throws JsonProcessingException;
    InputFile convertToImage(BattleFieldVO battleFieldVO) throws IOException;
}
