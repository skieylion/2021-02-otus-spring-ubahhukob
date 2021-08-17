package spring.project.bot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import spring.project.bot.model.*;
import spring.project.common.model.*;
import spring.project.common.model.Point;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BotConverterImpl implements BotConverter{

    @Override
    public String convertPointToString(Point point, int sizeColumn){
        int i=point.getY();
        int j=point.getX();
        int code=i*sizeColumn+j;
        String bin = Integer.toBinaryString(code);
        bin=bin.replaceAll("1","\u200C");
        bin=bin.replaceAll("0","\u200B");
        return bin;
    }

    @Override
    public Point convertStringCodeToPoint(String codeString,int sizeColumn){
        codeString=codeString.replaceAll("\u200C","1");
        codeString=codeString.replaceAll("\u200B","0");
        String binaryString=codeString;
        int code=Integer.parseInt(binaryString,2);
        int j=code % sizeColumn;
        int i=(code-j)/sizeColumn;

        return new Point(j,i);
    }

    @Override
    public BattleFieldVO convertToBattleFieldVO(BattleField battleField) {
        BattleFieldVO battleFieldVO=new BattleFieldVO();
        battleFieldVO.setSizeRow(battleField.getRows());
        battleFieldVO.setSizeColumn(battleField.getColumns());
        List<BattleCell> cells=new ArrayList<>();
        for(int i=0;i<battleField.getRows();i++){
            for(int j=0;j<battleField.getColumns();j++){
                BattleCell battleCell=new BattleCell();
                CellType cellType=battleField.getCell(i,j);
                battleCell.setPoint(new Point(j,i));
                battleCell.setCell(cellType);
                cells.add(battleCell);
            }
        }
        battleFieldVO.setCells(cells);

        return battleFieldVO;
    }

    @Override
    public ReplyKeyboardMarkup convert(BattleField battleField) throws JsonProcessingException {

        int rows=battleField.getRows();
        int columns=battleField.getColumns();
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
        List<KeyboardRow> table=new ArrayList<>();

        for(int i=0;i<rows;i++){
            KeyboardRow keyboardRow=new KeyboardRow();
            for(int j=0;j<columns;j++){
                KeyboardButton keyboardButton=new KeyboardButton(battleField.getCell(i,j).toString()+convertPointToString(new Point(j,i),columns));
                keyboardRow.add(keyboardButton);
            }
            table.add(keyboardRow);
        }
        replyKeyboardMarkup.setKeyboard(table);

        return replyKeyboardMarkup;
    }

    @Override
    public InputFile convertToImage(BattleFieldVO battleFieldVO) throws IOException {
        BattleField battleField=new BattleField(battleFieldVO.getSizeRow(),battleFieldVO.getSizeColumn());
        battleFieldVO.getCells().forEach(battleCell->{
            battleField.setCell(battleCell.getPoint().getY(),battleCell.getPoint().getX(),battleCell.getCell());
        });
        StringBuilder html=new StringBuilder();
        html.append("<table >");
        for(int i=0;i<battleField.getRows();i++){
            html.append("<tr style='line-height: 5px;'>");
            for(int j=0;j<battleField.getColumns();j++){
                html.append("<td style='width:10px;height:10px; border: 1px solid black; background-color:white; color:black; text-align:center;'>");
                html.append(battleField.getCell(i,j));
                html.append("</td>");
            }
            html.append("</tr>");
        }
        html.append("</td>");

        HtmlImageGenerator htmlImageGenerator=new HtmlImageGenerator();
        htmlImageGenerator.loadHtml(html.toString());
        BufferedImage bufferedImage=htmlImageGenerator.getBufferedImage();

        String name=RandomStringUtils.random(10,"BCDFGHJLKMNPQRSTVWXZ123456789")+".png";
        File file = File.createTempFile("field-",".png");
        ImageIO.write(bufferedImage,"png",file);
        InputFile inputFile=new InputFile();
        inputFile.setMedia(new FileInputStream(file),file.getName());
        file.deleteOnExit();
        return inputFile;
    }
}