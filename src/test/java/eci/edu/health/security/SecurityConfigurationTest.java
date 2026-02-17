package eci.edu.health.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SecurityConfigurationTest {

    @Test
    void passwordEncoder_ShouldNotBeNull() {
        SecurityConfiguration config = new SecurityConfiguration(mock(JwtRequestFilter.class));
        BCryptPasswordEncoder encoder = config.passwordEncoder();
        assertNotNull(encoder, "Password encoder bean should not be null");
    }
}
