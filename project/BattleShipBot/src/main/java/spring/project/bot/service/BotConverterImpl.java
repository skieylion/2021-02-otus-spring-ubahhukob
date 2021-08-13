package spring.project.bot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import spring.project.bot.model.BattleField;
import spring.project.bot.model.BattleFieldVO;
import spring.project.bot.model.CellType;
import spring.project.bot.model.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BotConverterImpl implements BotConverter{

    private String getCode(Point point,int sizeColumn){
        int i=point.getY();
        int j=point.getX();
        int code=i*sizeColumn+j;
        String bin = Integer.toBinaryString(code);
        bin=bin.replaceAll("1","\u200C");
        bin=bin.replaceAll("0","\u200B");
        return bin;
    }
    private Point getReverseCode(String codeString,int sizeColumn){
        codeString=codeString.replaceAll("\u200C","1");
        codeString=codeString.replaceAll("\u200B","0");
        String binaryString=codeString;
        int code=Integer.parseInt(binaryString,2);
        int j=code % sizeColumn;
        int i=(code-j)/sizeColumn;

        return new Point(j,i);
    }

    @Override
    public ReplyKeyboardMarkup convert(BattleFieldVO battleFieldVO) throws JsonProcessingException {

        int rows=battleFieldVO.getSizeRow();
        int columns=battleFieldVO.getSizeColumn();
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();

        List<KeyboardRow> table=new ArrayList<>();
        BattleField battleField=new BattleField(rows,columns);
        battleFieldVO.getCells().forEach(battleCell -> {
            CellType cellType=battleCell.getCell();
            Point point=battleCell.getPoint();
            battleField.setCell(point.getY(),point.getX(),cellType);
        });


        for(int i=0;i<rows;i++){
            KeyboardRow keyboardRow=new KeyboardRow();
            for(int j=0;j<columns;j++){
                KeyboardButton keyboardButton=new KeyboardButton(battleField.getCell(i,j).toString()+getCode(new Point(j,i),columns));
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
        html.append("<table>");
        for(int i=0;i<battleField.getRows();i++){
            html.append("<tr>");
            for(int j=0;j<battleField.getColumns();j++){
                html.append("<td>");
                html.append(battleField.getCell(i,j));
                html.append("</td>");
            }
            html.append("</tr>");
        }
        html.append("</table>");

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
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        List<KeyboardRow> table = new ArrayList<>();
//
//        for(int i=0;i<battleField.getRows();i++){
//            KeyboardRow keyboardRow = new KeyboardRow();
//            for(int j=0;j<battleField.getColumns();j++){
//                KeyboardButton button = new KeyboardButton(battleField.getCell(i,j).toString());
//                keyboardRow.add(button);
//            }
//            table.add(keyboardRow);
//        }
//        replyKeyboardMarkup.setKeyboard(table);