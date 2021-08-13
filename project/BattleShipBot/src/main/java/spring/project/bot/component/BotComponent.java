package spring.project.bot.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import gui.ava.html.image.generator.HtmlImageGenerator;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButtonPollType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import spring.project.bot.model.PlayerVO;
import spring.project.bot.model.Point;
import spring.project.bot.service.BotConverter;
import spring.project.bot.service.BotService;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JEditorPane;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class BotComponent extends TelegramLongPollingBot {

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

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${nameQueueInOut}")
    private String nameQueueInOut;

    @Autowired
    private BotConverter botConverter;

    private PlayerVO playerVO;





    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        //System.out.println(update.getMessage().isReply());
        if(update.hasCallbackQuery()) {
            String jsonPoint=update.getCallbackQuery().getData();
            ObjectMapper objectMapper=new ObjectMapper();
            Point point=objectMapper.readValue(jsonPoint,Point.class);
            System.out.println(point.getX());


            //стрельнули
            //очистили поля
            //записали поля
            //результат удара
            //если проигрыш/проигрыш, то сообщение и удаление данных
            //если пропустил то ход другого если попал то повторный ход

        } else {
            String message = update.getMessage().getText();
            System.out.println(message);
            if(message!=null&&(message.indexOf("\u200C",0)>-1||message.indexOf("\u200B",0)>-1)){
                System.out.println("COMMAND");
                DeleteMessage deleteMessage=new DeleteMessage();
                deleteMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                deleteMessage.setMessageId(update.getMessage().getMessageId());
                execute(deleteMessage);
            }


            //System.out.println(message.indexOf("\u200C"));
            if (message.equals("/start") || message.equals("/restart")) {
                MessageProperties messageProperties=new MessageProperties();
                messageProperties.setType("Start");
                Message message1=new Message("".getBytes(),messageProperties);
                Message response=amqpTemplate.sendAndReceive(nameQueueInOut,message1);
                assert response != null;
                String jsonResponse=new String(response.getBody(), StandardCharsets.UTF_8);
                ObjectMapper objectMapper=new ObjectMapper();
                playerVO=objectMapper.readValue(jsonResponse,PlayerVO.class);

                MessageProperties messageProperties2=new MessageProperties();
                messageProperties2.setType("Join");
                messageProperties2.setHeader("playerId",playerVO.getSessionId());
                Message message2=new Message("".getBytes(),messageProperties2);
                amqpTemplate.sendAndReceive(nameQueueInOut,message2);

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                sendMessage.setText("Hello. Give this key your partner");
                execute(sendMessage);
                sendMessage.setText(playerVO.getSessionEnemyId());
                ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
                List<KeyboardRow> table=new ArrayList<>();
                KeyboardRow keyboardRow=new KeyboardRow();
                KeyboardButton keyboardButton=new KeyboardButton("generate");
                keyboardRow.add(keyboardButton);
                table.add(keyboardRow);
                replyKeyboardMarkup.setKeyboard(table);
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                execute(sendMessage);
            } else if (message.equals("generate") || message.equals("new")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));

                MessageProperties messageProperties=new MessageProperties();
                messageProperties.setType("Generate");
                messageProperties.setHeader("playerId",playerVO.getSessionId());
                Message message1=new Message("".getBytes(),messageProperties);
                Message response=amqpTemplate.sendAndReceive(nameQueueInOut,message1);
                assert response != null;
                String jsonResponse=new String(response.getBody(), StandardCharsets.UTF_8);
                System.out.println(jsonResponse);
                ObjectMapper objectMapper=new ObjectMapper();
                playerVO=objectMapper.readValue(jsonResponse,PlayerVO.class);

                sendMessage.setText("it's your field");
                ReplyKeyboardMarkup replyKeyboardMarkup=botConverter.convert(playerVO.getField());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                execute(sendMessage);

                SendPhoto sendPhoto=new SendPhoto();
                InputFile inputFile=botConverter.convertToImage(playerVO.getField());
                sendPhoto.setPhoto(inputFile);
                sendPhoto.setChatId(String.valueOf(update.getMessage().getChatId()));
                execute(sendPhoto);

            } else if (message.equals("ok")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                sendMessage.setText("if you are ready then click the button 'go'");
                sendMessage.setReplyMarkup(botConverter.convert(null));
                execute(sendMessage);
            } else if (message.equals("go")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                sendMessage.setText("it's your turn");


                //sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                execute(sendMessage);
            }
        }



//
//
//        try {
//            SendMessage sendMessage=new SendMessage();
//            sendMessage.enableMarkdown(true);
//            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//            sendMessage.setReplyMarkup(replyKeyboardMarkup);
//            sendMessage.setText("<a href=\"http://www.example.com/\">inline URL</a>");
//            sendMessage.enableHtml(true);
//            execute(sendMessage);
//
//            BufferedImage bufferedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
//            Graphics2D g2d = bufferedImage.createGraphics();
//            g2d.setColor(Color.red);
//            g2d.fillRect(0, 0, 500, 500);
//            g2d.setColor(Color.blue);
//            g2d.fillOval(0, 0, 20, 20);
//            g2d.setColor(Color.yellow);
//            g2d.drawString("hello", 30, 30);
//            g2d.dispose();
//            File file = new File("myimage.png");
//            ImageIO.write(bufferedImage,"png",file);
//
//            InputFile inputFile=new InputFile();
//            inputFile.setMedia(new FileInputStream(file),"myimage.png");
//            SendPhoto sendPhoto=new SendPhoto();
//            sendPhoto.setPhoto(inputFile);
//            sendPhoto.setChatId(String.valueOf(update.getMessage().getChatId()));
//            execute(sendPhoto);
//        } catch (TelegramApiException | IOException e) {
//            e.printStackTrace();
//        }
        //execute(new SendMessage())
    }
}


//--
//                String html = "<h1>Hello, world.</h1>Etc. Etc.";
//                int width = 200, height = 100;
//                BufferedImage image = GraphicsEnvironment.getLocalGraphicsEnvironment()
//                        .getDefaultScreenDevice().getDefaultConfiguration()
//                        .createCompatibleImage(width, height);
//                Graphics graphics = image.createGraphics();
//                JEditorPane jep = new JEditorPane("text/html", html);
//                jep.setSize(width, height);
//                jep.print(graphics);
//                File file=new File("myimage.png");
//                ImageIO.write(image, "png",file);
//                InputFile inputFile=new InputFile();
//                inputFile.setMedia(new FileInputStream(file),"myimage.png");
//                SendPhoto sendPhoto=new SendPhoto();
//                sendPhoto.setPhoto(inputFile);
//                sendPhoto.setChatId(String.valueOf(update.getMessage().getChatId()));
//                execute(sendPhoto);
//InlineKeyboardMarkup inlineKeyboardMarkup = botConverter.converter(playerVO.getField());
//--
//sendMessage.setReplyMarkup(inlineKeyboardMarkup);