package spring.project.engine.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private final String nameQueueInOut;

    public RabbitConfig(@Value("${nameQueueInOut}") String nameQueueInOut) {
        this.nameQueueInOut = nameQueueInOut;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("rabbitmq");//
    }
    @Bean
    public RabbitAdmin rabbitAdmin(){
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate    (){
        return new RabbitTemplate(connectionFactory());
    }
    @Bean
    public Queue queueInOut(){
        return  new Queue(nameQueueInOut);
    }
}
