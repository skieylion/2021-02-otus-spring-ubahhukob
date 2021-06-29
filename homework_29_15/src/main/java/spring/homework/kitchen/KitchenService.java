package spring.homework.kitchen;

import org.springframework.stereotype.Service;
import spring.homework.domain.Food;
import spring.homework.domain.OrderItem;

@Service
public class KitchenService {

    public Food cook(OrderItem orderItem) throws Exception {
        System.out.println("Cooking " + orderItem.getItemName());
        Thread.sleep(3000);
        System.out.println("Cooking " + orderItem.getItemName() + " done");
        return new Food(orderItem.getItemName());
    }
}
