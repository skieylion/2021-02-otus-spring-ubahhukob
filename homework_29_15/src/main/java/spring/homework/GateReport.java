package spring.homework;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import spring.homework.domain.Food;
import spring.homework.domain.OrderItem;
import spring.homework.domain.WeatherInfo;
import spring.homework.domain.WeatherReport;

import java.util.Collection;

@MessagingGateway
public interface GateReport {

    @Gateway(requestChannel = "weatherChannel", replyChannel = "reportChannel")
    WeatherReport process(Collection<WeatherInfo> weatherInfo);
}
