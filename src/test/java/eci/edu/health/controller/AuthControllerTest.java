package eci.edu.health.controller;

import eci.edu.health.dto.AuthRequest;
import eci.edu.health.dto.AuthResponse;
import eci.edu.health.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock private AuthenticationManager authManager;
    @Mock private JwtUtil jwtUtil;

    @InjectMocks private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_WhenValidCredentials_ShouldReturnToken() {
        AuthRequest request = new AuthRequest();
        request.setEmail("test@mail.com");
        request.setPassword("1234");

        when(jwtUtil.generateToken("test@mail.com")).thenReturn("token123");

        ResponseEntity<AuthResponse> response = authController.login(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("token123", response.getBody().getToken());
        verify(authManager).authenticate(any());
        verify(jwtUtil).generateToken("test@mail.com");
    }

    @Test
    void login_WhenBadCredentials_ShouldThrowException() {
        AuthRequest request = new AuthRequest();
        request.setEmail("bad@mail.com");
        request.setPassword("wrong");

        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authManager).authenticate(any());

        assertThrows(BadCredentialsException.class, () -> authController.login(request));
        verify(jwtUtil, never()).generateToken(any());
    }
}
