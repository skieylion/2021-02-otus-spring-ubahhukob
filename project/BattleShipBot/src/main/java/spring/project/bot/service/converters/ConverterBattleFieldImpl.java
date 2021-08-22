package spring.project.bot.service.converters;

import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import spring.project.common.model.Point;
import spring.project.common.model.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ConverterBattleFieldImpl implements ConverterBattleField {

    private static final String nameRedSquareFile = "redsquare.png";
    private static final String namePointFile = "point.png";
    private static final String nameFullFile = "full.png";
    private static final String nameSquareFile = "square.png";

    private final ResourceLoader resourceLoader;
    private final ConverterCommand converterCommand;
    private final BufferedImage cellRed;
    private final BufferedImage cellPoint;
    private final BufferedImage cellShip;
    private final BufferedImage cellSquare;
    private final int cellWidth;
    private final int cellHeight;

    public ConverterBattleFieldImpl(ResourceLoader resourceLoader, ConverterCommand converterCommand) {
        this.resourceLoader = resourceLoader;
        this.converterCommand = converterCommand;
        cellRed = getImage(nameRedSquareFile);
        cellPoint = getImage(namePointFile);
        cellShip = getImage(nameFullFile);
        cellSquare = getImage(nameSquareFile);
        cellWidth = cellSquare.getWidth();
        cellHeight = cellSquare.getHeight();
    }


    @SneakyThrows
    private BufferedImage getImage(String name) {
        InputStream cellPicRed = Objects.requireNonNull(resourceLoader.getClassLoader()).getResourceAsStream(name);
        return ImageIO.read(Objects.requireNonNull(cellPicRed));
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
    public ReplyKeyboardMarkup convertToKeyboard(BattleField battleField) {

        int rows = battleField.getRows();
        int columns = battleField.getColumns();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> table = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            KeyboardRow keyboardRow = new KeyboardRow();
            for (int j = 0; j < columns; j++) {
                KeyboardButton keyboardButton = new KeyboardButton(battleField.getCell(i, j).toString() + converterCommand.convertPointToString(new Point(j, i), columns));
                keyboardRow.add(keyboardButton);
            }
            table.add(keyboardRow);
        }
        replyKeyboardMarkup.setKeyboard(table);

        return replyKeyboardMarkup;
    }

    private InputFile createTempFileByImage(BufferedImage bufferedImage) throws IOException {
        File file = File.createTempFile("field-", ".png");
        ImageIO.write(bufferedImage, "png", file);
        InputFile inputFile = new InputFile();
        inputFile.setMedia(new FileInputStream(file), file.getName());
        file.deleteOnExit();
        return inputFile;
    }

    private BufferedImage createImageByBattleField(BattleField battleField) {
        int width = cellWidth * battleField.getColumns();
        int height = cellHeight * battleField.getRows();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);

        for (int i = 0; i < battleField.getRows(); i++) {
            for (int j = 0; j < battleField.getColumns(); j++) {
                int coordX = j * cellWidth;
                int coordY = i * cellHeight;
                CellType cellType = battleField.getCell(i, j);
                BufferedImage img = cellSquare;
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
        return bufferedImage;
    }

    @Override
    @SneakyThrows
    public InputFile convertToImage(BattleField battleField) {
        BufferedImage bufferedImage = createImageByBattleField(battleField);
        return createTempFileByImage(bufferedImage);
    }
}