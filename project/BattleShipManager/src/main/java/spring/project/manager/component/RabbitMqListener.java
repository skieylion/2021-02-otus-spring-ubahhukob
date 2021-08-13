package spring.project.manager.component;

//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import spring.project.engine.exception.MessageTypeNotFoundException;
import spring.project.engine.model.BattleField;
import spring.project.engine.model.Fire;
import spring.project.engine.model.FireResult;
import spring.project.engine.model.Point;
import spring.project.engine.service.ShipShooter;
import spring.project.engine.service.ShipShooterImpl;
import spring.project.manager.exception.PlayerJoinException;
import spring.project.manager.model.FireResponse;
import spring.project.manager.model.Player;
import spring.project.manager.service.BattleFieldGenerator;
import spring.project.manager.service.BattleFieldGeneratorImpl;
import spring.project.manager.service.ServicePlayer;

import java.nio.charset.StandardCharsets;
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
                    return createMessageFromJson(mapper.writeValueAsString(servicePlayer.start()));
                case "Join":
                    servicePlayer.join(playerId);
                    return createMessageFromJson("ok");
                case "Generate":
                    return createMessageFromJson(mapper.writeValueAsString(battleFieldGenerator.generate10(playerId)));
                case "Go":
                    servicePlayer.go(playerId);
                    return createMessageFromJson("ok");
                case "Fire":
                    int x=Integer.parseInt(prop.getHeader("x"));
                    int y=Integer.parseInt(prop.getHeader("y"));
                    FireResponse fireResponse=servicePlayer.fire(playerId,x,y);
                    return createMessageFromJson(mapper.writeValueAsString(fireResponse));
                case "Close":
                    servicePlayer.close(playerId);
                    return createMessageFromJson("ok");
                default:
                    throw new MessageTypeNotFoundException();
            }
        } catch (Exception ex){
            return createErrorMessage(ex);
        }
    }
}
