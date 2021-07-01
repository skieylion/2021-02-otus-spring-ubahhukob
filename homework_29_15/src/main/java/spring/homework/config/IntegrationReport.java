package spring.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.*;
import org.springframework.integration.scheduling.PollerMetadata;


@Configuration
@IntegrationComponentScan
public class IntegrationReport {
    @Bean
    public QueueChannel weatherChannel() {
        return MessageChannels.queue( 7 ).get();
    }

    @Bean
    public PublishSubscribeChannel reportChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate( 100 ).maxMessagesPerPoll( 2 ).get();
    }

    @Bean
    public IntegrationFlow weatherFlow() {
        return IntegrationFlows.from( "weatherChannel" )
                .log()
                .handle( "weatherService", "handle" )
                .log()
                .channel( "reportChannel" )
                .get();
    }
}
