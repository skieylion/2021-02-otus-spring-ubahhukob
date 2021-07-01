package spring.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.homework.domain.WeatherInfo;
import spring.homework.domain.WeatherReport;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@SpringBootTest
public class GateReportTest {

    @Autowired
    private GateReport gateReport;

    @Test
    void testGateReport() throws Exception {
        Collection<WeatherInfo> items = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            items.add(new WeatherInfo(9f, Date.from(Instant.now().plusSeconds(i * 3600 * 24))));
        }
        WeatherReport weatherReport = gateReport.process(items);

        Assertions.assertSame(Float.compare(weatherReport.getAvgTemperature(),9f),0);
    }
}
