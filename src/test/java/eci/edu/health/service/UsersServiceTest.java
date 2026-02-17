package eci.edu.health.service;

import eci.edu.health.model.User;
import eci.edu.health.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UsersService usersService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("1", "Juan", "Pérez", "juan@mail.com");
    }

    // ─── SAVE ────────────────────────────────────────────────

    @Test
    void save_ShouldReturnSavedUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = usersService.save(user);

        assertNotNull(result);
        assertEquals("Juan", result.getName());
        assertEquals("Pérez", result.getLastName());
        assertEquals("juan@mail.com", result.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void save_ShouldCallRepositoryOnce() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        usersService.save(user);

        verify(userRepository, times(1)).save(any(User.class));
    }

    // ─── FIND BY ID ──────────────────────────────────────────

    @Test
    void findById_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        Optional<User> result = usersService.findById("1");

        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getName());
    }

    @Test
    void findById_WhenUserDoesNotExist_ShouldReturnEmpty() {
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        Optional<User> result = usersService.findById("999");

        assertFalse(result.isPresent());
    }

    // ─── FIND ALL ────────────────────────────────────────────

    @Test
    void findAll_ShouldReturnAllUsers() {
        User user2 = new User("2", "María", "García", "maria@mail.com");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user, user2));

        List<User> result = usersService.findAll();

        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).getName());
        assertEquals("María", result.get(1).getName());
    }

    @Test
    void findAll_WhenNoUsers_ShouldReturnEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> result = usersService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ─── UPDATE ──────────────────────────────────────────────

    @Test
    void update_WhenUserExists_ShouldReturnUpdatedUser() {
        User updated = new User(null, "Juan Actualizado", "López", "juannuevo@mail.com");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = usersService.update("1", updated);

        assertNotNull(result);
        assertEquals("Juan Actualizado", result.getName());
        assertEquals("López", result.getLastName());
        assertEquals("juannuevo@mail.com", result.getEmail());
    }

    @Test
    void update_WhenUserDoesNotExist_ShouldReturnNull() {
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        User result = usersService.update("999", user);

        assertNull(result);
        verify(userRepository, never()).save(any(User.class));
    }

    // ─── DELETE ──────────────────────────────────────────────

    @Test
    void delete_WhenUserExists_ShouldCallDeleteById() {
        doNothing().when(userRepository).deleteById("1");

        usersService.delete("1");

        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    void delete_ShouldNeverCallSave() {
        doNothing().when(userRepository).deleteById(any());

        usersService.delete("1");

        verify(userRepository, never()).save(any());
    }
}