package spring.project.bot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.project.bot.model.Chat;
import spring.project.common.model.FireResponse;
import spring.project.common.model.Player;
import spring.project.common.model.Point;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class RabbitServiceImpl implements RabbitService {
    private final AmqpTemplate amqpTemplate;
    private final String nameQueueInOut;

    public RabbitServiceImpl(AmqpTemplate amqpTemplate, @Value("${nameQueueInOut}") String nameQueueInOut) {
        this.amqpTemplate = amqpTemplate;
        this.nameQueueInOut = nameQueueInOut;
    }

    @Override
    @SneakyThrows
    public Player startBattle() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setType("Start");
        Message message = new Message("".getBytes(), messageProperties);
        Message response = amqpTemplate.sendAndReceive(nameQueueInOut, message);
        String jsonResponse = new String(Objects.requireNonNull(response).getBody(), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonResponse, Player.class);
    }

    @Override
    @SneakyThrows
    public Player joinToBattle(String playerId) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setType("Join");
        messageProperties.setHeader("playerId", playerId);
        Message message = new Message("".getBytes(), messageProperties);
        Message response = amqpTemplate.sendAndReceive(nameQueueInOut, message);
        String jsonResponse = new String(Objects.requireNonNull(response).getBody(), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonResponse, Player.class);
    }

    @Override
    @SneakyThrows
    public Player generateField(String playerId) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setType("Generate");
        messageProperties.setHeader("playerId", playerId);
        Message message = new Message("".getBytes(), messageProperties);
        Message response = amqpTemplate.sendAndReceive(nameQueueInOut, message);
        String jsonResponse = new String(Objects.requireNonNull(response).getBody(), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonResponse, Player.class);
    }

    @Override
    @SneakyThrows
    public FireResponse fireOnShip(String playerId, Point point) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setType("Fire");
        messageProperties.setHeader("x", String.valueOf(point.getX()));
        messageProperties.setHeader("y", String.valueOf(point.getY()));
        messageProperties.setHeader("playerId", playerId);
        Message message = new Message("".getBytes(), messageProperties);
        Message response = amqpTemplate.sendAndReceive(nameQueueInOut, message);
        String jsonResponse = new String(Objects.requireNonNull(response).getBody(), StandardCharsets.UTF_8);
        return new ObjectMapper().readValue(jsonResponse, FireResponse.class);
    }

    @Override
    public void closeBattle(String playerId) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setType("Close");
        messageProperties.setHeader("playerId", playerId);
        Message message = new Message("".getBytes(), messageProperties);
        amqpTemplate.sendAndReceive(nameQueueInOut, message);
    }
}
