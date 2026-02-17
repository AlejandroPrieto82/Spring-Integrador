package eci.edu.health.service;

import eci.edu.health.model.User;
import eci.edu.health.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User update(String id, User user) {
        return userRepository.findById(id).map(existing -> {
            existing.setName(user.getName());
            existing.setLastName(user.getLastName());
            existing.setEmail(user.getEmail());
            return userRepository.save(existing);
        }).orElse(null);
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }
}