package com.example.connectify.api.services;
import com.example.connectify.api.models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("User with the same username or email already exists.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the user.", e);
        }
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving users.", e);
        }
    }


    public User getUserById(Long userId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                return userOptional.get();
            } else {
                throw new IllegalArgumentException("User with ID " + userId + " not found.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("An error occurred while retrieving the user with ID " + userId + ".", e);
        }
    }

    public User updateUser(Long userId, User updatedUser) {
        try {
            Optional<User> existingUserOptional = userRepository.findById(userId);
            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();

                existingUser.setUsername(updatedUser.getUsername());
                existingUser.setEmail(updatedUser.getEmail());

                return userRepository.save(existingUser);
            } else {
                throw new IllegalArgumentException("User with ID " + userId + " not found.");
            }
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("User with the same username or email already exists.");
        } catch (Exception e) {
            throw new IllegalArgumentException("User with ID " + userId + " not found.", e);
        }
    }

    public void deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the user with ID " + userId + ".", e);
        }
    }

    public User getUserByUsername(String username) {
        try {
            Optional<User> optionalUser = userRepository.getUserByUsername(username);
            return optionalUser.orElseThrow(() -> new IllegalArgumentException("User with username " + username + " not found."));
        } catch (Exception e) {
            throw new IllegalArgumentException("User with username " + username + " not found.", e);
        }
    }

    public User getUserByEmail(String email) {
        try {
            Optional<User> optionalUser = userRepository.getUserByEmail(email);
            return optionalUser.orElseThrow(() -> new IllegalArgumentException("User with email " + email + " not found."));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error retrieving user by email", e);
        }
    }


}
