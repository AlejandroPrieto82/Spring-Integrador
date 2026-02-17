package eci.edu.health.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HealthControllerTest {

    private final HealthController controller = new HealthController();

    @Test
    void checkAPI_ShouldReturnExpectedString() {
        String result = controller.checkAPI();
        assertEquals("<h1>The API is working ok!</h1>", result);
    }
}
