package spring.project.bot.service.converters;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import spring.project.bot.model.HiddenSymbol;
import spring.project.common.model.Point;
import spring.project.common.model.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ConverterCommandImpl implements ConverterCommand {

    @Override
    public String convertPointToString(Point point, int sizeColumn) {
        int i = point.getY();
        int j = point.getX();
        int code = i * sizeColumn + j;
        String bin = Integer.toBinaryString(code);
        bin = bin.replaceAll("1", String.valueOf(HiddenSymbol.ONE));
        bin = bin.replaceAll("0", String.valueOf(HiddenSymbol.ZERO));
        return bin;
    }

    @Override
    public Point convertStringCodeToPoint(String codeString, int sizeColumn) {
        codeString = codeString.replaceAll(String.valueOf(HiddenSymbol.ONE), "1");
        codeString = codeString.replaceAll(String.valueOf(HiddenSymbol.ZERO), "0");
        String binaryString = codeString;
        int code = Integer.parseInt(binaryString, 2);
        int j = code % sizeColumn;
        int i = (code - j) / sizeColumn;

        return new Point(j, i);
    }
}