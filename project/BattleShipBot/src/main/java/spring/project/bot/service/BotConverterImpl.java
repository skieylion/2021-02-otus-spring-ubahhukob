package spring.project.bot.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import spring.project.bot.model.*;
import spring.project.common.model.*;
import spring.project.common.model.Point;

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
public class BotConverterImpl implements BotConverter {

    @Autowired
    ResourceLoader resourceLoader;


    @SneakyThrows
    private BufferedImage getImage(String name) {
        InputStream cellPicRed = Objects.requireNonNull(resourceLoader.getClassLoader()).getResourceAsStream(name);
        return ImageIO.read(Objects.requireNonNull(cellPicRed));
    }

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

    @Override
    public BattleField convertToBattleField(BattleFieldVO battleFieldVO) {
        BattleField battleField = new BattleField(battleFieldVO.getSizeRow(), battleFieldVO.getSizeColumn());
        List<BattleCell> cells = battleFieldVO.getCells();

        battleFieldVO.getCells().forEach(battleCell -> {
            battleField.setCell(battleCell.getPoint().getY(), battleCell.getPoint().getX(), battleCell.getCell());
        });
        return battleField;
    }

    @Override
    public ReplyKeyboardMarkup convert(BattleField battleField) {

        int rows = battleField.getRows();
        int columns = battleField.getColumns();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> table = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            KeyboardRow keyboardRow = new KeyboardRow();
            for (int j = 0; j < columns; j++) {
                KeyboardButton keyboardButton = new KeyboardButton(battleField.getCell(i, j).toString() + convertPointToString(new Point(j, i), columns));
                keyboardRow.add(keyboardButton);
            }
            table.add(keyboardRow);
        }
        replyKeyboardMarkup.setKeyboard(table);

        return replyKeyboardMarkup;
    }

    @Override
    @SneakyThrows
    public InputFile convertToImage(BattleField battleField) {
        BufferedImage cellRed = getImage("redsquare.png");
        BufferedImage cellPoint = getImage("point.png");
        BufferedImage cellShip = getImage("full.png");
        BufferedImage cell = getImage("square.png");
        int cellWidth = cell.getWidth();
        int cellHeight = cell.getHeight();
        int countCellWidth = battleField.getColumns();
        int countCellHeight = battleField.getRows();
        int width = cellWidth * countCellWidth;
        int height = cellHeight * countCellHeight;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);

        for (int i = 0; i < battleField.getRows(); i++) {
            for (int j = 0; j < battleField.getColumns(); j++) {
                int coordX = j * cellWidth;
                int coordY = i * cellHeight;
                CellType cellType = battleField.getCell(i, j);
                BufferedImage img = cell;
                if (CellType.FULL.equals(cellType)) {
                    img = cellShip;
                } else if (CellType.MISS.equals(cellType)) {
                    img = cellPoint;
                } else if (CellType.DAMAGE.equals(cellType)) {
                    img = cellRed;
                }
                graphics2D.drawImage(img, coordX, coordY, null);
            }
        }


        File file = File.createTempFile("field-", ".png");
        ImageIO.write(bufferedImage, "png", file);
        InputFile inputFile = new InputFile();
        inputFile.setMedia(new FileInputStream(file), file.getName());
        file.deleteOnExit();

        return inputFile;
    }
}