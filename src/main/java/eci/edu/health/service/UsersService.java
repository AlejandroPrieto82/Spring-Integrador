package eci.edu.health.service;

import eci.edu.health.model.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    User save(User user);
    Optional<User> findById(String id);
    List<User> findAll();
    User update(String id, User user);
    void delete(String id);
}
