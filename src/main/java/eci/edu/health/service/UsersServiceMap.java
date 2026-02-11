package eci.edu.health.service;

import eci.edu.health.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersServiceMap implements UsersService {

    private final Map<String, User> userMap = new HashMap<>();
    private int counter = 1;

    @Override
    public User save(User user) {
        String id = String.valueOf(counter++);
        user.setId(id);
        userMap.put(id, user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User update(String id, User user) {
        User existing = userMap.get(id);
        if (existing != null) {
            existing.setName(user.getName());
            existing.setLastName(user.getLastName());
            existing.setEmail(user.getEmail());
        }
        return existing;
    }

    @Override
    public void delete(String id) {
        userMap.remove(id);
    }
}
