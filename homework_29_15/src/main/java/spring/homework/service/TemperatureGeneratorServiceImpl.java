package spring.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.homework.GateReport;
import spring.homework.domain.WeatherInfo;
import spring.homework.domain.WeatherReport;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TemperatureGeneratorServiceImpl implements TemperatureGeneratorService {
    private final GateReport gateReport;

    private static Collection<WeatherInfo> generateWeatherWeek(int offset) {
        List<WeatherInfo> items = new ArrayList<>();
        for ( int i = offset; i < 7+offset; ++ i ) {
            items.add(new WeatherInfo(8f,Date.from(Instant.now().plusSeconds(i*3600*24))));
        }
        return items;
    }

    @Override
    public void generate() throws InterruptedException {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        int offset=0;

        while ( true ) {
            Thread.sleep( 4000 );
            final int finalOffset = offset;
            pool.execute( () -> {
                System.out.println("start");
                Collection<WeatherInfo> items = generateWeatherWeek(finalOffset);
                WeatherReport weatherReport = gateReport.process( items );
                System.out.println("end");
            } );
            offset+=7;
        }
    }
}
