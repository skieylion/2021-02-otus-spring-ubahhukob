package spring.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class WeatherReport {
    private float avgTemperature;
    private Date dateStart;
    private Date dateEnd;
}
