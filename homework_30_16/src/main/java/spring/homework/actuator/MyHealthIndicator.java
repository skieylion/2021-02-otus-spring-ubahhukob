package spring.homework.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MyHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().status(Status.UP).withDetail("value",7).build();
    }
}