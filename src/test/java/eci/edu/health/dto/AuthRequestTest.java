package eci.edu.health.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {

    @Test
    void testGettersAndSetters() {
        AuthRequest request = new AuthRequest();
        request.setEmail("test@mail.com");
        request.setPassword("1234");

        assertEquals("test@mail.com", request.getEmail());
        assertEquals("1234", request.getPassword());
    }

    @Test
    void testToString() {
        AuthRequest request = new AuthRequest();
        request.setEmail("user@mail.com");
        request.setPassword("abcd");
        String str = request.toString();
        assertTrue(str.contains("email=user@mail.com"));
        assertTrue(str.contains("password=abcd"));
    }
}
