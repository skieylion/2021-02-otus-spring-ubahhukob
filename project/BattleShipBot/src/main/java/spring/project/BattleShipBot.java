package spring.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButtonPollType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BattleShipBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String name;
    @Value("${bot.token}")
    private String token;

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();

        List<KeyboardRow> table=new ArrayList<>();
        for(int i=0;i<10;i++){
            KeyboardRow keyboardRow=new KeyboardRow();

            for(int j=0;j<10;j++){
                KeyboardButton keyboardButton=new KeyboardButton("x");
                keyboardRow.add(keyboardButton);
            }
            table.add(keyboardRow);
        }
        replyKeyboardMarkup.setKeyboard(table);


        try {
            SendMessage sendMessage=new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setText("<a href=\"http://www.example.com/\">inline URL</a>");
            sendMessage.enableHtml(true);
            execute(sendMessage);

            BufferedImage bufferedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setColor(Color.red);
            g2d.fillRect(0, 0, 500, 500);
            g2d.setColor(Color.blue);
            g2d.fillOval(0, 0, 20, 20);
            g2d.setColor(Color.yellow);
            g2d.drawString("hello", 30, 30);
            g2d.dispose();
            File file = new File("myimage.png");
            ImageIO.write(bufferedImage,"png",file);

            InputFile inputFile=new InputFile();
            inputFile.setMedia(new FileInputStream(file),"myimage.png");
            SendPhoto sendPhoto=new SendPhoto();
            sendPhoto.setPhoto(inputFile);
            sendPhoto.setChatId(String.valueOf(update.getMessage().getChatId()));
            execute(sendPhoto);
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
        //execute(new SendMessage())
    }
}
