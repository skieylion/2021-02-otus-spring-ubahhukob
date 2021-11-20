package spring.project.engine.component;

//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import spring.project.common.model.FireResponse;
import spring.project.engine.exception.MessageTypeNotFoundException;
import spring.project.engine.model.PlayerDB;
import spring.project.engine.service.ShipShooter;
import spring.project.engine.service.BattleFieldGenerator;
import spring.project.engine.service.ServicePlayer;
//import spring.project.common.model.Point;


@Component
@AllArgsConstructor
public class RabbitMqListener {
    private final BattleFieldGenerator battleFieldGenerator;
    private final ShipShooter shipShooter;
    private final ServicePlayer servicePlayer;


    private Message createMessageFromJson(String json){
        MessageProperties messageProperties=new MessageProperties();
        messageProperties.setType("Response");
        return new Message(json.getBytes(),messageProperties);
    }
    private Message createErrorMessage(Exception ex){
        MessageProperties messageProperties=new MessageProperties();
        messageProperties.setType("Error");

        messageProperties.setHeader("Class",ex.getClass().getName());
        return new Message(ex.toString().getBytes(),messageProperties);
    }


    @RabbitListener(queues = "${nameQueueInOut}")
    public Message worker(Message message) {
        try {
            MessageProperties prop=message.getMessageProperties();
            String typeMessage=prop.getType();
            //String body=new String(message.getBody(), StandardCharsets.UTF_8);
            String playerId=prop.getHeader("playerId");
            ObjectMapper mapper=new ObjectMapper();

            switch (typeMessage) {
                case "Start":
                    return createMessageFromJson(mapper.writeValueAsString(servicePlayer.startBattle()));
                case "Join":
                    return createMessageFromJson(mapper.writeValueAsString( servicePlayer.joinToBattle(playerId)));
                case "Generate":
                    PlayerDB playerDB=battleFieldGenerator.generate10(playerId);
                    String json=mapper.writeValueAsString(playerDB);
                    return createMessageFromJson(json);
                case "Go":
                    servicePlayer.goToBattle(playerId);
                    return createMessageFromJson("ok");
                case "Fire":
                    int x=Integer.parseInt(prop.getHeader("x"));
                    int y=Integer.parseInt(prop.getHeader("y"));
                    System.out.println(playerId);
                    FireResponse fireResponse=servicePlayer.fireToBattleField(playerId,x,y);
                    return createMessageFromJson(mapper.writeValueAsString(fireResponse));
                case "Close":
                    servicePlayer.closeBattle(playerId);
                    return createMessageFromJson("ok");
                default:
                    throw new MessageTypeNotFoundException();
            }
        } catch (Exception ex){
            return createErrorMessage(ex);
        }
    }
}
