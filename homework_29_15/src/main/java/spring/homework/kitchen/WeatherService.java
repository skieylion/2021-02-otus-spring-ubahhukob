package spring.homework.kitchen;

import org.springframework.stereotype.Service;
import spring.homework.domain.Food;
import spring.homework.domain.OrderItem;
import spring.homework.domain.WeatherInfo;
import spring.homework.domain.WeatherReport;

import java.util.Date;
import java.util.List;

@Service
public class WeatherService {

    public WeatherReport handle(List<WeatherInfo> weatherInfo) throws Exception {
        Date dateStart=weatherInfo.get(0).getDate();
        Date dateEnd=weatherInfo.get(weatherInfo.size()-1).getDate();
        float avg=(float)weatherInfo.stream().mapToDouble(value -> value.getTemperature()).average().getAsDouble();

        return new WeatherReport(avg,dateStart,dateEnd);
    }
}
