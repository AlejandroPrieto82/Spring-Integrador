package eci.edu.health.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {

    @Test
    void testAllArgsConstructorAndGetter() {
        AuthResponse response = new AuthResponse("token123");
        assertEquals("token123", response.getToken());
    }

    @Test
    void testSetterAndGetter() {
        AuthResponse response = new AuthResponse(null);
        response.setToken("token456");
        assertEquals("token456", response.getToken());
    }

    @Test
    void testToString() {
        AuthResponse response = new AuthResponse("token789");
        String str = response.toString();
        assertTrue(str.contains("token789"));
    }
}
