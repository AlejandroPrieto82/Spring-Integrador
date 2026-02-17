package eci.edu.health.security;

import eci.edu.health.model.User;
import eci.edu.health.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock private UserRepository userRepository;
    @InjectMocks private CustomUserDetailsService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_WhenExists_ShouldReturnUserDetails() {
        User user = new User("1", "Juan", "Pérez", "juan@mail.com", "1234", "ROLE_USER");
        when(userRepository.findByEmail("juan@mail.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = service.loadUserByUsername("juan@mail.com");

        assertEquals("juan@mail.com", userDetails.getUsername());
        assertEquals("1234", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
    }

    @Test
    void loadUserByUsername_WhenNotExists_ShouldThrow() {
        when(userRepository.findByEmail("missing@mail.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("missing@mail.com"));
    }
}
