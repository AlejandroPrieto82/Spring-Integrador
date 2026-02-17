package eci.edu.health.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    void testGenerateAndExtractToken() {
        String token = jwtUtil.generateToken("test@mail.com");
        assertNotNull(token);
        String email = jwtUtil.extractUsername(token);
        assertEquals("test@mail.com", email);
    }

    @Test
    void testValidateToken_Valid() {
        String token = jwtUtil.generateToken("user@mail.com");
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void testValidateToken_Invalid() {
        assertFalse(jwtUtil.validateToken("invalid.token"));
    }
}
