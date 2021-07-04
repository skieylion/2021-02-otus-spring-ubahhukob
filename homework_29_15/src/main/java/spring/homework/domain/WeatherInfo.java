package spring.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class WeatherInfo {
    private float temperature;
    private Date date;
}