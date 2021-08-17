package spring.project.engine.component;

//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.nio.charset.StandardCharsets;

@RestController
public class RabbitMqController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${nameQueueInOut}")
    private String nameQueueInOut;


    //тестовый метод, для проверки обработки сообщений
    @PostMapping("/emit/{typeMessage}")
    public ResponseEntity<String> emit(
            @PathVariable("typeMessage") String typeMessage,
            @PathParam("playerId") String playerId,
            @PathParam("x") String x,
            @PathParam("y") String y,
            @RequestBody String body
    ) {
        MessageProperties messageProperties=new MessageProperties();
        messageProperties.setType(typeMessage);
        messageProperties.setHeader("playerId",playerId);
        messageProperties.setHeader("x",x);
        messageProperties.setHeader("y",y);
        Message message=new Message(body.getBytes(),messageProperties);

        Message response = amqpTemplate.sendAndReceive(nameQueueInOut,message);

        assert response != null;
        String typeAnswer=response.getMessageProperties().getType();

        if(!typeAnswer.equals("Error")){
            return ResponseEntity.ok(new String(response.getBody(), StandardCharsets.UTF_8));
        } else {
            String stringBuilder = "Class: " +
                    response.getMessageProperties().getHeaders().get("Class") +
                    new String(response.getBody(), StandardCharsets.UTF_8);
            return ResponseEntity.badRequest().body(stringBuilder);
        }
    }
}
