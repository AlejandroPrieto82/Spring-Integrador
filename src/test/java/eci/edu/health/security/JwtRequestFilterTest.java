package eci.edu.health.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtRequestFilterTest {

    @Mock private JwtUtil jwtUtil;
    @Mock private CustomUserDetailsService userDetailsService;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain chain;

    private JwtRequestFilter filter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new JwtRequestFilter(jwtUtil, userDetailsService);
    }

    @Test
    void doFilterInternal_WithValidToken_ShouldSetAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token123");
        when(jwtUtil.extractUsername("token123")).thenReturn("user@mail.com");
        when(jwtUtil.validateToken("token123")).thenReturn(true);

        var userDetails = org.springframework.security.core.userdetails.User
                .withUsername("user@mail.com")
                .password("1234")
                .roles("USER")
                .build();

        when(userDetailsService.loadUserByUsername("user@mail.com")).thenReturn(userDetails);

        filter.doFilterInternal(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(request, response);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_WithNoToken_ShouldCallChain() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
