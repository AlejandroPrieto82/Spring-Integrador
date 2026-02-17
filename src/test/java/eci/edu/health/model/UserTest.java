package eci.edu.health.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGettersAndSetters() {
        User user = new User();
        user.setId("1");
        user.setName("Juan");
        user.setLastName("Pérez");
        user.setEmail("juan@mail.com");
        user.setPassword("1234");
        user.setRole("ROLE_USER");

        assertEquals("1", user.getId());
        assertEquals("Juan", user.getName());
        assertEquals("Pérez", user.getLastName());
        assertEquals("juan@mail.com", user.getEmail());
        assertEquals("1234", user.getPassword());
        assertEquals("ROLE_USER", user.getRole());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User("1", "Juan", "Pérez", "juan@mail.com", "1234", "ROLE_USER");
        User user2 = new User("1", "Juan", "Pérez", "juan@mail.com", "1234", "ROLE_USER");
        User user3 = new User("2", "María", "García", "maria@mail.com", "4321", "ROLE_ADMIN");

        // equals
        assertEquals(user1, user2); // iguales
        assertNotEquals(user1, user3); // diferentes
        assertNotEquals(user1, null); // null check
        assertNotEquals(user1, "some string"); // diferente clase
        assertTrue(user1.canEqual(user2)); // canEqual cubierto
        assertFalse(user1.canEqual(null));

        // hashCode
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    void testToString() {
        User user = new User("1", "Juan", "Pérez", "juan@mail.com", "1234", "ROLE_USER");
        String str = user.toString();

        assertTrue(str.contains("Juan"));
        assertTrue(str.contains("Pérez"));
        assertTrue(str.contains("juan@mail.com"));
        assertTrue(str.contains("ROLE_USER"));
    }
}
