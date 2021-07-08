package spring.homework.actuator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("actuator test")
@SpringBootTest
@AutoConfigureMockMvc
class ActuatorTest {

    @Autowired
    private MockMvc mvc;


    @DisplayName("health")
    @Test
    void health() throws Exception {
        mvc
        .perform(get("/actuator/health"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("{\"status\":\"UP\"}")));
    }
    @DisplayName("logfile")
    @Test
    void logfile() throws Exception {
        mvc
        .perform(get("/actuator/logfile"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("INFO")));
    }
    @DisplayName("healthchecks")
    @Test
    void healthchecks() throws Exception {
        mvc
        .perform(get("/actuator/health/db"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string(containsString("UP")));
    }

    @DisplayName("metrics")
    @Test
    void metrics() throws Exception {
        mvc
        .perform(get("/actuator/metrics/jvm.buffer.count"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string(containsString("An estimate of the number of buffers in the pool")));
    }

    @DisplayName("my indicator")
    @Test
    void my() throws Exception {
        mvc
        .perform(get("/actuator/health/my"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string(containsString("7")));
    }
}