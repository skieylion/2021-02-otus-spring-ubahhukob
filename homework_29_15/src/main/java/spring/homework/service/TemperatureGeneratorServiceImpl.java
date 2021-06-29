package spring.homework.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Service;
import spring.homework.App;
import spring.homework.Cafe;
import spring.homework.domain.Food;
import spring.homework.domain.OrderItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
public class TemperatureGeneratorServiceImpl implements TemperatureGeneratorService {
    private static final String[] MENU = { "coffee", "tea", "smoothie", "whiskey", "beer", "cola", "water" };

    private static OrderItem generateOrderItem() {
        return new OrderItem( MENU[ RandomUtils.nextInt( 0, MENU.length ) ] );
    }

    private static Collection<OrderItem> generateOrderItems() {
        List<OrderItem> items = new ArrayList<>();
        for ( int i = 0; i < RandomUtils.nextInt( 1, 5 ); ++ i ) {
            items.add( generateOrderItem() );
        }
        return items;
    }

    @Override
    public void generate() {
        ForkJoinPool pool = ForkJoinPool.commonPool();

        while ( true ) {
            Thread.sleep( 7000 );

            pool.execute( () -> {
                Collection<OrderItem> items = generateOrderItems();
                System.out.println( "New orderItems: " +
                        items.stream().map( OrderItem::getItemName )
                                .collect( Collectors.joining( "," ) ) );
                Collection<Food> food = cafe.process( items );
                System.out.println( "Ready food: " + food.stream()
                        .map( Food::getName )
                        .collect( Collectors.joining( "," ) ) );
            } );
        }
    }
}
