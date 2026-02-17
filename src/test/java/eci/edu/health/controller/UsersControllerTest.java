package eci.edu.health.controller;

import eci.edu.health.model.User;
import eci.edu.health.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eci.edu.health.security.JwtRequestFilter;
import eci.edu.health.security.CustomUserDetailsService;
import eci.edu.health.security.JwtUtil;

@WebMvcTest(UsersController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private UsersService usersService;
    @MockBean private JwtRequestFilter jwtRequestFilter;
    @MockBean private CustomUserDetailsService customUserDetailsService;
    @MockBean private JwtUtil jwtUtil;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("1", "Juan", "Pérez", "juan@mail.com", null, null);
    }

    // ─── POST ────────────────────────────────────────────────

    @Test
    void create_ShouldReturn201AndUser() throws Exception {
        when(usersService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Juan",
                            "lastName": "Pérez",
                            "email": "juan@mail.com"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@mail.com"));
    }

    // ─── GET ALL ─────────────────────────────────────────────

    @Test
    void getAll_ShouldReturn200AndList() throws Exception {
        User user2 = new User("2", "María", "García", "maria@mail.com", null, null);
        when(usersService.findAll()).thenReturn(Arrays.asList(user, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Juan"))
                .andExpect(jsonPath("$[1].name").value("María"));
    }

    @Test
    void getAll_WhenEmpty_ShouldReturn200AndEmptyList() throws Exception {
        when(usersService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // ─── GET BY ID ───────────────────────────────────────────

    @Test
    void getById_WhenExists_ShouldReturn200AndUser() throws Exception {
        when(usersService.findById("1")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@mail.com"));
    }

    @Test
    void getById_WhenNotExists_ShouldReturn404() throws Exception {
        when(usersService.findById("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/999"))
                .andExpect(status().isNotFound());
    }

    // ─── PUT ─────────────────────────────────────────────────

    @Test
    void update_WhenExists_ShouldReturn200AndUpdatedUser() throws Exception {
        User updated = new User("1", "Juan Actualizado", "López", "juannuevo@mail.com", null, null);
        when(usersService.update(eq("1"), any(User.class))).thenReturn(updated);

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Juan Actualizado",
                            "lastName": "López",
                            "email": "juannuevo@mail.com"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Actualizado"))
                .andExpect(jsonPath("$.email").value("juannuevo@mail.com"));
    }

    @Test
    void update_WhenNotExists_ShouldReturn404() throws Exception {
        when(usersService.update(eq("999"), any(User.class))).thenReturn(null);

        mockMvc.perform(put("/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "X",
                            "lastName": "Y",
                            "email": "x@mail.com"
                        }
                        """))
                .andExpect(status().isNotFound());
    }

    // ─── DELETE ──────────────────────────────────────────────

    @Test
    void delete_WhenExists_ShouldReturn200() throws Exception {
        when(usersService.findById("1")).thenReturn(Optional.of(user));
        doNothing().when(usersService).delete("1");

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_WhenNotExists_ShouldReturn404() throws Exception {
        when(usersService.findById("999")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/users/999"))
                .andExpect(status().isNotFound());
    }
}
