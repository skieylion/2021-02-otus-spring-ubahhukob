package spring.homework;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import spring.homework.domain.Food;
import spring.homework.domain.OrderItem;

import java.util.Collection;

@MessagingGateway
public interface Cafe {

    @Gateway(requestChannel = "itemsChannel", replyChannel = "foodChannel")
    Collection<Food> process(Collection<OrderItem> orderItem);
}
